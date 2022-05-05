package com.example.aplikasipencatatpelanggaran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityUpdate extends AppCompatActivity {

    EditText nama;
    Spinner spnKelas, spnPoin;
    Button update_button, delete_button;
    private BottomNavigationView bottomNavigationView;


    String id_data, nama_data, kelas_data, poin_data, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.tambah);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        nama = findViewById(R.id.nama2);
        spnKelas = (Spinner) findViewById(R.id.spnKelas2);
        spnPoin = (Spinner) findViewById(R.id.spnPoin2);
        spnPoin = findViewById(R.id.spnPoin2);
        update_button = findViewById(R.id.btnUpdate);
        delete_button = findViewById(R.id.btnDelete);

//        String kelasInput = mykelas_input.getSelectedItem().toString().trim();

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlah;
                int hasil = 0;
                String poin = spnPoin.getSelectedItem().toString().trim();
                MyDatabaseHelper myDBpoin = new MyDatabaseHelper(ActivityUpdate.this);
                hasil = MyDatabaseHelper.cekpoin(poin);
//                Log.d("poin di update", String.valueOf(hasil));

//                if(poin.equals("Pilih Jenis Pelanggaran")){
//                    Toast.makeText(ActivityUpdate.this,"Jenis pelanggaran belum dipilih", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                jumlah = hasil + Integer.parseInt(poin_data);
                MyDatabaseHelper myDB = new MyDatabaseHelper(ActivityUpdate.this);
                Log.d("test", String.valueOf(id));
                myDB.updateData(id, nama.getText().toString().trim(), spnKelas.getSelectedItem().toString().trim(), Integer.toString(jumlah));
                Toast.makeText(ActivityUpdate.this,"Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ActivityUpdate.this, ActivityUtama.class);
                startActivity(in);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konfirmasi();
            }
        });

//        getAndSetIntentData();


    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id_asli") && getIntent().hasExtra("id") && getIntent().hasExtra("nama") && getIntent().hasExtra("kelas") && getIntent().hasExtra("poin")){
            String[] arrayKelas = getResources().getStringArray(R.array.kelas);
            String container;
//            Integer test = arrayKelas.length;
            id = getIntent().getStringExtra("id_asli");
            id_data = getIntent().getStringExtra("id");
            nama_data = getIntent().getStringExtra("nama");
            kelas_data = getIntent().getStringExtra("kelas");
            poin_data = getIntent().getStringExtra("poin");

//            Log.d("jalan", String.valueOf(kelas_data));
//            Log.d("jalan", String.valueOf(arrayKelas[1]));
            for (int i=0; i<arrayKelas.length;i++){
//                Log.d("nama", String.valueOf(test));
                container = arrayKelas[i];
                if (arrayKelas[i].equals(kelas_data)){

                    spnKelas.setSelection(i);
                }
            }

            nama.setText(nama_data);

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }

    void konfirmasi(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Hapus data");
        builder.setMessage("Yakin ingin menghapus data " + nama_data + " ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ActivityUpdate.this);
//                Log.d("jalan", String.valueOf(id));
//                id_data = String.valueOf(id_data);
                myDB.deleteData(id);
                Toast.makeText(ActivityUpdate.this,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ActivityUpdate.this, ActivityUtama.class);
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

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//                    Fragment fragment = null;

                    switch (item.getItemId())
                    {
                        case R.id.main:
                            startActivity(new Intent(getApplicationContext(), ActivityUtama.class));
//                            fragment = new HomeFragment();
                            break;

                        case R.id.tambah:
                            startActivity(new Intent(getApplicationContext(), ActivityTambah.class));
//                            fragment = new AddFragment();
                            break;

                        case R.id.list:
                            startActivity(new Intent(getApplicationContext(), ActivityDaftarKelas.class));
//                            fragment = new ListFragment();
                            break;
                    }

//                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                    return true;

                }
            };
}