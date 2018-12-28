package com.example.xenahort.dss_proyect;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
    ListViewAdapter adapter;

    String[] titulo = new String[]{"Ibuprofen"};

    int[] imagenes = {R.drawable.ic_carrito};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        farmacia = getIntent().getExtras().getString("name");
        cargarProducos();
    }

    public void crearLista() {
        final ListView lista = (ListView) findViewById(R.id.listView1);
        adapter = new ListViewAdapter(this, titulo, imagenes);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "presiono " + i, Toast.LENGTH_SHORT).show();
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "presiono LARGO " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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
                    if (post.getPharmacy().equals(farmacia)) {
                        productos.add(post);
                    }
                }
                titulo = new String[productos.size()];
                imagenes= new int[productos.size()];
                for(int i=0;i<productos.size();i++){
                    titulo[i]=productos.get(i).getName();
                    imagenes[i] = R.drawable.ic_carrito;
                }
                crearLista();
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
            }
        });
    }
}
