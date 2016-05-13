package com.MCIT.raf;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import  static android.support.test.espresso.Espresso.*;
import  static android.support.test.espresso.action.ViewActions.*;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class FailedSignupGUITest {
    @Rule
    public ActivityTestRule<signup> mActivityRule = new ActivityTestRule<>(
            signup.class);

    @Test
    public void testSignupValid() {
        onView(withId(R.id.email)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("shrt"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.account)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.account)).check(matches(isDisplayed()));
    }
}
