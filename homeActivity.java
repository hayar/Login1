package com.example.soyeon.login2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class homeActivity extends AppCompatActivity {

    private Button buttonSetting;
    private Button buttonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        buttonSetting = (Button) findViewById(R.id.buttonSetting);
        buttonList = (Button) findViewById(R.id.buttonList);

        buttonList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), PlantList.class);
                startActivity(intent2);
            }
        });

    }


    }


