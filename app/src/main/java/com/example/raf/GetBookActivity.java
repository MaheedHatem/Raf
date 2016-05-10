package com.example.raf;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;

public class GetBookActivity extends AppCompatActivity {

    static TextView startDateText , endDateText;
    static int  startYear , startMonth , startDay ,
                endYear , endMonth , endDay,
                currentYear , currentMonth , currentDay;
    static boolean startDateFlag = false;
    static boolean endDateFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);
        currentDay = c.get(Calendar.DAY_OF_MONTH);

        String bookName = getIntent().getStringExtra(getString(R.string.book_intent_name));
        int bookPrice = getIntent().getIntExtra(getString(R.string.book_intent_price) , 0);

        ScrollView content = (ScrollView) findViewById(R.id.getBook_content);

        TextView bookNameTextView = (TextView)content.findViewById(R.id.bookName);
        final Button startDateButton = (Button)content.findViewById(R.id.start_date_button);
        final Button endDateButton = (Button)content.findViewById(R.id.end_date_button);
        final RadioButton purchaseBookRadioButton = (RadioButton)content.findViewById(R.id.purchaseBook);
        final RadioButton borrowBookRadioButton = (RadioButton)content.findViewById(R.id.borrowBook);
        startDateText = (TextView)content.findViewById(R.id.start_date_text);
        endDateText = (TextView)content.findViewById(R.id.end_date_text);

        bookNameTextView.setText(bookName);

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDatePickerFragment dialogFragment = new StartDatePickerFragment();
                dialogFragment.show(getFragmentManager() , "startDateFlag");
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndDatePickerFragment dialogFragment = new EndDatePickerFragment();
                dialogFragment.show(getFragmentManager() , "endDateFlag");
            }
        });

        purchaseBookRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateButton.setEnabled(false);
                endDateButton.setEnabled(false);
                startDateText.setText("");
                endDateText.setText("");
            }
        });

        borrowBookRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateButton.setEnabled(true);
                endDateButton.setEnabled(true);
            }
        });
    }

    public static void showStartDate(int year , int month , int day){
        startDateText.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));
        startYear = year;
        startMonth = month;
        startDay = day;
        startDateFlag = checkStartDate();
    }

    public static void showEndDate(int year , int month , int day){
        endDateText.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));
        endYear = year;
        endMonth = month;
        endDay = day;
        endDateFlag = checkEndDate();
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

    private static boolean checkEndDate(){
        if (endYear > currentYear) {
            endDateText.setTextColor(Color.BLACK);
            return true;
        }
        if (endYear == currentYear){
            if (endMonth > currentMonth) {
                endDateText.setTextColor(Color.BLACK);
                return true;
            }
            if (endMonth == currentMonth){
                if (endDay > currentDay) {
                    endDateText.setTextColor(Color.BLACK);
                    return true;
                }
            }
        }
        endDateText.setTextColor(Color.RED);
        return false;
    }

    private int calculatePoints (){
        if (endDateFlag && startDateFlag){
            int weeks;
        }
        return -1;
    }
}
