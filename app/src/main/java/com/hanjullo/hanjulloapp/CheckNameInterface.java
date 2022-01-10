package com.hanjullo.hanjulloapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CheckNameInterface {
    @FormUrlEncoded
    @POST("checkname.php")
    Call<CheckNamePullDTO> pushCheckName(
            @Field("mode") String mode,
            @Field("username") String username
    );
}
