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
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter adapter;
    private SQLiteDatabase database;
    private final String TAG = "my";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();

        ListView listView = findViewById(R.id.selectlistview);

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{"Service"},
                new int[]{android.R.id.text1});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: "+position+" "+id);
                //TextView tv = (TextView) view;
                String service = ((TextView)view).getText().toString();
                Log.d(TAG, "onItemClick: "+service);
                Intent intent = new Intent(SelectActivity.this, Main2Activity.class);
                intent.putExtra("Service",service);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(1, null, this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, id, null);
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
