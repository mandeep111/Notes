package com.example.mandeep.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote.class));
                finish();
            }
        });
        populateData();
    }

    public void populateData() {

        Cursor cursor = databaseHelper.getData();
        ArrayList<String> listNote = new ArrayList<>();
        while(cursor.moveToNext()) {
            listNote.add(cursor.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNote);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                String note = adapterView.getItemAtPosition(i).toString().trim();
                Cursor data = databaseHelper.getItemID(note);
                int itemID = -1;
//                String noteItem = "";
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1) {
                    Intent intent = new Intent(MainActivity.this, EditNotes.class);
                    intent.putExtra("id", itemID);
//                    Toast.makeText(MainActivity.this, "id " + itemID, Toast.LENGTH_SHORT).show();
                    intent.putExtra("note", note);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Selected note not available", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


}
