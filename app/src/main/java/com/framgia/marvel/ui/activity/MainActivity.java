package com.framgia.marvel.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.framgia.marvel.R;
import com.framgia.marvel.data.model.MarvelModel;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.service.MarvelService;
import com.framgia.marvel.service.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private int mOffset = 0;
    private int mLimit = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getData() {
        MarvelService service = ServiceGenerator.createService(MarvelService.class);

        service.getMarvel(Const.TS, Const.API_KEY, Const.HASH, String.valueOf(mOffset), String
                .valueOf
                (mLimit),
            null)
                .enqueue(new Callback<MarvelModel>() {
                    @Override
                    public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                        if (response != null) {
                            //TODO: get data from api
                        }
                    }

                    @Override
                    public void onFailure(Call<MarvelModel> call, Throwable t) {

                    }
                });
    }
}