package com.MCIT.raf;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.MCIT.raf.data.CurrentUser;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CurrentUser.fetchInBackGround();



        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);




        ViewPager.OnPageChangeListener myOnPageChangeListener =
            new ViewPager.OnPageChangeListener(){

                @Override
                public void onPageScrollStateChanged(int state) {
                    //Called when the scroll state changes.
                }

                @Override
                public void onPageScrolled(int position,
                                           float positionOffset, int positionOffsetPixels) {
                    //This method will be invoked when the current page is scrolled,
                    //either as part of a programmatically initiated smooth scroll
                    //or a user initiated touch scroll.
                }

                @Override
                public void onPageSelected(int position) {
                    //This method will be invoked when a new page becomes selected.
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);



                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                           HomeActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.addDrawerListener(toggle);
                    toggle.syncState();

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(HomeActivity.this);
                }
            };

        viewPager.addOnPageChangeListener(myOnPageChangeListener);

        final TabLayout.Tab home = tabLayout.newTab();
        final TabLayout.Tab inbox = tabLayout.newTab();
        final TabLayout.Tab star = tabLayout.newTab();

        home.setText("HOME");
        inbox.setText("CATEGORIES");
        star.setText("WISHLIST");

        tabLayout.addTab(home, 0);
        tabLayout.addTab(inbox, 1);
        tabLayout.addTab(star, 2);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));






        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {



            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Menu");



        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(HomeActivity.this);
                View header=navigationView.getHeaderView(0);
                ((TextView)header.findViewById(R.id.email_view)).setText(CurrentUser.getEmail());
                ((TextView)header.findViewById(R.id.name_view)).setText(CurrentUser.getPname());
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        View header=navigationView.getHeaderView(0);
        ((TextView)header.findViewById(R.id.email_view)).setText(CurrentUser.getEmail());
        ((TextView)header.findViewById(R.id.name_view)).setText(CurrentUser.getPname());


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(this, MoreActivity.class)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            // Handle the camera action

            Intent mainIntent = new Intent(HomeActivity.this, AddBookActivity.class);
            HomeActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);


        } else if (id == R.id.nav_get) {
            Intent mainIntent = new Intent(HomeActivity.this, GetPointsActivity.class);
            HomeActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);


        } else if (id == R.id.nav_contact) {

            Intent mainIntent = new Intent(HomeActivity.this, ContactUs.class);
            HomeActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);


        } else if (id == R.id.nav_suggest) {
            Intent mainIntent = new Intent(HomeActivity.this, RequestBook.class);
            HomeActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);


        } else if (id == R.id.nav_myacc) {


            Intent mainIntent = new Intent(HomeActivity.this, AccountActivity.class);
            HomeActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
        } else if (id == R.id.pick_locations) {


            Intent mainIntent = new Intent(HomeActivity.this, PickupLocations.class);
            HomeActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);

        } else if (id == R.id.nav_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ParseUser.getCurrentUser().logOut();
                    ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

                    Intent mainIntent = new Intent(HomeActivity.this ,LoginActivity.class);
                    HomeActivity.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
                    HomeActivity.this.finish();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            builder.setTitle("Are you sure to logout?");

            AlertDialog dialog = builder.create();
            dialog.show();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {

        Intent mainIntent = new Intent(HomeActivity.this,MoreActivity.class);
        mainIntent.putExtra(getString(R.string.more_intent),v.getTag().toString());
        HomeActivity.this.startActivity(mainIntent);
        overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
    }
}
