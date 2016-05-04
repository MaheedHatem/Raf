package com.example.raf;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raf.data.Book;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String bookID = getIntent().getStringExtra(getString(R.string.book_intent_id));
        String bookName = getIntent().getStringExtra(getString(R.string.book_intent_name));
        String bookAuthor = getIntent().getStringExtra(getString(R.string.book_intent_author));
        String bookDescription = getIntent().getStringExtra(getString(R.string.book_intent_description));
        byte[] bookCover = getIntent().getByteArrayExtra(getString(R.string.book_intent_cover));

        NestedScrollView book_content = (NestedScrollView) findViewById(R.id.content_id);
        ImageView imageCover = (ImageView)findViewById(R.id.cover);
        TextView description = (TextView) book_content.findViewById(R.id.book_description);

        setTitle(bookName);
        imageCover.setImageBitmap(BitmapFactory.decodeByteArray(bookCover,0,bookCover.length));
        description.setText(bookDescription);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book.addToWishList(bookID , getApplication());
                Snackbar.make(view, "Added to Whishlist", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
