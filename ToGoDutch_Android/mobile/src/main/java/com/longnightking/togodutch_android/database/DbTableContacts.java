package com.longnightking.togodutch_android.database;

import android.provider.BaseColumns;

/**
 * Created by longNightKing on 4/22/15.
 */
public final class DbTableContacts {
    public DbTableContacts(){}

    public static abstract  class Entry implements BaseColumns{
        public static final String TABLE_NAME = "Contacts";
        public static final String COLUMN_NAME_NAME = "Name";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + Entry.TABLE_NAME + " (" + Entry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + Entry.COLUMN_NAME_NAME + " TEXT NOT NULL)";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;
}
