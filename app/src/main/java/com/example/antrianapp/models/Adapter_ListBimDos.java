package com.example.antrianapp.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antrianapp.DetailsBimDosen;
import com.example.antrianapp.ItemClickListener;
import com.example.antrianapp.R;

import java.util.ArrayList;

public class Adapter_ListBimDos extends RecyclerView.Adapter<Adapter_ListBimDos.MyViewHolder> {


    Context context;

    ArrayList<Helper_ListBim> list;

    public Adapter_ListBimDos(Context context, ArrayList<Helper_ListBim> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_req_bimb_dosen,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Helper_ListBim helper_listBim = list.get(position);

        holder.namamhs.setText(helper_listBim.getNama_mhs());
        holder.tanggal.setText(helper_listBim.getTanggal());
        holder.waktu.setText(helper_listBim.getWaktu());
        holder.jenisbim.setText(helper_listBim.getBimb());
        holder.status.setText(helper_listBim.getStatus());
        holder.nim.setText(helper_listBim.getId_mhs());
        holder.prodi.setText(helper_listBim.getProdi());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String item = list.get(position).getId_bim();
                String item1 = list.get(position).getNama_mhs();
                String item2 = list.get(position).getTanggal();
                String item3 = list.get(position).getWaktu();
                String item4 = list.get(position).getBimb();
                String item5 = list.get(position).getStatus();
                String item6 = list.get(position).getId_dos();
                String item7 = list.get(position).getTo_dosen();
                String item8 = list.get(position).getId_mhs();
                String item9 = list.get(position).getPesan();
                String item10 = list.get(position).getProdi();

                Intent i = new Intent(context, DetailsBimDosen.class);
                i.putExtra("nama_mhs", item1); //"nama field di db", nama item
                i.putExtra("tanggal", item2);
                i.putExtra("waktu", item3);
                i.putExtra("bimb", item4);
                i.putExtra("status", item5);
                i.putExtra("id_bim",item);
                i.putExtra("id_dos",item6);
                i.putExtra("to_dosen",item7);
                i.putExtra("id_mhs",item8);
                i.putExtra("pesan",item9);
                i.putExtra("prodi",item10);


                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView namamhs, tanggal, waktu, jenisbim,status, pesan, nim, prodi;
        ItemClickListener itemClickListener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namamhs = itemView.findViewById(R.id.item_namamhs);
            tanggal = itemView.findViewById(R.id.item_tanggal);
            waktu = itemView.findViewById(R.id.item_namadosen);
            jenisbim = itemView.findViewById(R.id.item_jenisbimb);
            status = itemView.findViewById(R.id.item_status);
            nim = itemView.findViewById(R.id.item_nim);
            prodi = itemView.findViewById(R.id.item_prodi);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }
    }

}

