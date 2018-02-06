package eu.flatworks.cobwebcleaner;

import android.app.usage.UsageStats;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.flatworks.cobwebcleaner.util.AppListAdapter;
import eu.flatworks.cobwebcleaner.util.AppListItem;

/**
 * AppListActivity is the main activity for the app. It contains a RecyclerView with clickable list
 * items representing each of the user installed apps. Clicking a list item will take the user to a
 * AppDetailsActivity that displays all of the apps details, and allows them to uninstall the
 * selected app.
 */
public class AppListActivity extends AppCompatActivity implements AppListAdapter.ItemClickListener {
    private static final String TAG = AppListActivity.class.getSimpleName();
    @BindView(R.id.main_rv_apps) RecyclerView mAppsList;
    private AppListAdapter mAdapter;
    private List<UsageStats> mStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mStats = getIntent().getParcelableArrayListExtra(PermissionActivity.EXTRA_STATS);

        mAppsList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppListAdapter(this, getInstalledApps());
        mAdapter.setClickListener(this);
        mAppsList.setAdapter(mAdapter);
    }

    /**
     * Responds to user clicking on a RecyclerView item by launching an AppDetailsActivity for
     * the selected app.
     * @param view View being clicked
     * @param position Position of view in RecyclerView
     */
    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "Item #" + position + " pressed!");
        Toast.makeText(this, "Item " + position + " clicked!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Returns a List containing custom objects representing each of the user installed apps on the
     * device.
     * @return The List of user installed apps.
     */
    private List<AppListItem> getInstalledApps() {
        List<AppListItem> apps = new ArrayList<>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (!isSystemPackage(p)) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                long lastUsed = mStats.get(i).getLastTimeUsed();
                long uptime = mStats.get(i).getTotalTimeInForeground();
                apps.add(new AppListItem(appName, icon, lastUsed, uptime));
            }
        }
        return apps;
    }

    /**
     * Convenience app that checks if an app is flagged a system app.
     * @param pkgInfo The PackageInfo of the app to check.
     * @return Returns true if a system app, false if not.
     */
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
