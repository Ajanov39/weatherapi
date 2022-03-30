package com.ajanovski.weatherapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //http://api.weatherapi.com/v1/current.json?key=3d88db0eca2d44b7924153517221801&q=London&aqi=no

    @GET("current.json")
    Call<WeatherResponse> getWeather(
            @Query("key") String key,@Query("q") String city, @Query("aqi") String aqi);

    @POST("createUser")
    Call<WeatherResponse> createUser(@Body WeatherResponse weatherResponse);

    @GET("users/{user}/{repos}")
    Call<ArrayList<WeatherResponse>> listRepos(@Path("user") String user, @Path("repos") String repos);
}
