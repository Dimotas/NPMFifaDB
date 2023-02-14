package com.example.npmfifadb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class JogadorAdapter extends ArrayAdapter<Jogador> {
    public Context ctx;
    public int myresource;
    ArrayList<Jogador> mycontacts;

    public class Handler {
        public TextView txtid;
        public Spinner txttipo;
        public EditText editnome;
        public EditText editpontuacao;
        public ImageView imgfoto;
        public Button btupdate, btdelete, btinfo;
    }

    public JogadorAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Jogador> contactos) {
        super(context, resource, contactos);
        ctx = context;
        mycontacts = contactos;
        myresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        Handler handler = new Handler();
        if (v == null) {
            LayoutInflater.from(ctx).inflate(myresource, parent, false);
            handler.txtid = v.findViewById(R.id.txt_id_item);
            handler.editnome = v.findViewById(R.id.edit_nome_item);
            handler.editpontuacao = v.findViewById(R.id.edit_telefone_item);
            handler.imgfoto = v.findViewById(R.id.img_foto_item);
            handler.btinfo = v.findViewById(R.id.bt_info);
            handler.btupdate = v.findViewById(R.id.bt_update_item);
            handler.btdelete = v.findViewById(R.id.bt_delete_item);
            v.setTag(handler);
        }else{
            handler = (Handler) v.getTag();
        }
        Jogador contacto = App.contactos.get(position);
        handler.txtid.setText(String.valueOf(contacto.id));
        handler.editnome.setText(contacto.nome);
        handler.editpontuacao.setText(contacto.pontuacao);
        handler.btinfo.setTag(contacto);
        handler.btinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        handler.btdelete.setTag(contacto);
        handler.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        handler.btupdate.setTag(contacto);
        handler.btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return v;
    }
}
