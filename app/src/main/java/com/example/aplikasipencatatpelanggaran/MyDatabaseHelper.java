package com.example.aplikasipencatatpelanggaran;

import com.example.aplikasipencatatpelanggaran.ActivityKelas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

//  inisialisasi semua string yang diperlukan

    private Context context;
    private String nama_kelas;

    public static final String DATABASE_NAME = "DataPelanggaran.db";
    public static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "data_pelanggaran";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama_siswa";
    private static final String COLUMN_KELAS = "kelas_siswa";
    private static final String COLUMN_POIN = "poin";

    private static final String NAMA="nama_siswa";
    private static final String KELAS = "kelas_siswa";
    private static final String POIN = "poin";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//  Untuk membuat database
    @Override
    public void onCreate(SQLiteDatabase db) {
            String query = String.format("CREATE TABLE %s(%s integer primary key autoincrement, %s TEXT, %s TEXT, %s INTEGER);", TABLE_NAME, COLUMN_ID, COLUMN_NAMA, COLUMN_KELAS, COLUMN_POIN);
            db.execSQL(query);
    }

//  Untuk mengupgrade database jika ada perubahan di schema databasenya
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            String query = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
            db.execSQL(query);
            onCreate(db);
    }

//  Untuk menambahkan data ke dalam database
    void tambahData(String nama, String kelas, int poin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMA, nama);
        cv.put(COLUMN_KELAS, kelas);
        cv.put(COLUMN_POIN, poin);
        db.insert(TABLE_NAME, null, cv);

    }

//  Untuk membaca semua data yang ada di database
    Cursor bacaData(){
        String query = String.format("SELECT * FROM %s ORDER BY %s DESC", TABLE_NAME, COLUMN_POIN);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

//  Untuk membaca data spesifik di dalam database (Diperlukan di layout kelas)
    Cursor bacaDataSpesifik(){
        String query = String.format("SELECT * FROM data_pelanggaran WHERE kelas_siswa = '%s' ORDER BY poin DESC", nama_kelas);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

//  setter variabel nama_kelas yang dilempar ke class ActivityKelas
    public void setNamaKelas(String namakelaslempar){
        nama_kelas = namakelaslempar;
    }

//  Method untuk mengecek nilai poin
    public static int cekpoin(String poin){
        int hasil = 0;
//        Log.d("poin di mydatabase", String.valueOf(poin));
        if(poin.equals("Merokok"))
            hasil = 80;

        if(poin.equals("Bolos"))
            hasil = 10;

        if(poin.equals("Membuat Kekacauan"))
            hasil = 20;

        if(poin.equals("Merusak Fasilitas"))
            hasil = 40;

        if(poin.equals("Tawuran"))
            hasil = 100;

        if(poin.equals("Pilih Jenis Pelanggaran"))
            hasil = 0;

//        Log.d("isi", String.valueOf(hasil));
        return hasil;
    }

//  Untuk mendapatkan total poin pelanggaran yang akan ditampilkan di layout_utama
    public String jumlahPelanggaran(){
        SQLiteDatabase db = this.getReadableDatabase();
        String jumlah;
        String query = String.format("SELECT SUM(%s) FROM %s", COLUMN_POIN, TABLE_NAME);
//        String query = "SELECT SUM(poin) FROM data_pelanggaran";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            jumlah = String.valueOf(cursor.getInt(0));
        }
        else{
            jumlah = "0";
        }
        cursor.close();
        db.close();
        return jumlah;
    }

//  untuk mengupdate data di database
    void updateData(String row_id ,String nama, String kelas, String poin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMA, nama);
        cv.put(COLUMN_KELAS, kelas);
        cv.put(COLUMN_POIN, poin);
        db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
    }

//  untuk menghapus data di database
    void deleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{row_id});
    }

//  untuk menghapus semua data yang ada di database
    void hapusSemuaData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
