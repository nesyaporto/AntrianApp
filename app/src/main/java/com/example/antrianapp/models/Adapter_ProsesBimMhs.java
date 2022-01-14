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

public class Adapter_ProsesBimMhs extends RecyclerView.Adapter<Adapter_ProsesBimMhs.MyViewHolder> {


    Context context;
    ArrayList<Helper_AccBim> list;

    public Adapter_ProsesBimMhs(Context context, ArrayList<Helper_AccBim> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ITEM NYA DIIMPLEMENTASIKAN DARI XML
        View v = LayoutInflater.from(context).inflate(R.layout.item_antrian_mhs, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Helper_AccBim a = list.get(position);

        holder.namados.setText(a.getTo_dosen());
        holder.namamhs.setText(a.getNama_mhs());
        holder.tanggal.setText(a.getTanggal());
        holder.jenisbim.setText(a.getBimb());
        holder.status.setText(a.getStatus());
        String b = String.valueOf(a.getId_mhs());
        holder.nim.setText(b);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namados, namamhs, tanggal, waktu, jenisbim, status, nim;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namados = itemView.findViewById(R.id.item_namadosen);
            namamhs = itemView.findViewById(R.id.item_namamhs);
            tanggal = itemView.findViewById(R.id.item_tanggal);
            jenisbim = itemView.findViewById(R.id.item_jenisbimb);
            status = itemView.findViewById(R.id.item_status);
            nim = itemView.findViewById(R.id.item_nim);

        }
    }
}
