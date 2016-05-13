package com.MCIT.raf.data;

import android.content.Context;

import com.MCIT.raf.R;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Maheed on 5/2/2016.
 */
@ParseClassName("DeliveryPoint")
public class DeliveryPoint extends ParseObject {
    private static DeliveryPoint deliveryPoint;

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

    public static void getDeliveryPointFirstTime(Context context){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(context.getString(R.string.parse_deliveryPoint));
        try {
            deliveryPoint = (DeliveryPoint) query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static DeliveryPoint getDeliveryPoint(){
        return deliveryPoint;
    }
}
