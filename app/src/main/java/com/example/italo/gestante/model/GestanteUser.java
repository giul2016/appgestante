package com.example.italo.gestante.model;

import android.util.Log;

import com.example.italo.gestante.config.ConfigFireBase;
import com.example.italo.gestante.helper.UsuarioFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by italo on 04/01/2018.
 */

public class GestanteUser {
    private String id;
    private String nomeC;
    private String dataNAsc;
    private String sexo;
    private String idade;
    private String sangue;
    private String celular;
    private String email;
    private String senha;

    public GestanteUser(){

    }

    public void salvar(){
        DatabaseReference referenciaFireBase = ConfigFireBase.getFirebase();
        DatabaseReference user = referenciaFireBase.child("usuarios").child(getId());
        user.setValue(this);
    }


    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeC() {
        return nomeC;
    }

    public void setNomeC(String nomeC) {
        this.nomeC = nomeC;
    }

    public String getDataNAsc() {
        return dataNAsc;
    }

    public void setDataNAsc(String dataNAsc) {
        this.dataNAsc = dataNAsc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSangue() {
        return sangue;
    }

    public void setSangue(String sangue) {
        this.sangue = sangue;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
