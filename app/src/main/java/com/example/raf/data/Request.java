package com.example.raf.data;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by Maheed on 5/2/2016.
 */
@ParseClassName("Request")
public class Request extends ParseObject {
    public static final int BORROW_REQUEST = 0;
    public static final int ADD_REQUEST = 1;
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
        }
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

}
