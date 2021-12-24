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

    NetworkConnectionCheck network;

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

        network = NetworkConnectionCheck.getInstance();
    }

    @Override
    public void run() {
        Log.d("loadingthread", "loading started");
        if (network.isConnected()) {
            Log.d("loadingthread", "connected");
            String mode = "reg";
            String id = "testid";
            String password = "testpw";
            String username = "김밍멍";
            String phone = "010-0000-0000";

            Retrofit retrofit = RetrofitClient.getClient();
            RegisterInterface registerAPI = retrofit.create(RegisterInterface.class);
            Call<ResponseDTO> call = registerAPI.pushRegister(mode, id, password, username, phone);
            call.enqueue(new Callback<ResponseDTO>() {
                @Override
                public void onResponse(@NonNull Call<ResponseDTO> call, @NonNull Response<ResponseDTO> response) {
                    
                    Log.d("onSuccess", response.body().getResult());

                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("finished_loading", true);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseDTO> call, @NonNull Throwable t) {
                    Log.d("onFailure", "err = " + t.getMessage());
                }
            });
        } else {
            Log.d("loadingthread", "not_connected");
        }

        Log.d("loadingthread", "loading finished");
        Log.d("isOnline", ": " + network.isConnected());
    }

    private void loadUserData() {

    }

    private void loadUserLogin() {
        int launchTime;
        boolean isAutoLogin;
        String userID;
        String userPW;

        launchTime = debugPreferences.getInt("launch_time", 0);

        debugPreferences.edit().putInt("launch_time", launchTime + 1).apply();

        launchTime = debugPreferences.getInt("launch_time", 0);

        ExceptionToast.showExceptionToast(applicationContext,"0", "실행 횟수 : " + launchTime);


        isAutoLogin= userLoginPreferences.getBoolean("is_autologin",false);

        if (isAutoLogin) {
            userID = userLoginPreferences.getString("user_id", "");
            userPW = userLoginPreferences.getString("user_pw", "");
            userData.setCredential(userID, userPW);
        } else {
            ExceptionToast.showExceptionToast(applicationContext,"20000", "자동 로그인 정보 없음.");
        }
    }

}
