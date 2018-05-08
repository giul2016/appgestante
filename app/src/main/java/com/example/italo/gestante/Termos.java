package com.example.italo.gestante;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import static com.example.italo.gestante.R.drawable.btn_custon;

public class Termos extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);

        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Termos para acessar app");
        setSupportActionBar(toolbar);
        //FIM TOOLBAR;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CheckBox checkBox = (CheckBox)findViewById(R.id.ckBox);
        final Button proxTermo = (Button) findViewById(R.id.btnTermos);


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    //proxTermo.setEnabled(true);
                    //proxTermo.setBackgroundColor(btn_custon);
                }
            }
        });
    }
}
