package com.MCIT.raf;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.MCIT.raf.data.Book;
import com.MCIT.raf.data.Genre;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MoreActivity extends AppCompatActivity {
    TextView toolbarTitle = null;
    GridMoreAdapter mGridMoreAdapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private final static String SEARCH_TYPE = "search";
    private final static String SEARCH_RESULT = "Search Result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String type = getIntent().getStringExtra(getString(R.string.more_intent));
        getSupportActionBar().setTitle(type);
        String query = "";
        startAnim();
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            getSupportActionBar().setTitle(SEARCH_RESULT);
            type = SEARCH_TYPE;
            query = getIntent().getStringExtra(SearchManager.QUERY);
        }
// loop through all toolbar children right after setting support
// action bar because the text view has no id assigned

// also make sure that the activity has some title here
// because calling setText() with an empty string actually
// removes the text view from the toolbar


        for (int i = 0; i < toolbar.getChildCount(); ++i) {
            View child = toolbar.getChildAt(i);

            // assuming that the title is the first instance of TextView
            // you can also check if the title string matches
            if (child instanceof TextView) {
                toolbarTitle = (TextView)child;
                break;
            }

        }

        // Set custom adapter (GridAdapter) to gridview


        //GridView gridView = (GridView) findViewById(R.id.gridView);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        // Set custom adapter (GridAdapter) to gridview

        mGridMoreAdapter = new GridMoreAdapter(MoreActivity.this);
//        gridAdapter.addBooks(CurrentUser.getWishlist());

        mRecyclerView.setAdapter(mGridMoreAdapter);


        getMoreTask task = new getMoreTask(mGridMoreAdapter , getApplicationContext());
        task.execute(type , query);


    }

    public class getMoreTask extends AsyncTask <String , Void , Void>{

        private  GridMoreAdapter adapter;
        private Context context;

        getMoreTask(GridMoreAdapter adapter , Context context){
            this.adapter = adapter;
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... params) {
            String type = params[0];
            List <ParseObject> list = null;
            ParseQuery<ParseObject> queryHome = ParseQuery.getQuery(context.getString(R.string.parse_book));
            queryHome.include(context.getString(R.string.parse_book_author));
            queryHome.include(context.getString(R.string.parse_book_genre));
            queryHome.setLimit(20);
            Genre genre =null;
            if(type.equals(SEARCH_TYPE)){
                String query = params[1];
                ParseQuery<Book> bookQuery = ParseQuery.getQuery(context.getString(R.string.parse_book));
                bookQuery.whereContains(context.getString(R.string.parse_book_lower_case_name) , query.toLowerCase());
                bookQuery.include(context.getString(R.string.parse_book_author));
                bookQuery.include(context.getString(R.string.parse_book_genre));
                List<Book> result = null;
                try {
                    result = bookQuery.find();
                    for (final Book b: result){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addBook(b);
                            }
                        });
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
            if (!type.equals("Featured") || !type.equals("Most Popular") || !type.equals("New Release")) {
                ParseQuery<ParseObject> queryCategoryID = ParseQuery.getQuery(context.getString(R.string.parse_genre));
                queryCategoryID.whereEqualTo(context.getString(R.string.parse_genre_name), type);
                try {
                    genre = (Genre) queryCategoryID.getFirst();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            try {
                switch (type) {
                    case "Featured":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_featured), true);
                        break;
                    case "New Releases":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_new), true);
                        break;
                    case "Most Popular":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_popular), true);
                        break;
                    case "Classic":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_genre),genre);
                        break;
                    case "History":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_genre),genre);
                        break;
                    case "Biography":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_genre),genre);
                        break;
                    case "Series":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_genre),genre);
                        break;
                    case "Religion":
                        queryHome.whereEqualTo(context.getString(R.string.parse_book_genre),genre);
                        break;
                }
                list = queryHome.find();

            } catch (ParseException e){
                e.printStackTrace();
            }

            for (final ParseObject o: list){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addBook((Book)o);
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            stopAnim();
        }
    }

    void startAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }
}
