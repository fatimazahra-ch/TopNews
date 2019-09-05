package com.fatimazahra.topnews.model;

public class Article {

    //Les champs d'article
    private String date_article;
    private String titre_article;
    private String photo_article;
    private String text_article;


    //Les méthodes d'articles

    public Article(String ta, String pa, String taa, String da) {

        titre_article = ta;
        photo_article = pa;
        text_article = taa;
        date_article = da;
    }


    public String getTitre_article() {
        return titre_article;
    }

    public void setTitre_article(String titre_article) {
        this.titre_article = titre_article;
    }


    public String getDate_article() {
        return date_article;
    }

    public void setDate_article(String date_article) {
        this.date_article = date_article;
    }

    public String getPhoto_article() {
        return photo_article;
    }

    public void setPhoto_article(String photo_article) {
        this.photo_article = photo_article;
    }

    public String getText_article() {
        return text_article;
    }

    public void setText_article(String text_article) {
        this.text_article = text_article;
    }

    @Override
    public String toString() {
        return "Article{" +
                " titre_article='" + titre_article + '\'' +
                ", photo_article='" + photo_article + '\'' +
                ", text_article='" + text_article + '\'' +
                ", date_article='" + date_article + '\'' +
                '}';
    }
}

