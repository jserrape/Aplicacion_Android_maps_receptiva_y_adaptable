package com.example.xenahort.dss_proyect;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {

    @GET("rest/pharmacies/all")
    Call<List<Farmacia>> getAllPharm();

    @GET("/rest/products/all")
    Call<List<Producto>> getAllProduct();
}
