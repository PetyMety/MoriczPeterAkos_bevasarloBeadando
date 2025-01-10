package com.example.moriczpeterakos_bevasarlobeadando;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitApiService {
    @GET("CWzkmg/termekek")
    Call<List<Termekek>> getAllTermekek();


    @GET("CWzkmg/termekek/{id}")
    Call<Termekek> getTermekekById(@Path("id") int id);


    @POST("CWzkmg/termekek")
    Call<Termekek> createTermekek(@Body Termekek termek);


    @PUT("CWzkmg/termekek/{id}")
    Call<Termekek> updateTermekek(@Path("id") int id, @Body Termekek termek);


    @DELETE("CWzkmg/termekek/{id}")
    Call<Void> deleteTermek(@Path("id") int id);
}