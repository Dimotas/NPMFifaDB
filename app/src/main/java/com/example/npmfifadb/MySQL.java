package com.example.npmfifadb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MySQL extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "NPMFifa.db";

    final static int VERSAO = 1;
    public static final String ID = "id";
    public static final String ID_MAISINFO = "id_maisinfo";
    public static final String NOME = "nome";
    public static final String PONTUACAO = "pontuacao";
    public static final String FOTO = "foto";
    public static final String TAB_CONTACTOS = "contactos";
    public static final String TAB_MAISINFO = "mais_info";
    public static final String TIPO = "tipo";
    public static final String ALTURA = "altura";
    public static final String PESO = "peso";
    public static final String CLUBE = "clube";
    public static final String NACIONALIDADE = "nacionalidade";



    public Context ctx;

    public MySQL(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSAO);
        ctx = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tabcontactos = "CREATE TABLE " + TAB_CONTACTOS + " (" +
                "    " + ID + "       INTEGER        PRIMARY KEY AUTOINCREMENT," +
                "    " + NOME + "     NVARCHAR (120) NOT NULL," +
                "    " + PONTUACAO + " NVARCHAR (20)," +
                "    " + TIPO + " NVARCHAR (20)," +
                "    " + FOTO + "     BLOB" +
                ");";
        String tabmaisinfo = "CREATE TABLE " + TAB_MAISINFO + " (" +
                "    " + ID_MAISINFO + "       INTEGER        PRIMARY KEY AUTOINCREMENT," +
                "    " + ALTURA + "     NVARCHAR (120) NOT NULL," +
                "    " + PESO + " NVARCHAR (120)," +
                "    " + NACIONALIDADE + "     NVARCHAR(120)," +
                "    " + CLUBE + " NVARCHAR(120)" +
                ");";

        db.execSQL(tabcontactos);
        db.execSQL(tabmaisinfo);


    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TAB_CONTACTOS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TAB_MAISINFO  + ";");

    }

    public ArrayList<Jogador> DevolveLista() {
        ArrayList<Jogador> contactos = new ArrayList<Jogador>();
        Jogador novo;
        Cursor cur = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT  * FROM " + TAB_CONTACTOS + ";";
            cur = db.rawQuery(sql, null);
            if (cur.moveToFirst()) {
                do {
                    novo = new Jogador(cur.getInt(0), cur.getString(1), cur.getString(2),cur.getString(3),cur.getBlob(4));
                    contactos.add(novo);
                } while (cur.moveToNext());

            }

        } catch (SQLException erro) {
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cur != null && !cur.isClosed()) cur.close();
            return contactos;
        }
    }
    public ArrayList<MaisInformacao> DevolveLista2() {
        ArrayList<MaisInformacao> maisinfo = new ArrayList<MaisInformacao>();
        MaisInformacao novo;
        Cursor cur = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT  * FROM " + TAB_MAISINFO + ";";
            cur = db.rawQuery(sql, null);
            if (cur.moveToFirst()) {
                do {
                    novo = new MaisInformacao(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3),cur.getString(4));
                    maisinfo.add(novo);
                } while (cur.moveToNext());

            }

        } catch (SQLException erro) {
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cur != null && !cur.isClosed()) cur.close();
            return maisinfo;
        }
    }

    public boolean addContacto(Jogador ct) {
        long total = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ID, ct.id);
            cv.put(NOME, ct.nome);
            cv.put(PONTUACAO, ct.pontuacao);
            cv.put(TIPO, ct.tipo);
            cv.put(FOTO, ct.foto);
            db.beginTransaction();
            total = db.insert(TAB_CONTACTOS, null, cv);
            db.setTransactionSuccessful();
        } catch (SQLException erro) {

            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            db.endTransaction();
        }
        return total != 0;
    }

    public boolean addmaisinfo(MaisInformacao mi){
        long total = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ID_MAISINFO,mi.id);
            cv.put(ALTURA,mi.altura);
            cv.put(PESO,mi.peso);
            cv.put(NACIONALIDADE,mi.nacionalidade);
            cv.put(CLUBE,mi.clube);
            db.beginTransaction();
            total = db.insert(TAB_MAISINFO,null,cv);
            db.setTransactionSuccessful();
        }catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            db.endTransaction();
        }
        return total !=0;
    }

    public Boolean updateContacto(Jogador ct) {
        long total = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(NOME, ct.nome);
            cv.put(PONTUACAO, ct.pontuacao);
            cv.put(TIPO,ct.tipo);
            cv.put(FOTO, ct.foto);
            db.beginTransaction();
            total = db.update(TAB_CONTACTOS, cv, ID + "=?", new String[]{String.valueOf(ct.id)});
            db.setTransactionSuccessful();
        } catch (SQLException erro) {

            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        } finally {


            db.endTransaction();
        }

        return total != 0;
    }

    public int novoid() {
        int total = 0;
        Cursor cur = null;
        SQLiteDatabase db = getReadableDatabase();
        try {
            String sql = "SELECT  max(id) FROM " + TAB_CONTACTOS + ";";
            cur = db.rawQuery(sql, null);
            cur.moveToFirst();
            total = cur.getInt(0);


        } catch (SQLException erro) {
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cur != null && !cur.isClosed()) cur.close();
            return total + 1;
        }
    }


    public Boolean deleteContacto(int id) {
        long i=0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            i = db.delete(TAB_CONTACTOS, ID + "=?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception erro) {

            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction();
        }
        return i!=0;
    }

    public Boolean deleteMaisInfo(int id) {
        long i=0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            i = db.delete(TAB_MAISINFO, ID_MAISINFO + "=?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception erro) {

            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction();
        }
        return i!=0;
    }

}


