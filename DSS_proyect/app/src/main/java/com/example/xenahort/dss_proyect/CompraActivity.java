package com.example.xenahort.dss_proyect;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompraActivity extends Activity {

    private String farmacia;
    private List<Producto> productos;


    String[] titulo = new String[]{
            "titulo1",
            "titulo2",
            "titulo3",
            "titulo4",
    };

    int[] imagenes = {
            R.drawable.ic_carrito,
            R.drawable.ic_carrito,
            R.drawable.ic_farmacia1,
            R.drawable.ic_farmacia1
    };

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
