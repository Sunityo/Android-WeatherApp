package com.example.sunny.mainweatherapp.Retrofit;


import com.example.sunny.mainweatherapp.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

// this is the API split in certain parts from the URL
// this makes it easier to track and know what information is getting received from the API
// some parts of the URL is split into @query, since it queries the location of the user
// this is an interface class for the app

public interface OpenWeatherMapIO {

    // endpoint of what the today weather display will need in lat and lon
    // API Request method
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);

    // endpoint of what the today weather display will need in city search
    @GET("weather")
    Observable<WeatherResult> getWeatherByCity(@Query("q") String cityName,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);



}
