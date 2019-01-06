/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:07
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/01/19 12:08
 *
 */

package com.example.xenahort.dss_proyect.ElementosGestion;

import com.example.xenahort.dss_proyect.Comunicacion.ApiUtils;
import com.example.xenahort.dss_proyect.Comunicacion.GetPostService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Carrito implements Serializable {

    private String email;
    private List<Producto> productos;
    private final String tipo;

    public Carrito() {
        tipo = "Purchase";
        this.productos = new ArrayList<Producto>();
    }

    public void addProducto(Producto pr) {
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

    public void eliminarPosicion(int n) {
        productos.remove(n);
    }

    public boolean yaEsta(Producto pr) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).toString().equals(pr.toString())) {
                return true;
            }
        }
        return false;
    }

    public void incrementarUnidad(Producto pr) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).toString().equals(pr.toString())) {
                productos.get(i).incrementarUnidad();
                return;
            }
        }
    }

    public String generarJSON() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        String prod = "";
        for (int i = 0; i < productos.size(); i++) {
            prod += productos.get(i).getName() + ". ";
            prod += productos.get(i).getDescription() + ". ";
            prod += productos.get(i).getPharmacy() + ". ";
            prod += productos.get(i).getPrice() + "EUR x ";
            prod += productos.get(i).getUnidad() + "u;";
        }
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("email", email);
            jsonObj.put("type", tipo);
            jsonObj.put("date", c.getTime());
            jsonObj.put("products", prod);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hacerPOST(c.getTime().toString(),prod);
        return jsonObj.toString();
    }

    private void hacerPOST(String fecha, String pro) {
        GetPostService mAPIService = ApiUtils.getAPIService();
        mAPIService.crearPedido(email,tipo,fecha,pro).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, retrofit2.Response<Producto> response) {

            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {

            }
        });
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
