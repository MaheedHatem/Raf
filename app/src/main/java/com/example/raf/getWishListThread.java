package com.example.raf;

import com.example.raf.data.CurrentUser;

/**
 * Created by ahmed on 09-May-16.
 */
public class getWishListThread extends Thread {
    public void run(){
        CurrentUser.getWishlistFirstTime();
    }
}
