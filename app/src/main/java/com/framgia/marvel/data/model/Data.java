package com.framgia.marvel.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus on 5/12/2017.
 */
public class Data {
    @SerializedName("total")
    private int mTotal;
    @SerializedName("results")
    private List<Result> mResults;
    private String mHeadTitle;

    public Data(String title,List<Result> results) {
        this.mResults = results;
        this.mHeadTitle = title;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(
        List<Result> results) {
        mResults = results;
    }

    public String getHeadTitle() {
        return mHeadTitle;
    }

    public void setHeadTitle(String title) {
        mHeadTitle = title;
    }
}
