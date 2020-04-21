package com.example.sunny.mainweatherapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // retrofit is a HTTP client, where we get a HTTP request from the API
    // it turns the API into a java interface which is needed for this app
    // makes it easy to use a JSON data and parse it inot java objects
    // this is the retrofit client, since we are using retro fit
    // it also has the URL to obtain the information from the API
    // i created it as an instance so it can be easy to get
    
    private static Retrofit instance;

    public static Retrofit getInstance(){
        if(instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;

    }
}
