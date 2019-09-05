package com.fatimazahra.topnews.model;

import java.util.List;

public class ArticleBanque {

    private List<Article> mArticle;
    private static int mNextArticelIndex;
    private int aide =0;

        ///Le constructeur
    public ArticleBanque(List<Article> articleList)
    {
        //Le tri de la liste des artilces selon la date
        for( int j = 0 ; j < articleList.size()-1 ; j++ ) {
            for (int i = j+1 ; i < articleList.size() ; i++) {
                if ( articleList.get(j).getDate_article().compareTo(articleList.get(i).getDate_article()) < 0 ) {
                    Article aide = new Article(articleList.get(i).getTitre_article(),
                                                articleList.get(i).getPhoto_article(),
                                                articleList.get(i).getText_article(),
                                                articleList.get(i).getDate_article());

                    articleList.get(i).setTitre_article(articleList.get(j).getTitre_article());
                    articleList.get(i).setPhoto_article(articleList.get(j).getPhoto_article());
                    articleList.get(i).setText_article(articleList.get(j).getText_article());
                    articleList.get(i).setDate_article(articleList.get(j).getDate_article());



                    articleList.get(j).setTitre_article(aide.getTitre_article());
                    articleList.get(j).setPhoto_article(aide.getPhoto_article());
                    articleList.get(j).setText_article(aide.getText_article());
                    articleList.get(j).setDate_article(aide.getDate_article());
                }
            }
        }

        mArticle = articleList ;
        mNextArticelIndex = 0;
    }

    public Article getArticleSuivant()
    {

        if( aide == 0 ) {
            return mArticle.get(mNextArticelIndex++);
        }
        else{

            aide = 0;
            mNextArticelIndex++;
            return mArticle.get(mNextArticelIndex++);
        }

    }

    public Article getArticlePrecedant() {

        if (aide == 1) {
            return mArticle.get(--mNextArticelIndex);

        }else{
            aide = 1;
            mNextArticelIndex--;
            return mArticle.get(--mNextArticelIndex);
        }
    }
}
