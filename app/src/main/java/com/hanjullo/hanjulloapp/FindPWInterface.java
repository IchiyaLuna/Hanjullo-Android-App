package com.hanjullo.hanjulloapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FindPWInterface {
    @FormUrlEncoded
    @POST("reset_pw.php")
    Call<FindPWPullDTO> pushFindPW(
            @Field("mode") String mode,
            @Field("id") String id,
            @Field("password") String password,
            @Field("phone") String phone
    );
}
