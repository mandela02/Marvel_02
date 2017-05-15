package com.framgia.marvel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;

import java.util.List;

/**
 * Created by asus on 5/12/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Result> mResults;
    private Context mContext;
    private boolean mIsGrid;

    public RecyclerAdapter(List<Result> mResults, Context mContext, boolean isGrid) {
        this.mResults = mResults;
        this.mContext = mContext;
        this.mIsGrid = isGrid;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(mIsGrid ? R.layout.grid_item : R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Result item = mResults.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageAvatar;
        private TextView mTextName;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = (ImageView) itemView.findViewById(R.id.image_avatar);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
        }

        public void bindData(Result item) {
            if (item == null) return;
            String avatarUrl = item.getThumnail().getPath() + Const.SIZE_MEDIUM +
                item.getThumnail().getExtension();
            mTextName.setText(item.getName());
            Glide.with(mContext).load(avatarUrl).into(mImageAvatar);
        }
    }
}
