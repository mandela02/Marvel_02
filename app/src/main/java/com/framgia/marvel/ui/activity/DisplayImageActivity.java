package com.framgia.marvel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;

import static android.content.Intent.EXTRA_USER;

public class DisplayImageActivity extends AppCompatActivity {
    public static Intent getInstance(Context context, Result result) {
        Intent intent = new Intent(context, DisplayImageActivity.class);
        intent.putExtra(Const.EXTRA_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        Intent intent = getIntent();
        Result result = intent.getParcelableExtra(Const.EXTRA_RESULT);
        setTitle(result.getName());
        Glide.with(this).load(result.getAvatar()).into((ImageView) findViewById(R.id.image_full));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
