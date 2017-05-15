package com.framgia.marvel.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 5/15/2017.
 */
public class MarvelDataSource extends DatabaseHelper {
    public MarvelDataSource(Context context) {
        super(context);
    }

    public List<Result> getAllChar() {
        List<Result> result = new ArrayList<>();
        String[] Column = {MarvelDatabase.MarvelEntry._ID,
            MarvelDatabase.MarvelEntry.COLUMN_NAME,
            MarvelDatabase.MarvelEntry.COLUMN_ID,
            MarvelDatabase.MarvelEntry.COLUMN_DES,
            MarvelDatabase.MarvelEntry.COLUMN_URL,
        };
        SQLiteDatabase database = getWritableDatabase();
        String orderBY = MarvelDatabase.MarvelEntry.COLUMN_NAME;
        Cursor cursor = database.query(MarvelDatabase.MarvelEntry.TABLE_NAME,
            Column,
            null,
            null,
            null,
            null,
            orderBY
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Result contacts = new Result(cursor);
                    result.add(contacts);
                }
                while (cursor.moveToNext());
                return result;
            }
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
            database.close();
        }
        return null;
    }

    public long insertContact(Result character) {
        if (character != null) {
            SQLiteDatabase database = getWritableDatabase();
            ContentValues values = new ContentValues();
            try {
                values.put(MarvelDatabase.MarvelEntry.COLUMN_NAME, character.getName());
                values.put(MarvelDatabase.MarvelEntry.COLUMN_ID, character.getId());
                values.put(MarvelDatabase.MarvelEntry.COLUMN_DES, character.getDescription());
                values.put(MarvelDatabase.MarvelEntry.COLUMN_URL, character.getAvatar());
                return database.insert(MarvelDatabase.MarvelEntry.TABLE_NAME, null, values);
            } catch (Exception e) {
                return -1;
            } finally {
                database.close();
            }
        }
        return -1;
    }

    public boolean deleteByID(int id) {
        SQLiteDatabase database = getWritableDatabase();
        try {
            String whereClause = MarvelDatabase.MarvelEntry._ID + " =?";
            String[] whereArgs = {String.valueOf(id)};
            return database.delete(MarvelDatabase.MarvelEntry.TABLE_NAME, whereClause, whereArgs)
                > 0;
        } catch (Exception e) {
            return false;
        } finally {
            database.close();
        }
    }
}
