package eu.flatworks.cobwebcleaner;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.flatworks.cobwebcleaner.util.AppListAdapter;

public class MainActivity extends AppCompatActivity implements AppListAdapter.ItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.main_rv_apps) RecyclerView mAppsList;
    private AppListAdapter mAdapter;
    private List<ApplicationInfo> mAppInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // set up the RecyclerView
        updateAppsList();
        mAppsList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppListAdapter(this, mAppInfoList);
        mAdapter.setClickListener(this);
        mAppsList.setAdapter(mAdapter);
        getInstalledAppsList();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "Item #" + position + " pressed!");
    }

    //Returns a List of ApplicationInfo for all the apps installed on the device
    private List<ApplicationInfo> getInstalledAppsList(){
        List<ApplicationInfo> appInfo = getPackageManager().getInstalledApplications(0);
        Log.d(TAG, "Installed App Count - " + appInfo.size());
        return appInfo;
    }

    private void updateAppsList() {
        mAppInfoList = getInstalledAppsList();
    }
}
