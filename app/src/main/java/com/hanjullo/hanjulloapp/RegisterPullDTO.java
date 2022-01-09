package com.hanjullo.hanjulloapp;

import com.google.gson.annotations.SerializedName;

public class RegisterPullDTO {
    @SerializedName("success")
    boolean success;

    @SerializedName("result")
    String result;

    @SerializedName("id")
    String id;

    @SerializedName("UID")
    int UID;

    public boolean isSuccess() {
        return success;
    }

    public String getResult() {
        return result;
    }

    public String getId() {
        return id;
    }

    public int getUID() {
        return UID;
    }
}
