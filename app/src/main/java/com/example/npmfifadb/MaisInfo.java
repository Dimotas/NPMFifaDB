package com.example.npmfifadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MaisInfo extends AppCompatActivity {

    EditText editaltura,editclube,editnacionalidade,editpeso;
    Spinner spintipo;
    Button bt1;
    MySQL mysql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mais_info);
        Intent it =getIntent();
        int id= it.getIntExtra("id",0);
        Toast.makeText(MaisInfo.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        mysql = new MySQL(MaisInfo.this);
        editaltura = findViewById(R.id.altura);
        editpeso = findViewById(R.id.Peso);
        editclube = findViewById(R.id.clube);
        editnacionalidade = findViewById(R.id.nacionalidade);
        spintipo = findViewById(R.id.melhor_pe);
        ArrayAdapter<CharSequence> adpt = ArrayAdapter.createFromResource(MaisInfo.this,R.array.melhor_pe,R.layout.support_simple_spinner_dropdown_item);
        spintipo.setAdapter(adpt);
        bt1 =findViewById(R.id.bt_inserir);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    MaisInformacao novo = new MaisInformacao();
                    novo.id = id;
                    novo.clube=editclube.getText().toString();
                    novo.altura=editaltura.getText().toString();
                    novo.peso=editpeso.getText().toString();
                    novo.nacionalidade=editnacionalidade.getText().toString();
                    mysql.addmaisinfo(novo);
                    App.criaLista2();
                    finish();






                }catch (Exception erro){
                    Toast.makeText(MaisInfo.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}