package com.example.antrianapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

import javax.sql.StatementEvent;

// ini class untuk proses session user

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_NOHP = "nohp";
    public static final String KEY_NOMOR = "nomor";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PRODI = "prodi";


    public SessionManager(Context _context) {
        context = _context;
        userSession = _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }


    public void createLoginSession(String email, String level, String nama, String nohp, String nomor, String password, String prodi) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_LEVEL, level);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_NOHP, nohp);
        editor.putString(KEY_NOMOR, nomor);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PRODI, prodi);

        editor.commit();
    }


    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_NAMA, userSession.getString(KEY_NAMA, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_LEVEL, userSession.getString(KEY_LEVEL, null));
        userData.put(KEY_NOHP, userSession.getString(KEY_NOHP, null));
        userData.put(KEY_NOMOR, userSession.getString(KEY_NOMOR, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_PRODI, userSession.getString(KEY_PRODI, null));

        return userData;
    }

    public boolean isLoggedIn() {
        return userSession.getBoolean(IS_LOGIN, false);
    }

    public void cekLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, LogIn.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);

        }
    }

    public void logout() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LogIn.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
       }

}