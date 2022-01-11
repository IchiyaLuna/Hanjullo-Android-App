package com.hanjullo.hanjulloapp;

import com.google.gson.annotations.SerializedName;

public class FindIDPullDTO {
    @SerializedName("success")
    boolean success;

    @SerializedName("result")
    String result;

    @SerializedName("id")
    String id;

    public boolean isSuccess() {
        return success;
    }

    public String getID() {
        return id;
    }
}
