package com.example.raf;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.raf.data.Book;
import com.example.raf.data.Genre;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MoreActivity extends AppCompatActivity {
    TextView toolbarTitle = null;
    GridAdapter gridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String type = getIntent().getStringExtra(getString(R.string.more_intent));
        getSupportActionBar().setTitle(type);

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


        GridView gridView = (GridView) findViewById(R.id.gridView);

        // Set custom adapter (GridAdapter) to gridview

        gridAdapter = new GridAdapter(this);
//        gridAdapter.addBooks(CurrentUser.getWishlist());

        gridView.setAdapter(gridAdapter);


        getMoreTask task = new getMoreTask(gridAdapter , getApplicationContext());
        task.execute(type);


    }

    public class getMoreTask extends AsyncTask <String , Void , Void>{

        private  GridAdapter adapter;
        private Context context;

        getMoreTask(GridAdapter adapter , Context context){
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
                    case "Relegion":
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
    }
}
