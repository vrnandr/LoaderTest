package com.example.vrnandr.loadertest;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Andrey on 22.05.2018.
 */

public class myCursorLoader extends CursorLoader {
    private SQLiteDatabase database;
    private String tableName;
    private int id;
    private String[] args;

    public myCursorLoader(Context context,int id, String[] args) {
        super(context);

        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();

        this.id= id;
        this.args = args;
    }

    @Override
    public Cursor loadInBackground() {
        switch (id){
            case 0: return database.rawQuery("SELECT * FROM Works", null);
            case 1: return database.rawQuery("SELECT * FROM ServiceCatalog GROUP BY Service", null);
            case 2: return database.rawQuery("SELECT * FROM ServiceCatalog WHERE Service = '"+args[0]+"'", null);
            default: return null;
        }
    }
}