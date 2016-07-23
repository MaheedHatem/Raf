package com.MCIT.raf;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.data.Request;

import java.util.Calendar;
import java.util.concurrent.Callable;

public class GetBookActivity extends AppCompatActivity {

    static TextView startDateText  ,endDateText , yourPointsTextView;
    static int  startYear , startMonth , startDay ,
                endYear , endMonth , endDay,
                currentYear , currentMonth , currentDay;
    static boolean startDateFlag = false;
    static boolean endDateFlag = false;
    static boolean operation = true;
    private boolean PURCHASE = true;
    private boolean BORROW = false;
    static LinearLayout endDateButton;

    static Context mContext;

   static TextView pickdelivery;
    static TextView pickend;
    static SwitchCompat switchCompat;



    static LinearLayout startDateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_book);
        mContext = this;
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
        endDateButton = (LinearLayout) content.findViewById(R.id.end_date_button);

        pickdelivery = (TextView)findViewById(R.id.delivery_date_title);
        pickend = (TextView)findViewById(R.id.pick2);





        ImageView bookImage = (ImageView)content.findViewById(R.id.cover);
        startDateButton = (LinearLayout) content.findViewById(R.id.start_date_button);
        final TextView pointsTextView= (TextView)content.findViewById(R.id.points_text);
        yourPointsTextView = (TextView)content.findViewById(R.id.your_points_text);
        final FloatingActionButton getBookButton = (FloatingActionButton) content.findViewById(R.id.getBook_button);
        LinearLayout getPointButton = (LinearLayout) content.findViewById(R.id.linear_get_point);
        startDateText = (TextView)content.findViewById(R.id.start_date_text);
        endDateText = (TextView)content.findViewById(R.id.end_date_text);
        final TextView deliverDateTitle = (TextView)content.findViewById(R.id.delivery_date_title);
        endDateButton.setVisibility(View.GONE);
        endDateButton.setEnabled(false);


        switchCompat = (SwitchCompat) findViewById(R.id.switch_compat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                operation = isChecked;
                if(isChecked == PURCHASE){
                    deliverDateTitle.setText("Pick Delivery date");
                    endDateButton.setVisibility(View.GONE);
                    endDateButton.setEnabled(false);
                    endDateText.setText("");
                    startDateText.setText("");
                    pointsTextView.setText(Integer.toString(bookPrice));
                    startDateFlag = false;
                    endDateFlag = false;
                }else{
                    deliverDateTitle.setText("Pick Start date");
                    endDateButton.setVisibility(View.VISIBLE);
                    endDateButton.setEnabled(true);
                    startDateText.setText("");
                    pointsTextView.setText("");

                }

            }
        });

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

//        purchaseBookRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDateButton.setText("Delivery date");
//                endDateButton.setEnabled(false);
//                endDateText.setText("");
//                pointsTextView.setText(Integer.toString(bookPrice));
//                startDateFlag = false;
//                endDateFlag = false;
//            }
//        });

//        borrowBookRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDateButton.setText("Start date");
//                endDateButton.setEnabled(true);
//                pointsTextView.setText("");
//            }
//        });


        getBookButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 if (!operation) {
                                                     if (startDateFlag && endDateFlag) {
                                                         if (CurrentUser.getPoints() > bookPrice) {
                                                             startAnim();
                                                             Request.addBorrowRequest(startYear, startMonth, startDay, endYear,
                                                                     endMonth, endDay, bookID, bookPrice, getApplicationContext(), new Callable() {
                                                                 @Override
                                                                 public Object call() throws Exception {
                                                                     stopAnim();
                                                                     finish();
                                                                     return null;
                                                                 }
                                                             });
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
                                                             startAnim();
                                                             Request.addGetRequest(startYear, startMonth, startDay, bookID,
                                                                     bookPrice, getApplicationContext() ,  new Callable() {
                                                                         @Override
                                                                         public Object call() throws Exception {
                                                                             stopAnim();
                                                                             finish();
                                                                             return null;
                                                                         }
                                                                     });
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
                                         }
        /*}*/);

//
        getPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getPointIntent = new Intent(getApplicationContext(), GetPointsActivity.class);
                GetBookActivity.this.startActivity(getPointIntent);
                overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i;
                if ((i = calculatePoints(bookPrice , operation))!=-1){
                    pointsTextView.setText(Integer.toString(i));
                } else
                    pointsTextView.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
//
        startDateText.addTextChangedListener(textWatcher);
        endDateText.addTextChangedListener(textWatcher);

    }

    @Override
    protected void onResume() {
        super.onResume();
        yourPointsTextView.setText(Integer.toString(CurrentUser.getPoints()));
    }

    public static void showStartDate(int year , int month , int day ){
        startYear = year;
        startMonth = month;
        startDay = day;
        startDateFlag = checkStartDate();
        if(!startDateFlag){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            // Add the buttons
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    startDateButton.performClick();

                }
            });

            builder.setTitle("You picked unavailable date");

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        startDateText.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));

        if(switchCompat.isChecked()) {
            pickdelivery.setText("Delivery Date");
        }else
        {
            pickdelivery.setText("Start Date");
        }
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) startDateButton.getLayoutParams();
//        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        startDateButton.setLayoutParams(lp);
//        lp = (RelativeLayout.LayoutParams) startDateText.getLayoutParams();
//        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        startDateText.setLayoutParams(lp);
    }

    public static void showEndDate(int year , int month , int day){
        endYear = year;
        endMonth = month;
        endDay = day;
        endDateFlag = checkEndDate();
        if(!endDateFlag){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            // Add the buttons
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    endDateButton.performClick();

                }
            });

            builder.setTitle("You picked unavailable date");

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        endDateText.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));

        pickend.setText("End Date");
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

    private int calculatePoints (int bookPrice , boolean operation){
        if (operation == PURCHASE)
            return bookPrice;
        if (endDateFlag && startDateFlag){
            //TODO complete this function
            return (int)bookPrice/2;
        }
        return -1;
    }

    void startAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }
    void stopAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }

}
