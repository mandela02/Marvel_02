package com.framgia.marvel.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.marvel.R;
import com.framgia.marvel.data.database.MarvelDataSource;
import com.framgia.marvel.data.model.Result;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.ui.activity.InformationActivity;

import java.util.List;

/**
 * Created by asus on 5/12/2017.
 */
public class CharactersAdapter
    extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
    private List<Result> mResults;
    private Context mContext;
    private boolean mIsGrid;

    public CharactersAdapter(List<Result> mResults, Context mContext, boolean isGrid) {
        this.mResults = mResults;
        this.mContext = mContext;
        this.mIsGrid = isGrid;
    }

    @Override
    public CharactersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(mIsGrid ? R.layout.grid_item : R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CharactersAdapter.ViewHolder holder, int position) {
        MarvelDataSource database = new MarvelDataSource(mContext);
        Result item = mResults.get(position);
        item.setAvatar(
            item.getThumbnail().getPath() + Const.Size.SIZE_DETAIL +
                item.getThumbnail().getExtension
                    ());
        item.setLiked(database.isInDatabse(item.getId()));
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageAvatar;
        private TextView mTextName;
        private ImageView mImageLike;
        private MarvelDataSource mDatabase;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = (ImageView) itemView.findViewById(R.id.image_avatar);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mImageLike = (ImageView) itemView.findViewById(R.id.image_like);
            mDatabase = new MarvelDataSource(mContext);
            itemView.setOnClickListener(this);
            mImageLike.setOnClickListener(this);
        }

        public void bindData(Result item) {
            if (item == null) return;
            String avatarUrl = item.getThumbnail().getPath() + Const.Size.SIZE_MEDIUM +
                item.getThumbnail().getExtension();
            mTextName.setText(item.getName());
            Glide.with(mContext).load(avatarUrl).into(mImageAvatar);
            mImageLike.setImageResource(item.isLiked() ? R.drawable
                .ic_like_red : R.drawable.ic_like_white);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card:
                    Result hero = mResults.get(getAdapterPosition());
                    ((Activity) mContext).startActivityForResult(InformationActivity.getInstance
                        (mContext, hero), Const.RequestCode.REQUEST_CODE_INFOMATION);
                    break;
                case R.id.image_like:
                    if (mResults.get(getAdapterPosition()).isLiked()) {
                        mDatabase.deleteByID(mResults.get(getAdapterPosition()).getId());
                        mResults.get(getAdapterPosition()).setLiked(false);
                        notifyItemChanged(getAdapterPosition());
                    } else {
                        mDatabase.insertCharacter(mResults.get(getAdapterPosition()));
                        mResults.get(getAdapterPosition()).setLiked(true);
                        notifyItemChanged(getAdapterPosition());
                    }
            }
        }
    }
}
