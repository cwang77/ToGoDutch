package com.longnightking.togodutch_android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by longNightKing on 4/22/15.
 */
public class GoDutchDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "GoDutch.db";

    public GoDutchDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GoDutchDbContract.ContactEntry.SQL_CREATE_TABLE);
        db.execSQL(GoDutchDbContract.PurchaseEntry.SQL_CREATE_TABLE);
        db.execSQL(GoDutchDbContract.StatisticEntry.SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
        // db.execSQL(DbTableContacts.SQL_DROP_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
