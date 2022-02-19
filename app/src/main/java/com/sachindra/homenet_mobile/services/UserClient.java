package com.sachindra.homenet_mobile.services;

import com.sachindra.homenet_mobile.models.History;
import com.sachindra.homenet_mobile.models.LoginCredentials;
import com.sachindra.homenet_mobile.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserClient {

    //Login as user
    @POST("auth/login")
    Call<ResponseBody> login(@Body LoginCredentials loginCredentials);

    //Register
    @POST("register-user")
    Call<ResponseBody> register(@Body User user);

    @GET("history")
    Call<List<History>> getHistory();
}
