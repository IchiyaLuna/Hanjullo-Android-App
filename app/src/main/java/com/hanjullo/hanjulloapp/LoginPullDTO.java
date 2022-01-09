package com.hanjullo.hanjulloapp;

import com.google.gson.annotations.SerializedName;

public class LoginPullDTO {
    @SerializedName("success")
    boolean success;

    @SerializedName("result")
    String result;

    @SerializedName("username")
    String username;

    public boolean isSuccess() {
        return success;
    }

    public String getResult() {
        return result;
    }

    public String getUsername() {
        return username;
    }
}
