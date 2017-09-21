package com.xianzhi.rxmvp.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xianzhi.rxmvp.R;
import com.xianzhi.rxmvp.data.testsource.Test;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yaocui on 2017/7/25.
 *
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> implements View.OnClickListener {

    private List<Test> testList;
    private LayoutInflater inflater;

    private OnListItemClickListener onListItemClickListener;

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public TestAdapter(Context context, List<Test> testList) {
        this.testList = testList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TestAdapter.ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.list_item_navgation, null));
        viewHolder.layout.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, int position) {
        Test testbean = testList.get(position);
        holder.titleTxt.setText(testbean.getTitle());
        holder.contentTxt.setText(testbean.getDescription());
        holder.layout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    @Override
    public void onClick(View v) {
        if(v.getTag() instanceof Integer&&onListItemClickListener!=null) {
            Integer position = (Integer) v.getTag();
            onListItemClickListener.onItemClick(position);
        }
    }

    public interface OnListItemClickListener{
        void onItemClick(int position);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title_txt)
        TextView titleTxt;
        @Bind(R.id.content_txt)
        TextView contentTxt;
        @Bind(R.id.layout)
        FrameLayout layout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
