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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Carrito implements Serializable {

    private String email;
    private List<Producto> productos;
    private final String tipo;

    public String pr="";

    public Carrito() {
        tipo = "Purchase";
        this.productos = new ArrayList<>();
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


    /**
     * Elimina el producto del carrito de una posicion
     */
    public void eliminarPosicion(int n) {
        productos.remove(n);
    }

    /**
     * Comprueba si un producto esa en el carrito
     */
    public boolean yaEsta(Producto pr) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).toString().equals(pr.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Incrementa la unidad de un producto
     */
    public void incrementarUnidad(Producto pr) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).toString().equals(pr.toString())) {
                productos.get(i).incrementarUnidad();
                return;
            }
        }
    }

    /**
     * Genera un JSON con el contenido del carrito
     */
    public String generarJSON() {
        pr = "";
        for (int i = 0; i < productos.size(); i++) {
            pr += productos.get(i).getName() + ". ";
            pr += productos.get(i).getDescription() + ". ";
            pr += productos.get(i).getPharmacy() + ". ";
            pr += productos.get(i).getPrice() + "EUR x ";
            pr += productos.get(i).getUnidad() + "u;";
        }
        JSONObject jsonObj = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        try {
            jsonObj.put("email", email);
            jsonObj.put("type", tipo);
            jsonObj.put("date", currentDateandTime);
            jsonObj.put("products", pr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hacerPOST(currentDateandTime, pr);
        return jsonObj.toString();
    }

    /**
     * Realiza una peticion POST con el contenido del carrito
     */
    private void hacerPOST(String fecha, String pro) {
        GetPostService mAPIService = ApiUtils.getAPIService();
        mAPIService.crearPedido(email, tipo, fecha, pro).enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, retrofit2.Response<Respuesta> response) {

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

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
