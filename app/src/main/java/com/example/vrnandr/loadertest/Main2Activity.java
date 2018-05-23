package com.example.vrnandr.loadertest;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity{


    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        final EditText editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.execSQL("INSERT INTO mytable(text) VALUES('"+ editText.getText().toString() +"')");
            }
        });

    }
}
