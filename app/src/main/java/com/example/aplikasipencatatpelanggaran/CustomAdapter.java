package com.example.aplikasipencatatpelanggaran;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    //inisialisasi
    private Context context;
    Activity activity;
    private ArrayList id, data_id, data_nama, data_kelas, data_poin;
    Animation translate_anim;
    int position;

    //constructor customadapter
    CustomAdapter(Activity activity, Context context, ArrayList id , ArrayList data_id, ArrayList data_nama, ArrayList data_kelas, ArrayList data_poin){

        //mengisi variabel
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.data_id = data_id;
        this.data_nama = data_nama;
        this.data_kelas = data_kelas;
        this.data_poin = data_poin;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    //method untuk menegeset isi text dan jika recyclerview ditekan
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.data_id_txt.setText(String.valueOf(data_id.get(position)));
        holder.data_nama_txt.setText(String.valueOf(data_nama.get(position)));
        holder.data_kelas_txt.setText(String.valueOf(data_kelas.get(position)));
        holder.data_poin_txt.setText(String.valueOf(data_poin.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityUpdate.class);
                intent.putExtra("id_asli", String.valueOf(id.get(position)));
                intent.putExtra("id", String.valueOf(data_id.get(position)));
                intent.putExtra("nama", String.valueOf(data_nama.get(position)));
                intent.putExtra("kelas", String.valueOf(data_kelas.get(position)));
                intent.putExtra("poin", String.valueOf(data_poin.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_id.size();
    }

    //untuk menampilkan recyclerview dengan isi data dari database
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView data_id_txt, data_nama_txt, data_kelas_txt, data_poin_txt;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data_id_txt = itemView.findViewById(R.id.data_id_txt);
            data_nama_txt = itemView.findViewById(R.id.data_nama_txt);
            data_kelas_txt = itemView.findViewById(R.id.data_kelas_txt);
            data_poin_txt = itemView.findViewById(R.id.data_poin_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
