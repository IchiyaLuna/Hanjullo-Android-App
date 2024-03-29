package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SplashActivity extends AppCompatActivity {

    private boolean loadingSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadingSkip = false;

        LoadingHandler handler = new LoadingHandler(Looper.getMainLooper());

        LoadingTimerThread timerThread = new LoadingTimerThread(handler);

        try {
            InitialLoadThread loadThread = new InitialLoadThread(getApplicationContext(), handler);
            loadThread.start();
        } catch (GeneralSecurityException e) {
            ExceptionToast.showExceptionToast(this,"20001", "자동 로그인 정보를 불러오지 못했습니다.");
            loadingSkip = true;
        } catch (IOException e) {
            ExceptionToast.showExceptionToast(this,"10001", "자동 로그인 정보를 불러오지 못했습니다.");
            loadingSkip = true;
        }

        timerThread.start();
    }

    class LoadingHandler extends Handler {

        private boolean timerFinished;
        private boolean loadingFinished;

        public LoadingHandler(Looper looper) {
            super(looper);
            timerFinished = false;
            loadingFinished = false;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (!timerFinished) {
                timerFinished = msg.getData().getBoolean("finished_timer");
            }

            if (!loadingFinished) {
                loadingFinished = msg.getData().getBoolean("finished_loading");
            }

            if (timerFinished && (loadingSkip || loadingFinished)) {

                UserData userData = UserData.getInstance();

                Intent intent;

                if (userData.isLoggedIn()) {
                    intent = new Intent(SplashActivity.this, ProfileActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish();
            }
        }
    }
}