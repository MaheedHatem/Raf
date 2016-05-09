package com.example.raf;

import android.content.Context;

import com.example.raf.data.Book;

/**
 * Created by ahmed on 09-May-16.
 */
public class getTopCategoryThread extends Thread {
    private Context context;
    private int category;

    public getTopCategoryThread(int category , Context context){
        this.category = category;
        this.context = context;
    }

    public void run(){
        Book.getTopCategoryFirstTime(category , context);
    }

}
