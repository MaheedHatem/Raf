package com.MCIT.raf;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.MCIT.raf.data.Book;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import  static android.support.test.espresso.Espresso.*;
import  static android.support.test.espresso.action.ViewActions.*;

import static android.support.test.espresso.matcher.ViewMatchers.*;

import static android.support.test.espresso.assertion.ViewAssertions.*;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class SuccessfulLoginGUITest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void testLoginInValid() {
        Book.getTopHomeFirstTime(0, InstrumentationRegistry.getTargetContext());
        Book.getTopHomeFirstTime(1, InstrumentationRegistry.getTargetContext());
        Book.getTopHomeFirstTime(2, InstrumentationRegistry.getTargetContext());
        onView(withId(R.id.email)).perform(typeText("taha"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.email_sign_in_button)).check(doesNotExist());
    }
}
