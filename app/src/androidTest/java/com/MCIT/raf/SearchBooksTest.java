//package com.MCIT.raf;
//
//import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.MCIT.raf.data.Book;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.ArrayList;
//
///**
// * Created by Maheed on 5/1/2016.
// */
//@RunWith(AndroidJUnit4.class)
//public class SearchBooksTest {
//    @Test
//    public void testSearchBooks() {
//        Book.getTopHomeFirstTime(0,InstrumentationRegistry.getTargetContext());
//        Book.getTopHomeFirstTime(1,InstrumentationRegistry.getTargetContext());
//        Book.getTopHomeFirstTime(2,InstrumentationRegistry.getTargetContext());
//        MyAdapter mAdapter = new MyAdapter(InstrumentationRegistry.getTargetContext());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Book.getTopHome(mAdapter ,InstrumentationRegistry.getTargetContext() , 0 );
//        Book.getTopHome(mAdapter ,InstrumentationRegistry.getTargetContext() , 1 );
//        Book.getTopHome(mAdapter ,InstrumentationRegistry.getTargetContext() , 2 );
//
//        ArrayList<Book> list0 = MyAdapter.gAdapter0.getList();
//        Assert.assertTrue(list0.size()>0);
//        for(Book b : list0){
//            Assert.assertTrue(b.getFeatured());
//        }
//
//        ArrayList<Book> list1 = MyAdapter.gAdapter1.getList();
//        Assert.assertTrue(list1.size()>0);
//        for(Book b : list1){
//            Assert.assertTrue(b.getNew());
//        }
//
//        ArrayList<Book> list2 = MyAdapter.gAdapter2.getList();
//        Assert.assertTrue(list2.size()>0);
//        for(Book b : list2){
//            Assert.assertTrue(b.getPopular());
//        }
//    }
//}
