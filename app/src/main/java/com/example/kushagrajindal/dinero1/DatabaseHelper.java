package com.example.kushagrajindal.dinero1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;

/**
 * Created by kushagrajindal on 20/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "money.db";
    public static final String TABLE_NAME = "money_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "CATEGORY";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "CnD";
    public static final String COL_5 = "DESCRIPTION";
    public static final String COL_6 = "CURRENT";
    public static final String COL_7 = "GST";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY TEXT,AMOUNT INTEGER,C&D TEXT,DESCRIPTION TEXT,CURRENT TEXT,GST INTEGER)");
         db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY TEXT,AMOUNT REAL,CnD TEXT,DESCRIPTION TEXT,CURRENT TEXT,GST REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String category,float amount,String cnd,String desc,float gst) {
        Calendar calobj = Calendar.getInstance();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,category);
        contentValues.put(COL_3,amount);
        contentValues.put(COL_4,cnd);
        contentValues.put(COL_5,desc);
        contentValues.put(COL_6,calobj.getTime().toString());
        contentValues.put(COL_7,gst);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
        return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
