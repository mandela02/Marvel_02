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
import com.framgia.marvel.ui.activity.ComicActivity;

import java.util.List;

/**
 * Created by Bruce Wayne on 5/26/2017.
 */
public class CreatorAdapter extends RecyclerView.Adapter<CreatorAdapter.ViewHolder>{
    private Context mContext;
    private List<Result> mResults;

    public CreatorAdapter(Context context,
                             List<Result> results) {
        this.mContext = context;
        this.mResults = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
            .from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result item = mResults.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mResults != null ? mResults.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageAvatar;
        private TextView mTextName;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = (ImageView) itemView.findViewById(R.id.image_book_avatar);
            mTextName = (TextView) itemView.findViewById(R.id.text_book_name);
        }

        public void bindData(Result item) {
            if (item == null) return;
            String avatarUrl = item.getThumbnail() != null ? item.getThumbnail().getPath() +
                Const.Size.SIZE_MEDIUM +
                item.getThumbnail().getExtension() : null;
            Glide.with(mContext).load(avatarUrl).error(R.drawable.no_image).into(mImageAvatar);
            mTextName.setText(item.getFullName());
        }
    }
}
