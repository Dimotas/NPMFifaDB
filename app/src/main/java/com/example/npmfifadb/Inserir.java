package com.example.npmfifadb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Inserir extends AppCompatActivity {

    private static final int SACAFOTO = 3;
    EditText editnome, editpontuacao;
    Spinner spintipo;
    ImageView imgfoto;
    Button btinserir;
    MySQL mySQL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);
        mySQL=new MySQL(Inserir.this);
        editnome = findViewById(R.id.edit_nome_inserir);
        editpontuacao = findViewById(R.id.edit_pontuacao_inserir);
        spintipo = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adpt = ArrayAdapter.createFromResource(Inserir.this,R.array.tipos,R.layout.support_simple_spinner_dropdown_item);
        spintipo.setAdapter(adpt);
        imgfoto = findViewById(R.id.img_foto_inserir);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it, SACAFOTO);
            }
        });
        btinserir = findViewById(R.id.bt_inert_inserir);
        btinserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                   Jogador novo = new Jogador();
                   novo.id=mySQL.novoid();
                   novo.nome=editnome.getText().toString();
                   novo.pontuacao=editpontuacao.getText().toString();
                   novo.tipo=spintipo.getSelectedItem().toString();
                   Drawable dw=imgfoto.getDrawable();
                   Bitmap bmp = ((BitmapDrawable)dw).getBitmap();
                   novo.foto=Jogador.BitmapToArray(bmp);
                   mySQL.addContacto(novo);
                   App.criaLista();
                   finish();
               }catch (Exception erro){
                   Toast.makeText(Inserir.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SACAFOTO) {
            Uri uri=Uri.parse(data.getData().toString());
            imgfoto.setImageURI(uri);

        }
    }
}