package com.framgia.marvel.data.database;

import android.provider.BaseColumns;

/**
 * Created by asus on 5/15/2017.
 */
public class MarvelDatabase {
    public class MarvelEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_marvel";
        public static final String COLUMN_NAME = "column_name";
        public static final String COLUMN_ID = "column_id";
        public static final String COLUMN_DES = "column_des";
        public static final String COLUMN_URL = "column_url";
    }
}
