/*
 * Created on 01/02/18 18:51 by Peter Mulligan.
 * Copyright (c) 2018.
 */

package eu.flatworks.cobwebcleaner.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        Drawable icon = mData.get(position).getIcon();
        String name = mData.get(position).getName();
        holder.appIcon.setImageDrawable(icon);
        holder.appName.setText(name);
        String lastUsed = getDateFromTimeStamp(mData.get(position).getLastUsedTime());
        holder.appLastUsed.setText(lastUsed);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.main_iv_appIcon)
        ImageView appIcon;
        @BindView(R.id.main_tv_appName)
        TextView appName;
        @BindView(R.id.main_tv_lastUsed)
        TextView appLastUsed;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        //called when item is clicked
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at id position
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

    private String getDateFromTimeStamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy - HH:mm");
        String dateString;
        if(timestamp != 0) {
            dateString = format.format(date);
        }
        else {
            dateString = "N/A";
        }
        return dateString;
    }
}
