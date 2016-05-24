package com.MCIT.raf;

import android.support.test.runner.AndroidJUnit4;

import com.MCIT.raf.data.CurrentUser;
import com.parse.ParseException;
import com.parse.ParseUser;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class RemovePointsTest {

    @Test
    public void testRemovePoints() {
        int currentPoints = CurrentUser.getPoints();
        CurrentUser.removePoints(10);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(currentPoints - 10 == CurrentUser.getPoints());
    }

}
