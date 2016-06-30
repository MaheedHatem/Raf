package com.MCIT.raf;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.data.Request;

import java.util.Calendar;

public class GetBookActivity extends AppCompatActivity {

    static TextView startDateText  ,endDateText , yourPointsTextView;
    static int  startYear , startMonth , startDay ,
                endYear , endMonth , endDay,
                currentYear , currentMonth , currentDay;
    static boolean startDateFlag = false;
    static boolean endDateFlag = false;

    static Button startDateButton;
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

        TextView bookNameTextView = (TextView)content.findViewById(R.id.BookNameTXT);
        final Button endDateButton = (Button)content.findViewById(R.id.end_date_button);
        final RadioButton purchaseBookRadioButton = (RadioButton)content.findViewById(R.id.purchaseBook);
        final RadioButton borrowBookRadioButton = (RadioButton)content.findViewById(R.id.borrowBook);

        ImageView bookImage = (ImageView)content.findViewById(R.id.cover);
        startDateButton = (Button)content.findViewById(R.id.start_date_button);
        final TextView pointsTextView= (TextView)content.findViewById(R.id.points_text);
        yourPointsTextView = (TextView)content.findViewById(R.id.your_points_text);
        Button getBookButton = (Button)content.findViewById(R.id.getBook_button);
        Button getPointButton = (Button) content.findViewById(R.id.getPoint_button);
        startDateText = (TextView)content.findViewById(R.id.start_date_text);
        endDateText = (TextView)content.findViewById(R.id.end_date_text);

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
//                startDateButton.setEnabled(false);
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
                if (borrowBookRadioButton.isChecked()) {
                    if (startDateFlag && endDateFlag) {
                        if (CurrentUser.getPoints() > bookPrice) {
                            Request.addBorrowRequest(startYear, startMonth, startDay, endYear,
                                    endMonth, endDay, bookID, bookPrice, getApplicationContext());
                            Snackbar.make(v, "processing your request", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            yourPointsTextView.setText(Integer.toString(CurrentUser.getPoints()));
                        } else
                            Toast.makeText(getApplicationContext(), "you don't have enough coins",
                                    Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "please select valid start and end date",
                                Toast.LENGTH_LONG).show();
                } else {
                    if (startDateFlag) {
                        if (CurrentUser.getPoints() > bookPrice) {
                            Request.addGetRequest(startYear, startMonth, startDay, bookID,
                                    bookPrice, getApplicationContext());
                            Snackbar.make(v, "processing your request", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            yourPointsTextView.setText(Integer.toString(CurrentUser.getPoints()));

                        } else
                            Toast.makeText(getApplicationContext(), "you don't have enough coins",
                                    Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "please select valid delivery date",
                                Toast.LENGTH_LONG).show();
                    }
                }
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
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) startDateButton.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        startDateButton.setLayoutParams(lp);
        lp = (RelativeLayout.LayoutParams) startDateText.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        startDateText.setLayoutParams(lp);
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
