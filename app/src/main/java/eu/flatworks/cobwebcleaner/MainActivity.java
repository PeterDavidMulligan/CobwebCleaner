package eu.flatworks.cobwebcleaner;

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

public class MainActivity extends AppCompatActivity implements AppListAdapter.ItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.main_rv_apps) RecyclerView mAppsList;
    private AppListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAppsList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppListAdapter(this, getInstalledApps());
        mAdapter.setClickListener(this);
        mAppsList.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "Item #" + position + " pressed!");
        Toast.makeText(this, "Item " + position + " clicked!", Toast.LENGTH_SHORT).show();
    }

    //returns a list of AppListItems that contains all the non-system (user) apps
    private List<AppListItem> getInstalledApps() {
        List<AppListItem> apps = new ArrayList<>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (!isSystemPackage(p)) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                apps.add(new AppListItem(appName, icon));
            }
        }
        return apps;
    }

    //checks if an app is a system app or not
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
