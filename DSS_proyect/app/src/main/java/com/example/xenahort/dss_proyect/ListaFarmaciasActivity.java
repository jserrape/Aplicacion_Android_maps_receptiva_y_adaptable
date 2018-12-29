/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 15:26
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 29/12/18 13:04
 *
 */

package com.example.xenahort.dss_proyect;

import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaFarmaciasActivity extends AppCompatActivity {

    private ListView lst;
    private ArrayAdapter<String> adaptador;
    private MatrixCursor cursor;

    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_farmacias);

        String farmaciasNombres[] = getIntent().getExtras().getStringArray("lista");
        lst = findViewById(R.id.listvw);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, farmaciasNombres);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) lst.getItemAtPosition(position);
                Intent intent = new Intent(ListaFarmaciasActivity.this, ListaProductosActivity.class);
                intent.putExtra("name", itemValue);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        });

        carrito= (Carrito) getIntent().getSerializableExtra("Carrito");
        Log.d("carrito lista", this.carrito.toString());
    }
}
