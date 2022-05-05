package com.example.aplikasipencatatpelanggaran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityDaftarKelas extends AppCompatActivity {

    //inisialisasi
    String kelas;
    ListView listView;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kelas);

        //mengisi variabel
        listView = findViewById(R.id.lisview);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.list);

        //inisialisasi string values untuk listview
        String[] values = new String[]{
                "X-1", "X-2", "X-3",
                "XI-1", "XI-2", "XI-3",
                "XII-1", "XII-2", "XII-3"
        };

        //memasukkan variabel array values ke dalam listview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);
        listView.setAdapter(adapter);

        //method untuk pindah ke intent layout_kelas dari listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                kelas = adapterView.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(), ActivityKelas.class);
                intent.putExtra("kelas", kelas);
                startActivity(intent);
            }
        });

        //untuk berpindah halaman ketika menekan bottomnavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
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
                            break;
                    }
                    return true;
                }
            };
}