package com.example.xenahort.dss_proyect;

import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaFarmaciasActivity extends AppCompatActivity {

    private ListView lst;
    private ArrayAdapter<String> adaptador;
    private MatrixCursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_farmacias);

        String farmaciasNombres[] = getIntent().getExtras().getStringArray("lista");
        lst = findViewById(R.id.listvw);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, farmaciasNombres);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) lst.getItemAtPosition(position);
                Intent intent = new Intent(ListaFarmaciasActivity.this, CompraActivity.class);
                intent.putExtra("name", itemValue);
                startActivityForResult(intent, 0);
            }
        });
    }
}
