package com.fatimazahra.topnews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatimazahra.topnews.R;
import com.fatimazahra.topnews.model.Article;
import com.fatimazahra.topnews.model.ArticleBanque;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;


public class Main2Activity extends AppCompatActivity {

    private TextView date_Article;
    private TextView titre_Article;
    private ImageView image_Article;
    private TextView text_Article;

    private Button Suivant;
    private Button Precedent;

    private ArrayList<Article> articles;
    private int numberOfArticles = 0;

    private Article currentArticle;
    private ArticleBanque mesArticles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        long Id =  getIntent().getExtras().getLong("Id");
        mesArticles = this.generateArticles(Id);
        numberOfArticles = articles.size();

        //wire widget
        date_Article  = (TextView) findViewById(R.id.dateArticle);
        titre_Article = (TextView) findViewById(R.id.titreArticle);
        image_Article = (ImageView) findViewById(R.id.imageArticle);
        text_Article  = (TextView) findViewById(R.id.textArticle);
        Suivant = (Button) findViewById(R.id.Suivant);
        Precedent = (Button) findViewById(R.id.Precedent);

        Precedent.setVisibility(View.INVISIBLE);
        currentArticle = mesArticles.getArticleSuivant();
        this.displayArticle(currentArticle);
        if( numberOfArticles == 1 ){
            Suivant.setVisibility(INVISIBLE);
        }

        Suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( --numberOfArticles == 1 ){
                    //end of articles
                    endArticles();
                    currentArticle = mesArticles.getArticleSuivant();
                    displayArticle(currentArticle);
                }
                else {
                    Precedent.setVisibility(View.VISIBLE);
                    currentArticle = mesArticles.getArticleSuivant();
                    displayArticle(currentArticle);
                }
            }
        });

        Precedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    
                if ( ++numberOfArticles >= articles.size() ){
                    Precedent.setVisibility(INVISIBLE);
                    currentArticle = mesArticles.getArticlePrecedant();
                    displayArticle(currentArticle);
                } else {
                    Suivant.setVisibility(View.VISIBLE);
                    currentArticle = mesArticles.getArticlePrecedant();
                    displayArticle(currentArticle);
                }
            }
        });
        
    }


    private void endArticles(){
        Suivant.setVisibility(View.INVISIBLE);
    }

    private void displayArticle(final Article article){

      date_Article.setText(article.getDate_article());
      titre_Article.setText(article.getTitre_article());
      image_Article.setBackgroundResource(R.drawable.pprojet);
      text_Article.setText(article.getText_article());

    }

    private ArticleBanque generateArticles(long id) {

        DatabaseAcces dbAcces = DatabaseAcces.getInstance(getApplicationContext());
        dbAcces.open();

        articles = dbAcces.getArticles(id);

        dbAcces.close();
        return new ArticleBanque(articles); 
    }
}
