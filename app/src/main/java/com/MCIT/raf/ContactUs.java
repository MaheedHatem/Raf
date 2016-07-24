package com.MCIT.raf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("3al Raf");
        Element versionElement = new Element();
        versionElement.setTitle("Version 0.89 (Beta)");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("3al raf is an online libarary where you can find any book you need cheaper than anywhere else, You can also borrow your favourite books")
                .setImage(R.drawable.logo)
                .addItem(versionElement)
                .addGroup("Connect with us")
                .addEmail("info@3alraf.com")
                .addWebsite("http://www.3alraf.com/")
                .addPlayStore("com.MCIT.raf")
                .create();

                setContentView(aboutPage);
    }


}
