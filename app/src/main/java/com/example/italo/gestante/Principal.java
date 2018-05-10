package com.example.italo.gestante;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.italo.gestante.Adapter.TabAdapterMenu;
import com.example.italo.gestante.config.ConfigFireBase;
import com.example.italo.gestante.helper.UsuarioFireBase;
import com.example.italo.gestante.model.GestanteUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Principal extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseAuth autenticacao;
    private TextView nome,sangue,idade;
    private CircleImageView img;
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

        nome = (TextView)findViewById(R.id.txtNome_fire);
        sangue = (TextView)findViewById(R.id.txtSangue_fire);
        idade = (TextView)findViewById(R.id.txtidade_fire);
        img = (CircleImageView)findViewById(R.id.imgPerfil);

        autenticacao = ConfigFireBase.getFireBaseAutenticacao();

        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Principal");
        setSupportActionBar(toolbar);

        //FIM TOOLBAR;

        FirebaseUser prinGestante = UsuarioFireBase.getUserAtual();
        Uri url = prinGestante.getPhotoUrl();

        if (url !=null){
            Glide.with(Principal.this).load(url).into(img);
        }else{
            img.setImageResource(R.drawable.pregnancy);
        }


        menu = new Menu();


        fragmentTabs = new ArrayList<>();
        fragmentTabs.add(menu);


        tabLayout = (TabLayout) findViewById(R.id.stl_label);
        viewPager = (ViewPager)findViewById(R.id.vp_conteudo);
        viewPager.setAdapter(new TabAdapterMenu(getSupportFragmentManager(), this,
                fragmentTabs, tabs));
        tabLayout.setupWithViewPager(viewPager);

        //recuperando dados
        DatabaseReference userGestante = ConfigFireBase.getFirebase();
        DatabaseReference principalGestante = userGestante.child("usuarios").child(UsuarioFireBase.getIdentificadorUsuario());
        principalGestante.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GestanteUser gtUser = dataSnapshot.getValue(GestanteUser.class);
                nome.setText(gtUser.getNomeC());
                idade.setText(gtUser.getIdade());
                sangue.setText(gtUser.getSangue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            case R.id.menuConfiguracoes:
                    abrirConfig();
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
    public void abrirConfig(){
        Intent intent = new Intent(Principal.this, activity_configuracoes.class);
        startActivity(intent);
    }
}
