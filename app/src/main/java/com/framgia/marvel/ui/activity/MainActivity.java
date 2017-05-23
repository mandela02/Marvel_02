package com.framgia.marvel.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.framgia.marvel.R;
import com.framgia.marvel.data.model.MarvelModel;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.service.MarvelService;
import com.framgia.marvel.service.ServiceGenerator;
import com.framgia.marvel.ui.adapter.CharactersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Result> mResults;
    private RecyclerView mRecyclerView;
    private CharactersAdapter mAdapter;
    private FloatingActionButton mChangeButton;
    private int mOffset = 0;
    private int mLimit = 100;
    private boolean mIsGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResults = new ArrayList<>();
        initToolbar();
        initView();
        getData();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_heroes);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, Const.COLUMN_NUMB));
        mChangeButton = (FloatingActionButton) findViewById(R.id.btn_change);
        mChangeButton.setOnClickListener(this);
    }

    public void getData() {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.show();
        dialog.setMessage(getString(R.string.dialog_message));
        dialog.setTitle(R.string.dialog_title);
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        MarvelService service = ServiceGenerator.createService(MarvelService.class);
        service.getMarvel(Const.Key.TS, Const.Key.API_KEY, Const.Key.HASH, String.valueOf
                (mOffset),
            String.valueOf(mLimit), null)
            .enqueue(new Callback<MarvelModel>() {
                @Override
                public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                    if (response != null) {
                        MarvelModel model = response.body();
                        mResults = model.getData().getResults();
                        mAdapter = new CharactersAdapter(mResults, MainActivity.this, mIsGrid);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<MarvelModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
    }

    @Override
    public void onClick(View view) {
        mIsGrid = !mIsGrid;
        mRecyclerView
            .setLayoutManager(mIsGrid ? new GridLayoutManager(this, Const.COLUMN_NUMB) : new
                LinearLayoutManager(this));
        mAdapter = new CharactersAdapter(mResults, MainActivity.this, mIsGrid);
        mRecyclerView.setAdapter(mAdapter);
        mChangeButton.setImageResource(mIsGrid ? R.drawable.ic_list : R.drawable.ic_options);
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