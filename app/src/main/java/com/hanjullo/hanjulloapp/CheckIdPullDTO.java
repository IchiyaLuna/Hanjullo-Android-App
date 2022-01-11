package com.hanjullo.hanjulloapp;

import com.google.gson.annotations.SerializedName;

public class CheckIdPullDTO {
    @SerializedName("success")
    boolean success;

    @SerializedName("result")
    String result;

    public boolean isSuccess() {
        return success;
    }

    public String getResult() {
        return result;
    }
}
