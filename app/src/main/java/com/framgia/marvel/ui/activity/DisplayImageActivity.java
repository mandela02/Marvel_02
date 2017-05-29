package com.framgia.marvel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.model.Thumbnail;
import com.framgia.marvel.data.value.Const;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DisplayImageActivity extends AppCompatActivity {
    private ImageView mImage;
    public static Intent getInstance(Context context, String thumbnail) {
        Intent intent = new Intent(context, DisplayImageActivity.class);
        intent.putExtra(Const.Extra.EXTRA_RESULT, thumbnail);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);
        setTitle("");
        Intent intent = getIntent();
        mImage = (ImageView) findViewById(R.id.image_full);
        String thumbnail = intent.getStringExtra(Const.Extra.EXTRA_RESULT);
        Glide.with(this).load(thumbnail).into(mImage);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mImage);
        photoViewAttacher.update();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
