/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 16:07
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/01/19 16:07
 *
 */

package com.example.xenahort.dss_proyect.ElementosGestion;

public class Respuesta {

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
