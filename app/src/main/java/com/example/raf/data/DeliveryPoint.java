package com.example.raf.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Maheed on 5/2/2016.
 */
@ParseClassName("DeliveryPoint")
public class DeliveryPoint extends ParseObject {
    public String getName(){
        return getString("name");
    }
    public void setName(String value){
        put("name" , value);
    }
    public String getAddress(){
        return getString("address");
    }
    public void setAddress(String value){
        put("address" , value);
    }
}
