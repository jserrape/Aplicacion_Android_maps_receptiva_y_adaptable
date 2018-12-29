/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 15:26
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 27/12/18 21:42
 *
 */

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
