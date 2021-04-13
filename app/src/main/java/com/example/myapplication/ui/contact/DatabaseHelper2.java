package com.example.myapplication.ui.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    //Initialisierung der Variablen für die Datenbank
    public static final String DATABASE_NAME = "stammdaten_Ansprechpartner.db";
    public static final String TABLE_NAME = "ansprechpartner_table";
    public static final String ID = "ID";
    public static final String COL_1 = "Vorname";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Telefonnummer";
    public static final String COL_4 = "Beziehung";


    //Konstruktur für Datenbank
    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //onCreate der Datenbank mit Autoincrement für ID + es wird der erste Eintrag für die Stammdaten reserviert
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Vorname TEXT, Name TEXT, Telefonnummer TEXT, Beziehung TEXT)");
    }

    //onUpgrade, das es nur eine Datenbank gibt und nicht immer eine neue
    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //inserData: Fügt Daten in die Datenbank ein; result ist true wenn erfolgreich
    public boolean insertData (String vorname, String name, String telefonnummer, String beziehung) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,vorname);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,telefonnummer);
        contentValues.put(COL_4,beziehung);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //getAllData: Holt sich alle Daten aus der Datenbank
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return result;
    }

    //updateData: Updated alle Daten, wo id = eingegebene id
    public boolean updateData(String id, String vorname, String name, String telefonnummer, String beziehung) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(COL_1,vorname);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,telefonnummer);
        contentValues.put(COL_4,beziehung);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    //deleteData: Löscht alle Daten, wo id = eingegebene id
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
