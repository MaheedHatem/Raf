package com.example.raf.data;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Maheed on 5/2/2016.
 */
public class CurrentUser {
    public static ParseUser getCurrentUser(){
        return ParseUser.getCurrentUser();
    }
    public static void addBookToWishlist(Book book){
        ArrayList<Book> wishlist = getWishlist();
        if(wishlist!=null){
            if(!wishlist.contains(book)){
                wishlist.add(book);
                getCurrentUser().put("wishlist" , wishlist);
            }
        }
        else{
            wishlist = new ArrayList<Book>();
            wishlist.add(book);

            getCurrentUser().put("wishlist" , wishlist);
            getCurrentUser().saveInBackground();
        }
    }
    public static ArrayList<Book> getWishlist(){
        try {
            getCurrentUser().fetch();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (ArrayList<Book>) getCurrentUser().get("wishlist");
    }
}
