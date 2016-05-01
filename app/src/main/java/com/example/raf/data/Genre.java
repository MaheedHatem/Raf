package com.example.raf.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Maheed on 4/22/2016.
 */
@ParseClassName("Genre")
public class Genre extends ParseObject {

    public String getName(){
        return getString("name");
    }
    public void setName(String value){
        put("name" , value);
    }
}
