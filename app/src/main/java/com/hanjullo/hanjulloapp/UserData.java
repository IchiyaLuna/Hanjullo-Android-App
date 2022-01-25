package com.hanjullo.hanjulloapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class UserData {
    private boolean isLoggedIn;
    private String userId;
    private String userPassword;
    private String userName;

    private static UserData instance = null;

    private UserData() {
        userId = "";
        userPassword = "";
        userName = "";
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }

        return instance;
    }

    public void setCredential(String id, String password) {
        this.userId = id;
        this.userPassword = password;
    }

    public void setAutoLogin(Context context, boolean isAutologin) throws GeneralSecurityException, IOException {

        MasterKey masterKey = new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();


        SharedPreferences userLoginPreferences = EncryptedSharedPreferences
                .create(context,
                        "LoginInfo",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        SharedPreferences.Editor editor = userLoginPreferences.edit();

        if (isAutologin) {
            editor.putBoolean("is_autologin", true);
            editor.putString("user_id", userId);
            editor.putString("user_pw", userPassword);
        } else {
            editor.putBoolean("is_autologin", false);
            editor.putString("user_id", "");
            editor.putString("user_pw", "");
        }

        editor.apply();
    }

    public void setLoginState(boolean state) {
        this.isLoggedIn = state;
        Log.d("[USERDATA]", "setLoginState: " + state);
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
