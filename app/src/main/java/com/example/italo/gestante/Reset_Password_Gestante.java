package com.example.italo.gestante;

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

public class Reset_Password_Gestante extends AppCompatActivity {

    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_gestante);

        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Recuperar senha");
        setSupportActionBar(toolbar);
        //FIM TOOLBAR;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button conftrocaPSW = (Button)findViewById(R.id.btnconfTrocaSenhaGt);
        conftrocaPSW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Reset_Password_Gestante.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_psw,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                //dialog aceintando drawable com fundo personalizado
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Fim dialog aceintando drawable com fundo personalizado
                dialog.setCancelable(false);
                dialog.show();

                //tempo de fechamendo do dialog
                final Timer temp = new Timer();
                temp.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        temp.cancel();
                    }
                },3000);
            }
        });

    }
}
