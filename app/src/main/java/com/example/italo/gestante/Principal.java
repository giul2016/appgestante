package com.example.italo.gestante;

import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.italo.gestante.Adapter.TabAdapterMenu;
import com.example.italo.gestante.config.ConfigFireBase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseAuth autenticacao;
    //Nome das tabs
    String tabs[] = new String[] { "Menu","Agenda de vacinas","Agenda de consulta"};

    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentTabs;


    //chamando class Menu
    Menu menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        autenticacao = ConfigFireBase.getFireBaseAutenticacao();

        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Principal");
        //FIM TOOLBAR;


        menu = new Menu();


        fragmentTabs = new ArrayList<>();
        fragmentTabs.add(menu);


        tabLayout = (TabLayout) findViewById(R.id.stl_label);
        viewPager = (ViewPager)findViewById(R.id.vp_conteudo);
        viewPager.setAdapter(new TabAdapterMenu(getSupportFragmentManager(), this,
                fragmentTabs, tabs));
        tabLayout.setupWithViewPager(viewPager);



    }

    //sobre escrever menu

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarUser();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void deslogarUser(){
        try {

            autenticacao.signOut();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
