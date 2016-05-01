package com.example.raf;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.raf.data.Book;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by Maheed on 5/1/2016.
 */
@RunWith(AndroidJUnit4.class)
public class SearchBooksByGenreTest {
    @Test
    public void testSearchBooksByGenre() {
        Context mContext = InstrumentationRegistry.getTargetContext();
        CategoriesAdapter mAdapter = new CategoriesAdapter(mContext);
        Book.getTopCategory(mAdapter , mContext , CategoriesAdapter.CLASSIC);
        Book.getTopCategory(mAdapter , mContext , CategoriesAdapter.HISTORY);
        Book.getTopCategory(mAdapter , mContext , CategoriesAdapter.BIO);
        Book.getTopCategory(mAdapter , mContext , CategoriesAdapter.SERIES);
        Book.getTopCategory(mAdapter , mContext , CategoriesAdapter.RELEGION);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Book> list0 = CategoriesAdapter.gAdapter0.getList();
        Assert.assertTrue(list0.size()>0);
        for(Book b : list0){
            Assert.assertTrue(b.getGenre().getName().equals("Classic"));
        }

        ArrayList<Book> list1 = CategoriesAdapter.gAdapter1.getList();
        Assert.assertTrue(list1.size()>0);
        for(Book b : list1){
            Assert.assertTrue(b.getGenre().getName().equals("History"));
        }

        ArrayList<Book> list2 = CategoriesAdapter.gAdapter2.getList();
        Assert.assertTrue(list2.size()>0);
        for(Book b : list2){
            Assert.assertTrue(b.getGenre().getName().equals("Biography"));
        }
        ArrayList<Book> list3 = CategoriesAdapter.gAdapter3.getList();
        Assert.assertTrue(list3.size()>0);
        for(Book b : list2){
            Assert.assertTrue(b.getGenre().getName().equals("Series"));
        }
        ArrayList<Book> list4 = CategoriesAdapter.gAdapter4.getList();
        Assert.assertTrue(list4.size()>0);
        for(Book b : list2){
            Assert.assertTrue(b.getGenre().getName().equals("Religion"));
        }
    }
}
