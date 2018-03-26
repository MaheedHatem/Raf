package com.MCIT.raf;

/**
 * Created by Tahaz on 4/25/2016.
 */

import android.app.Application;

import com.MCIT.raf.data.Author;
import com.MCIT.raf.data.Book;
import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.data.DeliveryPoint;
import com.MCIT.raf.data.Genre;
import com.MCIT.raf.data.NewBookRequest;
import com.MCIT.raf.data.Request;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CurrentUser.setFetched(false);
        ParseObject.registerSubclass(Author.class);
        ParseObject.registerSubclass(Book.class);
        ParseObject.registerSubclass(Genre.class);
        ParseObject.registerSubclass(DeliveryPoint.class);
        ParseObject.registerSubclass(Request.class);
        ParseObject.registerSubclass(NewBookRequest.class);
        //Parse.initialize(this, getString(R.string.parse_AppID), getString(R.string.parse_ClientKey));
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("E4KJFk3dW4oBSuyzfOHnw0oND4x86ilQOf5iczOy")
                .server("https://alraf.herokuapp.com/parse/")
        .build()
        );

        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        ParseFacebookUtils.initialize(this);
        ParseTwitterUtils.initialize("PfSxcfOWxRArClq2llkSHVIAw", "QCLDUh2pLgv2QlLrQz9gL42gHIsbrSjlVQQA87MxSBXbtOl0Ew");

    }
}
