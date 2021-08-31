package com.example.george.controldeembarazadas.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Embarazadas.db";
    private static final String TABLE_NAME = "TABLA";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FOTO";
    private static final String COL_3 = "NOMBRE";
    private static final String COL_4 = "DIA_NACIMIENTO";
    private static final String COL_5 = "MES_NACIMIENTO";
    private static final String COL_6 = "ANNO_NACIMIENTO";
    private static final String COL_7 = "CALLE";
    private static final String COL_8 = "NUM_CASA";
    private static final String COL_9 = "TELEFONO";
    private static final String COL_10 = "CONCEPCION_DIA";
    private static final String COL_11 = "CONCEPCION_MES";
    private static final String COL_12 = "CONCEPCION_ANNO";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FOTO BLOB, NOMBRE TEXT,DIA_NACIMIENTO TEXT,MES_NACIMIENTO TEXT, ANNO_NACIMIENTO TEXT, CALLE TEXT,NUM_CASA TEXT,TELEFONO TEXT,CONCEPCION_DIA TEXT,CONCEPCION_MES TEXT,CONCEPCION_ANNO TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(byte[] foto, String nombre, String dia_nacimiento, String mes_nacimiento, String anno_nacimiento, String calle, String num_casa, String telefono, String menstruacion_dia, String menstruacion_mes, String menstruacion_anno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, foto);
        contentValues.put(COL_3, nombre);
        contentValues.put(COL_4, dia_nacimiento);
        contentValues.put(COL_5, mes_nacimiento);
        contentValues.put(COL_6, anno_nacimiento);
        contentValues.put(COL_7, calle);
        contentValues.put(COL_8, num_casa);
        contentValues.put(COL_9, telefono);
        contentValues.put(COL_10, menstruacion_dia);
        contentValues.put(COL_11, menstruacion_mes);
        contentValues.put(COL_12, menstruacion_anno);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == 1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;

    }

    public boolean updateData(String id, byte[] foto, String nombre, String dia_nacimiento, String mes_nacimiento, String anno_nacimiento, String calle, String num_casa, String telefono, String menstruacion_dia, String menstruacion_mes, String menstruacion_anno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, foto);
        contentValues.put(COL_3, nombre);
        contentValues.put(COL_4, dia_nacimiento);
        contentValues.put(COL_5, mes_nacimiento);
        contentValues.put(COL_6, anno_nacimiento);
        contentValues.put(COL_7, calle);
        contentValues.put(COL_8, num_casa);
        contentValues.put(COL_9, telefono);
        contentValues.put(COL_10, menstruacion_dia);
        contentValues.put(COL_11, menstruacion_mes);
        contentValues.put(COL_12, menstruacion_anno);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }


    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }
}
