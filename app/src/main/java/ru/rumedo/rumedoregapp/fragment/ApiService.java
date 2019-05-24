package ru.rumedo.rumedoregapp.fragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users/create.php")
    Call<ApiRequest> addUser(
            @Query("skey") String skey,
            @Query("name") String name,
            @Query("surname") String surname,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("event") String event
    );
    @GET("users/list.php")
    Call<ApiRequest> listUsers(
        @Query("skey") String skey
    );

    @GET("users/find.php")
    Call<ApiRequest> getUserByEmail(
            @Query("skey") String skey,
            @Query("email") String email
    );
}
