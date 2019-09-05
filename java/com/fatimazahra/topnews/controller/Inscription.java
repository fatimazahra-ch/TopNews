package com.fatimazahra.topnews.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatimazahra.topnews.R;


import java.util.ArrayList;

public class Inscription extends AppCompatActivity {

    private EditText nomVisiteur , motVisiteur , confirmeVisiteur ;
    private TextView choixTheme;
    private String IMEInumber;
    private String[] listThemes;
    private boolean[] checkedTheme;
    private ArrayList<Integer> UserThemes = new ArrayList<>();

    private Button inscrire;
    private TextView dejaConnecter;

    static final int PERMISSION_READ_STATE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        inscrireView();

        choixTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Inscription.this);
                mBuilder.setTitle("Les thémes valables sont :");
                mBuilder.setMultiChoiceItems(listThemes, checkedTheme, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            if( !UserThemes.contains(position)){
                                UserThemes.add(position);
                            }else{
                                UserThemes.remove(position);
                            }
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item ="";
                        for ( int j = 0; j < UserThemes.size(); j++){

                            item = item + listThemes[UserThemes.get(j)];
                            if( j != UserThemes.size()-1 ){
                                item = item + ", ";
                            }
                        }
                        choixTheme.setText(item);
                    }
                });

                mBuilder.setNeutralButton(" Suuprimer tout !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                   for ( int i = 0 ; i < checkedTheme.length ; i++ ){
                        checkedTheme[i] = false;
                        UserThemes.clear();
                        choixTheme.setText("");
                   }

                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( validate() ){

                    String nom = nomVisiteur.getText().toString();
                    String mdp = motVisiteur.getText().toString();

                    int permissionCheck = ContextCompat.checkSelfPermission(Inscription.this, Manifest.permission.READ_PHONE_STATE);
                    if( permissionCheck == PackageManager.PERMISSION_GRANTED){

                        MyTelephonyManager();

                    } else {
                        ActivityCompat.requestPermissions(Inscription.this, new String[] {Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
                    }

                    DatabaseAcces dbAcces = DatabaseAcces.getInstance(getApplicationContext());
                    dbAcces.open();

                    dbAcces.addUser(nom, mdp, IMEInumber);
                    long id = dbAcces.getUser(nom, mdp, IMEInumber);
                    for( int i = 0 ; i < UserThemes.size() ; i++ )
                        dbAcces.addUserTheme(id, UserThemes.get(i)+1);

                    dbAcces.closeDB();

                    Toast.makeText(Inscription.this, "Inscription réussie" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Inscription.this, MainActivity.class));
                }
            }
        });


        dejaConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inscription.this, MainActivity.class));
            }
        });

    }

    private void inscrireView(){

        nomVisiteur = (EditText) findViewById(R.id.nom_Visiteur);
        motVisiteur = (EditText) findViewById(R.id.mot_de_passe_Visiteur);
        confirmeVisiteur = (EditText) findViewById(R.id.mot_de_passe_Confirm_Visiteur);
        choixTheme = (TextView) findViewById(R.id.choixTheme);
        listThemes = getResources().getStringArray(R.array.Themes);
        checkedTheme = new boolean[listThemes.length];

        inscrire = (Button) findViewById(R.id.Inscrire);
        dejaConnecter = (TextView) findViewById(R.id.deja_Connecter);

    }

    private Boolean validate(){

       Boolean result = false;

        String nom = nomVisiteur.getText().toString();
        String mdp = motVisiteur.getText().toString();
        String confirmdp = confirmeVisiteur.getText().toString();

        if( nom.isEmpty() || mdp.isEmpty() || confirmdp.isEmpty() || UserThemes.size() == 0 ){

            Toast.makeText(this, "  Entrer tous vos informations  " , Toast.LENGTH_SHORT).show();

        }else{

            DatabaseAcces dbAcces = DatabaseAcces.getInstance(getApplicationContext());
            dbAcces.open();

            long exist = dbAcces.getUser(nom, mdp, IMEInumber);

            if( mdp.equals(confirmdp)) {

                if( exist != 0){

                    Toast.makeText(this, "  Vous étes déja inscrit !!  " , Toast.LENGTH_SHORT).show();

                } else {

                    if( dbAcces.getUserByNI(nom,IMEInumber) )
                        Toast.makeText(this, "  Vous étes déja inscrit !! , avez-vous oublier votre mot de passe " , Toast.LENGTH_SHORT).show();

                    result = true;

                }

            } else {

                Toast.makeText(this, "  Entrer le méme mot de passe  " , Toast.LENGTH_SHORT).show();

            }

        }

        return result;
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

}
