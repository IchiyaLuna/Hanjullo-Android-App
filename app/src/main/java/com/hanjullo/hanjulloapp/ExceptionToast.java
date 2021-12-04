package com.hanjullo.hanjulloapp;

import android.content.Context;
import android.widget.Toast;

public class ExceptionToast {
    static void showExceptionToast(Context context, String errorCode, String errorMsg) {
        Toast.makeText(context, String.format("%s (%s)", errorMsg, errorCode), Toast.LENGTH_SHORT).show();
    }
}
