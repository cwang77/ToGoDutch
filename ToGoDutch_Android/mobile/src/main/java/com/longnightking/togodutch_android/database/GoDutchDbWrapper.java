package com.longnightking.togodutch_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by longNightKing on 4/22/15.
 */
public class GoDutchDbWrapper {

    private static GoDutchDbWrapper instance = null;

    private Context mContext;

    public static synchronized GoDutchDbWrapper getInstance(Context context){
        if(instance == null)
            instance = new GoDutchDbWrapper(context);
        return instance;
    }

    private GoDutchDbHelper mDbHelper;

    private GoDutchDbWrapper(Context context){
        mContext = context;
        mDbHelper = new GoDutchDbHelper(context);
    }

    public long insertToContacts(String name){
        ContentValues values = new ContentValues();
        values.put(DbTableContacts.Entry.COLUMN_NAME_NAME, name);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.insert(DbTableContacts.Entry.TABLE_NAME, null, values);
    }

    

}
