package com.fatimazahra.topnews.controller;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatimazahra.topnews.R;
import com.fatimazahra.topnews.model.Article;
import com.fatimazahra.topnews.model.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText nom_form;
    private EditText mot_de_passe;
    private String IMEInumber;
    private Button connecter;
    private TextView register;

    public Person visiteur;
    private int counter = 3;


    static final int PERMISSION_READ_STATE = 123;


    SharedPreferences mPreference;
    SharedPreferences.Editor editor;
    public static final String PREF_NAME = "prefs";
    public static final String KEY_ID = "Id_User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mPreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        //editor = mPreference.edit();


        this.nom_form = (EditText) findViewById(R.id.nom_form);
        this.mot_de_passe = (EditText) findViewById(R.id.mot_de_passe);
        this.connecter = (Button) findViewById(R.id.connecter);
        this.register = (TextView) findViewById(R.id.register);

        connecter.setEnabled(false);

        nom_form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                connecter.setEnabled(charSequence.toString().length() != 0);

                connecter.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        String login = nom_form.getText().toString();
                        String psw = mot_de_passe.getText().toString();

                        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);
                        if( permissionCheck == PackageManager.PERMISSION_GRANTED){

                            MyTelephonyManager();

                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
                        }

                        validate(login, psw, IMEInumber);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Inscription.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case PERMISSION_READ_STATE:
            {
                if(grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    MyTelephonyManager();
                }else{
                    Toast.makeText(this,
                            "You don't have required permission to make the action",
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void MyTelephonyManager(){
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        IMEInumber = manager.getDeviceId();

      //  Toast.makeText(this," IMEI number : "+ IMEInumber, Toast.LENGTH_SHORT).show();
    }


    private void validate(String nom, String motDePasse, String IMEI){

          ///////////     Pour le cas d'une base de données externe    ///
        DatabaseAcces dbAcces = DatabaseAcces.getInstance(getApplicationContext());
        dbAcces.open();

        long exist = dbAcces.getUser(nom, motDePasse, IMEI);

        if( exist != 0 ){

            //editor.putLong(KEY_ID, exist).apply();
            Intent autreActivite = new Intent(MainActivity.this, Main2Activity.class);
            autreActivite.putExtra("Id", exist);
            startActivity(autreActivite);
            finish();

        } else {
//            if ((nom.equals(c.getString(1))) && !(motDePasse.equals(c.getString(2)))){
//                Toast.makeText(this, "Mot de Passe n'est pas correct !", Toast.LENGTH_SHORT).show();
                counter--;
                if( counter == 0 )
                {
                    connecter.setEnabled(false);
                    Toast.makeText(this, "Impossible de se connecter!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Informations Invalides !", Toast.LENGTH_SHORT).show();
                }
        }

        //dbAcces.close();

       /* long res = db.checkUser(nom, motDePasse, IMEI);
        if(res != 0 ){

            Toast.makeText(MainActivity.this, "Bienvenue", Toast.LENGTH_SHORT).show();
            Intent autreActivite = new Intent(MainActivity.this, Main2Activity.class);
            autreActivite.putExtra("Id", res);
            startActivity(autreActivite);
            finish();

        } else {

            counter--;
            if( counter == 0 )
            {
                connecter.setEnabled(false);
                Toast.makeText(this, "Impossible de se connecter!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Informations Invalides !", Toast.LENGTH_SHORT).show();
            }

        }*/


    }

}
