package com.example.raf;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.raf.data.Request;

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
        final String bookID = getIntent().getStringExtra(getString(R.string.book_intent_id));
        final int bookPrice = getIntent().getIntExtra(getString(R.string.book_intent_price) , 0);

        ScrollView content = (ScrollView) findViewById(R.id.getBook_content);

        TextView bookNameTextView = (TextView)content.findViewById(R.id.bookName);
        final Button startDateButton = (Button)content.findViewById(R.id.start_date_button);
        final Button endDateButton = (Button)content.findViewById(R.id.end_date_button);
        final RadioButton purchaseBookRadioButton = (RadioButton)content.findViewById(R.id.purchaseBook);
        final RadioButton borrowBookRadioButton = (RadioButton)content.findViewById(R.id.borrowBook);
        final TextView pointsTextView= (TextView)content.findViewById(R.id.points_text);
        Button getBookButton = (Button)content.findViewById(R.id.getBook_button);
        Button getPointButton = (Button) content.findViewById(R.id.getPoint_button);
        startDateText = (TextView)content.findViewById(R.id.start_date_text);
        endDateText = (TextView)content.findViewById(R.id.end_date_text);

        bookNameTextView.setText(bookName);
        pointsTextView.setText(Integer.toString(bookPrice));

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
                //startDateButton.setEnabled(false);
                startDateButton.setText("Delivery date");
                endDateButton.setEnabled(false);
//                startDateText.setText("");
                endDateText.setText("");
                pointsTextView.setText(Integer.toString(bookPrice));
                startDateFlag = false;
                endDateFlag = false;
            }
        });

        borrowBookRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startDateButton.setEnabled(true);
                startDateButton.setText("Start date");
                endDateButton.setEnabled(true);
                pointsTextView.setText("");
            }
        });

        getBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (borrowBookRadioButton.isChecked())
                    Request.addBorrowRequest(startYear , startMonth , startDay,
                            endYear , endMonth , endDay , bookID , getApplicationContext());
                else
                    Request.addGetRequest(startYear , startMonth , startDay , bookID ,
                                            getApplicationContext());


                Snackbar.make(v, "processing your request", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getPointIntent = new Intent(getApplicationContext(), GetPointsActivity.class);
                GetBookActivity.this.startActivity(getPointIntent);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i;
                if ((i = calculatePoints(bookPrice , purchaseBookRadioButton))!=-1){
                    pointsTextView.setText(Integer.toString(i));
                } else
                    pointsTextView.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        startDateText.addTextChangedListener(textWatcher);
        endDateText.addTextChangedListener(textWatcher);
    }

    public static void showStartDate(int year , int month , int day){
        startYear = year;
        startMonth = month;
        startDay = day;
        startDateFlag = checkStartDate();
        startDateText.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));
    }

    public static void showEndDate(int year , int month , int day){
        endYear = year;
        endMonth = month;
        endDay = day;
        endDateFlag = checkEndDate();
        endDateText.setText(new StringBuilder().append(day).append("/").append(month +1)
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

    private int calculatePoints (int bookPrice , RadioButton radioButton){
        if (radioButton.isChecked())
            return bookPrice;
        if (endDateFlag && startDateFlag){
            //TODO complete this function
            return (int)bookPrice/2;
        }
        return -1;
    }
}
