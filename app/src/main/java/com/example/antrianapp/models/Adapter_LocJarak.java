package com.example.antrianapp.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antrianapp.DetailsAntrian;
import com.example.antrianapp.DetailsBimDosen;
import com.example.antrianapp.DetailsSetWaktuBim;
import com.example.antrianapp.ItemClickListener;
import com.example.antrianapp.R;

import java.util.ArrayList;

public class Adapter_LocJarak extends RecyclerView.Adapter<Adapter_LocJarak.MyViewHolder> {


    Context context;
    ArrayList<Helper_LocJarak> list;

    String item1, item2, item3, item5, item6;
    Integer item4;

    public Adapter_LocJarak(Context context, ArrayList<Helper_LocJarak> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ITEM NYA DIIMPLEMENTASIKAN DARI XML
        View v = LayoutInflater.from(context).inflate(R.layout.item_kehadiran, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Helper_LocJarak a = list.get(position);

        holder.namamhs.setText(a.getNama());
        holder.nim.setText(""+a.getNomor());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                 item1 = list.get(position).getId();
                 item2 = list.get(position).getIddos();
                 item3 = list.get(position).getNamados();
                 item4 = list.get(position).getNomor(); //nomornya integer
                 item5 = list.get(position).getNama();
                 item6 = list.get(position).getTgl();

                Intent i = new Intent(context, DetailsSetWaktuBim.class);
                i.putExtra("id", item1);
                i.putExtra("iddos", item2);
                i.putExtra("namados", item3);
                i.putExtra("idmhs", item4);
                i.putExtra("namamhs", item5);
                i.putExtra("tgl", item6);
                context.startActivity(i);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView namamhs, nim;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namamhs = itemView.findViewById(R.id.item_namamhs);
            nim = itemView.findViewById(R.id.item_nim);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClickListener(view,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic){

            this.itemClickListener = ic;
        }
    }


}