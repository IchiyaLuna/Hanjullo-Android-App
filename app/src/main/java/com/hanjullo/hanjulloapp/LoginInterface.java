package com.hanjullo.hanjulloapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginPullDTO> pushLogin(
            @Field("mode") String mode,
            @Field("id") String id,
            @Field("password") String password
    );
}
