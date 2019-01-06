/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 15:26
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 29/12/18 14:54
 *
 */

package com.example.xenahort.dss_proyect.Activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xenahort.dss_proyect.ElementosGestion.Carrito;
import com.example.xenahort.dss_proyect.R;
import com.example.xenahort.dss_proyect.Util.AdminSQLiteOpenHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HacerReservaActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    public static String STR = "A string to be encoded as QR code";

    private GoogleApiClient googleApiClient;

    private Carrito carrito;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_finalizada);

        carrito = (Carrito) getIntent().getSerializableExtra("Carrito");


        realizarPOST();

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void realizarPOST() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            carrito.setEmail(account.getEmail());
            STR = carrito.generarJSON();
            ImageView imageView = (ImageView) findViewById(R.id.myImage);
            try {
                Bitmap bitmap = encodeAsBitmap(STR);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            btn = (Button) findViewById(R.id.btnMap);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HacerReservaActivity.this, MapsActivity.class);
                    carrito = new Carrito();
                    intent.putExtra("Carrito", carrito);
                    startActivityForResult(intent, 0);
                }
            });
            insertarCarritoBBDD();
        }
    }

    private void insertarCarritoBBDD() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("date", currentDateandTime);
        registro.put("email", carrito.getEmail());
        registro.put("products", carrito.pr);

        bd.insert("pedido", null, registro);
        bd.close();
    }

}
