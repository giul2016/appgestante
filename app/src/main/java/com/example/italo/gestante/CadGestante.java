package com.example.italo.gestante;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class CadGestante extends AppCompatActivity {

    private Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.italo.gestante.R.layout.activity_cad_gestante);
        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cadastro de gestante");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //FIM TOOLBAR;


        Button dataPerson = (Button) findViewById(R.id.btnDatagestante);
        Button termos = (Button) findViewById(R.id.btnTermos);
        Button concluir = (Button) findViewById(R.id.btnfinalizar);

        //dataPerson.setEnabled(false);
        //concluir.setEnabled(false);

        termos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadGestante.this, Termos.class));
            }
        });
        dataPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadGestante.this, Dados_Pessoais_Gestante.class));
            }
        });


    }


}
