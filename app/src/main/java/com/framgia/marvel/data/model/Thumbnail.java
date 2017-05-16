package com.framgia.marvel.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 5/12/2017.
 */
public class Thumbnail {
    @SerializedName("path")
    private String mPath;
    @SerializedName("extension")
    private String mExtension;

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getExtension() {
        return mExtension;
    }

    public void setExtension(String extension) {
        mExtension = extension;
    }
}
