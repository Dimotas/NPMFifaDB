package com.example.npmfifadb;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

public class App extends Application {
    private static final String TAG = "XPTO";
    public static ArrayList<Jogador> contactos = new ArrayList<Jogador>();
    public static ArrayList<MaisInformacao> maisinfo = new ArrayList<MaisInformacao>();
    static MySQL mySQL;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "On Create");
        mySQL = new MySQL(getApplicationContext());
        criaLista();

    }

    public static void criaLista() {
        //contactos.clear();
        contactos = mySQL.DevolveLista();

    }

    public static void criaLista2(){
        maisinfo = mySQL.DevolveLista2();

    }

}
