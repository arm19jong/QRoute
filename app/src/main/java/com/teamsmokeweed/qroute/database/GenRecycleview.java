package com.teamsmokeweed.qroute.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.swipe.util.Attributes;
import com.teamsmokeweed.qroute.R;

/**
 * Created by jongzazaal on 17/10/2559.
 */

public class GenRecycleview extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View decorView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gen_recycleview);

        decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ViewDatabaseQr viewDatabaseQr = new ViewDatabaseQr(getApplicationContext());
        viewDatabaseQr.queryGen();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager = new StaggeredGridLayoutManager(3,1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //myDataset = {"ggg",""};


        //ArrayList<String> email = new ArrayList<String>();


        mAdapter = new GenCustomAdapter(getApplicationContext(), viewDatabaseQr.getTitles());
        ((GenCustomAdapter) mAdapter).setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addOnItemTouchListener(

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

}
