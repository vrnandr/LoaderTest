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

    public myCursorLoader(Context context, SQLiteDatabase database) {
        super(context);
        this.database = database;
    }

    @Override
    public Cursor loadInBackground() {
        return database.rawQuery("SELECT * FROM mytable", null);
    }
}