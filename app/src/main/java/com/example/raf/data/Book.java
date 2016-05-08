package com.example.raf.data;

import android.content.Context;

import com.example.raf.CategoriesAdapter;
import com.example.raf.MyAdapter;
import com.example.raf.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Maheed on 4/22/2016.
 */
@ParseClassName("Book")
public class Book extends ParseObject{
    public Book() {
    }


    public int getNumberOfCopies() {
        return getInt("numberOfCopies");
    }
    public void setNumberOfCopies(int value) {
        put("numberOfCopies", value);
    }
    public int getAvailableCopies() {
        return getInt("availableCopies");
    }
    public void setAvailableCopies(int value) {
        put("availableCopies", value);
    }
    public String getName(){
        return getString("name");
    }
    public void setName(String value){
        put("name" , value);
    }
    public String getISBN(){
        return getString("ISBN");
    }
    public void setISBN(String value){
        put("ISBN" , value);
    }
    public String getLanguage(){
        return getString("language");
    }
    public void setLanguage(String value){
        put("language" , value);
    }
    public int getRating() {
        return getInt("rating");
    }
    public void setRating(int value) {
        put("rating", value);
    }
    public Author getAuthor(){
        return (Author)getParseObject("author");
    }
    public void setAuthor(Author author){
        put("author", author);
    }
    public void setGenre(Genre genre){
        put("Genre", genre);
    }
    public Genre getGenre(){
        return (Genre)getParseObject("Genre");
    }
    public int getPrice() {return getInt("price");}
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Book)){
            return false;
        }
        Book b = (Book)o;
        return getObjectId().equals(b.getObjectId());
    }
    public byte[] getCover(){
        try {
            return getParseFile("cover").getData();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDescription(){
        return getString("description");
    }
    public boolean getNew(){
        return getBoolean("new");
    }
    public boolean getFeatured(){
        return getBoolean("featured");
    }
    public boolean getPopular(){
        return getBoolean("popular");
    }

    public static void getTopHome(final MyAdapter mAdapter , Context context , final int type){
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery(context.getString(R.string.parse_book));
        query1.include(context.getString(R.string.parse_book_author));
        query1.setLimit(3);
        switch (type) {
            case 0:
                query1.whereEqualTo(context.getString(R.string.parse_book_featured), true);
                break;
            case 1:
                query1.whereEqualTo(context.getString(R.string.parse_book_new), true);
                break;
            case 2:
                query1.whereEqualTo(context.getString(R.string.parse_book_popular), true);
                break;
        }
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e== null){
                    for (int i=0; i<objects.size();i++) {
                        Book b = (Book)objects.get(i);
                        //b.initialize();
                        mAdapter.addBook( b, type);
                    }
                }
            }
        });
    }

    public static void getTopCategory (final CategoriesAdapter myAdapter , final Context context , final int category){
        ParseQuery<ParseObject> queryClassicID = ParseQuery.getQuery(context.getString(R.string.parse_genre));
        switch (category){
            case 0:
                queryClassicID.whereEqualTo(context.getString(R.string.parse_genre_name), "Classic");
                break;
            case 1:
                queryClassicID.whereEqualTo(context.getString(R.string.parse_genre_name), "History");
                break;
            case 2:
                queryClassicID.whereEqualTo(context.getString(R.string.parse_genre_name), "Biography");
                break;
            case 3:
                queryClassicID.whereEqualTo(context.getString(R.string.parse_genre_name), "Series");
                break;
            case 4:
                queryClassicID.whereEqualTo(context.getString(R.string.parse_genre_name), "Religion");
                break;
        }

        queryClassicID.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){
                    Genre genre = (Genre)object;
                    ParseQuery<ParseObject> queryClassics = ParseQuery.getQuery(context.getString(R.string.parse_book));
                    queryClassics.setLimit(3);
                    queryClassics.include(context.getString(R.string.parse_book_author));
                    queryClassics.include(context.getString(R.string.parse_book_genre));
                    queryClassics.whereEqualTo(context.getString(R.string.parse_book_genre),genre);
                    queryClassics.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e== null) {
                                for (int i = 0; i < objects.size(); i++) {
                                    myAdapter.addBook((Book) objects.get(i), category);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public static void addToWishList(String id , Context context){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(context.getString(R.string.parse_book));
        query.include(context.getString(R.string.parse_book_author));
        query.include(context.getString(R.string.parse_book_genre));
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e== null) {
                    Book b = (Book) object;
                    CurrentUser.addBookToWishlist(b);
                }
            }
        });
    }

    public static void removeFromWishList (String id , Context context){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(context.getString(R.string.parse_book));
        query.include(context.getString(R.string.parse_book_author));
        query.include(context.getString(R.string.parse_book_genre));
        try {
            Book b = (Book) query.get(id);
            CurrentUser.removeBookFromWishList(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        query.getInBackground(id, new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (e== null) {
//                    Book b = (Book) object;
//                    CurrentUser.removeBookFromWishList(b);
//                }
//            }
//        });
    }
}
