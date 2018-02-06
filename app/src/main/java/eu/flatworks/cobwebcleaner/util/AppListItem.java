/*
 * Created on 01/02/18 22:35 by Peter Mulligan.
 * Copyright (c) 2018.
 */

package eu.flatworks.cobwebcleaner.util;

import android.graphics.drawable.Drawable;

/**
 * AppListItem contains all the details needed for a single
 * user app installed on the device.
 */
public class AppListItem {
    private String mName;
    private long mLastUsedTime;
    private long mTotalUptime;
    Drawable mIcon;

    public AppListItem(String name, Drawable icon, long lastUsed, long totalUptime) {
        mName = name;
        mIcon = icon;
        mLastUsedTime = lastUsed;
        mTotalUptime = totalUptime;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }
    public Drawable getIcon() {
        return mIcon;
    }
    public void setIcon(Drawable icon) { this.mIcon = icon; }
    public long getLastUsedTime() { return mLastUsedTime; }
    public void setLastUsedTime(long mLastUsedTime) { this.mLastUsedTime = mLastUsedTime; }
    public long getTotalUptime() { return mTotalUptime; }
    public void setTotalUptime(long mTotalUptime) { this.mTotalUptime = mTotalUptime; }
}
