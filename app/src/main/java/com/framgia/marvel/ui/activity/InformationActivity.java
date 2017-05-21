package com.framgia.marvel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.database.MarvelDataSource;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mButtonLike;
    private TextView mTextId;
    private TextView mTextDes;
    private ImageView mImageAva;
    private Result mResult;
    private MarvelDataSource mDatabase;
    private boolean mIsLiked;

    public static Intent getInstance(Context context, Result result) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra(Const.Extra.EXTRA_NAME, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(mResult.getName());
    }

    public void initView() {
        mTextDes = (TextView) findViewById(R.id.text_description);
        mTextId = (TextView) findViewById(R.id.text_id);
        mImageAva = (ImageView) findViewById(R.id.image_avatar_infor);
        mButtonLike = (FloatingActionButton) findViewById(R.id.btn_like);
        mButtonLike.setOnClickListener(this);
    }

    public void getData() {
        Intent intent = getIntent();
        mResult = intent.getParcelableExtra(Const.Extra.EXTRA_NAME);
        mIsLiked = mResult.isLiked();
        mDatabase = new MarvelDataSource(getApplicationContext());
    }

    private void displayData(Result result) {
        String avatar = result.getAvatar();
        Glide.with(InformationActivity.this).load(avatar).into(mImageAva);
        mTextId.setText(getString(R.string.id) + result.getId());
        if (result.getDescription().equals(""))
            mTextDes.setText("\t" + getString(R.string.message));
        else {
            mTextDes.setText(getString(R.string.description) + result.getDescription());
        }
        mButtonLike
            .setImageResource(mIsLiked ? R.drawable.ic_like_red : R.drawable.ic_like_white);
    }
}
