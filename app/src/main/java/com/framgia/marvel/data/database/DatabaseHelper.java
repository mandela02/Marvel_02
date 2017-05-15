package com.framgia.marvel.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 5/15/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "marvel.db";
    private final static int DB_VERSION = 1;
    private final static String COMMAND_CREATE_TABLE =
        " CREATE TABLE " + MarvelDatabase.MarvelEntry.TABLE_NAME
            + "("
            + MarvelDatabase.MarvelEntry._ID
            + " INTEGER PRIMARY KEY NOT NULL, "
            + MarvelDatabase.MarvelEntry.COLUMN_NAME
            + " TEXT, "
            + MarvelDatabase.MarvelEntry.COLUMN_ID
            + " TEXT, "
            + MarvelDatabase.MarvelEntry.COLUMN_DES
            + " TEXT, "
            + MarvelDatabase.MarvelEntry.COLUMN_URL
            + " TEXT) ";
    private final static String COMMAND_DELETE_TABLE =
        "DROP TABLE" + MarvelDatabase.MarvelEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);
    }
}
