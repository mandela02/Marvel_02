package com.framgia.marvel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by asus on 5/25/2017.
 */
public class SearchFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private CharactersAdapter mAdapter;
    private List<Result> mResults;
    private int mOffset = 0;
    private int mLimit = 100;
    private String mName;

    public SearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        mName = getArguments().getString(Const.Bundle.BUNDLE_SEARCH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frame_content, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_search);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), Const.COLUMN_NUMB));
        mResults = new ArrayList<>();
        mAdapter = new CharactersAdapter(mResults, getContext(), true, false);
        mRecyclerView.setAdapter(mAdapter);
        getData();
        return v;
    }

    public void getData() {
        MarvelService service = ServiceGenerator.createService(MarvelService.class);
        service.getMarvel(Const.Key.TS, Const.Key.API_KEY, Const.Key.HASH, String.valueOf
                (mOffset),
            String.valueOf(mLimit), null, mName)
            .enqueue(new Callback<MarvelModel>() {
                @Override
                public void onResponse(Call<MarvelModel> call, Response<MarvelModel> response) {
                    if (response != null) {
                        MarvelModel model = response.body();
                        if (model.getData() != null) {
                            mResults.addAll(model.getData().getResults());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MarvelModel> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
