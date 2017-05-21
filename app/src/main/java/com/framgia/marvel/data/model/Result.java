package com.framgia.marvel.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.framgia.marvel.data.database.MarvelDatabase;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asus on 5/12/2017.
 */
public class Result implements Parcelable {
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
    private boolean mIsLiked;
    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    protected Result(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
        mDbId = in.readInt();
        mAvatar = in.readString();
        mIsLiked = in.readByte() != 0;
    }

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

    public Thumbnail getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        mThumbnail = thumbnail;
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

    public boolean isLiked() {
        return mIsLiked;
    }

    public void setLiked(boolean liked) {
        mIsLiked = liked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeInt(mDbId);
        parcel.writeString(mAvatar);
        parcel.writeByte((byte) (mIsLiked ? 1 : 0));
    }
}
