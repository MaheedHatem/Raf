//package com.MCIT.raf;
//
//
//import android.support.test.InstrumentationRegistry;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.view.View;
//import android.view.ViewGroup;
//
//
//import com.MCIT.raf.data.Book;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import  static android.support.test.espresso.Espresso.*;
//import  static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.assertion.ViewAssertions.*;
//import static android.support.test.espresso.matcher.ViewMatchers.*;
//
//import static org.hamcrest.Matchers.*;
//
//
///**
// * Created by Maheed on 5/1/2016.
// */
//@RunWith(AndroidJUnit4.class)
//public class NavigateToBookGUITest {
//    @Rule
//    public ActivityTestRule<Splash> mActivityRule2 = new ActivityTestRule<>(
//            Splash.class);
//
//
//
//    @Test
//    public void testNavigateToBook() {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        onView(firstChildOf(allOf(withId(R.id.recycle_home), isDisplayed()))).perform(click());
//        onView(withId(R.id.book_description)).check(matches(isDisplayed()));
//    }
//    public static Matcher<View> firstChildOf(final Matcher<View> parentMatcher) {
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("with first child view of type parentMatcher");
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//
//                if (!(view.getParent() instanceof ViewGroup)) {
//                    return parentMatcher.matches(view.getParent());
//                }
//                ViewGroup group = (ViewGroup) view.getParent();
//                return parentMatcher.matches(view.getParent()) && group.getChildAt(0).equals(view);
//
//            }
//        };
//    }
//}
