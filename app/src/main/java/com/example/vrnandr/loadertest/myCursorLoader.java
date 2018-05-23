package com.example.vrnandr.loadertest;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andrey on 22.05.2018.
 */

public class myCursorLoader extends CursorLoader {
    private SQLiteDatabase database;
    private String tableName;

    public myCursorLoader(Context context, SQLiteDatabase database, String tableName) {
        super(context);
        this.database = database;
        this.tableName = tableName;
    }

    @Override
    public Cursor loadInBackground() {
        return database.rawQuery("SELECT * FROM "+tableName, null);
    }
}