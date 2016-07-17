package com.MCIT.raf;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.MCIT.raf.data.Book;
import com.MCIT.raf.data.CurrentUser;
import com.MCIT.raf.data.DeliveryPoint;
import com.parse.ParseUser;



public class Splash extends AppCompatActivity {
    ImageView imgbooks;
    ImageView imgshelf;
    ImageView imgraf;
    int animCount=0;
    private final int SPLASH_DISPLAY_LENGTH = 3000;


    private Context current;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        current =this;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if(! isNetworkAvailable())
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(current);
            dlgAlert.setMessage("Please Check Your Internet Connection");
            dlgAlert.setTitle("3al Raf");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }



        imgbooks = (ImageView) findViewById(R.id.splash_books);
        imgraf = (ImageView) findViewById(R.id.raf);
        imgshelf = (ImageView) findViewById(R.id.splash_shelf);



        imgraf.setTranslationY(metrics.heightPixels/2f);


        ObjectAnimator a1 = ObjectAnimator.ofFloat(imgshelf, "x",-(metrics.widthPixels)*2 ,0f);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(imgbooks, "y",-(metrics.heightPixels) ,0f);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(imgraf, "y",(metrics.heightPixels) ,0f);

        a1.setDuration(1500);
        a2.setDuration(1500);
        a3.setDuration(1000);
        a3.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet as = new AnimatorSet();



        as.play(a3).after(500);
        as.play(a1).with(a2);
        as.start();
        // to do make sure that he has connection before starting


        as.addListener(new android.animation.Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                startAnim();

            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {

            }


        });

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            // Code to run once
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
            if(ParseUser.getCurrentUser() != null)
                ParseUser.getCurrentUser().logOut();
        }

        task.execute((Void)null);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void startAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }

     public  AsyncTask<Void , Void , Void> task = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {

            Book.getTopHomeRefresh(0 , getApplicationContext());
            Book.getTopHomeRefresh(1 , getApplicationContext());
            Book.getTopHomeRefresh(2 , getApplicationContext());
            Book.getTopCategoryRefresh(0 , getApplicationContext());
            Book.getTopCategoryRefresh(1 , getApplicationContext());
            Book.getTopCategoryRefresh(2 , getApplicationContext());
            Book.getTopCategoryRefresh(3 , getApplicationContext());
            Book.getTopCategoryRefresh(4 , getApplicationContext());
            DeliveryPoint.getDeliveryPointFirstTime(getApplicationContext());
            if (ParseUser.getCurrentUser() != null) {
                CurrentUser.fetchRequests();
                CurrentUser.getWishlistFirstTime();
            }
            return null;
        }
        @Override
        protected void onPostExecute(final Void none) {
            if (ParseUser.getCurrentUser() != null) {
                    Intent mainIntent = new Intent(Splash.this, HomeActivity.class);
                    Splash.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                    Splash.this.finish();
            } else {
                Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                Splash.this.finish();
            }
        }
    };
}
