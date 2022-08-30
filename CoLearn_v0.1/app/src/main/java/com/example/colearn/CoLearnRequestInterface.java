package com.example.colearn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.colearn.components.Data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CoLearnRequestInterface {


    @FormUrlEncoded
    @POST("register")
    Call<Data<JSON>> register(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<Data<JSON>> login(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("users/{path}")
    Call<Data<JSON>> changeInfo(@Field("changeResult") String changeResult, @Path("path") String path);

    @GET("list/getTodoList")
    Call<Data<JSON>> getTodoList();

    @GET("list/getCheckInList")
    Call<Data<JSON>> getCheckInList();

    @GET("list/getPlantsList")
    Call<Data<JSON>> getPlantsList();

    @FormUrlEncoded
    @POST("list/saveTodoList")
    Call<Data<JSON>> saveTodoList(@Field("todoList") JSONArray todoList);

    @FormUrlEncoded
    @POST("list/saveCheckInList")
    Call<Data<JSON>> saveCheckInList(@Field("saveCheckInList") JSONArray checkInList);

    @FormUrlEncoded
    @POST("list/savePlantsList")
    Call<Data<JSON>> savePlantsList(@Field("savePlantsList") JSONArray plantsList);


}
