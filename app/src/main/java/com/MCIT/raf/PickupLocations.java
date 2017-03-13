package com.MCIT.raf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PickupLocations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_locations);
        getSupportActionBar().setTitle("Pick up Locations");
    }
}
