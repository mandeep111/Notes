package com.example.mandeep.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String name ="notesInfo";
    private static final String COL1 = "id";
    private static final String COL2 = "note";
    public static final int version = 1;
    private  SQLiteDatabase db;

    private String createTable = "CREATE TABLE IF NOT EXISTS `notes` (\n" +
            "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`note`\tTEXT\n" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createTable);
    }

    public boolean addNote(String note) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("note", note);
        long result = db.insert("notes", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

//    public long insertNote(String note) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("note", note);
//        long id = db.insert("notes", null, values);
//        db.close();
//        return id;
//    }

    public Cursor getData() {
        db = this.getReadableDatabase();
        String sql = "SELECT * FROM notes";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getItemID(String note){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM notes WHERE " + COL2 + " = '" + note + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateNote(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE notes SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void deleteNote(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM notes WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        db.execSQL(query);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
