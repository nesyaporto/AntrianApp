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
import com.example.antrianapp.LihatTanggal;
import com.example.antrianapp.R;
import java.util.ArrayList;


public class Adapter_Calender extends RecyclerView.Adapter<Adapter_Calender.MyViewHolder> {


    Context context;
    ArrayList<Helper_EventCalender> list;

    public Adapter_Calender(Context context, ArrayList<Helper_EventCalender> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ITEM NYA DIIMPLEMENTASIKAN DARI XML
        View v = LayoutInflater.from(context).inflate(R.layout.item_event,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Helper_EventCalender a = list.get(position);

        holder.namados.setText(a.getNamados());
        holder.info.setText(a.getInfo());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String item1 = list.get(position).getId();
                String item2 = list.get(position).getIddos();
                String item3 = list.get(position).getNamados();
                String item4 = list.get(position).getTglpilihan();
                String item5 = list.get(position).getInfo();


                Intent i = new Intent(context, LihatTanggal.class);
                i.putExtra("id", item1);
                i.putExtra("iddos", item2);
                i.putExtra("namados", item3);
                i.putExtra("tgl", item4);
                i.putExtra("info", item5);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView namados, info;

        ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namados = itemView.findViewById(R.id.item_namadosen);
            info = itemView.findViewById(R.id.item_info);

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
