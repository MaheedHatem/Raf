package com.example.raf;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raf.data.Request;

import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity {
    static int  deliveryYear , deliveryMonth , deliveryDay,
            currentYear , currentMonth , currentDay;
    static TextView deliveryDateTextView;
    static boolean deliveryDateFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        deliveryDateFlag = false;
        String bookName = getIntent().getStringExtra(getString(R.string.book_intent_name));
        final String bookID = getIntent().getStringExtra(getString(R.string.book_intent_id));
        EditText bookNameEditText = (EditText)findViewById(R.id.bookName);
        final Button deliveryDateButton = (Button)findViewById(R.id.delivery_date_button);
        Button addBookButton = (Button)findViewById(R.id.addBook);
        deliveryDateTextView = (TextView)findViewById(R.id.delivery_date);
        if(bookName!=null){
            bookNameEditText.setText(bookName);
            bookNameEditText.setEnabled(false);
        }
        deliveryDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryDatePickerFragment dialogFragment = new DeliveryDatePickerFragment();
                dialogFragment.show(getFragmentManager() , "deliveryDateFlag");
            }
        });
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deliveryDateFlag){
                    if(bookID!=null){
                        Request.addAddCopyRequest(deliveryYear, deliveryMonth, deliveryDay, bookID, getApplicationContext());
                        Snackbar.make(v, "processing your request", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else{
                        //TODO if adding new book
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "please select valid delivery date",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void showDeliveryDate(int year, int month, int day) {
        deliveryDateFlag = checkDeliveryDate(year, month , day);
        deliveryDay = day;
        deliveryMonth = month;
        deliveryYear = year;
        deliveryDateTextView.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));
    }

    private static boolean checkDeliveryDate(int year , int month , int day) {
        if (year > currentYear) {
            deliveryDateTextView.setTextColor(Color.BLACK);
            return true;
        }
        if (year == currentYear){
            if (month > currentMonth) {
                deliveryDateTextView.setTextColor(Color.BLACK);
                return true;
            }
            if (month == currentMonth){
                if (day > currentDay) {
                    deliveryDateTextView.setTextColor(Color.BLACK);
                    return true;
                }
            }
        }
        deliveryDateTextView.setTextColor(Color.RED);
        return false;
    }
}
