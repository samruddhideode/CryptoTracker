package com.example.cryptotracker;

import static java.sql.Types.NULL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lowerLimitDB.db";
    private static final String TABLE = "lowerLimit";
    private static final String COL1 = "NAME";
    private static final String COL2 = "LL";

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " varchar(255)," + COL2 + " DOUBLE );";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
        onCreate(db);
    }

    public boolean insertData(String name, double llimit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, name);
        values.put(COL2, llimit);
        System.out.println(values);
        long row = db.insert(TABLE, null, values);
        if (row == -1)
            return false;
        else
            return true;
    }

    public long getLimit(String currencyName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ll = NULL;
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE +
                        " WHERE " + COL1 + "=" + "'" + currencyName + "'",null);
        ArrayList<String> arrayList = new ArrayList<>();
        while (res.moveToNext()) {
            ll = res.getLong(2);
        }
        if(res.getCount()<=0){
            return -1;
        }
        return ll;
    }

    public Cursor showAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " +TABLE+";",
                null);
        return res;
    }

    public boolean deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE,"name=?", new String[]{name});
        if(rows>0) return true;
        else return false;
    }
}