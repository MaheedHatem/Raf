//package com.MCIT.raf;
//
//import android.support.test.runner.AndroidJUnit4;
//
//import com.MCIT.raf.data.Book;
//import com.MCIT.raf.data.CurrentUser;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
///**
// * Created by Maheed on 5/1/2016.
// */
//@RunWith(AndroidJUnit4.class)
//public class RemoveBookFromWishlistTest {
//
//    @Test
//    public void testRemoveBookFromWishlist() {
//
//        Assert.assertNotNull(CurrentUser.getWishlistFirstTime());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Book b = CurrentUser.getWishlistFirstTime().get(0);
//        CurrentUser.removeBookFromWishList(b);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Assert.assertTrue(!CurrentUser.getWishlist().contains((b)));
//
//    }
//
//}
