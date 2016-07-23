package com.MCIT.raf.data;


import android.content.Context;
import android.telecom.Call;
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
@ParseClassName("NewBookRequest")
public class NewBookRequest extends ParseObject {

    public NewBookRequest(){
    }
    public ParseUser getUser(){
        return getParseUser("user");
    }
    public void setUser(ParseUser user){
        put("user" , user);
    }
    public String getBookName(){
        return getString("bookName");
    }
    public void setBookName(String bookName){ put("bookName" , bookName); }

    public static void addNewBookRequest(final String bookName , final Context context , final Callable callable){


        ParseQuery<ParseObject> query = ParseQuery.getQuery(context.getString(R.string.new_book_request));
        query.include(context.getString(R.string.parse_new_book_request_user));
        query.whereEqualTo(context.getString(R.string.parse_new_book_request_book_name),bookName);
        query.whereEqualTo(context.getString(R.string.parse_new_book_request_user) , CurrentUser.getCurrentUser());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() >0){
                    Toast.makeText(context, "You have already requested this book", Toast.LENGTH_LONG).show();
                    try {
                        callable.call();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    NewBookRequest newBookRequest = new NewBookRequest();
                    newBookRequest.setBookName(bookName);
                    newBookRequest.setUser(CurrentUser.getCurrentUser());
                    newBookRequest.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(context, "Your request have been placed", Toast.LENGTH_LONG).show();
                            try {
                                callable.call();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
    public static void getAutoCompleteList(FindCallback<NewBookRequest> callback , Context context){
        ParseQuery<NewBookRequest> query = ParseQuery.getQuery(context.getString(R.string.new_book_request));
        query.findInBackground(callback);
    }

}
