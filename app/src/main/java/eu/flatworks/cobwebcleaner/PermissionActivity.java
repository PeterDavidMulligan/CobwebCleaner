/*
 * Created on 04/02/18 11:25 by Peter Mulligan.
 * Copyright (c) 2018.
 */

package eu.flatworks.cobwebcleaner;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PermissionActivity is the first activity launched. It thoroughly checks
 * whether or not the user has granted Usage Access to the app, and prompts
 * them if they have not. They cannot continue into the AppListActivity
 * until they have granted the app the correct permissions to avoid a crash.
 */
public class PermissionActivity extends AppCompatActivity {
    private static final String TAG = PermissionActivity.class.getSimpleName();
    public static final String EXTRA_STATS = "stats";
    private List<UsageStats> mStats = Collections.EMPTY_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        enforceUsageStatsPermissions(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        enforceUsageStatsPermissions(this);
    }

    /**
     * Convenience method that wraps 'hasUsageStatsPermission' and 'respondToPermissionState'
     * into a single call.
     * @param context The context to be passed to 'hasUsagePermission'.
     */
    private void enforceUsageStatsPermissions(@NonNull final Context context) {
        respondToPermissionState(hasUsageStatsPermission(context));
    }

    /** Method that thoroughly checks whether a user has Usage Access permissions granted
     * to this app.
     * @param context The context for the activity being checked for access. (App level access)
     * @return Returns true if the app has usage access, false if not.
     */
    private boolean hasUsageStatsPermission(@NonNull final Context context) {
        //Get 'application operations manager' and return false if it is null
        final AppOpsManager appOpsManager =
                (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        if (appOpsManager == null) { return false; }

        //check the appOpsManager to see if we have permission to access the usage stats
        //returning false if we cannot
        final int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        if (mode != AppOpsManager.MODE_ALLOWED) { return false; }

        //Create a usage stats manager, use it to get a list of usage stats
        final long now = System.currentTimeMillis();
        final UsageStatsManager mUsageStatsManager =
                (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        final List<UsageStats> stats =
                mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, now - 1000 * 10, now);

        //Check that list is not null or empty. If it isn't, store it in mStats and confirm
        //the user has permission
        if(stats != null && !stats.isEmpty()) {
            mStats = stats;
            return true;
        }
        else { return false; }
    }

    /**
     * Method to handle a user either having, or not having granted the app
     * usage stats access. If they have, the method displays a confirmation message and
     * takes them to the AppListActivity (passing it the usage stats). If they have not,
     * the methods displays a Snackbar with a clickable link to let them change the
     * access rights.
     * @param hasPermission Boolean value indicating Usage Access state
     */
    private void respondToPermissionState(boolean hasPermission) {
        if(!hasPermission) {
            //Prompt user to give permission using a Snackbar action
            Snackbar sb = Snackbar.make(findViewById(R.id.permission_layout),
                    "Usage Access needed.", Snackbar.LENGTH_INDEFINITE);
            sb.setAction("Grant Access", new UsageAccessListener());
            sb.show();
        }
        else {
            //Confirm the user has access
            Toast.makeText(this, "Access granted!", Toast.LENGTH_LONG).show();
            //Create intent with UsageStats extra
            Intent appListIntent = new Intent(this, AppListActivity.class);
            appListIntent.putExtra(PermissionActivity.EXTRA_STATS, new ArrayList<>(mStats));
            startActivity(appListIntent);
        }
    }

    /**
     * UsageAccessListener is an OnClickListener that launches the Usage Access Settings page.
     */
    private class UsageAccessListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent usageAccessIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(usageAccessIntent);
        }
    }
}
