package com.example.raf;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Timestamp;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your Name");
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View lo = (View) findViewById(R.id.bellow_actionbar);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) lo.getLayoutParams();
            params.topMargin=getResources().getDimensionPixelOffset(R.dimen.top_margin);
            lo.setLayoutParams(params);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycle_account);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ActivityAdapter mAdapter = new ActivityAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addActivity("Hello it's me", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"InPreogress");

        mAdapter.addActivity("Hello it's you", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"Returned");

        mAdapter.addActivity("Hello it's us", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"Purchase");

    }
}
