package com.MCIT.raf;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.util.IabHelper;
import com.MCIT.raf.util.IabResult;

public class GetPointsActivity extends AppCompatActivity {
    IabHelper mHelper;
    static final String TAG = "GetPointsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_points);

        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp/7Q5PKVYq0FNToPCqKzisIHJpxW02R12JuyPGD/yovu0nM4hM8bvDXoJ/w2HJdGkWVNjQGrfajb/73zPP7qsXE/dYSvOfy4lNPUxheNeJxRnT8IsfKYYD62zLPNMEPjytAkgudcYqbEUxf97nnvdk/ukllLEB6I+o3VSdJZYc2lHOt7v68Vtl9OY/s5NE8ZtgtyZRNYXl9E0O/KwLwSiGmUm0hVAaDiTYRslldsUI7SNGRD8RUIJ2D5ioqbFMeaEZG2zhrJ60MPmtY/kh7p1wTEiF6xxv0S0EszFP8yMopus90/Isko7JUx/J6uq0U+seNdGzzDlGhlERXi3T7owwIDAQAB";

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    return;
                }
                // IAB is fully set up!
            }
        });
        Button button5 = (Button)findViewById(R.id.button3);
        Button button10 = (Button)findViewById(R.id.button6);
        Button button20 = (Button)findViewById(R.id.button5);
        Button button35 = (Button)findViewById(R.id.button4);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.addPoints(10);
                Snackbar.make(v, "10 points added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.addPoints(25);
                Snackbar.make(v, "25 points added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.addPoints(50);
                Snackbar.make(v, "50 points added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.addPoints(100);
                Snackbar.make(v, "100 points added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
}
