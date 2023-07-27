package com.project.term_project.activity.service;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/user/signup")
    Call<ResponseBody> postSignupRequest(@Body JsonObject requestBody);

    @POST("/user/login")
    Call<ResponseBody> postLoginRequest(@Body JsonObject requestBody);
}
