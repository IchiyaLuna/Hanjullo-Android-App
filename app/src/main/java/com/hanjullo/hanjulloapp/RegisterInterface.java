package com.hanjullo.hanjulloapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseDTO> pushRegister(
            @Field("mode") String mode,
            @Field("id") String id,
            @Field("password") String password,
            @Field("username") String username,
            @Field("phone") String phone
    );
}
