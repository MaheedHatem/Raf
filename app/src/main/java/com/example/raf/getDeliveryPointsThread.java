package com.example.raf;

import android.content.Context;

import com.example.raf.data.DeliveryPoint;

/**
 * Created by ahmed on 13-May-16.
 */
public class getDeliveryPointsThread extends Thread {
    private Context mContext;

    public getDeliveryPointsThread(Context context){
        this.mContext = context;
    }
    @Override
    public void run() {
        super.run();
        DeliveryPoint.getDeliveryPointFirstTime(mContext);
    }
}
