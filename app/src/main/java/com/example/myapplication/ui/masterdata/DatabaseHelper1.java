package com.example.myapplication.ui.masterdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper1 extends SQLiteOpenHelper {

    //Initialisierung der Variablen für die Datenbank
    public static final String DATABASE_NAME = "stammdaten_User.db";
    public static final String TABLE_NAME = "stammdaten_table";
    public static final String ID = "ID";
    public static final String COL_1 = "Vorname";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Telefonnummer";
    public static final String COL_4 = "Geburtsdatum";
    public static final String COL_5 = "Blutgruppe";
    public static final String COL_6 = "Rhesusfaktor";
    public static final String COL_7 = "Vorerkrankungen";

    //Konstruktur für Datenbank
    public DatabaseHelper1(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //onCreate der Datenbank mit Autoincrement für ID + es wird der erste Eintrag für die Stammdaten reserviert
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Vorname TEXT, Name TEXT, Telefonnummer TEXT, Geburtsdatum TEXT, Blutgruppe TEXT, Rhesusfaktor TEXT, Vorerkrankungen TEXT)");

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1,"");
            contentValues.put(COL_2,"");
            contentValues.put(COL_3,"");
            contentValues.put(COL_4,"");
            contentValues.put(COL_5,"");
            contentValues.put(COL_6,"");
            contentValues.put(COL_7,"");
            db.insert(TABLE_NAME,null, contentValues);

    }

    //onUpgrade, das es nur eine Datenbank gibt und nicht immer eine neue
    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //getAllData: Holt sich alle Daten aus der Datenbank
    public Cursor getStammdatenData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return cursor;
    }

    //updateData: Updated alle Daten, wo id = eingegebene id
    public boolean updateData(String id, String vorname, String name, String telefonnummer, String geburtsdatum, String blutgruppe, String rhesusfaktor, String vorerkrankungen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(COL_1,vorname);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,telefonnummer);
        contentValues.put(COL_4,geburtsdatum);
        contentValues.put(COL_5,blutgruppe);
        contentValues.put(COL_6,rhesusfaktor);
        contentValues.put(COL_7,vorerkrankungen);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    //deleteData: Updatet alle Daten zu leeren Platzhaltern
    public boolean deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(COL_1,"");
        contentValues.put(COL_2,"");
        contentValues.put(COL_3,"");
        contentValues.put(COL_4,"");
        contentValues.put(COL_5,"");
        contentValues.put(COL_6,"");
        contentValues.put(COL_7,"");
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }
}
