package com.example.mandeep.notes;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNotes extends AppCompatActivity implements View.OnClickListener {
    EditText editNote;
    Button save, delete;

    private String selectedNote;
    private int selectedID;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        databaseHelper = new DatabaseHelper(this);
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        selectedNote = receivedIntent.getStringExtra("note");

        editNote = findViewById(R.id.editNotes);
        editNote.setText(selectedNote);
        editNote.setSelection(editNote.getText().length());
//        Cursor;

        save = findViewById(R.id.save);
        delete = findViewById(R.id.delete);

        save.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v == save) {
            String item = editNote.getText().toString();
            if(!item.equals("")){
                databaseHelper.updateNote(item,selectedID,selectedNote);
                startActivity(new Intent(EditNotes.this, MainActivity.class));
                finish();
            }else{
                Toast.makeText(this, "enter some note", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == delete) {
            databaseHelper.deleteNote(selectedID,selectedNote);
            editNote.setText("");
            startActivity(new Intent(EditNotes.this, MainActivity.class));
            Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
