package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences userLoginPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userLoginPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
    }


    private void loadUserLogin() {
        boolean isAutoLogin;

        isAutoLogin= userLoginPreferences.getBoolean("is_autologin",false);

        if (isAutoLogin) {

        }


    }
}