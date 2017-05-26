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
import com.framgia.marvel.data.model.Thumbnail;
import com.framgia.marvel.data.value.Const;
import com.framgia.marvel.ui.activity.DisplayImageActivity;

import java.util.List;

/**
 * Created by Bruce Wayne on 5/26/2017.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private Context mContext;
    private List<Thumbnail> mUrl;

    public ImageAdapter(Context context,
                          List<Thumbnail> mUrl) {
        this.mContext = context;
        this.mUrl = mUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
            .from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Thumbnail item = mUrl.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mUrl != null ? mUrl.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String avatarUrl = mUrl.get(getAdapterPosition()).getPath() + Const.Size.SIZE_DETAIL + mUrl.get(getAdapterPosition()).getExtension();
                    mContext.startActivity(DisplayImageActivity.getInstance(mContext, avatarUrl));
                }
            });
        }

        public void bindData(Thumbnail item) {
            if (item == null) return;
            String avatarUrl = item.getPath() + Const.Size.SIZE_DETAIL + item.getExtension();
            Glide.with(mContext).load(avatarUrl).error(R.drawable.no_image).into(mImage);
        }
    }

}
