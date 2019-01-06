/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 21:56
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/01/19 21:56
 *
 */

package com.example.xenahort.dss_proyect.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xenahort.dss_proyect.ElementosGestion.Carrito;
import com.example.xenahort.dss_proyect.R;
import com.example.xenahort.dss_proyect.Util.AdminSQLiteOpenHelper;

public class DetallesReservaActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textViewMail;
    private TextView textViewDetalles;

    private Button btnVolver;

    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_reserva);

        carrito = (Carrito) getIntent().getSerializableExtra("Carrito");

        textViewTitulo = (TextView) findViewById(R.id.fechaped);
        String fecha = getIntent().getExtras().getString("Fecha");
        textViewTitulo.setText("     Pedido:  " + fecha);

        textViewMail = (TextView) findViewById(R.id.mailped);
        textViewDetalles = (TextView) findViewById(R.id.detallesaped);

        btnVolver = (Button) findViewById(R.id.btnvolvermapf3);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesReservaActivity.this, HistorialActivity.class);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        });

        selectBBDD(fecha);
    }


    private void selectBBDD(String fecha) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor c = bd.rawQuery("select date, email, products from pedido where date = '" + fecha + "'", null);

        String column1 = "", column2 = "", column3 = "";
        if (c.moveToFirst()) {
            do {
                column1 = c.getString(0);
                column2 = c.getString(1);
                column3 = c.getString(2);
                Log.d("SQLLITE", column1 + " " + column2 + " " + column3);
            } while (c.moveToNext());
        }

        textViewTitulo.setText("     Email:  " + column2);
    }
}
