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
    Button button5;
    static final String TAG = "GetPoinstActivity";
    // SKUs for our products:
    static final String SKU_TEN_POINTS = "ten_points";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_points);


        CustomList cs = new CustomList(this);
        ListView listView = (ListView) findViewById(R.id.points_list);


        listView.setAdapter(cs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text

                TextView ts = (TextView) findViewById(R.id.points_text);

                switch(position){
//                    case 0:
//                        CurrentUser.addPoints(5);
//                        Snackbar.make(view, "5 points added", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                        ts.setText(Integer.toString(CurrentUser.getPoints()));
//                        break;
//                    case 1:
//                        CurrentUser.addPoints(10);
//                         Snackbar.make(view, "10 points added", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                        ts.setText(Integer.toString(CurrentUser.getPoints()));
//                        break;
//                    case 2:
//                        CurrentUser.addPoints(20);
//                        Snackbar.make(view, "20 points added", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                         ts = (TextView) findViewById(R.id.points_text);
//                        ts.setText(Integer.toString(CurrentUser.getPoints()));
//                        break;
//                    case 3:
//                        CurrentUser.addPoints(35);
//                        Snackbar.make(view, "35 points added", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                         ts = (TextView) findViewById(R.id.points_text);
//                        ts.setText(Integer.toString(CurrentUser.getPoints()));
//                        break;
                    default:
                        Snackbar.make(view, "Online Purchasing is Coming Soon", Snackbar.LENGTH_LONG)
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
        super.onDestroy();

    }

    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] nPoints = {"50 Points" ,"100 Points", "250 Points", "500 Points"};
        private final String[] Price = {"15 L.E", "25 L.E", "50 L.E", "90 L.E"};

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
