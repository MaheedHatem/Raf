package com.MCIT.raf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class GetPointsActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{
    private static final int RC_REQUEST = 10001;
    Button button5;
    BillingProcessor bp;
    static final String TAG = "GetPoinstActivity";
    // SKUs for our products:
    static final String SKU_TEN_POINTS = "ten_points";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_points);


        CustomList cs = new CustomList(this);
        ListView listView = (ListView) findViewById(R.id.points_list);
        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp/7Q5PKVYq0FNToPCqKzisIHJpxW02R12JuyPGD/yovu0nM4hM8bvDXoJ/w2HJdGkWVNjQGrfajb/73zPP7qsXE/dYSvOfy4lNPUxheNeJxRnT8IsfKYYD62zLPNMEPjytAkgudcYqbEUxf97nnvdk/ukllLEB6I+o3VSdJZYc2lHOt7v68Vtl9OY/s5NE8ZtgtyZRNYXl9E0O/KwLwSiGmUm0hVAaDiTYRslldsUI7SNGRD8RUIJ2D5ioqbFMeaEZG2zhrJ60MPmtY/kh7p1wTEiF6xxv0S0EszFP8yMopus90/Isko7JUx/J6uq0U+seNdGzzDlGhlERXi3T7owwIDAQAB", this);


        listView.setAdapter(cs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text

                TextView ts = (TextView) findViewById(R.id.points_text);
                boolean isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();
                if (!isOneTimePurchaseSupported)
                    position = 99;


                switch(position){
                    case 0:
                        bp.purchase(GetPointsActivity.this, "points.50");
                        break;
                    case 1:
//                        CurrentUser.addPoints(10);
//                         Snackbar.make(view, "10 points added", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                        ts.setText(Integer.toString(CurrentUser.getPoints()));
                        bp.purchase(GetPointsActivity.this, "points.100");
                        break;
                    case 2:
//                        CurrentUser.addPoints(20);
//                        Snackbar.make(view, "20 points added", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                         ts = (TextView) findViewById(R.id.points_text);
//                        ts.setText(Integer.toString(CurrentUser.getPoints()));
                        bp.purchase(GetPointsActivity.this, "points.250");
                        break;
                    case 3:
//                        CurrentUser.addPoints(35);
//                        Snackbar.make(view, "35 points added", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                         ts = (TextView) findViewById(R.id.points_text);
//                        ts.setText(Integer.toString(CurrentUser.getPoints()));
                        bp.purchase(GetPointsActivity.this, "points.500");
                        break;
                    case 99:
                        Snackbar.make(view, "Google Play Purchases is not Available on this Device", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    default:
                        Snackbar.make(view, "Something went Wrong", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

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
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();

    }



    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

        int points = Integer.parseInt(productId.substring(7));
        CurrentUser.addPoints(points, new SaveCallback() {
            @Override
            public void done(ParseException ex) {
                TextView ts = (TextView) findViewById(R.id.points_text);
                ts.setText(Integer.toString(CurrentUser.getPoints()));
            }});
        bp.consumePurchase(productId);
        Snackbar.make(GetPointsActivity.this.getCurrentFocus(), points + " Points Added Successfully", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

        if (errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED){
            Snackbar.make(GetPointsActivity.this.getCurrentFocus(), "Something went Wrong during the Purchase",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }

    @Override
    public void onBillingInitialized() {

    }

    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] nPoints = {"50 Points" ,"100 Points", "250 Points", "500 Points"};
        private final String[] Price = {"20 L.E", "45 L.E", "100 L.E", "200 L.E"};

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
