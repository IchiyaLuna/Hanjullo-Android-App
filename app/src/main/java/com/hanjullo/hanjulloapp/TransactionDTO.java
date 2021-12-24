package com.hanjullo.hanjulloapp;

import com.google.gson.annotations.SerializedName;

public class TransactionDTO {

    @SerializedName("mode")
    public String mode;

    @SerializedName("UID")
    public int UID;

    @SerializedName("id")
    public String id;

    @SerializedName("password")
    public String password;

    @SerializedName("username")
    public String username;

    @SerializedName("phone")
    public String phone;
}
