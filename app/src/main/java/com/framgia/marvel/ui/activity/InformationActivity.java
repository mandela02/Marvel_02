package com.framgia.marvel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.database.MarvelDataSource;
import com.framgia.marvel.data.model.MarvelModel;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.service.CollectionService;
import com.framgia.marvel.service.ServiceGenerator;
import com.framgia.marvel.ui.adapter.CollectionAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mButtonLike;
    private TextView mTextDes;
    private ImageView mImageAva;
    private RecyclerView mRecyclerComics;
    private RecyclerView mRecyclerEvents;
    private RecyclerView mRecyclerSeries;
    private CollectionAdapter mAdapterComics, mAdapterSeries, mAdapterEvents;
    private Result mResult;
    private MarvelDataSource mDatabase;
    private boolean mIsLiked;
    private List<Result> mComics, mSeries, mEvents;
    private int mLimit = 100;

    public static Intent getInstance(Context context, Result result) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra(Const.Extra.EXTRA_NAME, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_information);
        initView();
        getData();
        initToolbar();
        displayData(mResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_like:
                mIsLiked = !mIsLiked;
                if (mIsLiked) {
                    addFavorite(mResult);
                } else {
                    removeFavorite(mResult);
                }
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                mButtonLike
                    .setImageResource(mIsLiked ? R.drawable.ic_like_red : R.drawable
                        .ic_like_white);
                Snackbar.make(v, mIsLiked ? R.string.add_message :
                        R.string.delete_message,
                    Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
                break;
            case R.id.image_avatar_infor:
                startActivity(DisplayImageActivity.getInstance(this, mResult.getAvatar()));
                break;
        }
    }

    private void addFavorite(Result result) {
        mDatabase.insertCharacter(result);
        result.setLiked(true);
    }

    private void removeFavorite(Result result) {
        mDatabase.deleteByID(result.getId());
        result.setLiked(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(mResult.getName());
    }

    public void initView() {
        mTextDes = (TextView) findViewById(R.id.text_description);
        mImageAva = (ImageView) findViewById(R.id.image_avatar_infor);
        mButtonLike = (FloatingActionButton) findViewById(R.id.btn_like);
        mButtonLike.setOnClickListener(this);
        mImageAva.setOnClickListener(this);
    }

    public void getData() {
        Intent intent = getIntent();
        mResult = intent.getParcelableExtra(Const.Extra.EXTRA_NAME);
        mIsLiked = mResult.isLiked();
        mDatabase = new MarvelDataSource(getApplicationContext());
        initComicsView();
        initSeriesView();
        initEventsView();
    }

    public void initComicsView() {
        mComics = new ArrayList<>();
        mRecyclerComics = (RecyclerView) findViewById(R.id.recycler_infor_comics);
        mRecyclerComics
            .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mResult.getComics() != null) {
            if (mResult.getComics().getAvailable() != 0)
                getBookData(mComics, String.valueOf(mResult.getId()), Const.TYPE[0]);
            else findViewById(R.id.text_infor_comic).setVisibility(View.GONE);
        } else findViewById(R.id.text_infor_comic).setVisibility(View.GONE);
        mAdapterComics = new CollectionAdapter(InformationActivity.this, mComics);
        mRecyclerComics.setAdapter(mAdapterComics);
    }

    public void initSeriesView() {
        mSeries = new ArrayList<>();
        mRecyclerSeries = (RecyclerView) findViewById(R.id.recycler_infor_series);
        mRecyclerSeries
            .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mResult.getSeries() != null) {
            if (mResult.getSeries().getAvailable() != 0)
                getBookData(mSeries, String.valueOf(mResult.getId()), Const.TYPE[1]);
            else findViewById(R.id.text_infor_series).setVisibility(View.GONE);
        } else findViewById(R.id.text_infor_series).setVisibility(View.GONE);
        mAdapterSeries = new CollectionAdapter(InformationActivity.this, mSeries);
        mRecyclerSeries.setAdapter(mAdapterSeries);
    }

    public void initEventsView() {
        mEvents = new ArrayList<>();
        mRecyclerEvents = (RecyclerView) findViewById(R.id.recycler_infor_events);
        mRecyclerEvents
            .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mResult.getEvents() != null) {
            if (mResult.getEvents().getAvailable() != 0)
                getBookData(mEvents, String.valueOf(mResult.getId()), Const.TYPE[2]);
            else findViewById(R.id.text_infor_events).setVisibility(View.GONE);
        } else findViewById(R.id.text_infor_events).setVisibility(View.GONE);
        mAdapterEvents = new CollectionAdapter(InformationActivity.this, mEvents);
        mRecyclerEvents.setAdapter(mAdapterEvents);
    }

    public void getBookData(final List<Result> list, final String CollectionId, final String type) {
        CollectionService service = ServiceGenerator.createService(CollectionService.class);
        service.getMarvel(Const.TYPE[5], CollectionId, type, Const.Key.TS, Const.Key.API_KEY, Const
            .Key.HASH, String.valueOf(mLimit)).enqueue(new Callback<MarvelModel>() {
            @Override
            public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                if (response != null) {
                    MarvelModel model = response.body();
                    list.addAll(model.getData().getResults());
                    mAdapterComics.notifyDataSetChanged();
                    mAdapterSeries.notifyDataSetChanged();
                    mAdapterEvents.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MarvelModel> call, Throwable t) {
                Toast.makeText(InformationActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                    .show();
            }
        });
    }

    private void displayData(Result result) {
        Glide.with(InformationActivity.this).load(result.getAvatar()).centerCrop().into(mImageAva);
        if (result.getDescription().equals("")) {
            mTextDes.setVisibility(View.GONE);
            findViewById(R.id.empty_state).setVisibility(View.VISIBLE);
        } else {
            mTextDes.setText(getString(R.string.eleven_tab) +
                result.getDescription());
        }
        mButtonLike
            .setImageResource(mIsLiked ? R.drawable.ic_like_red : R.drawable.ic_like_white);
    }
}
