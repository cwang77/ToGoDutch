package com.longnightking.togodutch_android.database;

import android.provider.BaseColumns;

/**
 * Created by longNightKing on 4/22/15.
 */
public final class DbTablePurchases {
    public DbTablePurchases(){}

    public static abstract  class Entry implements BaseColumns {
        public static final String TABLE_NAME = "Purchases";
        public static final String COLUMN_NAME_Title = "Title";
        public static final String COLUMN_NAME_CONSUMPTION = "Consumption";
    }
}
