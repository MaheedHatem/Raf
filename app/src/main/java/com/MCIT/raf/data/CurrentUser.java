package com.MCIT.raf.data;

import com.parse.ParseException;
import com.parse.ParseFile;
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
    private static boolean fetched = false;
    public static void addBookToWishlist(final Book book){
        //ArrayList<Book> wishlist = getWishlist();
        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();

    }

    public static void removeBookFromWishList(final Book book){
        //ArrayList<Book> wishlist = getWishlist();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (wishList.contains(book)){
                    wishList.remove(book);
                    getCurrentUser().put("wishlist" , wishList);
                    getCurrentUser().saveInBackground();
                }
            }
        }).start();

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
                    b.getAuthor().fetchIfNeeded();
                    b.getGenre().fetchIfNeeded();
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
        if(wishList != null) {
            for (Book b : wishList) {
                if (b.getObjectId().equals(bookID))
                    return true;
            }
        }
            return false;

    }

    public static String getUsername() {
        return getCurrentUser().getUsername();
    }

    public static String getEmail() {
        return getCurrentUser().getEmail();
    }

    public static int getPoints() {
        return getCurrentUser().getInt("coins");
    }

    private static void setPoints(int points){
        getCurrentUser().put ("coins" , points);
    }

    public static void addPoints(int points){
        setPoints(getPoints()+points);
        getCurrentUser().saveInBackground();
    }

    public static void removePoints(int points){
        setPoints(getPoints()-points);
        getCurrentUser().saveInBackground();
    }

    public static byte[] getImage() {
        try {
            ParseFile image = getCurrentUser().getParseFile("photo");
            if(image!=null){
                return image.getData();
            }
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setImage(ParseFile image) {
        getCurrentUser().put("photo",image);
        getCurrentUser().saveInBackground();
    }

    public static void fetchInBackGround() {
        if(getCurrentUser()!=null && !fetched){
            getCurrentUser().fetchInBackground();
            fetched = true;
        }

    }

    public static void setFetched(boolean fetched) {
        CurrentUser.fetched = fetched;
    }
}
