package com.example.antrianapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.antrianapp.ItemClickListener;
import com.example.antrianapp.R;
import java.util.ArrayList;


public class Adapter_ReqBim extends RecyclerView.Adapter<Adapter_ReqBim.MyViewHolder> {


    Context context;
    ArrayList<Helper_ReqBim> list;


    public Adapter_ReqBim(Context context, ArrayList<Helper_ReqBim> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ITEM NYA DIIMPLEMENTASIKAN DARI XML
        View v = LayoutInflater.from(context).inflate(R.layout.item_antrian_mhs,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Helper_ReqBim a = list.get(position);

        holder.namados.setText(a.getTo_dosen());
        holder.namamhs.setText(a.getNama_mhs());
        holder.tanggal.setText(a.getTanggal());
        holder.jenisbim.setText(a.getBimb());
        holder.status.setText(a.getStatus());
        holder.nim.setText(a.getId_mhs());
        holder.pesan.setText(a.getPesan());
        holder.prodi.setText(a.getProdi());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namados, tanggal, namamhs, jenisbim, status, pesan, nim,prodi;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namados = itemView.findViewById(R.id.item_namadosen);
            namamhs = itemView.findViewById(R.id.item_namamhs);
            tanggal = itemView.findViewById(R.id.item_tanggal);
            jenisbim = itemView.findViewById(R.id.item_jenisbimb);
            status = itemView.findViewById(R.id.item_status);
            nim = itemView.findViewById(R.id.item_nim);
            pesan = itemView.findViewById(R.id.item_pesan);
            prodi = itemView.findViewById(R.id.item_prodi);

        }

    }

}
