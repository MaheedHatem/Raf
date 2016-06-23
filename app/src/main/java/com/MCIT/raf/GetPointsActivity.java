package com.MCIT.raf;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.util.IabHelper;
import com.MCIT.raf.util.IabResult;
import com.MCIT.raf.util.Purchase;

public class GetPointsActivity extends AppCompatActivity {
    private static final int RC_REQUEST = 10001;
    IabHelper mHelper;
    Button button5;
    static final String TAG = "GetPoinstActivity";
    // SKUs for our products:
    static final String SKU_TEN_POINTS = "ten_points";


    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp/7Q5PKVYq0FNToPCqKzisIHJpxW02R12JuyPGD/yovu0nM4hM8bvDXoJ/w2HJdGkWVNjQGrfajb/73zPP7qsXE/dYSvOfy4lNPUxheNeJxRnT8IsfKYYD62zLPNMEPjytAkgudcYqbEUxf97nnvdk/ukllLEB6I+o3VSdJZYc2lHOt7v68Vtl9OY/s5NE8ZtgtyZRNYXl9E0O/KwLwSiGmUm0hVAaDiTYRslldsUI7SNGRD8RUIJ2D5ioqbFMeaEZG2zhrJ60MPmtY/kh7p1wTEiF6xxv0S0EszFP8yMopus90/Isko7JUx/J6uq0U+seNdGzzDlGhlERXi3T7owwIDAQAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_points);


        Log.d(TAG, "Starting setup.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");
                if (!result.isSuccess()) {
                    // there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }
                if (mHelper == null) return;
                // IAB is fully set up!
            }
        });

        CustomList cs = new CustomList(this);
        ListView listView = (ListView) findViewById(R.id.points_list);


        listView.setAdapter(cs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text

                switch(position){
                    case 0:

                        Log.d(TAG, "Launching purchase flow for gas.");
                         String payload = "";
                         try {
                             mHelper.launchPurchaseFlow(GetPointsActivity.this, SKU_TEN_POINTS, RC_REQUEST,
                                mPurchaseFinishedListener, payload);
                            } catch (IabHelper.IabAsyncInProgressException e) {
                               complain("Error launching purchase flow. Another async operation in progress.");
                         }
                        break;


                    case 1:
                        CurrentUser.addPoints(10);
                         Snackbar.make(view, "10 points added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        break;
                    case 2:
                        CurrentUser.addPoints(20);
                        Snackbar.make(view, "20 points added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case 3:
                        CurrentUser.addPoints(35);
                        Snackbar.make(view, "35 points added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                }
            }
        });


//        button5 = (Button)findViewById(R.id.button3);
//        Button button10 = (Button)findViewById(R.id.button6);
//        Button button20 = (Button)findViewById(R.id.button5);
//        Button button35 = (Button)findViewById(R.id.button4);
//
//
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "Launching purchase flow for gas.");
//                String payload = "";
//
//                try {
//                    mHelper.launchPurchaseFlow(GetPointsActivity.this, SKU_TEN_POINTS, RC_REQUEST,
//                            mPurchaseFinishedListener, payload);
//                } catch (IabHelper.IabAsyncInProgressException e) {
//                    complain("Error launching purchase flow. Another async operation in progress.");
//                }
//            }
//        });
//
//        button10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CurrentUser.addPoints(25);
//                Snackbar.make(v, "25 points added", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        button20.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CurrentUser.addPoints(50);
//                Snackbar.make(v, "50 points added", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        button35.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CurrentUser.addPoints(100);
//                Snackbar.make(v, "100 points added", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    @Override
    protected void onResume() {

        TextView ts = (TextView) findViewById(R.id.points_text);
        ts.setText(Integer.toString(CurrentUser.getPoints()));
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            try {
                mHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
        mHelper = null;
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }


            if (purchase.getSku().equals(SKU_TEN_POINTS)) {
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error Consuming Points. Another async operation in progress.");
                    return;
                }
            }
        }
    };



    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {

            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isSuccess()) {
                if (purchase.getSku().equals(SKU_TEN_POINTS)) {
                    Log.d(TAG, "Consumption successful. Provisioning.");
                    CurrentUser.addPoints(10);
                    Snackbar.make(button5, "10 points added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
            else {
                complain("Error while consuming: " + result);
            }
        }
    };

    private boolean verifyDeveloperPayload(Purchase purchase) {
        //TODO
        return true;
    }
    //TODO remove both functions
    void complain(String message) {
        Log.e(TAG, "**** Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] nPoints = {"5 Points" ,"10 Points", "20 Points", "35 Points"};
        private final String[] Price = {"5 L.E", "10 L.E", "20 L.E", "35 L.E"};

        public CustomList(Activity context) {
            super(context, R.layout.points_list);
            this.context = context;
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if (view == null) // no view to re-use, create new
                view = context.getLayoutInflater().inflate(R.layout.points_list, null);

            TextView txtPrice = (TextView) view.findViewById(R.id.price);
            TextView txtPoints = (TextView) view.findViewById(R.id.points);

            txtPrice.setText(Price[position]);
            txtPoints.setText((nPoints[position]));

            return view;
        }

        @Override
        public int getCount() {
            return nPoints.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


}
