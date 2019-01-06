/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:07
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/01/19 12:08
 *
 */

package com.example.xenahort.dss_proyect.ElementosGestion;

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

    public boolean yaEsta(Producto pr){
        for(int i=0;i<productos.size();i++){
            if(productos.get(i).toString().equals(pr.toString())){
                return true;
            }
        }
        return false;
    }

    public void incrementarUnidad(Producto pr){
        for(int i=0;i<productos.size();i++){
            if(productos.get(i).toString().equals(pr.toString())){
                productos.get(i).incrementarUnidad();
                return;
            }
        }
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
