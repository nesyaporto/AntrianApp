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

public class Adapter_Location extends RecyclerView.Adapter<Adapter_Location.MyViewHolder> {


    Context context;
    ArrayList<Helper_LokasiTerkini> list;

    public Adapter_Location(Context context, ArrayList<Helper_LokasiTerkini> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ITEM NYA DIIMPLEMENTASIKAN DARI XML
        View v = LayoutInflater.from(context).inflate(R.layout.item_lokasi, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Helper_LokasiTerkini a = list.get(position);

        holder.namamhs.setText(a.getNama());
        holder.tanggal.setText(a.getTgl());
        holder.jenisbim.setText(a.getJam());
        holder.posisi.setText(a.getStatus());
        holder.nim.setText(a.getNomor());
        holder.kehadiran.setText(a.getKehadiran());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namamhs, tanggal, jenisbim, posisi, nim, kehadiran;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namamhs = itemView.findViewById(R.id.item_namamhs);
            tanggal = itemView.findViewById(R.id.item_tanggal);
            jenisbim = itemView.findViewById(R.id.item_jenisbimb);
            posisi = itemView.findViewById(R.id.item_status);
            nim = itemView.findViewById(R.id.item_nim);
            kehadiran = itemView.findViewById(R.id.item_kehadiran);

        }
    }
}
