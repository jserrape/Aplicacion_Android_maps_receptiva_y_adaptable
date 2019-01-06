/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:04
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/01/19 12:55
 *
 */

package com.example.xenahort.dss_proyect.Activitys;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xenahort.dss_proyect.ElementosGestion.Carrito;
import com.example.xenahort.dss_proyect.Comunicacion.GetService;
import com.example.xenahort.dss_proyect.Util.MultiSelectionAdapter;
import com.example.xenahort.dss_proyect.ElementosGestion.Producto;
import com.example.xenahort.dss_proyect.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaProductosActivity extends Activity implements OnClickListener {

    private ListView mListView;
    private Button btnShowCheckedItems;
    private MultiSelectionAdapter<Producto> mAdapter;

    private String farmacia;
    private ArrayList<Producto> productos;

    private Carrito carrito;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_tienda);
        cargarProducos();

        carrito = (Carrito) getIntent().getSerializableExtra("Carrito");
        Log.d("carrito lista productos", this.carrito.toString());
        this.carrito.setEmail("aniadidos cosas");
    }

    public void cargarProducos() {
        mListView = (ListView) findViewById(android.R.id.list);
        btnShowCheckedItems = (Button) findViewById(R.id.btnShowCheckedItems);
        farmacia = getIntent().getExtras().getString("name");
        TextView textView = (TextView) findViewById(R.id.simpleTextView3);
        textView.setText("Farmacia: "+farmacia);
        productos = new ArrayList<Producto>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dss-pharmacy.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        GetService service = retrofit.create(GetService.class);
        Call<List<Producto>> call = service.getAllProduct();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                for (Producto post : response.body()) {
                    if (post.getPharmacy().equals(farmacia)) {
                        productos.add(post);
                    }
                }
                mAdapter = new MultiSelectionAdapter<>(ListaProductosActivity.this, productos);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
            }
        });
        btnShowCheckedItems.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mAdapter != null) {
            ArrayList<Producto> mArrayProducts = mAdapter.getCheckedItems();

            if (mArrayProducts.toString().equals("[]")) {
                Toast.makeText(getApplicationContext(), "Por favor, seleccione algún artículo", Toast.LENGTH_LONG).show();
            } else {
                //Intent intent = new Intent(ListaProductosActivity.this, CompraFinalizadaActivity.class);
                //intent.putExtra("Productos", mArrayProducts.toString());
                //intent.putExtra("Farmacia", farmacia);
                for (int i = 0; i < productos.size(); i++) {
                    for (int j = 0; j < mArrayProducts.size(); j++) {
                        Log.d("carrito lista productos", "."+productos.get(i).toString()+"=="+mArrayProducts.get(j)+".");
                        if (productos.get(i).toString().equals(mArrayProducts.get(j).toString())) {
                            if(carrito.yaEsta(mArrayProducts.get(j))){
                                carrito.incrementarUnidad(mArrayProducts.get(j));
                            }else{
                                mArrayProducts.get(j).setUnidad(1);
                                carrito.addProducto(mArrayProducts.get(j));
                            }
                        }
                    }
                }

                Intent intent = new Intent(ListaProductosActivity.this, MapsActivity.class);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        }
    }

}
