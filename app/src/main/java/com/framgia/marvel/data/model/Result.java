package com.framgia.marvel.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.framgia.marvel.data.database.MarvelDatabase;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 5/12/2017.
 */
public class Result implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("thumbnail")
    private Thumbnail mThumbnail;
    @SerializedName("comics")
    private Collection mComics;
    @SerializedName("series")
    private Collection mSeries;
    @SerializedName("stories")
    private Collection mStories;
    @SerializedName("events")
    private Collection mEvents;
    private int mDbId;
    private String mAvatar;
    private boolean mIsLiked;

    public Result(String avatar) {
        mAvatar = avatar;
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

    protected Result(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mThumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        mComics = in.readParcelable(Collection.class.getClassLoader());
        mSeries = in.readParcelable(Collection.class.getClassLoader());
        mStories = in.readParcelable(Collection.class.getClassLoader());
        mEvents = in.readParcelable(Collection.class.getClassLoader());
        mDbId = in.readInt();
        mAvatar = in.readString();
        mIsLiked = in.readByte() != 0;
    }

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

    public Collection getComics() {
        return mComics;
    }

    public void setComics(Collection comics) {
        mComics = comics;
    }

    public Collection getSeries() {
        return mSeries;
    }

    public void setSeries(Collection series) {
        mSeries = series;
    }

    public Collection getStories() {
        return mStories;
    }

    public void setStories(Collection stories) {
        mStories = stories;
    }

    public Collection getEvents() {
        return mEvents;
    }

    public void setEvents(Collection events) {
        mEvents = events;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    public void setLiked(boolean liked) {
        mIsLiked = liked;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeParcelable(mThumbnail, flags);
        dest.writeParcelable(mComics, flags);
        dest.writeParcelable(mSeries, flags);
        dest.writeParcelable(mStories, flags);
        dest.writeParcelable(mEvents, flags);
        dest.writeInt(mDbId);
        dest.writeString(mAvatar);
        dest.writeByte((byte) (mIsLiked ? 1 : 0));
    }
}
