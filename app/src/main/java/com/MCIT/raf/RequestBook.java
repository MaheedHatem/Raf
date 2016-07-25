package com.MCIT.raf;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.MCIT.raf.data.NewBookRequest;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class RequestBook extends AppCompatActivity {

    private static final String[] suggestions = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Suggest book");
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView bookNameTextView = (AutoCompleteTextView)findViewById(R.id.req_sugg);
                if(!bookNameTextView.getText().toString().equals("")){
                    startAnim();
                    NewBookRequest.addNewBookRequest(bookNameTextView.getText().toString(), getApplicationContext(), new Callable() {
                        @Override
                        public Object call() throws Exception {
                            endAnim();
                            finish();
                            return null;
                        }
                    });
                    Snackbar.make(v, "Request in progress", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{
                    Snackbar.make(v, "Invalid Book Name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        NewBookRequest.getAutoCompleteList( new FindCallback<NewBookRequest>(){
            ArrayList<String> books = new ArrayList<String>();
            @Override
            public void done(List<NewBookRequest> objects, ParseException e) {
                for(NewBookRequest request : objects){
                    if(!books.contains(request.getBookName())){
                        books.add(request.getBookName());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_dropdown_item_1line,books);
                AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.req_sugg);
                textView.setAdapter(adapter);
            }
        } , getApplicationContext());



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View current = getCurrentFocus();
        if (current != null) current.clearFocus();
    }
    void startAnim(){

    }
    void endAnim(){

    }
}
