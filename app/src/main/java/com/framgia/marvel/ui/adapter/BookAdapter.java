package com.framgia.marvel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.widget.LinearLayout;
>>>>>>> 45ed1faa6880e149d346a49b3ec64145ca4c5e2b
import android.widget.TextView;

import com.framgia.marvel.R;
import com.framgia.marvel.data.model.Data;

import java.util.List;

/**
 * Created by Bruce Wayne on 5/22/2017.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context mContext;
    private List<Data> mDatas;

    public BookAdapter(Context context,
                       List<Data> results) {
        this.mContext = context;
        this.mDatas = results;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
            .from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {
        List item = mDatas.get(position).getResults();
        holder.mTextInfor.setText(mDatas.get(position).getHeadTitle());
<<<<<<< HEAD
        CollectionAdapter adapter = new CollectionAdapter(mContext,item);
        holder.mRecyclerInfor.setLayoutManager(new LinearLayoutManager(mContext));
=======
        CollectionAdapter adapter = new CollectionAdapter(mContext, item);
        holder.mRecyclerInfor.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout
            .HORIZONTAL, false));
>>>>>>> 45ed1faa6880e149d346a49b3ec64145ca4c5e2b
        holder.mRecyclerInfor.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD
        return mDatas!=null?mDatas.size():0;
=======
        return mDatas != null ? mDatas.size() : 0;
>>>>>>> 45ed1faa6880e149d346a49b3ec64145ca4c5e2b
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextInfor;
        private RecyclerView mRecyclerInfor;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextInfor = (TextView) itemView.findViewById(R.id.text_book_infor);
            mRecyclerInfor = (RecyclerView) itemView.findViewById(R.id.recycler_book_infor);
        }
    }
}
