package com.example.vrnandr.loadertest;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Andrey on 22.05.2018.
 */

public class MyCursorLoader extends CursorLoader {
    private SQLiteDatabase database;
    private String tableName;
    private int id;
    private String[] args;

    public MyCursorLoader(Context context, int id, String[] args) {
        super(context);

        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();

        this.id = id;
        this.args = args;
    }

    @Override
    public Cursor loadInBackground() {
        switch (id){
            case 0: return database.rawQuery("SELECT * FROM Works", null);
            case 1: return database.rawQuery("SELECT * FROM ServiceCatalog GROUP BY Service", null);
            case 2: return database.rawQuery("SELECT * FROM ServiceCatalog WHERE Service = '"+args[0]+"'", null);
            case 3: Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    String dateString = format.format(date);
                    return database.rawQuery("select Works._id, Works.Date, sum(ServiceCatalog.TimeNorm) from Works inner join ServiceCatalog on ServiceCatalog._id=Works.WorkID where date = '"+dateString+"'", null);
            default: return null;
        }
    }
}