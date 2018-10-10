package com.rojo.travel.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DB_TEMP";

    private static final String TABLE_AKUN = "tbl_akun";

    private static final String KEY_ID = "id";
    private static final String KEY_GELAR = "akun_gelar";
    private static final String KEY_NAMA_DEPAN = "akun_nama_depan";
    private static final String KEY_NAMA_BELAKANG = "akun_nama_belakang";
    private static final String KEY_NAME = "akun_username";
    private static final String KEY_EMAIL = "akun_email";
    private static final String KEY_TELEPON = "akun_telepon";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AKUN_TABLE = "CREATE TABLE " + TABLE_AKUN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_GELAR + " TEXT," + KEY_NAMA_DEPAN + " TEXT," + KEY_NAMA_BELAKANG + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_TELEPON + " TEXT " + ")";
        db.execSQL(CREATE_AKUN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AKUN);

        onCreate(db);
    }

    public void save(temp_akun tempAkun){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID, 1);
        values.put(KEY_GELAR, tempAkun.getAkun_gelar());
        values.put(KEY_NAMA_DEPAN, tempAkun.getAkun_nama_depan());
        values.put(KEY_NAMA_BELAKANG, tempAkun.getAkun_nama_belakang());
        //values.put(KEY_NAME, tempAkun.getAkun_username());
        values.put(KEY_EMAIL, tempAkun.getAkun_email());
        values.put(KEY_TELEPON, tempAkun.getAkun_telepon());

        db.insert(TABLE_AKUN, null, values);
        db.close();
    }

    public temp_akun findOne(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_AKUN, new String[]{KEY_ID,KEY_GELAR,KEY_NAMA_DEPAN,KEY_NAMA_BELAKANG,KEY_NAME,KEY_EMAIL,KEY_TELEPON},
                KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }


        return new temp_akun(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
    }

    public List<temp_akun> findAll(){
        List<temp_akun> listAkun=new ArrayList<temp_akun>();
        String query="SELECT * FROM "+TABLE_AKUN;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                temp_akun Akun=new temp_akun();
                Akun.setId(Integer.valueOf(cursor.getString(0)));
                Akun.setAkun_username(cursor.getString(1));
                Akun.setAkun_email(cursor.getString(2));
                listAkun.add(Akun);
            }while(cursor.moveToNext());
        }

        return listAkun;
    }

    public void update(temp_akun tempAkun){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME, tempAkun.getAkun_username());
        values.put(KEY_EMAIL, tempAkun.getAkun_email());

        db.update(TABLE_AKUN, values, KEY_ID+"=?", new String[]{String.valueOf(tempAkun.getId())});
        db.close();
    }

    public void delete(temp_akun tempAkun){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_AKUN, null, null);
        db.close();
    }

//    public void truncate(temp_akun buku){
//        SQLiteDatabase db=this.getWritableDatabase();
//        db.delete(TABLE_AKUN, KEY_ID+"=?", new String[]{String.valueOf(buku.getId())});
//        db.close();
//    }

}
