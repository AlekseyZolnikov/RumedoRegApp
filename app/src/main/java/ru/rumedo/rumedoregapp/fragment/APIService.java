package ru.rumedo.rumedoregapp.fragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("data/2.5/weather")
    Call<APIService> addUser(
            @Query("q") String cityCountry,
            @Query("appid") String keyApi
    );
}
