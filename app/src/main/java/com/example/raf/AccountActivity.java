package com.example.raf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raf.data.CurrentUser;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;

public class AccountActivity extends AppCompatActivity {
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(CurrentUser.getUsername());
        setSupportActionBar(toolbar);
        TextView email = (TextView)findViewById(R.id.tvEmail);
        email.setText(CurrentUser.getEmail());
        TextView points = (TextView)findViewById(R.id.tvPoints);
        points.setText(Integer.toString(CurrentUser.getPoints()));
        image = (ImageView)findViewById(R.id.cover);
        if(CurrentUser.getImage()!=null){

        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });



//        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycle_account);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ActivityAdapter mAdapter = new ActivityAdapter();
//        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addActivity("Hello it's me", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"InPreogress");

        mAdapter.addActivity("Hello it's you", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"Returned");

        mAdapter.addActivity("Hello it's us", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"Purchase");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapBytes = stream.toByteArray();

                final ParseFile imageFile = new ParseFile(CurrentUser.getUsername()+"image.jpg", bitmapBytes);
                imageFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e!=null){
                            Toast.makeText(getApplicationContext(),
                                    "Error saving photo ",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            CurrentUser.setImage(imageFile);
                        }
                    }
                });

                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
