package com.MCIT.raf.data;


import android.content.Context;
import android.widget.Toast;

import com.MCIT.raf.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Maheed on 5/2/2016.
 */
@ParseClassName("Request")
public class Request extends ParseObject {
    public static final int BORROW_REQUEST = 0;
    public static final int ADD_REQUEST = 1;
    public static final int BUY_REQUEST = 2;

    public static final String PENDING = "Pending";
    public static final String IN_PROGRESS = "In Progress";
    public static final String COMPLETED = "Completed";

    public Request(){
    }
    public ParseUser getUser(){
        return getParseUser("user");
    }
    public void setUser(ParseUser user){
        put("user" , user);
    }
    public Book getBook(){
        return(Book)getParseObject("book");
    }
    public void setBook(Book book){
        put("book" , book);
    }
    public DeliveryPoint getDeliveryPoint(){
        return (DeliveryPoint) getParseObject("deliveryPoint");
    }
    public void setDeliveryPoint(DeliveryPoint deliveryPoint){
        put("deliveryPoint", deliveryPoint);
    }
    public String getType(){
        return getString("type");
    }
    public void setType(int type){
        switch (type){
            case BORROW_REQUEST:
                put("type" , "borrow_request");
                break;
            case ADD_REQUEST:
                put("type" , "add_request");
                break;
            case BUY_REQUEST:
                put ("type" , "buy_request");
                break;
        }
    }
    public void setType(String bookName){
        put("type" , "add_new_request_" + bookName);
    }
    public Date getDeliveryDate(){
        return getDate("deliveryDate");
    }
    public void setDeliveryDate( Date deliveryDate){
        put("deliveryDate" , deliveryDate);
    }
    public Date getStartDate(){
        return getDate("startDate");
    }
    public void setStartDate( Date deliveryDate){
        put("startDate" , deliveryDate);
    }
    public Date getEndDate(){
        return getDate("endDate");
    }
    public void setEndDate( Date deliveryDate){
        put("endDate" , deliveryDate);
    }
    public String getStatus(){
        return getString("status");
    }
    public void setStatus( String status){
        put("status" , status);
    }

    public static void addBorrowRequest(int startYear , int startMonth , int startDay , int endYear
            , int endMonth , int endDay , String bookId ,int bookPrice, final Context context ,final Callable callback){
        Calendar c = Calendar.getInstance();
        c.set(startYear , startMonth , startDay);
        final Date startDate = c.getTime();
        c.set(endYear , endMonth , endDay);
        final Date endDate = c.getTime();
        CurrentUser.removePoints(bookPrice/2);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(context.getString(R.string.parse_book));
        query.include(context.getString(R.string.parse_book_author));
        query.include(context.getString(R.string.parse_book_genre));
        query.getInBackground(bookId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                //TODO add request and subtract from points
                final Request request = new Request();
                request.setBook((Book)object);
                request.setDeliveryDate(startDate);
                request.setStartDate(startDate);
                request.setEndDate(endDate);
                request.setUser(CurrentUser.getCurrentUser());
                request.setType(Request.BORROW_REQUEST);
                request.setDeliveryPoint(DeliveryPoint.getDeliveryPoint());
                request.setStatus(PENDING);
                request.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(context, "your request has been placed", Toast.LENGTH_LONG).show();
                        CurrentUser.getRequest().add(request);
                        try {
                            callback.call();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    

    public static void addGetRequest(int startYear , int startMonth , int startDay ,
                                     String bookId ,int bookPrice, final Context context,final Callable callback){
        Calendar c = Calendar.getInstance();
        c.set(startYear , startMonth , startDay);
        final Date startDate = c.getTime();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(context.getString(R.string.parse_book));
        query.include(context.getString(R.string.parse_book_author));
        query.include(context.getString(R.string.parse_book_genre));
        CurrentUser.removePoints(bookPrice);
        query.getInBackground(bookId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                //TODO add request and subtract from points
                final Request request = new Request();
                request.setBook((Book)object);
                request.setDeliveryDate(startDate);
                request.setUser(CurrentUser.getCurrentUser());
                request.setType(Request.BUY_REQUEST);
                request.setDeliveryPoint(DeliveryPoint.getDeliveryPoint());
                request.setStatus(PENDING);
                request.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(context, "your request has been placed", Toast.LENGTH_LONG).show();
                        CurrentUser.getRequest().add(request);
                        try {
                            callback.call();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    public static void addAddCopyRequest(int deliveryYear, int deliveryMonth, int deliveryDay,
                                         String bookID, String bookName , final Context context,final Callable callback) {
        Calendar c = Calendar.getInstance();
        c.set(deliveryYear , deliveryMonth , deliveryDay);
        final Date deliveryDate = c.getTime();
        if (bookID != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery(context.getString(R.string.parse_book));
            query.include(context.getString(R.string.parse_book_author));
            query.include(context.getString(R.string.parse_book_genre));
            query.getInBackground(bookID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    //TODO add request and subtract from points
                    final Request request = new Request();
                    request.setBook((Book) object);
                    request.setDeliveryDate(deliveryDate);
                    request.setUser(CurrentUser.getCurrentUser());
                    request.setType(Request.ADD_REQUEST);
                    request.setDeliveryPoint(DeliveryPoint.getDeliveryPoint());
                    request.setStatus(PENDING);
                    request.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(context, "your request has been placed", Toast.LENGTH_LONG).show();
                            CurrentUser.getRequest().add(request);
                            try {
                                callback.call();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            });
        } else {
            final Request request = new Request();
            request.setDeliveryDate(deliveryDate);
            request.setUser(CurrentUser.getCurrentUser());
            request.setType(bookName);
            request.setDeliveryPoint(DeliveryPoint.getDeliveryPoint());
            request.setStatus(PENDING);
            request.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(context, "your request has been placed", Toast.LENGTH_LONG).show();
                    CurrentUser.getRequest().add(request);
                    try {
                        callback.call();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    public static ArrayList<Request> getUserRequest (){
        final ArrayList<Request> requests = new ArrayList<Request>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.include("book");
        query.include("user");
        query.whereEqualTo("user", CurrentUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    for (int i=0;i<objects.size();i++)
                        requests.add((Request)objects.get(i));
                }
            }
        });
        return requests;
    }
}
