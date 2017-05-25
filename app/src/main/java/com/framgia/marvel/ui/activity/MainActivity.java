package com.framgia.marvel.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.framgia.marvel.R;
import com.framgia.marvel.data.model.MarvelModel;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.service.MarvelService;
import com.framgia.marvel.service.ServiceGenerator;
import com.framgia.marvel.ui.adapter.CharactersAdapter;
import com.framgia.marvel.ui.adapter.EndlessRecyclerViewScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Result> mResults;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private CharactersAdapter mAdapter;
    private FloatingActionButton mChangeButton;
    private ProgressBar mProgressHeroes;
    private ProgressDialog mProgressDialog;
    private int mTotal;
    private int mOffset = 0;
    private int mLimit = 100;
    private boolean mIsGrid = true;
    private boolean mIsAllowLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResults = new ArrayList<>();
        initToolbar();
        initView();
        setDialog();
        getData();
        initRecyclerView();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_heroes);
        mGridLayoutManager = new GridLayoutManager(this, Const.COLUMN_NUMB);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mChangeButton = (FloatingActionButton) findViewById(R.id.btn_change);
        mChangeButton.setOnClickListener(this);
        mProgressHeroes = (ProgressBar) findViewById(R.id.progress_heroes);
    }

    public void setDialog() {
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.show();
        mProgressDialog.setMessage(getString(R.string.dialog_message));
        mProgressDialog.setTitle(R.string.dialog_title);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
    }

    public void getData() {
        mProgressHeroes.setVisibility(View.VISIBLE);
        mIsAllowLoadMore = false;
        MarvelService service = ServiceGenerator.createService(MarvelService.class);
        service.getMarvel(Const.Key.TS, Const.Key.API_KEY, Const.Key.HASH, String.valueOf
                (mOffset),
            String.valueOf(mLimit), null)
            .enqueue(new Callback<MarvelModel>() {
                @Override
                public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                    if (response != null) {
                        MarvelModel model = response.body();
                        mResults.addAll(model.getData().getResults());
                        if (model.getData().getResults().size() < mLimit) mIsAllowLoadMore = false;
                        else mIsAllowLoadMore = true;
                        mAdapter.notifyDataSetChanged();
                    }
                    mProgressDialog.dismiss();
                    mProgressHeroes.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MarvelModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    mProgressHeroes.setVisibility(View.GONE);
                    mIsAllowLoadMore = true;
                }
            });
        mOffset += mLimit;
    }

    public void initRecyclerView() {
        mAdapter = new CharactersAdapter(mResults, MainActivity.this, mIsGrid);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView
            .addOnScrollListener(new EndlessRecyclerViewScrollListener
                (mIsGrid ? mGridLayoutManager : mLinearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    if (mIsAllowLoadMore) {
                        getData();
                    }
                }
            });
    }

    @Override
    public void onClick(View view) {
        mIsGrid = !mIsGrid;
        mRecyclerView
            .setLayoutManager(mIsGrid ? mGridLayoutManager : mLinearLayoutManager);
        mChangeButton.setImageResource(mIsGrid ? R.drawable.ic_list : R.drawable.ic_options);
        initRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Const.RequestCode.REQUEST_CODE_INFOMATION:
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}