package com.example.myapplication.ui.masterdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperMasterdata extends SQLiteOpenHelper {

    //Variables that are relevant for this class
    public static final String DATABASE_NAME = "masterdata1_User.db";
    public static final String TABLE_NAME = "masterdata_table";
    public static final String ID = "ID";
    public static final String COL_1 = "Firstname";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Telephone";
    public static final String COL_4 = "Birthday";
    public static final String COL_5 = "Bloodgroup";
    public static final String COL_6 = "Rhesusfactor";
    public static final String COL_7 = "Preconditions";

    //Constructor for database
    public DatabaseHelperMasterdata(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //onCreate() of the database with autoincrement for the id, only the first database entry is used for the masterdata at the moment
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Firstname TEXT, Name TEXT, Telephone TEXT, Birthday TEXT, Bloodgroup TEXT, Rhesusfactor TEXT, Preconditions TEXT)");

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, "");
        contentValues.put(COL_2, "");
        contentValues.put(COL_3, "");
        contentValues.put(COL_4, "");
        contentValues.put(COL_5, "");
        contentValues.put(COL_6, "");
        contentValues.put(COL_7, "");
        db.insert(TABLE_NAME, null, contentValues);
    }

    //onUpgrade(): the existing database is used if available
    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //getMasterdata(): Gets all data from the database
    public Cursor getMasterdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    //updateData: updates all data where ID = ID from textEdit
    public boolean updateData(String id, String firstname, String name, String telephone, String birthday, String bloodgroup, String rhesusfactor, String preconditions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(COL_1, firstname);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, telephone);
        contentValues.put(COL_4, birthday);
        contentValues.put(COL_5, bloodgroup);
        contentValues.put(COL_6, rhesusfactor);
        contentValues.put(COL_7, preconditions);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    //deleteData: "Deletes" all data, in this case it replaces the data with empty Strings
    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(COL_1, "");
        contentValues.put(COL_2, "");
        contentValues.put(COL_3, "");
        contentValues.put(COL_4, "");
        contentValues.put(COL_5, "");
        contentValues.put(COL_6, "");
        contentValues.put(COL_7, "");
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }
}
