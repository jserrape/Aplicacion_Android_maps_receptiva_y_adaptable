/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:06
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/12/18 19:27
 *
 */

package com.example.xenahort.dss_proyect.Comunicacion;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://dss-pharmacy.herokuapp.com/";

    public static PostService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(PostService.class);
    }
}
