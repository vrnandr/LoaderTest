package com.example.vrnandr.loadertest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vrnandr on 23.05.18.
 */

 public class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "mytable", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.execSQL("CREATE TABLE mytable (_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT)");
            sqLiteDatabase.execSQL("INSERT INTO mytable(text) VALUES ('раз')");
            sqLiteDatabase.execSQL("INSERT INTO mytable(text) VALUES ('два')");
            sqLiteDatabase.execSQL("INSERT INTO mytable(text) VALUES ('три')");
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
}
