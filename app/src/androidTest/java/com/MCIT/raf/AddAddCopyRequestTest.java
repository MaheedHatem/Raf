package com.MCIT.raf;

import android.support.test.InstrumentationRegistry;

import com.MCIT.raf.data.Book;
import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.data.DeliveryPoint;
import com.MCIT.raf.data.Request;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Maheed on 5/24/2016.
 */
public class AddAddCopyRequestTest {
    @Test
    public void testAddCopyRequest(){
        DeliveryPoint.getDeliveryPointFirstTime(InstrumentationRegistry.getTargetContext());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Book");
        Book b = null;
        try {
            b = (Book) query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(b);
        Request.addAddCopyRequest(2016 , 5 , 28  , b.getObjectId(), b.getName() , InstrumentationRegistry.getTargetContext());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Request");
        Request r = null;
        try {
            query2.whereEqualTo("book" , b);
            query2.whereEqualTo("user" , CurrentUser.getCurrentUser());
            r = (Request) query2.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(r);
    }
}
