package com.hanjullo.hanjulloapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class LoadingTimerThread extends Thread {

    Handler handler;

    public LoadingTimerThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        int tick = 0;
        Log.d("thread", "run: thread on");

        while (tick < 2) {
            Log.d("tick", "run: currentSec" + tick / 10);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                tick += 1;
                continue;
            }

            tick += 1;
        }

        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putBoolean("finished_timer", true);
        message.setData(bundle);
        handler.sendMessage(message);

        Log.d("thread", "run: timer thread off");
    }
}
