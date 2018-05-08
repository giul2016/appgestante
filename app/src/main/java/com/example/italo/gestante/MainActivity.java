package com.example.italo.gestante;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.italo.gestante.Adapter.TabAdapter;
import com.example.italo.gestante.config.ConfigFireBase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private DatabaseReference referenciaFirebase;
    private AppCompatButton btnButton;

    String tabs[] = new String[] { "GESTANTE","MÉDICO"};

    ViewPager viewPager;
    TabLayout tabLayout;

    List<Fragment> fragmentTabs;
    Login_gestante loginGestante;
    Login_medico loginMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginGestante = new Login_gestante();
        loginMedico = new Login_medico();


        fragmentTabs = new ArrayList<>();
        fragmentTabs.add(loginGestante);
        fragmentTabs.add(loginMedico);

        viewPager = (ViewPager) findViewById(R.id.vp_login);
        tabLayout = (TabLayout) findViewById(R.id.stl_login);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), this,
                fragmentTabs, tabs));
        tabLayout.setupWithViewPager(viewPager);


        //CHAMANDO TELA DE CADASTRO EM UM ALERTA;
        Button BtnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        BtnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                 final View mView = getLayoutInflater().inflate(R.layout.dialog_alertcad,null);

                RadioGroup rgSelect = (RadioGroup) mView.findViewById(R.id.groupSelect);
                RadioButton rbGestante = (RadioButton) mView.findViewById(R.id.rbGestante);
                RadioButton rbMedico = (RadioButton) mView.findViewById(R.id.rbMedico);
                Button btnFechar = (Button) mView.findViewById(R.id.btnFechar);

                //CHAMANDO ALERTDIALOG
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                //dialog aceintando drawable com fundo personalizado
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Fim dialog aceintando drawable com fundo personalizado
                dialog.setCancelable(false);
                btnFechar.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       //mudar para fechar so o alertDialog;
                        dialog.hide();

                    }
                });

                //coloca condições para select;
                rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.rbGestante:

                                startActivity(new Intent(MainActivity.this, CadGestante.class));
                                dialog.hide();
                                break;
                            case R.id.rbMedico:

                                startActivity(new Intent(MainActivity.this, CadMedico.class));
                                dialog.hide();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

    }
}

