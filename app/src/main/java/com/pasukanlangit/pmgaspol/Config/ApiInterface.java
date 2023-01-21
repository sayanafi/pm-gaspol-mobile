package com.pasukanlangit.pmgaspol.Config;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"Accept: application/json"})

    @GET("login")
    Call<ApiLogin> loginPost(@Body ApiLogin userLogin);

    @POST("register")
    Call<ApiRegister> registerPost(@Body ApiRegister userModel);
}
