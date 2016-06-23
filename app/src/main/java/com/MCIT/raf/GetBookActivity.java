package com.MCIT.raf;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.data.Request;

import java.util.Calendar;

public class GetBookActivity extends AppCompatActivity {

    static TextView startDateText  , yourPointsTextView;
    static int  startYear , startMonth , startDay ,
                currentYear , currentMonth , currentDay;
    static boolean startDateFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String bookName = getIntent().getStringExtra(getString(R.string.book_intent_name));
        getSupportActionBar().setTitle("Get Copy of "+ bookName);

        Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);
        currentDay = c.get(Calendar.DAY_OF_MONTH);


        final String bookID = getIntent().getStringExtra(getString(R.string.book_intent_id));
        final int bookPrice = getIntent().getIntExtra(getString(R.string.book_intent_price) , 0);
        final byte[] bookCover = getIntent().getByteArrayExtra(getString(R.string.book_intent_cover));

        ScrollView content = (ScrollView) findViewById(R.id.getBook_content);

        ImageView bookImage = (ImageView)content.findViewById(R.id.book_cover);
        final Button startDateButton = (Button)content.findViewById(R.id.start_date_button);
        final TextView pointsTextView= (TextView)content.findViewById(R.id.points_text);
        yourPointsTextView = (TextView)content.findViewById(R.id.your_points_text);
        Button getBookButton = (Button)content.findViewById(R.id.getBook_button);
        Button getPointButton = (Button) content.findViewById(R.id.getPoint_button);
        startDateText = (TextView)content.findViewById(R.id.start_date_text);

        bookImage.setImageBitmap(BitmapFactory.decodeByteArray(bookCover,0,bookCover.length));
        pointsTextView.setText(Integer.toString(bookPrice));
        pointsTextView.setText(Integer.toString(bookPrice));

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDatePickerFragment dialogFragment = new StartDatePickerFragment();
                dialogFragment.show(getFragmentManager() , "startDateFlag");
            }
        });





        getBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (startDateFlag) {
                        if (CurrentUser.getPoints() > bookPrice) {
                            Request.addGetRequest(startYear, startMonth, startDay, bookID,
                                                    bookPrice , getApplicationContext());
                            Snackbar.make(v, "processing your request", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            yourPointsTextView.setText(Integer.toString(CurrentUser.getPoints()));
                        }
                        else
                            Toast.makeText(getApplicationContext(), "you don't have enough coins",
                                                            Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "please select valid delivery date",
                                                            Toast.LENGTH_LONG).show();
                    }
                }
            }
        );

        getPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getPointIntent = new Intent(getApplicationContext(), GetPointsActivity.class);
                GetBookActivity.this.startActivity(getPointIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        yourPointsTextView.setText(Integer.toString(CurrentUser.getPoints()));
    }

    public static void showStartDate(int year , int month , int day){
        startYear = year;
        startMonth = month;
        startDay = day;
        startDateFlag = checkStartDate();
        startDateText.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));
    }


    private static boolean checkStartDate(){
        if (startYear > currentYear) {
            startDateText.setTextColor(Color.BLACK);
            return true;
        }
        if (startYear == currentYear){
            if (startMonth > currentMonth) {
                startDateText.setTextColor(Color.BLACK);
                return true;
            }
            if (startMonth == currentMonth){
                if (startDay > currentDay) {
                    startDateText.setTextColor(Color.BLACK);
                    return true;
                }
            }
        }
        startDateText.setTextColor(Color.RED);
        return false;
    }




}
