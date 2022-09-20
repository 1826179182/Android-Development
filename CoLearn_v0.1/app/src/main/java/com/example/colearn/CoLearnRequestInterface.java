package com.example.colearn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.colearn.pojo.Data;

import okhttp3.ResponseBody;
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
    Call<ResponseBody> login(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("users/{path}")
    Call<ResponseBody> changeInfo(@Field("changeResult") String changeResult, @Path("path") String path);

    @FormUrlEncoded
    @POST("lists/{path}")
    Call<ResponseBody> getList(@Path("path") String path);

    @GET("list/getTodoList")
    Call<ResponseBody> getTodoList();

    @GET("list/getCheckInList")
    Call<ResponseBody> getCheckInList();

    @GET("list/getPlantsList")
    Call<ResponseBody> getPlantsList();

    @FormUrlEncoded
    @POST("lists/{path}")
    Call<ResponseBody> saveList(@Field("todoList") JSONArray list, @Path("path") String path);

    @FormUrlEncoded
    @POST("list/saveTodoList")
    Call<ResponseBody> saveTodoList(@Field("todoList") JSONArray todoList);

    @FormUrlEncoded
    @POST("list/saveCheckInList")
    Call<ResponseBody> saveCheckInList(@Field("saveCheckInList") JSONArray checkInList);

    @FormUrlEncoded
    @POST("list/savePlantsList")
    Call<ResponseBody> savePlantsList(@Field("savePlantsList") JSONArray plantsList);

    @FormUrlEncoded
    @POST("insight/daily")
    Call<ResponseBody> getDailyAcitvities(@Field("account") String account,@Field("year") String year,@Field("month") String month);

}
