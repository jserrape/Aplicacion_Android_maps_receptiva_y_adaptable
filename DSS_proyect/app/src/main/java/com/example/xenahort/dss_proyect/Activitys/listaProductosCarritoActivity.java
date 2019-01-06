/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:05
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/01/19 12:58
 *
 */

package com.example.xenahort.dss_proyect.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.xenahort.dss_proyect.ElementosGestion.Carrito;
import com.example.xenahort.dss_proyect.R;

public class ListaProductosCarritoActivity extends AppCompatActivity {

    private Carrito carrito;

    private ListView lst;

    private Button btnVolver;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos_carrito);

        carrito= (Carrito) getIntent().getSerializableExtra("Carrito");
        Log.d("caro lista protos carro", this.carrito.toString());

        btnVolver = (Button) findViewById(R.id.btnvolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProductosCarritoActivity.this, MapsActivity.class);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        });
        btnComprar = (Button) findViewById(R.id.btncomprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProductosCarritoActivity.this, HacerReservaActivity.class);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        });

        crearLista();
    }

    public void dialogoEliminarProducto(final int position){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("¿Quieres eliminar el producto del carrito?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        carrito.eliminarPosicion(position);
                        crearLista();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void crearLista(){
        String productos[]=new String[carrito.getProductos().size()];
        for(int i=0;i<carrito.getProductos().size();i++){
            productos[i]=carrito.getProductos().get(i).getName()+" X "+carrito.getProductos().get(i).getUnidad()+"u     "+(carrito.getProductos().get(i).getUnidad()*Integer.valueOf(carrito.getProductos().get(i).getPrice())+"€");
        }

        lst = findViewById(R.id.listacarro);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, productos);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogoEliminarProducto(position);
            }
        });
        if(carrito.getProductos().isEmpty()){
            btnComprar.setEnabled(false);
        }
    }
}
