package com.longnightking.togodutch_android.database;

import android.provider.BaseColumns;

/**
 * Created by longNightKing on 4/22/15.
 */
public class DbTableStatistic {
    public DbTableStatistic(){}

    public static abstract  class Entry implements BaseColumns {
        public static final String TABLE_NAME = "Statistic";
    }
}
