package com.example.raf;

/**
 * Created by Tahaz on 4/25/2016.
 */

import android.app.Application;
import android.util.Log;

import com.example.raf.data.Author;
import com.example.raf.data.Book;
import com.example.raf.data.DeliveryPoint;
import com.example.raf.data.Genre;
import com.example.raf.data.Request;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.Collection;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Author.class);
        ParseObject.registerSubclass(Book.class);
        ParseObject.registerSubclass(Genre.class);
        ParseObject.registerSubclass(DeliveryPoint.class);
        ParseObject.registerSubclass(Request.class);
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
