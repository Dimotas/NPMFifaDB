package com.example.npmfifadb;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    ArrayList<Jogador> contactos;
    ArrayList<MaisInformacao> maisinfo;
    Context ctx;
    MySQL mySQL;


    public RecycleAdapter(Context c, ArrayList<Jogador> contactos, ArrayList<MaisInformacao> maisinfo) {
        this.contactos = contactos;
        this.maisinfo = maisinfo;
        this.ctx = c;
        mySQL = new MySQL(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle2, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Jogador contacto = contactos.get(position);
            holder.txtid.setText(String.valueOf(contacto.id));
            holder.editnome.setText(contacto.nome);
            holder.editpontuacao.setText(contacto.pontuacao);
            holder.txttipo.setText(contacto.tipo);
            holder.imgfoto.setImageBitmap(Jogador.ArrayToBitmap(contacto.foto));
            holder.imgfoto.setOnClickListener(this::onClick);
            holder.imgfoto.setTag(position);
            holder.btdelete.setTag(contacto);
            holder.btdelete.setOnClickListener(this);
            holder.btinfo.setTag(contacto);
            holder.btinfo.setOnClickListener(this);
            holder.btupdate.setTag(holder);
            holder.btupdate.setOnClickListener(this);
            ArrayAdapter<CharSequence> adpt = ArrayAdapter.createFromResource(ctx,R.array.tipos,R.layout.support_simple_spinner_dropdown_item);
            adpt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            holder.infospinner.setAdapter(adpt);

            try {
                MaisInformacao oi;
                oi = maisinfo.get(position);
                holder.txtclube.setText(oi.clube);
                holder.txtnacionalidade.setText(oi.nacionalidade);
                holder.txtpeso.setText(oi.peso);
                holder.txtaltura.setText(oi.altura);

            }catch (Exception e){
                holder.txtclube.setText("");
                holder.txtnacionalidade.setText("");
                holder.txtpeso.setText("");
                holder.txtaltura.setText("");

            }








    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    IOnSacaFoto listener;

    public void setOnSacaFotoListener(IOnSacaFoto lst) {
        this.listener = lst;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_call_item_rec:
                Jogador contacto = (Jogador) view.getTag();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +  contacto.pontuacao));
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(((MainActivity)ctx), new String[]{Manifest.permission.CALL_PHONE} ,MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    //You already have permission

                    try {
                        ctx.startActivity(intent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }


                break;


            case R.id.img_foto_item_rec:
                int posicao = (Integer) view.getTag();
                listener.OnSacaFotoHandler(posicao);
                break;
            case R.id.bt_update_item_rec:
                Jogador atual = null;
                Toast.makeText(ctx, "Atualizado", Toast.LENGTH_SHORT).show();
                ViewHolder holder = (ViewHolder) view.getTag();
                int id = Integer.parseInt(holder.txtid.getText().toString());
                int pos = -1;

                for (Jogador c : App.contactos) {
                    if (c.id == id) {
                        atual = c;
                        atual.id = Integer.parseInt(holder.txtid.getText().toString());
                        atual.nome = holder.editnome.getText().toString();
                        atual.pontuacao = holder.editpontuacao.getText().toString();
                        atual.tipo = holder.infospinner.getSelectedItem().toString();
                        Drawable dw = holder.imgfoto.getDrawable();
                        Bitmap bmp = ((BitmapDrawable) dw).getBitmap();
                        atual.foto = Jogador.BitmapToArray(bmp);
                        mySQL.updateContacto(atual);
                        this.notifyDataSetChanged();
                        break;
                    }
                }

                break;
            case R.id.bt_delete_item_rec:
                try {
                    Toast.makeText(ctx, "Eliminado", Toast.LENGTH_SHORT).show();
                    contacto = (Jogador) view.getTag();
                    mySQL.deleteMaisInfo(contacto.id);

                    mySQL.deleteContacto(contacto.id);
                    App.criaLista();
                    ((MainActivity) ctx).finish();
                    ((MainActivity) ctx).overridePendingTransition(0, 0);
                    ((MainActivity) ctx).startActivity(((MainActivity) ctx).getIntent());
                    ((MainActivity) ctx).overridePendingTransition(0, 0);

                } catch (Exception erro) {
                    Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_info:

                Intent it = new Intent(ctx,MaisInfo.class);
                Jogador j=(Jogador) view.getTag();
                it.putExtra("id",j.id);
                ((MainActivity)ctx).startActivity(it);
                break;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtid,txtclube,txtnacionalidade,txtaltura,txtpeso,txttipo;
        EditText editnome, editpontuacao;
        Button btinfo, btupdate, btdelete;
        ImageView imgfoto;
        Spinner infospinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.txt_id_item_rec);
            editnome = itemView.findViewById(R.id.edit_nome_item_rec);
            editpontuacao = itemView.findViewById(R.id.edit_pontuacao_item_rec);
            imgfoto = itemView.findViewById(R.id.img_foto_item_rec);
            btupdate = itemView.findViewById(R.id.bt_update_item_rec);
            btinfo = itemView.findViewById(R.id.bt_info);
            btdelete = itemView.findViewById(R.id.bt_delete_item_rec);
            txtclube = itemView.findViewById(R.id.rec_clube);
            txtnacionalidade = itemView.findViewById(R.id.rec_nacionalidade);
            txtaltura = itemView.findViewById(R.id.rec_altura);
            txtpeso = itemView.findViewById(R.id.rec_peso);
            infospinner = itemView.findViewById(R.id.spinner3);
            txttipo = itemView.findViewById(R.id.tipo);

        }

    }
}
