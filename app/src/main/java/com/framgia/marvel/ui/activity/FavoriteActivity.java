package com.framgia.marvel.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.framgia.marvel.R;
import com.framgia.marvel.data.database.MarvelDataSource;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.ui.adapter.CharactersAdapter;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Result> mResults;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private CharactersAdapter mAdapter;
    private FloatingActionButton mChangeButton;
    private MarvelDataSource mDatabase;
    private boolean mIsGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initToolbar();
        initView();
        getData();
        initRecyclerView();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.action_favorite);
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_heroes);
        mGridLayoutManager = new GridLayoutManager(this, Const.COLUMN_NUMB);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mChangeButton = (FloatingActionButton) findViewById(R.id.btn_change);
        mChangeButton.setOnClickListener(this);
    }

    public void getData() {
        mDatabase = new MarvelDataSource(getApplicationContext());
        mResults = mDatabase.getAllChar();
    }

    public void initRecyclerView() {
        mAdapter = new CharactersAdapter(mResults, FavoriteActivity.this, mIsGrid, true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                mIsGrid=!mIsGrid;
                mRecyclerView
                    .setLayoutManager(mIsGrid ? mGridLayoutManager : mLinearLayoutManager);
                mChangeButton
                    .setImageResource(mIsGrid ? R.drawable.ic_list : R.drawable.ic_options);
                initRecyclerView();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Const.RequestCode.REQUEST_CODE_INFOMATION:
                mResults = mDatabase.getAllChar();
                initRecyclerView();
                break;
            default:
                break;
        }
    }
}
