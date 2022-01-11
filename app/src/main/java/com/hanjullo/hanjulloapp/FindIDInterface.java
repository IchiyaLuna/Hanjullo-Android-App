package com.hanjullo.hanjulloapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FindIDInterface {
    @FormUrlEncoded
    @POST("find_id.php")
    Call<FindIDPullDTO> pushFindID(
            @Field("mode") String mode,
            @Field("username") String username,
            @Field("phone") String phone
    );
}
