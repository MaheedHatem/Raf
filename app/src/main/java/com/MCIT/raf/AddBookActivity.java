package com.MCIT.raf;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MCIT.raf.data.Request;

import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity {
    static int  deliveryYear , deliveryMonth , deliveryDay,
            currentYear , currentMonth , currentDay;
    static TextView deliveryDateTextView;
    static boolean deliveryDateFlag = false;
    static Context mContext;
    static LinearLayout deliveryDateButton;

    static TextView pickdelivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Book");
        Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        deliveryDateFlag = false;
        final String[] bookName = {getIntent().getStringExtra(getString(R.string.book_intent_name))};
        final String bookID = getIntent().getStringExtra(getString(R.string.book_intent_id));
        int bookPrice = getIntent().getIntExtra(getString(R.string.book_intent_price) , 0);
        final byte[] bookCover = getIntent().getByteArrayExtra(getString(R.string.book_intent_cover));

        ImageView bookImage = (ImageView)findViewById(R.id.cover);
        final EditText bookNameEditText = (EditText)findViewById(R.id.bookName_EditText);
        TextView bookNameTextView = (TextView)findViewById(R.id.bookName_TextView);
        TextView priceTextView = (TextView)findViewById(R.id.price_textView);
        TextView booknametxt = (TextView)findViewById(R.id.BookNameTXT);
        deliveryDateButton = (LinearLayout) findViewById(R.id.linear_delivery_date);
        FloatingActionButton addBookButton = (FloatingActionButton)findViewById(R.id.addBook);
        deliveryDateTextView = (TextView)findViewById(R.id.delivery_date);
        pickdelivery = (TextView) findViewById(R.id.pick1);

        if(bookName[0] !=null){

            bookNameTextView.setText(bookName[0]);
            bookNameEditText.setVisibility(View.GONE);
            booknametxt.setVisibility(View.GONE);
            priceTextView.setText(Integer.toString(bookPrice));
            bookImage.setImageBitmap(BitmapFactory.decodeByteArray(bookCover,0,bookCover.length));
            getSupportActionBar().setTitle("Add "+bookName[0] +" Book");
        } else {
            bookNameEditText.setVisibility(View.VISIBLE);
            findViewById(R.id.divider).setVisibility(View.VISIBLE);
            bookNameTextView.setVisibility(View.GONE);
        }
        bookNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookName[0] = bookNameEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                        Request.addAddCopyRequest(deliveryYear, deliveryMonth, deliveryDay, bookID,
                                bookName[0], getApplicationContext());
                        Snackbar.make(v, "processing your request", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
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
        if(!deliveryDateFlag){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            // Add the buttons
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                }
            });

            builder.setTitle("You picked unavailable date");

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        deliveryDay = day;
        deliveryMonth = month;
        deliveryYear = year;
        deliveryDateTextView.setText(new StringBuilder().append(day).append("/").append(month +1)
                .append("/").append(year));
        pickdelivery.setText("Delivery Date");


//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) deliveryDateButton.getLayoutParams();
//        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        deliveryDateButton.setLayoutParams(lp);

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
