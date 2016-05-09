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
    private static ArrayList<Book> wishList = null;

    public static void addBookToWishlist(Book book){
        //ArrayList<Book> wishlist = getWishlist();
        if(wishList!=null){
            if(!wishList.contains(book)){
                wishList.add(book);
                getCurrentUser().put("wishlist" , wishList);
            }
        }
        else{
            wishList = new ArrayList<Book>();
            wishList.add(book);

            getCurrentUser().put("wishlist" , wishList);
        }
        getCurrentUser().saveInBackground();
    }

    public static void removeBookFromWishList(Book book){
        //ArrayList<Book> wishlist = getWishlist();
        if (wishList.contains(book)){
            wishList.remove(book);
            getCurrentUser().put("wishlist" , wishList);
            getCurrentUser().saveInBackground();
        }
    }
    public static ArrayList<Book> getWishlistFirstTime(){
//        try {
//            getCurrentUser().fetch();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //ArrayList <Book> list = (ArrayList<Book>) getCurrentUser().get("wishlist");
        wishList = (ArrayList<Book>) getCurrentUser().get("wishlist");
        if(wishList != null) {
            for (Book b : wishList) {
                try {
                    b = b.fetch();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return wishList;
    }

    public static ArrayList<Book> getWishlist(){
        return wishList;
    }

    public static boolean isWishListed(String bookID){
        for (Book b:wishList){
            if (b.getObjectId().equals(bookID))
                return true;
        }
        return false;
    }
}
