package com.framgia.marvel.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.database.MarvelDataSource;
import com.framgia.marvel.data.model.Data;
import com.framgia.marvel.data.model.MarvelModel;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.service.CollectionService;
import com.framgia.marvel.service.ServiceGenerator;
import com.framgia.marvel.ui.adapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mButtonLike;
    private TextView mTextDes;
    private ImageView mImageAva;
    private RecyclerView mRecycler;
    private BookAdapter mAdapter;
    private Result mResult;
    private MarvelDataSource mDatabase;
    private boolean mIsLiked;
    private List<Data> mBookData;
    private List<Result> mBookInfor;
    private int mLimit = 100;

    public static Intent getInstance(Context context, Result result) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra(Const.Extra.EXTRA_NAME, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mBookData = new ArrayList<>();
        mBookInfor = new ArrayList<>();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_zoom:
                startActivity(DisplayImageActivity.getInstance(this, mResult));
                break;
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
    }

    public void getData() {
        Intent intent = getIntent();
        mResult = intent.getParcelableExtra(Const.Extra.EXTRA_NAME);
        mIsLiked = mResult.isLiked();
        mDatabase = new MarvelDataSource(getApplicationContext());
        mRecycler = (RecyclerView) findViewById(R.id.recycler_title_infor);
        mRecycler.setLayoutManager(new LinearLayoutManager(InformationActivity.this));
        if (mResult.getComics().getAvailable() != 0) getBookData(String.valueOf(mResult.getId()),
            Const.TYPE[0]);
        if (mResult.getSeries().getAvailable() != 0) getBookData(String.valueOf(mResult.getId()),
            Const.TYPE[1]);
        if (mResult.getEvents().getAvailable() != 0) getBookData(String.valueOf(mResult.getId()),
            Const.TYPE[2]);
    }

    public void getBookData(final String CollectionId, final String type) {
        final ProgressDialog dialog = new ProgressDialog(InformationActivity.this);
        dialog.show();
        dialog.setMessage(getString(R.string.dialog_message));
        dialog.setTitle(R.string.dialog_title);
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        CollectionService service = ServiceGenerator.createService(CollectionService.class);
        service.getMarvel(CollectionId, type, Const.Key.TS, Const.Key.API_KEY, Const.Key.HASH,
            String.valueOf(mLimit)).enqueue(new Callback<MarvelModel>() {
            @Override
            public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                if (response != null) {
                    MarvelModel model = response.body();
                    mBookInfor = model.getData().getResults();
                    mBookData
                        .add(new Data(type, mBookInfor));
                    mAdapter = new BookAdapter(InformationActivity.this, mBookData);
                    mRecycler.setAdapter(mAdapter);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MarvelModel> call, Throwable t) {
                Toast.makeText(InformationActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                    .show();
                dialog.dismiss();
            }
        });
    }

    private void displayData(Result result) {
        Glide.with(InformationActivity.this).load(result.getAvatar()).into(mImageAva);
        if (result.getDescription().equals(""))
            mTextDes.setText(getString(R.string.eleven_tab) +
                getString(R.string.message));
        else {
            mTextDes.setText(getString(R.string.eleven_tab) + result.getDescription());
        }
        mButtonLike
            .setImageResource(mIsLiked ? R.drawable.ic_like_red : R.drawable.ic_like_white);
    }
}
