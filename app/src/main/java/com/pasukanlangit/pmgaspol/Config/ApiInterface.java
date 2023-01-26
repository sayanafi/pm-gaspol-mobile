package com.pasukanlangit.pmgaspol.Config;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"Accept: application/json"})

    @POST("login")
    Call<ResponseBody> loginPost(@Body ApiLogin userLogin);

    @POST("register")
    Call<ResponseBody> registerPost(@Body ApiRegister userModel);
}
