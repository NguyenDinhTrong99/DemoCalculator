package com.trongdeptrai.democalculator;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    //Set time out splash
    private static final int SET_TIME_OUT = 2500;//2500 miliseconds
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish(); // đóng màn hình activity hiện tại
                mIntent = new Intent(SplashActivity.this, MainActivity.class);
                // hiệu ứng chuyển activity
                startActivity(mIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
            }
        }, SET_TIME_OUT);
    }
}
