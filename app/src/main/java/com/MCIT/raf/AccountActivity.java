package com.MCIT.raf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MCIT.raf.data.CurrentUser;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class AccountActivity extends AppCompatActivity {
    private ImageView image;
    TextView points;

    @Override
    protected void onStart() {
        super.onStart();
        points.setText(Integer.toString(CurrentUser.getPoints()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(CurrentUser.getPname());
        setSupportActionBar(toolbar);
        TextView email = (TextView)findViewById(R.id.tvEmail);
        email.setText(CurrentUser.getEmail());
        points = (TextView)findViewById(R.id.tvPoints);
        points.setText(Integer.toString(CurrentUser.getPoints()));
        image = (ImageView)findViewById(R.id.cover);
        AsyncTask<Void,Void,Void> loadImageTask = new AsyncTask<Void, Void, Void>() {
            byte[] userImage;
            @Override
            protected Void doInBackground(Void... params) {

                userImage = CurrentUser.getImage();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(userImage!=null){
                    image.setImageBitmap(BitmapFactory.decodeByteArray(userImage,0 , userImage.length));
                }
            }
        };
        loadImageTask.execute((Void)null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View lo = (View) findViewById(R.id.bellow_actionbar);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) lo.getLayoutParams();
            params.topMargin=getResources().getDimensionPixelOffset(R.dimen.top_margin);
            lo.setLayoutParams(params);
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



        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycle_account);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ActivityAdapter mAdapter = new ActivityAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addRequest(CurrentUser.getRequest());

//        mAdapter.addActivity("Hello it's me", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"InPreogress");
//
//        mAdapter.addActivity("Hello it's you", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"Returned");
//
//        mAdapter.addActivity("Hello it's us", new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"Purchase");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            Uri targetUri = data.getData();
            final Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //TODO chnage compression ratio
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] bitmapBytes = stream.toByteArray();
                Snackbar.make(image, "Updating Photo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                final ParseFile imageFile = new ParseFile(CurrentUser.getEmail()+"image.jpg", bitmapBytes);
                imageFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e!=null){
                            Snackbar.make(image, "Error Saving Photo", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        else{
                            CurrentUser.setImage(imageFile);
                            Snackbar.make(image, "Photo Updated", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            image.setImageBitmap(bitmap);
                        }
                    }
                });

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void onClick(View v) {
        // Perform action on click
        Intent mainIntent = new Intent(AccountActivity.this,GetPointsActivity.class);
        AccountActivity.this.startActivity(mainIntent);
        overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
    }

}
