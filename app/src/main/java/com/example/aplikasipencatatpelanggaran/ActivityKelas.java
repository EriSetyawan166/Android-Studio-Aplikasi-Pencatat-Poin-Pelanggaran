package com.example.aplikasipencatatpelanggaran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ActivityKelas extends AppCompatActivity {

    //inisialisasi
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> id, data_id, data_nama, data_kelas, data_poin;
    Integer nomor;
    CustomAdapter customAdapter;
    ImageView imgempty;
    TextView txtempty, txtView;
    private BottomNavigationView bottomNavigationView;
    public String kelas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);

        myDB = new MyDatabaseHelper(ActivityKelas.this);

        //mengisi variabel
        recyclerView = findViewById(R.id.recyclerView);
        imgempty = findViewById(R.id.imgempty2);
        txtempty = findViewById(R.id.txtkosong2);
        kelas = getIntent().getExtras().get("kelas").toString();
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.list);

        //mengisi varibel txtView dengan variabel kelas
        myDB.setNamaKelas(kelas);
        txtView = findViewById(R.id.txtkelas);
        txtView.setText("Kelas "+kelas);

        nomor = 1;
        id = new ArrayList<>();
        data_id = new ArrayList<>();
        data_nama = new ArrayList<>();
        data_kelas = new ArrayList<>();
        data_poin = new ArrayList<>();

        //Memasukkan data ke dalam array dan memindahkannya ke recyclerview
        masukanDataKeAray();
        customAdapter = new CustomAdapter(ActivityKelas.this, this,  id, data_id, data_nama, data_kelas, data_poin);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityKelas.this));

        //untuk berpindah halaman ketika menekan bottomnavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }

    //method untuk memasukkan data dari database ke dalam array
    void masukanDataKeAray(){
        Cursor cursor = myDB.bacaDataSpesifik();
        if(cursor.getCount() == 0){
            imgempty.setVisibility(View.VISIBLE);
            txtempty.setVisibility(View.VISIBLE);
        } else{
            while(cursor.moveToNext()){
                data_id.add(nomor.toString());
                id.add(cursor.getString(0));
                data_nama.add(cursor.getString(1));
                data_kelas.add(cursor.getString(2));
                data_poin.add(cursor.getString(3));
                nomor++;
            }
        }
    }

    //method untuk bottomnavigation
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.main:
                            startActivity(new Intent(getApplicationContext(), ActivityUtama.class));
                            break;

                        case R.id.tambah:
                            startActivity(new Intent(getApplicationContext(), ActivityTambah.class));
                            break;

                        case R.id.list:
                            startActivity(new Intent(getApplicationContext(), ActivityDaftarKelas.class));
                            break;
                    }
                    return true;
                }
            };
}