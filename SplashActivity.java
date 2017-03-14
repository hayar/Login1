package com.example.soyeon.login2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;

    protected void onCreate(Bundle savedInstanceStage)
    {
        super.onCreate(savedInstanceStage);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        handler = new Handler();

        handler.postDelayed(new Runnable(){

            public void run(){
                Intent intent = new Intent(SplashActivity.this, PlantList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 500);

    }

}