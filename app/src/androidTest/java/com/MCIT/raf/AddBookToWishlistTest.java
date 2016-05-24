package com.MCIT.raf;

import android.support.test.runner.AndroidJUnit4;

import com.MCIT.raf.data.Book;
import com.MCIT.raf.data.CurrentUser;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class AddBookToWishlistTest {

    @Test
    public void testAddBookToWishlist() {
        CurrentUser.getWishlistFirstTime();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Book");
        Book b = null;
        try {
            b = (Book) query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(b);

        CurrentUser.addBookToWishlist(b);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(CurrentUser.getWishlist().contains((b)));

    }

}
