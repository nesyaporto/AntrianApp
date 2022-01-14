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


public class Adapter_ListBim extends RecyclerView.Adapter<Adapter_ListBim.MyViewHolder> {


     Context context;
     ArrayList<Helper_ListBim> list;


    public Adapter_ListBim(Context context, ArrayList<Helper_ListBim> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //ITEM NYA DIIMPLEMENTASIKAN DARI XML
      View v = LayoutInflater.from(context).inflate(R.layout.item_req_bimb_mhs,parent,false);
      return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Helper_ListBim a = list.get(position);

        holder.namados.setText(a.getWaktu());
        holder.namamhs.setText(a.getTo_dosen());
        holder.tanggal.setText(a.getTanggal());
        holder.jenisbim.setText(a.getBimb());
        holder.status.setText(a.getStatus());
        holder.pesan.setText(a.getPesan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namados, tanggal, waktu, jenisbim, status, pesan, nim, namamhs;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namados = itemView.findViewById(R.id.item_namadosen);
            tanggal = itemView.findViewById(R.id.item_tanggal);
            namamhs = itemView.findViewById(R.id.item_namamhs);
            jenisbim = itemView.findViewById(R.id.item_jenisbimb);
            status = itemView.findViewById(R.id.item_status);
            pesan = itemView.findViewById(R.id.item_pesan);

        }

    }
}
