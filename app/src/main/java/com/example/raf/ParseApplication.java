package com.example.raf;

/**
 * Created by Tahaz on 4/25/2016.
 */
import android.app.Application;

import com.example.raf.data.Author;
import com.example.raf.data.Book;
import com.example.raf.data.Genre;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Author.class);
        ParseObject.registerSubclass(Book.class);
        ParseObject.registerSubclass(Genre.class);
        Parse.initialize(this, getString(R.string.parse_AppID), getString(R.string.parse_ClientKey));

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        ParseFacebookUtils.initialize(this);
    }
}
