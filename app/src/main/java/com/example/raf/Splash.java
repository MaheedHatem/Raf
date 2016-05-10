package com.example.raf;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.parse.ParseUser;



public class Splash extends AppCompatActivity {
    ImageView imgbooks;
    ImageView imgshelf;
    ImageView imgraf;
    int animCount=0;
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    private getTopHomeThread featuredThread = new getTopHomeThread(0 , this);
    private getTopHomeThread newThread = new getTopHomeThread(1 , this);
    private getTopHomeThread popularThread = new getTopHomeThread(2 , this);

    private getTopCategoryThread classicThread = new getTopCategoryThread(0 , this);
    private getTopCategoryThread historyThread = new getTopCategoryThread(1 , this);
    private getTopCategoryThread bioThread = new getTopCategoryThread(2 , this);
    private getTopCategoryThread seriesThread = new getTopCategoryThread(3 , this);
    private getTopCategoryThread religionThread = new getTopCategoryThread(4 , this);

    private getWishListThread wishListThread = new getWishListThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        featuredThread.start();
        newThread.start();
        popularThread.start();
        classicThread.start();
        historyThread.start();
        bioThread.start();
        seriesThread.start();
        religionThread.start();
        if (ParseUser.getCurrentUser().getUsername() != null)
            wishListThread.start();


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



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                try {
                    featuredThread.join();
                    newThread.join();
                    popularThread.join();
                    classicThread.join();
                    historyThread.join();
                    bioThread.join();
                    seriesThread.join();
                    religionThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(ParseUser.getCurrentUser().getUsername() != null)
                {
                    try {
                        wishListThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent mainIntent = new Intent(Splash.this,HomeActivity.class);
                    Splash.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
                    Splash.this.finish();
                }
                else{
                    Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
                    Splash.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
                    Splash.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);






    }




}
