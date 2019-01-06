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
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xenahort.dss_proyect.ElementosGestion.Carrito;
import com.example.xenahort.dss_proyect.R;
import com.example.xenahort.dss_proyect.Util.AdminSQLite;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.regex.Pattern;

public class DetallesReservaActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textViewMail;
    private TextView textViewDetalles;

    private Button btnVolver;

    private Carrito carrito;

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    public static String STR = "A string to be encoded as QR code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_reserva);

        carrito = (Carrito) getIntent().getSerializableExtra("Carrito");

        textViewTitulo = (TextView) findViewById(R.id.fechaped);
        String fecha = getIntent().getExtras().getString("Fecha");
        textViewTitulo.setText("       Pedido:  " + fecha.replaceAll("_", " "));

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
        crearQr();
    }


    private void selectBBDD(String fecha) {
        AdminSQLite admin = new AdminSQLite(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor c = bd.rawQuery("select date, email, products from pedido where date = '" + fecha + "'", null);

        String column1 = "", column2 = "", column3 = "";
        if (c.moveToFirst()) {
            do {
                column1 = c.getString(0);
                column2 = c.getString(1);
                column3 = c.getString(2);
                STR="date:"+column1+"; products:"+column3+"; email:"+column2;
            } while (c.moveToNext());
        }
        textViewMail.setText("     Email:\n     " + column2);
        String texto = "";
        String[] productos = column3.replaceAll("EUR", "€").split(";");
        for (int i = 0; i < productos.length; i++) {
            String[] parts = productos[i].split(Pattern.quote("."));
            texto += "    " + parts[2] + ":\n";
            texto += "       " + parts[0] + parts[3];
            texto += "\n\n";
        }

        textViewDetalles.setText(texto);
    }

    private void crearQr(){
        ImageView imageView = (ImageView) findViewById(R.id.myImage2);
        try {
            Bitmap bitmap = encodeAsBitmap(STR);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
