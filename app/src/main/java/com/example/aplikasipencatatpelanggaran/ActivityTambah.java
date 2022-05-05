package com.example.aplikasipencatatpelanggaran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityTambah extends AppCompatActivity {

//  inisialisasi
    String kelasInput;
    EditText nama_input;
    Spinner kelas_input, poin_input;
    Button add_button;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        //mengisi variabel
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.tambah);
        nama_input = findViewById(R.id.nama);
        poin_input = findViewById(R.id.spnPoin);
        kelas_input = findViewById(R.id.spnKelas);
        add_button = findViewById(R.id.btnTambah);

        //untuk berpindah halaman ketika menekan bottomnavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }

//  method untuk menambah data ke dalam database
    public void tambahData(View view){

        //mendapatkan inputan dari user
        Spinner mykelas_input = (Spinner) findViewById(R.id.spnKelas);
        kelasInput = mykelas_input.getSelectedItem().toString().trim();
        int hasil = 0;
        Spinner mypoin_input = (Spinner) findViewById(R.id.spnPoin);
        String poinInput = mypoin_input.getSelectedItem().toString().trim();

        //validasi input (tidak menerima input yang kosong)
        if(nama_input.getText().length() == 0){
            Toast.makeText(ActivityTambah.this,"Nama masih Kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if(kelasInput.equals("Pilih Kelas")){
            Toast.makeText(ActivityTambah.this,"Kelas belum dipilih", Toast.LENGTH_SHORT).show();
            return;
        }
        if(poinInput.equals("Pilih Jenis Pelanggaran")){
            Toast.makeText(ActivityTambah.this,"Jenis pelanggaran belum dipilih", Toast.LENGTH_SHORT).show();
            return;
        }

        //memasukkan input dari user ke dalam database
        hasil = MyDatabaseHelper.cekpoin(poinInput);
        MyDatabaseHelper myDB = new MyDatabaseHelper(ActivityTambah.this);
        myDB.tambahData(nama_input.getText().toString().trim(),
                        kelasInput,
                        hasil);

        Toast.makeText(ActivityTambah.this,"Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(this, ActivityUtama.class);
        startActivity(in);
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
                            break;

                        case R.id.list:
                            startActivity(new Intent(getApplicationContext(), ActivityDaftarKelas.class));
                            break;
                    }
                    return true;
                }
            };
}