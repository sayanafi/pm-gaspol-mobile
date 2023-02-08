package com.pasukanlangit.pmgaspol.Config;

import com.pasukanlangit.pmgaspol.SuperUser.Model.ProfileModel;
import com.pasukanlangit.pmgaspol.SuperUser.Model.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers({"Accept: application/json"})

    @POST("login")
    Call<ResponseBody> loginPost(@Body ApiLogin userLogin);

    @POST("register")
    Call<ResponseBody> registerPost(@Body ApiRegister userModel);

    //Show Unaccepted Users
    @GET("userapprove")
    Call<ResponseBody> getAllUserRequest();

    //Update Unaccepted Users By ID
    @POST("approve/{id}")
    Call<ResponseBody> ApproveUserById(@Path("id") String id);

//    Call<ResponseBody> ApproveUserById();

    //Show Accepted Users
    @GET("usersapi")
    Call<ResponseBody> getAlluser();

    //Search User
   /* @GET("usersapi/{id}")
    Call<ResponseBody> getUserById(@Path("id") String id);*/

    //Update User
    @PATCH("UsersApi/update/{id}")
    Call<ResponseBody> UpdateUserById(@Path("id") String id,@Body ProfileModel nama);

    @POST("createTeam")
    Call<ResponseBody> teamPost(@Body ApiCreateTeam createTeam);

    @GET("listTeam")
    Call<ResponseBody> getAllTeam();

    /*@DELETE("deleteTeam/{id}")
    Call<ResponseBody> deleteTeamById(@Path("id") int id);*/

    @GET("project")
    Call<ResponseBody> getAllProject();

}
