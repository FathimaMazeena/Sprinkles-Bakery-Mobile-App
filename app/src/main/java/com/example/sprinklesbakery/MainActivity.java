package com.example.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int delayInSeconds=5;
        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    Intent intent=new Intent(MainActivity.this, LoginOrRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
//TODO
                else{

                    Intent intent=new Intent(MainActivity.this, LoginOrRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, delayInSeconds * 1000);

    }
}