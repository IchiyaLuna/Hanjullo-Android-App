package com.hanjullo.hanjulloapp;

public class UserData {
    private String userId;
    private String userPassword;
    private String userName;

    private static UserData instance = null;

    private UserData() {
        userId = "";
        userPassword = "";
        userName = "";
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }

        return instance;
    }

    public void setCredential(String id, String password) {
        this.userId = id;
        this.userPassword = password;
    }



    public void requestUserInfo() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
