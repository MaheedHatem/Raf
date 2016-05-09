package com.example.raf;

import android.content.Context;

import com.example.raf.data.Book;

/**
 * Created by ahmed on 09-May-16.
 */
public class getTopHomeThread extends Thread {
    private Context context;
    private int type;

    public getTopHomeThread(int type , Context context){
        this.type = type;
        this.context = context;
    }

    public void run(){
        Book.getTopHomeFirstTime(type , context);
    }
}
