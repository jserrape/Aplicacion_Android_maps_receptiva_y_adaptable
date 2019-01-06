/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:04
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/12/18 22:07
 *
 */

package com.example.xenahort.dss_proyect.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import com.example.xenahort.dss_proyect.Comunicacion.ApiUtils;
import com.example.xenahort.dss_proyect.ElementosGestion.Carrito;
import com.example.xenahort.dss_proyect.ElementosGestion.Farmacia;
import com.example.xenahort.dss_proyect.Util.InfoFarmaciaCustom;
import com.example.xenahort.dss_proyect.Comunicacion.PostService;
import com.example.xenahort.dss_proyect.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private ImageButton farmaciasButton;
    private ImageButton carritoButton;
    private String farmaciasNombres[];

    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocationPermission();

        farmaciasButton = (ImageButton) findViewById(R.id.carr);
        farmaciasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ListaFarmaciasActivity.class);
                intent.putExtra("lista", farmaciasNombres);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        });

        carrito= (Carrito) getIntent().getSerializableExtra("Carrito");
        Log.d("carrito map", this.carrito.toString());


        carritoButton = (ImageButton) findViewById(R.id.carrito);
        carritoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, listaProductosCarritoActivity.class);
                intent.putExtra("Carrito", carrito);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 16));
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Pongo un nuevo estilo al mapa
        /*try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }*/

        //Añado botones de zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Pongo mi ubicacion
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        //Pongo la camara en la escuela
        LatLng center = new LatLng(37.1970976248000444, -3.624563798608392);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Pongo la marca de la farmacias
        //cargarFarmacias();
        PostService mAPIService = ApiUtils.getAPIService();
        mAPIService.getAllPharm().enqueue(new Callback<List<Farmacia>>() {
            @Override
            public void onResponse(Call<List<Farmacia>> call, Response<List<Farmacia>> response) {
                farmaciasNombres = new String[response.body().size()];
                int i = 0;
                mMap.setInfoWindowAdapter(new InfoFarmaciaCustom(MapsActivity.this));
                for (Farmacia post : response.body()) {
                    farmaciasNombres[i] = post.getName();
                    ++i;
                    Bitmap b = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_farmacia1)).getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 130, 130, false);

                    String snippet = "Dirección: " + "Calle no se que poner 36"+ "\n" +
                            "Teléfono: " + "654 58 65 23"+"\n" +
                            "Web: " + "https://github.com/xenahort"+ "\n" +
                            "Horario: " + "9:00 a 13:00 y 15:00 a 21:00"+"\n";

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(post.getLatitude(), post.getLongitude()))
                            .title(post.getName())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .snippet(snippet)
                    );
                }
            }

            @Override
            public void onFailure(Call<List<Farmacia>> call, Throwable t) {
            }
        });
    }
}
