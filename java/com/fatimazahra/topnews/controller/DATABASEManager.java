package com.fatimazahra.topnews.controller;


import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DATABASEManager extends SQLiteAssetHelper {

        private static final String DATABASE_NAME = "maTopNewsBD.db";
        private static final int DATABASE_VERSION = 1;

    public DATABASEManager(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
}
