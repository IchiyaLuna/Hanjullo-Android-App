package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences userLoginPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            loadUserLogin();
        } catch (GeneralSecurityException e) {
            ExceptionToast.showExceptionToast(this,"20001", "자동 로그인 정보를 불러오지 못했습니다.");
        } catch (IOException e) {
            ExceptionToast.showExceptionToast(this,"10001", "자동 로그인 정보를 불러오지 못했습니다.");
        }
    }

    private void checkNetwork() {
        boolean isConnected;
    }

    private void loadUserLogin() throws GeneralSecurityException, IOException {
        boolean isAutoLogin;
        String userID;
        String userPW;

        MasterKey masterKey = new MasterKey.Builder(getApplicationContext(), MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        userLoginPreferences = EncryptedSharedPreferences
        .create(getApplicationContext(),
                "LoginInfo",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        isAutoLogin= userLoginPreferences.getBoolean("is_autologin",false);

        if (isAutoLogin) {
            userID = userLoginPreferences.getString("user_id", "");
            userPW = userLoginPreferences.getString("user_pw", "");
        } else {

        }
    }
}