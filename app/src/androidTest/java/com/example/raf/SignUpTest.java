package com.example.raf;


import android.support.test.runner.AndroidJUnit4;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    private boolean signedUp;

    @Test
    public void testSignup() {
        ParseUser user = new ParseUser();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@gmail.com");


        try{
            user.signUp();
            signedUp = true;
        }
        catch (ParseException e)
        {
            signedUp = false;
        }
        finally {
            Assert.assertTrue(signedUp);
        }
    }
}
