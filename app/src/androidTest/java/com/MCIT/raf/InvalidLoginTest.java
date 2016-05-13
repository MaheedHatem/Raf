package com.MCIT.raf;

import android.support.test.runner.AndroidJUnit4;

import com.parse.ParseException;
import com.parse.ParseUser;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class InvalidLoginTest {
    private boolean failedLoggedIn;

    @Test
    public void testLoginInvalid() {
        try {
            ParseUser.logIn("asdf", "asd");
            failedLoggedIn = false;
        } catch (ParseException e) {
            failedLoggedIn = true;
        }
        finally {
            Assert.assertTrue(failedLoggedIn);
        }
    }

}
