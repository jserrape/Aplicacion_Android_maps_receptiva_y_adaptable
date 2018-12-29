/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 19:48
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 29/12/18 19:48
 *
 */

package com.example.xenahort.dss_proyect;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private String email;
    private List<Producto> productos;
    private final String tipo;

    public Carrito(String ema){
        this.email=ema;
        tipo="Reserve";
        this.productos= new ArrayList<Producto>();
    }
}
