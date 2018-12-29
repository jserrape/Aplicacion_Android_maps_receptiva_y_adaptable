/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 22:06
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 29/12/18 22:06
 *
 */

package com.example.xenahort.dss_proyect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class listaProductosCarrito extends AppCompatActivity {

    private Carrito carrito;

    private ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos_carrito);

        carrito= (Carrito) getIntent().getSerializableExtra("Carrito");
        Log.d("caro lista protos carro", this.carrito.toString());

        String productos[]=new String[carrito.getProductos().size()];
        for(int i=0;i<carrito.getProductos().size();i++){
            productos[i]=carrito.getProductos().get(i).getName();
        }

        lst = findViewById(R.id.listacarro);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, productos);
        lst.setAdapter(adapter);
    }
}
