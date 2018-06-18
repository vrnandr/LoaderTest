package com.example.vrnandr.loadertest;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    private SQLiteDatabase database;
    private CursorAdapter adapter;
    private String service;
    private final String TAG = "my";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();*/
        Intent intent = getIntent();
        service = intent.getStringExtra("Service");
        Log.d(TAG, "onCreate: "+service);

        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        ListView listView = findViewById(R.id.listview);

        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                new String[]{"ShortDesc","TimeNorm"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        listView.setAdapter(adapter);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String dateString = format.format(date);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: "+id);
                database.execSQL("INSERT INTO Works(Date, WorkID) VALUES('"+dateString+"','"+id+"')");
                Intent intent1 = new Intent(Main2Activity.this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        getLoaderManager().initLoader(2,null, this);

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.execSQL("INSERT INTO mytable(text) VALUES('"+ editText.getText().toString() +"')");
            }
        });*/

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, id, new String[]{service});
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
