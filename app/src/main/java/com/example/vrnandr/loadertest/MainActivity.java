package com.example.vrnandr.loadertest;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private SQLiteDatabase database;
    //private CursorAdapter adapter;
    private SimpleCursorTreeAdapter adapter;
    //private Menu menu;
    private MenuItem menuItem;
    private final String TAG="my";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        });

        //------------------------------------------

        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        /*adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[] {"WorkID"},
                new int[]{android.R.id.text1}
        );

        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                database.execSQL("DELETE FROM Works WHERE _id="+id);
                getLoaderManager().getLoader(0).forceLoad();
                invalidateOptionsMenu();
            }
        });*/

        adapter = new SimpleCursorTreeAdapter(
                this,

                null,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {"date","sum"},
                new int[] {android.R.id.text1,android.R.id.text2},

                android.R.layout.simple_expandable_list_item_2,
                new String[]{"work", "time"},
                new int[] {android.R.id.text1,android.R.id.text2 }
        ) {
            @Override
            protected Cursor getChildrenCursor(Cursor cursor) {
                int id = cursor.getPosition()+100;
                CharSequence date = cursor.getString(1);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("date", date);
                Loader<Cursor> myCursorLoader = getLoaderManager().getLoader(id);
                if (myCursorLoader!= null && !myCursorLoader.isReset()){
                    getLoaderManager().restartLoader(id,bundle,MainActivity.this);
                } else
                    getLoaderManager().initLoader(id,bundle,MainActivity.this);
                return null;
            }
        };

        ExpandableListView listView = findViewById(R.id.expandableListView);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
        //getLoaderManager().initLoader(4, null, this);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.count);
        //this.menu = menu;
        getLoaderManager().initLoader(3,null, this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getLoaderManager().getLoader(3).forceLoad();
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id>=100&&!args.isEmpty()) {
            return new MyCursorLoader(this, id, new String[]{args.getString("date")});
        }
        return new MyCursorLoader(this, id, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        Log.d(TAG, "onLoadFinished: "+id);
        switch (id){
            case 0: adapter.changeCursor(data);
                    break;
            case 3: if (data.moveToFirst())
                        menuItem.setTitle(data.getString(2));
                    break;
        }

        if (id>=100){
            adapter.setChildrenCursor(id-100,data);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().getLoader(0).forceLoad();
    }
}
