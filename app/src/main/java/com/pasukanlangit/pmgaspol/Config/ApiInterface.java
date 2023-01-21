package com.pasukanlangit.pmgaspol.Config;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Accept: application/json"})
    @POST("register")
    Call<ApiClient> createPost(@Body ApiClient userModel);
}
