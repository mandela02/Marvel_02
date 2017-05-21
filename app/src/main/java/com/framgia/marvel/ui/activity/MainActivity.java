package com.framgia.marvel.ui.activity;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
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
import com.framgia.marvel.ui.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Result> mResults;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private FloatingActionButton mChangeButton;
    private int mOffset = 0;
    private int mLimit = 100;
    private boolean mIsGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResults = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_heroes);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mChangeButton = (FloatingActionButton) findViewById(R.id.btn_change);
        mChangeButton.setOnClickListener(this);
        getData();
    }

    public void getData() {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.show();
        dialog.setMessage(getString(R.string.dialog_message));
        dialog.setTitle(R.string.dialog_title);
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        MarvelService service = ServiceGenerator.createService(MarvelService.class);
        service.getMarvel(Const.TS, Const.API_KEY, Const.HASH, String.valueOf(mOffset),
            String.valueOf(mLimit), null)
            .enqueue(new Callback<MarvelModel>() {
                @Override
                public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                    if (response != null) {
                        MarvelModel model = response.body();
                        mResults = model.getData().getResults();
                        mAdapter = new RecyclerAdapter(mResults, MainActivity.this, mIsGrid);
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
        mAdapter = new RecyclerAdapter(mResults, MainActivity.this, mIsGrid);
        mRecyclerView.setAdapter(mAdapter);
        mChangeButton.setImageResource(mIsGrid ? R.drawable.ic_list : R.drawable.ic_options);
    }
}