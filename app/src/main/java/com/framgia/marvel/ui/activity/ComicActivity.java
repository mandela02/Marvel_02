package com.framgia.marvel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.framgia.marvel.data.model.MarvelModel;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.service.CollectionService;
import com.framgia.marvel.service.ServiceGenerator;
import com.framgia.marvel.ui.adapter.CharactersAdapter;
import com.framgia.marvel.ui.adapter.CreatorAdapter;
import com.framgia.marvel.ui.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicActivity extends AppCompatActivity {
    private Result mResult;
    private TextView mTextIsbn;
    private TextView mTextDes;
    private TextView mTextTime;
    private ImageView mImageAvatar;
    private RecyclerView mCreatorRecycler;
    private CreatorAdapter mCreatorAdapter;
    private RecyclerView mCharacterRecycler;
    private CharactersAdapter mCharacterAdapter;
    private List<Result> mCharacterList;
    private List<Result> mCreatorList;
    private RecyclerView mImageRecycler;
    private ImageAdapter mImageAdapter;

    public static Intent getInstance(Context context, Result result) {
        Intent intent = new Intent(context, ComicActivity.class);
        intent.putExtra(Const.Extra.EXTRA_COMIC, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comic);
        Intent intent = getIntent();
        mResult = intent.getParcelableExtra(Const.Extra.EXTRA_COMIC);
        initToolBar();
        initView();
        displayData(mResult);
        initCreatorView();
        initCharacterView();
        setImage();
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

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(mResult.getTitle());
    }

    private void initCreatorView() {
        mCreatorList = new ArrayList<>();
        mCreatorRecycler = (RecyclerView) findViewById(R.id.recycler_comic_creator);
        mCreatorRecycler.
            setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mResult.getCreators().getAvailable() != 0)
            getData(mCreatorList, String.valueOf(mResult.getId()), Const.TYPE[4]);
        else findViewById(R.id.text_comic_creator).setVisibility(View.GONE);
        mCreatorAdapter = new CreatorAdapter(ComicActivity.this, mCreatorList);
        mCreatorRecycler.setAdapter(mCreatorAdapter);
        mCreatorRecycler.setHasFixedSize(true);
    }

    private void initCharacterView() {
        mCharacterList = new ArrayList<>();
        mCharacterRecycler = (RecyclerView) findViewById(R.id.recycler_comic_character);
        mCharacterRecycler
            .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mResult.getCharacters().getAvailable() != 0)
            getData(mCharacterList, String.valueOf(mResult.getId()), Const.TYPE[5]);
        else findViewById(R.id.text_comic_character).setVisibility(View.GONE);
        mCharacterAdapter = new CharactersAdapter(mCharacterList, ComicActivity.this, true, false);
        mCharacterRecycler.setAdapter(mCharacterAdapter);
        mCharacterRecycler.setHasFixedSize(true);
    }

    private void setImage() {
        mImageRecycler = (RecyclerView) findViewById(R.id.recycler_comic_image);
        mImageRecycler
            .setLayoutManager(new GridLayoutManager(this, Const.COLUMN_NUMB));
        if (mResult.getImages() != null)
            mImageAdapter = new ImageAdapter(ComicActivity.this, mResult.getImages());
        else findViewById(R.id.text_comic_image).setVisibility(View.GONE);
        mImageRecycler.setAdapter(mImageAdapter);
    }

    private void initView() {
        mTextIsbn = (TextView) findViewById(R.id.text_comic_isbn);
        mTextDes = (TextView) findViewById(R.id.text_comic_description);
        mTextTime = (TextView) findViewById(R.id.text_comic_time);
        mImageAvatar = (ImageView) findViewById(R.id.image_comic_avatar);
    }

    private void displayData(Result result) {
        Glide.with(ComicActivity.this).load(result.getAvatar()).centerCrop().into(mImageAvatar);
        mTextIsbn.setText(result.getIsbn() == null ? getString(R.string.isbn_message)
            : (result.getIsbn().equals("") ? getString(R.string.isbn_message) : getString(R
            .string.isbn) + result.getIsbn()));
        if (result.getDescription() == null) {
            mTextDes.setVisibility(View.GONE);
            mTextIsbn.setVisibility(View.GONE);
            findViewById(R.id.empty_state).setVisibility(View.VISIBLE);
        } else {
            if (result.getDescription().equals("")) {
                mTextDes.setVisibility(View.GONE);
                mTextIsbn.setVisibility(View.GONE);
                findViewById(R.id.empty_state).setVisibility(View.VISIBLE);
            } else
                mTextDes.setText(getString(R.string.eleven_tab) + result.getDescription());
        }
        if (result.getStartYear() != 0) {
            mTextTime.setText(getString(R.string.start_year) + result.getStartYear() + getString
                (R.string.end_year) +
                result.getEndYear());
        } else mTextTime.setVisibility(View.GONE);
    }

    public void getData(final List<Result> list, final String CollectionId, final String type) {
        CollectionService service = ServiceGenerator.createService(CollectionService.class);
        service.getMarvel(Const.TYPE[0], CollectionId, type, Const.Key.TS, Const.Key.API_KEY, Const
            .Key.HASH, null).enqueue(new Callback<MarvelModel>() {
            @Override
            public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                if (response != null) {
                    MarvelModel model = response.body();
                    list.addAll(model.getData().getResults());
                    mCharacterAdapter.notifyDataSetChanged();
                    mCreatorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MarvelModel> call, Throwable t) {
                Toast.makeText(ComicActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                    .show();
            }
        });
    }
}
