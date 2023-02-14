package com.example.npmfifadb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class MaisInformacao implements Parcelable, Serializable {
    public int id;
    public  String altura;
    public  String nacionalidade;
    public String clube;
    public String peso;

    @NonNull
    @Override
    public String toString() {
        return  String.format("Altura:%d  Nacionalidade:%s Clube:%s Peso:%s",this.altura,this.nacionalidade,this.clube,this.peso);
    }

    protected MaisInformacao(Parcel in) {
        id = in.readInt();
        altura = in.readString();
        nacionalidade = in.readString();
        clube = in.readString();
        peso = in.readString();
    }

    public static final Creator<MaisInformacao> CREATOR = new Creator<MaisInformacao>() {
        @Override
        public MaisInformacao createFromParcel(Parcel in) {
            return new MaisInformacao(in);
        }

        @Override
        public MaisInformacao[] newArray(int size) {
            return new MaisInformacao[size];
        }
    };

    public  MaisInformacao(){}

    public MaisInformacao(int id, String altura, String nacionalidade, String clube, String peso) {
        this.id = id;
        this.altura = altura;
        this.nacionalidade = nacionalidade;
        this.clube = clube;
        this.peso = peso;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(altura);
        parcel.writeString(nacionalidade);
        parcel.writeString(clube);
        parcel.writeString(peso);
    }
}
