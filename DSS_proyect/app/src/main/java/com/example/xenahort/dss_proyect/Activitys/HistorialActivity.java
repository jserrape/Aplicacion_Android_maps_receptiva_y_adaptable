/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 20:45
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/01/19 20:45
 *
 */

package com.example.xenahort.dss_proyect.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.xenahort.dss_proyect.ElementosGestion.Carrito;
import com.example.xenahort.dss_proyect.R;
import com.example.xenahort.dss_proyect.Util.AdminSQLite;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private Button btnVolver;

    private ListView lst;

    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        carrito= (Carrito) getIntent().getSerializableExtra("Carrito");

        btnVolver = (Button) findViewById(R.id.btnvolvermapf2);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistorialActivity.this, MapsActivity.class);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        });

        selectBBDD();
    }

    private void selectBBDD() {
        AdminSQLite admin = new AdminSQLite(this, "administracion", null, 1);
        final List<String> list= new ArrayList<String>();

        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor c = bd.rawQuery("select date, email, products from pedido", null);
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0));
            } while (c.moveToNext());
        }
        String fechas[]=new String[list.size()];
        for(int i=0;i<list.size();i++){
            fechas[i]=" Día "+list.get(i).replaceAll("_",", hora ");
        }

        lst = findViewById(R.id.listhistorial);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, fechas);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) lst.getItemAtPosition(position);
                Intent intent = new Intent(HistorialActivity.this, DetallesReservaActivity.class);
                intent.putExtra("Carrito", carrito);
                intent.putExtra("Fecha", list.get(position));
                startActivityForResult(intent, 0);
            }
        });
    }
}
