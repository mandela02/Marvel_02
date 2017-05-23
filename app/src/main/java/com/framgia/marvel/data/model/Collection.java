package com.framgia.marvel.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Bruce Wayne on 5/22/2017.
 */
public class Collection implements Parcelable {
    @SerializedName("available")
    private int mAvailable;
    @SerializedName("returned")
    private int mReturned;
    @SerializedName("collectionURI")
    private String mCollectionURI;

    protected Collection(Parcel in) {
        mAvailable = in.readInt();
        mReturned = in.readInt();
        mCollectionURI = in.readString();
    }

    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    public int getAvailable() {
        return mAvailable;
    }

    public void setAvailable(int available) {
        mAvailable = available;
    }

    public int getReturned() {
        return mReturned;
    }

    public void setReturned(int returned) {
        mReturned = returned;
    }

    public String getCollectionURI() {
        return mCollectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        mCollectionURI = collectionURI;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAvailable);
        dest.writeInt(mReturned);
        dest.writeString(mCollectionURI);
    }
}
