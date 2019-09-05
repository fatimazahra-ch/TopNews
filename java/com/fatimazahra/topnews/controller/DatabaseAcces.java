package com.fatimazahra.topnews.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fatimazahra.topnews.model.Article;
import com.fatimazahra.topnews.model.Person;
import java.util.ArrayList;


public class DatabaseAcces {

    private SQLiteOpenHelper  openHelper;
    private SQLiteDatabase db;
    private static DatabaseAcces instance;
    Cursor c = null;

    private DatabaseAcces(Context context){
        this.openHelper = new DATABASEManager(context); //Création d'une base de donées
    }

    public static DatabaseAcces getInstance(Context context){
        if (instance == null ){
            instance = new DatabaseAcces(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if( db != null ){
            this.db.close();
        }
    }

    public ArrayList getArticles (long id){

        ArrayList array_list = new ArrayList();

        Cursor c = db.rawQuery("select titre_article,text_article,date_article from article where Id_theme in ( select Id_theme from abonner where Id_User = " +
                                   id + "  order by Id_theme ) order by date_article desc " ,null);

        c.moveToFirst();

        while( c.isAfterLast() == false ){

            String date = c.getString(2);
            //byte[] image = c.getBlob(1);
            String titre = c.getString(0);
            String text = c.getString(1);
            Article a = new Article(titre, "ma photo", text, date);
            array_list.add(a);
            c.moveToNext();
        }

        return array_list;
    }

    public void addUser(String nom, String mdp, String IMEI){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Pseudo_User",nom);
        contentValues.put("Password_User", mdp );
        contentValues.put("IMEI_User", IMEI);
        db.insert("User", null, contentValues);
        //db.close();

    }

    public void addUserTheme(long id_User, int i){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Id_User", id_User);
        contentValues.put("Id_theme",i);
        db.insert("abonner", null, contentValues);
        //db.close();
    }

    public long getUser(String Pseudo, String Pass, String IMEI){

        Pseudo.replace("'","''");
        String requete_sql = "select Id_User,Pseudo_User,Password_User,IMEI_User " +
                             "  from User" +
                             "  where Pseudo_User = '" + Pseudo +"' and Password_User = '" + Pass + "' and IMEI_User = '" + IMEI + "'";

        Cursor c = db.rawQuery(requete_sql,null);

        c.moveToFirst();

        while ( c.getCount() > 0){ //cette boucle va tourner une seule fois .

            return c.getLong(0);
        }

        return 0;
    }

    public Boolean getUserByNI(String nom, String IMEI){

        nom.replace("'","''");
        String requete_sql = "select Pseudo_User,IMEI_User " +
                "  from User" +
                "  where Pseudo_User = '" + nom +"' and IMEI_User = '" + IMEI + "'";

        Cursor c = db.rawQuery(requete_sql,null);
        c.moveToFirst();
        if( c.getCount() > 0 )
            return true;

        return false;

    }

    public void closeDB(){
        db.close();
    }
}
