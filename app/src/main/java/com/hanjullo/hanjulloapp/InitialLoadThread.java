package com.hanjullo.hanjulloapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InitialLoadThread extends Thread {
    Context applicationContext;
    Handler handler;
    MasterKey masterKey;

    SharedPreferences debugPreferences;
    SharedPreferences userLoginPreferences;

    UserData userData;

    public InitialLoadThread(Context context, Handler handler) throws GeneralSecurityException, IOException {
        this.applicationContext = context;
        this.handler = handler;

        masterKey = new MasterKey.Builder(applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        debugPreferences = EncryptedSharedPreferences
                .create(applicationContext,
                        "DebugInfo",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        userLoginPreferences = EncryptedSharedPreferences
                .create(applicationContext,
                        "LoginInfo",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
    }

    @Override
    public void run() {
        Log.d("loading_thread", "loading started");

        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();

        boolean isAutologin = userLoginPreferences.getBoolean("is_autologin",false);

        if (isAutologin) {
            String userID = userLoginPreferences.getString("user_id", "");
            String userPW = userLoginPreferences.getString("user_pw", "");

            doLogin(userID, userPW);
        }

        bundle.putBoolean("finished_loading", true);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private void doLogin(String ID, String PW) {
        Retrofit retrofit = RetrofitClient.getClient();
        LoginInterface loginAPI = retrofit.create(LoginInterface.class);
        Call<LoginPullDTO> call = loginAPI.pushLogin("login", ID, PW);
        call.enqueue(new Callback<LoginPullDTO>() {
            @Override
            public void onResponse(@NonNull Call<LoginPullDTO> call, @NonNull Response<LoginPullDTO> response) {
                UserData userData = UserData.getInstance();
                if (!response.isSuccessful() || response.body() == null) {
                    userData.setLoginState(false);
                    return;
                }

                if (response.body().isSuccess()) {
                    userData.setCredential(ID, PW);
                    userData.setUserName(response.body().getUsername());
                    userData.setLoginState(true);
                } else {
                    userData.setLoginState(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginPullDTO> call, @NonNull Throwable t) {
                userData.setLoginState(false);
            }
        });
    }
}
