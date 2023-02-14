package com.example.npmfifadb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Jogador implements Parcelable, Serializable {
    public  int id;
    public  String nome;
    public String pontuacao;
    public String tipo;
    public  byte[] foto;

    @NonNull
    @Override
    public String toString() {
        return  String.format("Id:%d  Nome:%s Pontuacao:%s",this.id,this.nome,this.nome);
    }

    protected Jogador(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        pontuacao = in.readString();
        tipo = in.readString();
        foto = in.createByteArray();
    }

    public static final Creator<Jogador> CREATOR = new Creator<Jogador>() {
        @Override
        public Jogador createFromParcel(Parcel in) {
            return new Jogador(in);
        }

        @Override
        public Jogador[] newArray(int size) {
            return new Jogador[size];
        }
    };

    public  static Bitmap ArrayToBitmap(byte[] myfoto){
       return BitmapFactory.decodeByteArray(myfoto,0,myfoto.length);
    }

    public  static byte[] BitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    public  Jogador(){}

    public Jogador(int id, String nome, String pontuacao,String tipo, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.tipo = tipo;
        this.foto = foto;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(pontuacao);
        parcel.writeString(tipo);
        parcel.writeByteArray(foto);
    }
}
