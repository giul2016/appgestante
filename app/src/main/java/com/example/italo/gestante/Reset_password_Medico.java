package com.example.italo.gestante;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class Reset_password_Medico extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password__medico);


        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Recuperar senha");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button confTrocaPSW = (Button)findViewById(R.id.btnconfTrocaSenhaMd);
        confTrocaPSW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Reset_password_Medico.this);
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


        //FIM TOOLBAR;

    }
}
