package com.example.dooogg;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Retrofit_interface {
    @GET("database")
    Call<List<action>> test_api_get();
}
