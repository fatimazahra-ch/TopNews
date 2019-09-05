package com.fatimazahra.topnews.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registerUser";
    public static final String COL_1 = "Id_User";
    public static final String COL_2 = "Nom_User";
    public static final String COL_3 = "Password_User";
    public static final String COL_4 = "Email_User";
    public static final String COL_5 = "Theme_User";
    public static final String COL_6 = "IMEI_User";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS registerUser (Id_User INTEGER PRIMARY KEY AUTOINCREMENT , Nom_User TEXT , Password_User TEXT , Email_User TEXT , Theme_User TEXT, IMEI_User TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String User, String Password, String Email, String theme, String IMEI){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nom_User",User);
        contentValues.put("Password_User", Password );
        contentValues.put("Email_User", Email);
        contentValues.put("Theme_User", theme);
        contentValues.put("IMEI_User", IMEI);
        long res = db.insert("registerUser", null, contentValues);
        db.close();
        return res;
    }

    public long checkUser(String Nom, String Password, String IMEI){

        SQLiteDatabase db = getReadableDatabase();

        Nom.replace("'","''");
        String requete_sql = "select Nom_User,Password_User,Email_User,IMEI_User " +
                "  from User" +
                "  where Nom_User = '" + Nom +"' and Password_User = '" + Password +"' and IMEI_User = '" + IMEI + "'";

        Cursor c = db.rawQuery(requete_sql,null);
        c.moveToFirst();
        while ( c.getCount() > 0){ //cette boucle va tourner une seule fois .

            return c.getLong(0);
        }
        c.close();
        db.close();

        return 0;
    }
}
