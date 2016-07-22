package com.MCIT.raf.data;

import android.content.Context;

import com.MCIT.raf.CategoriesAdapter;
import com.MCIT.raf.MyAdapter;
import com.MCIT.raf.R;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maheed on 4/22/2016.
 */
@ParseClassName("Book")
public class Book extends ParseObject{

    private static ArrayList<Book> featured = new ArrayList<Book>();
    private static ArrayList<Book> popular = new ArrayList<Book>();
    private static ArrayList<Book> newReleases = new ArrayList<Book>();
    private static ArrayList<Book> classic = new ArrayList<Book>();
    private static ArrayList<Book> history = new ArrayList<Book>();
    private static ArrayList<Book> bio = new ArrayList<Book>();
    private static ArrayList<Book> series = new ArrayList<Book>();
    private static ArrayList<Book> religion = new ArrayList<Book>();

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
    public String getLowerCaseName(){
        return getString("lowerCaseName");
    }
    public void setLowerCaseName(String value){
        put("lowerCaseName" , value);
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
        } catch (Exception e) {
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

    public static void getTopHomeRefresh(int type , Context context){
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery(context.getString(R.string.parse_book));
        query1.include(context.getString(R.string.parse_book_author));
        query1.include(context.getString(R.string.parse_book_genre));
        ArrayList<String> keysList = new ArrayList<String>();
        addKeysWithoutCover(keysList , context);
        query1.selectKeys(keysList);
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
        try {
            List <ParseObject> list =  query1.find();
            switch (type) {
                case 0:
                    for (ParseObject o : list)
                        featured.add((Book)o);
                    break;
                case 1:
                    for (ParseObject o : list)
                        newReleases.add((Book)o);
                    break;
                case 2:
                    for (ParseObject o : list)
                        popular.add((Book)o);
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void addKeysWithoutCover(ArrayList<String> keysList , Context context) {
        keysList.add(context.getString(R.string.parse_book_author));
        keysList.add(context.getString(R.string.parse_book_name));
        keysList.add(context.getString(R.string.parse_book_available));
        keysList.add(context.getString(R.string.parse_book_copies));
        keysList.add(context.getString(R.string.parse_book_description));
        keysList.add(context.getString(R.string.parse_book_genre));
        keysList.add(context.getString(R.string.parse_book_objectId));
        keysList.add(context.getString(R.string.parse_book_isbn));
        keysList.add(context.getString(R.string.parse_book_rating));
        keysList.add(context.getString(R.string.parse_book_new));
        keysList.add(context.getString(R.string.parse_book_featured));
        keysList.add(context.getString(R.string.parse_book_popular));
        keysList.add(context.getString(R.string.parse_book_price));
    }

    public static void getTopHome(final MyAdapter mAdapter , Context context , final int type){
        switch (type) {
            case 0:
                for (Book b : featured) {
                    mAdapter.addBook(b, type);
                }
                break;
            case 1:
                for (Book b : newReleases) {
                    mAdapter.addBook(b, type);
                }
                break;
            case 2:
                for (Book b : popular) {
                    mAdapter.addBook(b, type);
                }
                break;
        }
    }

    public static void getTopCategoryRefresh(int category , Context context){
        ParseQuery<ParseObject> queryCategoryID = ParseQuery.getQuery(context.getString(R.string.parse_genre));
        switch (category){
            case 0:
                queryCategoryID.whereEqualTo(context.getString(R.string.parse_genre_name), "Classic");
                break;
            case 1:
                queryCategoryID.whereEqualTo(context.getString(R.string.parse_genre_name), "History");
                break;
            case 2:
                queryCategoryID.whereEqualTo(context.getString(R.string.parse_genre_name), "Biography");
                break;
            case 3:
                queryCategoryID.whereEqualTo(context.getString(R.string.parse_genre_name), "Series");
                break;
            case 4:
                queryCategoryID.whereEqualTo(context.getString(R.string.parse_genre_name), "Religion");
                break;
        }

        try {
            Genre genre = (Genre)queryCategoryID.getFirst();
            ParseQuery<ParseObject> queryBooks = ParseQuery.getQuery(context.getString(R.string.parse_book));
            ArrayList<String> keysList = new ArrayList<String>();
            addKeysWithoutCover(keysList , context);
            queryBooks.selectKeys(keysList);
            queryBooks.setLimit(3);
            queryBooks.include(context.getString(R.string.parse_book_author));
            queryBooks.include(context.getString(R.string.parse_book_genre));
            queryBooks.whereEqualTo(context.getString(R.string.parse_book_genre),genre);
            List <ParseObject> list = queryBooks.find();
            switch (category) {
                case 0:
                    for (ParseObject o : list) {
                        classic.add((Book)o);
                    }
                    break;
                case 1:
                    for (ParseObject o : list) {
                        history.add((Book)o);
                    }
                    break;
                case 2:
                    for (ParseObject o : list) {
                        bio.add((Book)o);
                    }
                    break;
                case 3:
                    for (ParseObject o : list) {
                        series.add((Book)o);
                    }
                    break;
                case 4:
                    for (ParseObject o : list) {
                        religion.add((Book)o);
                    }
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void getTopCategory (final CategoriesAdapter myAdapter , final Context context , final int category){
        switch (category){
            case 0:
                for (Book b:classic) {
                    myAdapter.addBook(b, category);
                }
                break;
            case 1:
                for (Book b:history) {
                    myAdapter.addBook(b, category);
                }
                break;
            case 2:
                for (Book b:bio) {
                    myAdapter.addBook(b, category);
                }
                break;
            case 3:
                for (Book b:series) {
                    myAdapter.addBook(b, category);
                }
                break;
            case 4:
                for (Book b:religion) {
                    myAdapter.addBook(b, category);
                }
                break;
        }
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
