package com.example.myapplication.ui.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperContact extends SQLiteOpenHelper {

    //Variables that are relevant for this class
    public static final String DATABASE_NAME = "stammdaten_contact.db";
    public static final String TABLE_NAME = "contact_table";
    public static final String ID = "ID";
    public static final String COL_1 = "Firstname";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Telephone";
    public static final String COL_4 = "Relation";


    //Constructor for database
    public DatabaseHelperContact(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //onCreate() of the database with autoincrement for the id
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Firstname TEXT, Name TEXT, Telephone TEXT, Relation TEXT)");
    }

    //onUpgrade(): the existing database is used if available
    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //insertData(): Inserts data in database, result is true if successful
    public boolean insertData(String firstname, String name, String telephone, String relation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, firstname);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, telephone);
        contentValues.put(COL_4, relation);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //getAllData(): Gets all data from the database
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    //updateData(): Updateds all data, where ID = ID from textEdit
    public boolean updateData(String id, String firstname, String name, String telephone, String relation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(COL_1, firstname);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, telephone);
        contentValues.put(COL_4, relation);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    //deleteData(): deletes all data, where ID = ID from textEdit
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
