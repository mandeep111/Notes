package com.example.mandeep.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity implements View.OnClickListener{
    EditText editNotes;
    Button save;
    DatabaseHelper databaseHelper;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        editNotes = findViewById(R.id.editNotes);
        save = findViewById(R.id.save);
        databaseHelper = new DatabaseHelper(this);
        preferences = getSharedPreferences("notesInfo", Context.MODE_PRIVATE);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==save) {
            String notesInfo = editNotes.getText().toString();
            if (notesInfo.length() != 0) {
                addNote(notesInfo);
            } else {
                Toast.makeText(this, "Enter some note", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addNote(String notesInfo) {
        boolean insertNote = databaseHelper.addNote(notesInfo);
        if (insertNote) {
            Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddNote.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();
        }
    }
}
