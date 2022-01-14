package com.example.antrianapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.antrianapp.R;
import java.util.ArrayList;

public class Adapter_DataUser extends RecyclerView.Adapter<Adapter_DataUser.MyViewHolder> {


    Context context;
    ArrayList<DataUser> list;

    public Adapter_DataUser(Context context, ArrayList<DataUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_dos,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DataUser a = list.get(position);

        holder.w.setText(a.getNama());
        holder.x.setText(a.getNomor());
        holder.y.setText(a.getNohp());
        holder.z.setText(a.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView w, x, y, z;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            w = itemView.findViewById(R.id.item_namadosen);
            x = itemView.findViewById(R.id.item_nip);
            y = itemView.findViewById(R.id.item_nohp);
            z = itemView.findViewById(R.id.item_email);

        }
    }
}
