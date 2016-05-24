package com.MCIT.raf;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;


import com.MCIT.raf.data.Book;
import com.MCIT.raf.data.CurrentUser;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import junit.framework.Assert;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import  static android.support.test.espresso.Espresso.*;
import  static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.*;


/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class BookToWishlistGUITest {
    Book b;
    @Rule
    public ActivityTestRule<BookActivity> mActivityRule = new ActivityTestRule<BookActivity>(
            BookActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context context = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(context, BookActivity.class);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Book");
            query.include("author");
            b = null;
            try {
                b = (Book) query.getFirst();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Assert.assertNotNull(b);
            result.putExtra(context.getString(R.string.book_intent_id) , b.getObjectId());
            result.putExtra(context.getString(R.string.book_intent_name) , b.getName());
            result.putExtra(context.getString(R.string.book_intent_author) , b.getAuthor().getName());
            result.putExtra(context.getString(R.string.book_intent_cover) , b.getCover());
            result.putExtra(context.getString(R.string.book_intent_description) , b.getDescription());
            result.putExtra(context.getString(R.string.book_intent_price) ,b.getPrice());
            return result;
        }
    };

    @Test
    public void testBookToWishlist() {

        ArrayList<Book> wishList = CurrentUser.getWishlistFirstTime();
        boolean contains = false;
        if(wishList.contains(b)){
            contains = true;
        }
        onView(withId(R.id.fab)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(contains){
            Assert.assertTrue(!wishList.contains(b));
        }
        else{
            Assert.assertTrue(wishList.contains(b));
        }
    }
    public static Matcher<View> firstChildOf(final Matcher<View> parentMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with first child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {

                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }
                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(0).equals(view);

            }
        };
    }
}
