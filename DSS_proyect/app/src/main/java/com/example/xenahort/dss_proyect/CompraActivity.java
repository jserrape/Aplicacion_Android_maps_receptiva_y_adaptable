package com.example.xenahort.dss_proyect;

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
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompraActivity extends Activity implements OnClickListener {

    ListView mListView;
    Button btnShowCheckedItems;
    ArrayList<Producto> mProducts;
    MultiSelectionAdapter<Producto> mAdapter;

    private String farmacia;
    private ArrayList<Producto> productos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        cargarProducos();
    }

    public void cargarProducos() {
        mListView = (ListView) findViewById(android.R.id.list);
        btnShowCheckedItems = (Button) findViewById(R.id.btnShowCheckedItems);
        farmacia = getIntent().getExtras().getString("name");
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
                mAdapter = new MultiSelectionAdapter<Producto>(CompraActivity.this, productos);
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
        if(mAdapter != null) {
            ArrayList<Producto> mArrayProducts = mAdapter.getCheckedItems();

            if(mArrayProducts.toString().equals("[]")){
                Toast.makeText(getApplicationContext(), "Por favor, seleccione algún artículo", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(CompraActivity.this, CompraFinalizadaActivity.class);
                intent.putExtra("Productos", mArrayProducts.toString());
                startActivityForResult(intent, 0);
            }
        }
    }

}
