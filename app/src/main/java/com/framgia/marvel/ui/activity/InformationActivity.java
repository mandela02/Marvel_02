package com.framgia.marvel.ui.activity;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.model.MarvelModel;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.service.MarvelService;
import com.framgia.marvel.service.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.EXTRA_USER;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mButtonLike;
    private TextView mTextId;
    private TextView mTextDes;
    private ImageView mImageAva;
    private Result mResult;
    private String mName;
    private boolean mIsLiked;

    public static Intent getInstance(Context context, String name) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra(Const.EXTRA_NAME, name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextDes = (TextView) findViewById(R.id.text_description);
        mTextId = (TextView) findViewById(R.id.text_id);
        mImageAva = (ImageView) findViewById(R.id.image_avatar_infor);
        Intent intent = getIntent();
        mName = intent.getParcelableExtra(Const.EXTRA_NAME);
        setTitle(mName);
        getData();
        mButtonLike = (FloatingActionButton) findViewById(R.id.btn_like);
        mButtonLike.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_like:
                //TODO: Add character to favorite list
                mIsLiked = !mIsLiked;
                mButtonLike
                    .setImageResource(mIsLiked ? R.drawable.ic_like_red : R.drawable.ic_like_white);
                Snackbar.make(v, mIsLiked ? R.string.add_message : R.string.delete_message,
                    Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
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

    public void getData() {
        final ProgressDialog dialog = new ProgressDialog(InformationActivity.this);
        dialog.show();
        dialog.setMessage(getString(R.string.dialog_message));
        dialog.setTitle(R.string.dialog_title);
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        MarvelService service = ServiceGenerator.createService(MarvelService.class);
        service.getMarvel(Const.TS, Const.API_KEY, Const.HASH, null,
            null, mName)
            .enqueue(new Callback<MarvelModel>() {
                @Override
                public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                    if (response != null) {
                        MarvelModel model = response.body();
                        mResult = model.getData().getResults().get(0);
                        mResult.setAvatar(mResult.getThumbnail().getPath() + Const.SIZE_DETAIL +
                            mResult.getThumbnail().getExtension());
                        displayData(mResult);
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<MarvelModel> call, Throwable t) {
                    Toast.makeText(InformationActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                    dialog.dismiss();
                }
            });
    }

    private void displayData(Result result) {
        String avatar = result.getThumbnail().getPath() + Const.SIZE_DETAIL + result.getThumbnail()
            .getExtension();
        Glide.with(InformationActivity.this).load(avatar).into(mImageAva);
        mTextId.setText(getString(R.string.id) + result.getId());
        if (result.getDescription().equals(""))
            mTextDes.setText("\t" + getString(R.string.message));
        else {
            mTextDes.setText(getString(R.string.description) + mResult.getDescription());
        }
    }
}
