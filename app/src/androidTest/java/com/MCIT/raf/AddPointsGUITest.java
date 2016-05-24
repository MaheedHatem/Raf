package com.MCIT.raf;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.MCIT.raf.data.CurrentUser;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import  static android.support.test.espresso.Espresso.*;
import  static android.support.test.espresso.action.ViewActions.*;

import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class AddPointsGUITest {
    @Rule
    public ActivityTestRule<GetPointsActivity> mActivityRule = new ActivityTestRule<>(
            GetPointsActivity.class);

    @Test
    public void testAddPoints() {
        int currentPoints = CurrentUser.getPoints();
        onView(withId(R.id.button5)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(currentPoints + 50 == CurrentUser.getPoints());
    }
}
