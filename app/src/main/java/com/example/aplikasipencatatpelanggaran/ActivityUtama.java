package com.example.aplikasipencatatpelanggaran;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityUtama extends AppCompatActivity {

//  inisialisasi
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> data_id, data_nama, data_kelas, data_poin, id;
    Integer nomor;
    CustomAdapter customAdapter;
    TextView totalPelanggaranOutput;
    ImageView imgempty;
    TextView txtkosong;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_utama);

//      mengisi variabel
        totalPelanggaranOutput = findViewById(R.id.totalPelanggaran);
        recyclerView = findViewById(R.id.recyclerView);
        imgempty = findViewById(R.id.imgempty);
        txtkosong = findViewById(R.id.txtkosong);
        bottomNavigationView = findViewById(R.id.bottomNav);

//      untuk berpindah halaman ketika menekan bottomnavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        myDB = new MyDatabaseHelper(ActivityUtama.this);

//      mengisi text totalPelanggaranOutput dengan menggunakan method jumlahpelanggaran()
        totalPelanggaranOutput.setText(String.format("%s", myDB.jumlahPelanggaran()));

        nomor = 1;
        id = new ArrayList<>();
        data_id = new ArrayList<>();
        data_nama = new ArrayList<>();
        data_kelas = new ArrayList<>();
        data_poin = new ArrayList<>();

//      Memasukkan data ke dalam array dan memindahkannya ke recyclerview
        masukanDataKeAray();
        customAdapter = new CustomAdapter(ActivityUtama.this, this,id,data_id, data_nama, data_kelas, data_poin);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityUtama.this));
    }

//  memperbarui tampilan jika ada perubahan di data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

//  method untuk memasukkan data dari database ke dalam array
    void masukanDataKeAray(){
        Cursor cursor = myDB.bacaData();
        if(cursor.getCount() == 0){
            imgempty.setVisibility(View.VISIBLE);
            txtkosong.setVisibility(View.VISIBLE);
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

//  method untuk bottomnavigation
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.main:
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

//  method untuk MenuInflater di atas
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_atas, menu);
        return super.onCreateOptionsMenu(menu);
    }

//  method untuk mendapatkan item yang dipilih dari MenuInflater di atas
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.hapus_semua){
            konfirmasi();
        }
        return super.onOptionsItemSelected(item);
    }

//  dialog pop up konfirmasi penghapusan data
    void konfirmasi(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Hapus data");
        builder.setMessage("Yakin ingin menghapus semua data ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ActivityUtama.this);
                myDB.hapusSemuaData();
                Toast.makeText(ActivityUtama.this,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ActivityUtama.this, ActivityUtama.class);
                startActivity(in);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}