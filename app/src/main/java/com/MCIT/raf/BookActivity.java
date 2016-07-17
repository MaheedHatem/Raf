package com.MCIT.raf;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.MCIT.raf.data.Book;
import com.MCIT.raf.data.CurrentUser;

public class BookActivity extends AppCompatActivity {

    private Context mContext = getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View lo = (View) findViewById(R.id.bellow_actionbar);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) lo.getLayoutParams();
            params.topMargin=getResources().getDimensionPixelOffset(R.dimen.top_margin);
            lo.setLayoutParams(params);
        }


        final String bookID = getIntent().getStringExtra(getString(R.string.book_intent_id));
        final String bookName = getIntent().getStringExtra(getString(R.string.book_intent_name));
        String bookAuthor = getIntent().getStringExtra(getString(R.string.book_intent_author));
        String bookDescription = getIntent().getStringExtra(getString(R.string.book_intent_description));
        final byte[] bookCover = getIntent().getByteArrayExtra(getString(R.string.book_intent_cover));
        final int bookPrice = getIntent().getIntExtra(getString(R.string.book_intent_price),0);

        NestedScrollView book_content = (NestedScrollView) findViewById(R.id.content_id);
        ImageView imageCover = (ImageView)findViewById(R.id.cover);
        TextView description = (TextView) book_content.findViewById(R.id.book_description);
        TextView author = (TextView)findViewById(R.id.book_author);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(CurrentUser.isWishListed(bookID))
            fab.setImageResource(R.drawable.ic_favorite_white_18dp);
        else
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(bookName);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#D9000000")); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE); //Color of your title

        Bitmap b = BitmapFactory.decodeByteArray(bookCover,0,bookCover.length);
        if(b!=null)
        imageCover.setImageBitmap((Bitmap.createScaledBitmap(b, 271, 400, true)));
        description.setText(bookDescription);
        author.setText(bookAuthor);

        ImageButton getCopyButton = (ImageButton)findViewById(R.id.getcopy);
        getCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getBookIntent = new Intent(BookActivity.this , GetBookActivity.class);
                getBookIntent.putExtra(getString(R.string.book_intent_name) , bookName);
                getBookIntent.putExtra(getString(R.string.book_intent_price) , bookPrice);
                getBookIntent.putExtra(getString(R.string.book_intent_id) , bookID);
                getBookIntent.putExtra(getString(R.string.book_intent_cover) , bookCover);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(BookActivity.this, (ImageView)findViewById(R.id.cover), "cover");
                    // start the new activity
                    startActivity(getBookIntent, options.toBundle());
                }
                else
                    BookActivity.this.startActivity(getBookIntent);
            }
        });

        ImageButton addCopy = (ImageButton)findViewById(R.id.addCopy);
        addCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBookIntent = new Intent(getApplicationContext(), AddBookActivity.class);
                addBookIntent.putExtra(getString(R.string.book_intent_name) , bookName);
                addBookIntent.putExtra(getString(R.string.book_intent_price) , bookPrice);
                addBookIntent.putExtra(getString(R.string.book_intent_id) , bookID);
                addBookIntent.putExtra(getString(R.string.book_intent_cover) , bookCover);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(BookActivity.this, (ImageView)findViewById(R.id.cover), "cover");
                    // start the new activity
                    startActivity(addBookIntent, options.toBundle());
                }
                else
                    BookActivity.this.startActivity(addBookIntent);
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUser.isWishListed(bookID)) {
                    Book.removeFromWishList(bookID , getApplication());
                    fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Snackbar.make(v, "Removed from Whishlist", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Book.addToWishList(bookID, getApplication());
                    fab.setImageResource(R.drawable.ic_favorite_white_18dp);
                    Snackbar.make(v, "Added to Whishlist", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
