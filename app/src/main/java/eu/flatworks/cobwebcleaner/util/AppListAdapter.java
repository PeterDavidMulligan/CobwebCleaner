/*
 * Created on 01/02/18 18:51 by Peter Mulligan.
 * Copyright (c) 2018.
 */

package eu.flatworks.cobwebcleaner.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import eu.flatworks.cobwebcleaner.R;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
    private static final String TAG = AppListAdapter.class.getSimpleName();

    private List<AppListItem> mData = Collections.EMPTY_LIST;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public AppListAdapter(Context context, List<AppListItem> data) {
        this.mInflater = LayoutInflater.from(context);
        mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String appName = mData.get(position).getName();
        holder.appNameTextView.setText(appName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView appNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            appNameTextView = (TextView) itemView.findViewById(R.id.main_tv_appName);
            itemView.setOnClickListener(this);
        }

        //called when item is clicked
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public AppListItem getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
