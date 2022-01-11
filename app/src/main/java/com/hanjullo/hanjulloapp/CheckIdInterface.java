package com.hanjullo.hanjulloapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CheckIdInterface {
    @FormUrlEncoded
    @POST("checkid.php")
    Call<CheckIdPullDTO> pushCheckId(
            @Field("mode") String mode,
            @Field("id") String id
    );
}
