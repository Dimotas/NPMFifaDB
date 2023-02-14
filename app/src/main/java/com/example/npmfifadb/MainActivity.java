package com.example.npmfifadb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int CANALFOTO = 1;
    private static final int CANALINSERT =2 ;
    public int pos;
    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    FloatingActionButton fab;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CANALFOTO) {
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                App.contactos.get(pos).foto = Jogador.BitmapToArray(bmp);
                recycleAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(requestCode==CANALINSERT){
            finish();
            overridePendingTransition( 0, 0);
            startActivity(getIntent());
            overridePendingTransition( 0, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_main);
        recycleAdapter = new RecycleAdapter(MainActivity.this, App.contactos, App.maisinfo);
        recycleAdapter.setOnSacaFotoListener(new IOnSacaFoto() {
            @Override
            public void OnSacaFotoHandler(int posicao) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it, CANALFOTO);
                pos = posicao;
            }
        });
      recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
      fab= findViewById(R.id.fab_insert_main);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Intent it= new Intent(MainActivity.this,Inserir.class);
              startActivityForResult(it,CANALINSERT);
           }
       });
    }
}