package com.framgia.marvel.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 5/12/2017.
 */
public class MarvelModel {
    @SerializedName("copyright")
    private String mCopyright;
    @SerializedName("data")
    private Data mData;

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }
}
