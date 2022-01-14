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

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.MyViewHolder> {


    Context context;
    ArrayList<HelperRiwayat> list;

    public AdapterRiwayat(Context context, ArrayList<HelperRiwayat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ITEM NYA DIIMPLEMENTASIKAN DARI XML
        View v = LayoutInflater.from(context).inflate(R.layout.item_riwayat, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HelperRiwayat a = list.get(position);

        holder.namados.setText(a.getTo_dosen());
        holder.namamhs.setText(a.getNama_mhs());
        holder.tanggal.setText(a.getTanggal());
        holder.jenisbim.setText(a.getBimb());
        holder.status.setText(a.getStatus());
        holder.pesan.setText(a.getPesan());
        holder.nim.setText(a.getId_mhs());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namados, namamhs, tanggal, waktu, jenisbim, status, pesan, nim;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namados = itemView.findViewById(R.id.item_namadosen);
            namamhs = itemView.findViewById(R.id.item_namamhs);
            tanggal = itemView.findViewById(R.id.item_tanggal);
            jenisbim = itemView.findViewById(R.id.item_jenisbimb);
            status = itemView.findViewById(R.id.item_status);
            pesan = itemView.findViewById(R.id.item_pesan);
            nim = itemView.findViewById(R.id.item_nim);

        }
    }
}
