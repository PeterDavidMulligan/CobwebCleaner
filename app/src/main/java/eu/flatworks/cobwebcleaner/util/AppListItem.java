/*
 * Created on 01/02/18 22:35 by Peter Mulligan.
 * Copyright (c) 2018.
 */

package eu.flatworks.cobwebcleaner.util;

import android.graphics.drawable.Drawable;

public class AppListItem {
    private String mName;
    Drawable mIcon;

    public AppListItem(String name, Drawable icon) {
        mName = name;
        mIcon = icon;
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

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }
}
