package com.framgia.marvel.data.model;

import android.database.Cursor;

import com.framgia.marvel.data.database.MarvelDatabase;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 5/12/2017.
 */
public class Result {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("thumbnail")
    private Thumbnail mThumbnail;
    private int mDbId;
    private String mAvatar;

    public Result(Cursor cursor) {
        this.mId = cursor.getInt(cursor.getColumnIndex(MarvelDatabase.MarvelEntry.COLUMN_ID));
        this.mName =
            cursor.getString(cursor.getColumnIndex(MarvelDatabase.MarvelEntry.COLUMN_NAME));
        this.mDescription = cursor.getString(cursor.getColumnIndex(MarvelDatabase.MarvelEntry
            .COLUMN_DES));
        this.mAvatar = cursor.getString(cursor.getColumnIndex(MarvelDatabase.MarvelEntry
            .COLUMN_URL));
        this.mDbId = cursor.getInt(cursor.getColumnIndex(MarvelDatabase.MarvelEntry._ID));
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Thumbnail getThumnail() {
        return mThumbnail;
    }

    public void setThumnail(Thumbnail thumnail) {
        mThumbnail = thumnail;
    }

    public int getDbId() {
        return mDbId;
    }

    public void setDbId(int dbId) {
        mDbId = dbId;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }
}
