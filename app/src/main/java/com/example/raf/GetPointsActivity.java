package com.example.raf;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.raf.data.CurrentUser;

public class GetPointsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_points);

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
}
