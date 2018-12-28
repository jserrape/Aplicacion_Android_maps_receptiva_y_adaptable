package com.example.xenahort.dss_proyect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompraActivity extends AppCompatActivity {

    private String farmacia;
    private List<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        farmacia = getIntent().getExtras().getString("name");

        cargarProducos();
    }

    public void cargarProducos() {
        Log.d("Producto", "Voy a cargar");
        productos = new ArrayList<Producto>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dss-pharmacy.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        PostService service = retrofit.create(PostService.class);
        Call<List<Producto>> call = service.getAllProduct();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                for (Producto post : response.body()) {
                    if(post.getPharmacy().equals(farmacia)){
                        productos.add(post);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
            }
        });
    }
}
