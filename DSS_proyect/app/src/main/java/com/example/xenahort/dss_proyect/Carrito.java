/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 19:48
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 29/12/18 19:48
 *
 */

package com.example.xenahort.dss_proyect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements Serializable {

    private String email;
    private List<Producto> productos;
    private final String tipo;

    public Carrito(){
        tipo="Reserve";
        this.productos= new ArrayList<Producto>();
    }

    public void addProducto(Producto pr){
        this.productos.add(pr);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "email='" + email + '\'' +
                ", productos=" + productos +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
