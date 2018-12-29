/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 19:09
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 29/12/18 19:09
 *
 */

package com.example.xenahort.dss_proyect;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://dss-pharmacy.herokuapp.com/";

    public static PostService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(PostService.class);
    }
}
