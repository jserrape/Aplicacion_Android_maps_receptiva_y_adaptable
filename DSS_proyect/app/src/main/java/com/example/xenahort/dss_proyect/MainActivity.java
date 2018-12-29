/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 29/12/18 15:26
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 27/12/18 22:09
 *
 */

package com.example.xenahort.dss_proyect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        this.signInButton = (SignInButton) findViewById(R.id.signInButton);
        this.signInButton.setSize(SignInButton.SIZE_WIDE);
        this.signInButton.setColorScheme(SignInButton.COLOR_DARK);
        this.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, 777);
            }
        });


        /*
        PostService mAPIService = ApiUtils.getAPIService();
        mAPIService.crearUsu("jcsp@gmail.com", "Pedro puto amo", "1234").enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, retrofit2.Response<Producto> response) {

            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {

            }
        });*/

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Toast.makeText(this, "Inicio correcto", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MapsActivity.class);
                startActivityForResult(intent, 0);
            } else {
                Toast.makeText(this, "No se puede iniciar sesión", Toast.LENGTH_LONG).show();
            }
        }
    }
}
