package com.longnightking.togodutch_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by longNightKing on 4/22/15.
 */
public final class GoDutchDbContract {

    public GoDutchDbContract(){

    }

    public static abstract  class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contacts";
        public static final String COLUMN_NAME_SESSION_ID = "SessionID";
        public static final String COLUMN_NAME_CONTACT_ID = "ContactID";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_SESSION_ID + " INT NOT NULL, " + COLUMN_NAME_CONTACT_ID + " INT NOT NULL)";
    }

    public static abstract  class PurchaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "Purchases";
        public static final String COLUMN_NAME_SESSION_ID = "SessionID";
        public static final String COLUMN_NAME_PURCHASE_ID = "PurchaseID";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_COST = "Cost";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_SESSION_ID + " INT NOT NULL, " + COLUMN_NAME_PURCHASE_ID + " INT NOT NULL, " + COLUMN_NAME_TITLE + " TEXT NOT NULL, "
                + COLUMN_NAME_COST + " DOUBLE NOT NULL)";
    }

    public static abstract  class StatisticEntry implements BaseColumns {
        public static final String TABLE_NAME = "Statistic";
        public static final String COLUMN_NAME_SESSION_ID = "SessionID";
        public static final String COLUMN_NAME_CONTACT_ID = "ContactID";
        public static final String COLUMN_NAME_PURCHASE_ID = "PurchaseID";
        public static final String COLUMN_NAME_ISCHECK = "IsCheck";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_SESSION_ID + " INT NOT NULL, " + COLUMN_NAME_CONTACT_ID + " INT NOT NULL, " + COLUMN_NAME_PURCHASE_ID + " INT NOT NULL, " + COLUMN_NAME_ISCHECK + " Boolean NOT NULL)";
    }
}
