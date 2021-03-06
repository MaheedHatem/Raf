//package com.MCIT.raf;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.espresso.UiController;
//import android.support.test.espresso.ViewAction;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.DatePicker;
//
//
//import com.MCIT.raf.data.Book;
//import com.MCIT.raf.data.CurrentUser;
//import com.MCIT.raf.data.DeliveryPoint;
//import com.MCIT.raf.data.Request;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//
//import junit.framework.Assert;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.Matchers;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import  static android.support.test.espresso.Espresso.*;
//import  static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.matcher.ViewMatchers.*;
//
//import static org.hamcrest.Matchers.*;
//
//
///**
// * Created by Maheed on 5/1/2016.
// */
//@RunWith(AndroidJUnit4.class)
//public class AddBorrowRequestGUITest {
//    Book b;
//    @Rule
//    public ActivityTestRule<BookActivity> mActivityRule = new ActivityTestRule<BookActivity>(
//            BookActivity.class){
//        @Override
//        protected Intent getActivityIntent() {
//            Context context = InstrumentationRegistry.getInstrumentation()
//                    .getTargetContext();
//            Intent result = new Intent(context, BookActivity.class);
//            ParseQuery<ParseObject> query = ParseQuery.getQuery("Book");
//            query.include("author");
//            b = null;
//            try {
//                b = (Book) query.getFirst();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Assert.assertNotNull(b);
//            result.putExtra(context.getString(R.string.book_intent_id) , b.getObjectId());
//            result.putExtra(context.getString(R.string.book_intent_name) , b.getName());
//            result.putExtra(context.getString(R.string.book_intent_author) , b.getAuthor().getName());
//            result.putExtra(context.getString(R.string.book_intent_cover) , b.getCover());
//            result.putExtra(context.getString(R.string.book_intent_description) , b.getDescription());
//            result.putExtra(context.getString(R.string.book_intent_price) ,b.getPrice());
//            return result;
//        }
//    };
//
//    @Test
//    public void testAddBorrowRequest() {
//        DeliveryPoint.getDeliveryPointFirstTime(InstrumentationRegistry.getTargetContext());
//        onView(withId(R.id.getcopy)).perform(click());
//        onView(withId(R.id.borrowBook)).perform(click());
//        onView(withId(R.id.start_date_button)).perform(click());
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(2017, 8, 25));
//        onView(withText("OK")).perform(click());
//        onView(withId(R.id.end_date_button)).perform( click());
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(2017, 9, 25));
//        onView(withText("OK")).perform(click());
//        onView(withId(2131558556)).perform(swipeUp());
//        onView(withId(R.id.getBook_button)).perform( click());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Request");
//        Request r = null;
//        try {
//            query2.whereEqualTo("book" , b);
//            query2.whereEqualTo("user" , CurrentUser.getCurrentUser());
//            r = (Request)  query2.getFirst();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Assert.assertNotNull(r);
//    }
//
//    public static ViewAction setDate(final int year, final int monthOfYear, final int dayOfMonth) {
//
//        // monthOfYear which starts with zero in DatePicker widget.
//        final int normalizedMonthOfYear = monthOfYear - 1;
//
//        return new ViewAction() {
//
//            @Override
//            public void perform(UiController uiController, View view) {
//                final DatePicker datePicker = (DatePicker) view;
//                datePicker.updateDate(year, normalizedMonthOfYear, dayOfMonth);
//            }
//
//            @Override
//            public String getDescription() {
//                return "set date";
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public Matcher<View> getConstraints() {
//                return allOf(isAssignableFrom(DatePicker.class), isDisplayed());
//            }
//        };
//
//    }
//}
