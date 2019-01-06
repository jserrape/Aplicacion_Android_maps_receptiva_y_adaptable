/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:06
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/12/18 20:45
 *
 */

package com.example.xenahort.dss_proyect.Comunicacion;

import com.example.xenahort.dss_proyect.ElementosGestion.Farmacia;
import com.example.xenahort.dss_proyect.ElementosGestion.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostService {

    @POST("/rest/users")
    @FormUrlEncoded
    Call<Producto> crearUsu(@Field("email") String email,
                            @Field("name") String name,
                            @Field("password") String password);

    @GET("/rest/pharmacies")
    Call<List<Farmacia>> getAllPharm();
}
