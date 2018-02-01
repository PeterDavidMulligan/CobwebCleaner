package eu.flatworks.cobwebcleaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import eu.flatworks.cobwebcleaner.util.AppListAdapter;

public class MainActivity extends AppCompatActivity implements AppListAdapter.ItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    AppListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Facebook");
        animalNames.add("Messaging");
        animalNames.add("Pokemon GO");
        animalNames.add("Whatsapp");

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv_apps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppListAdapter(this, animalNames);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "Item #" + position + " pressed!");
    }
}
