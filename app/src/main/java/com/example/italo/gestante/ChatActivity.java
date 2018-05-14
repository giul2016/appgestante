package com.example.italo.gestante;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.italo.gestante.Adapter.MensagensAdapter;
import com.example.italo.gestante.config.ConfigFireBase;
import com.example.italo.gestante.helper.Base64Custom;
import com.example.italo.gestante.helper.UsuarioFireBase;
import com.example.italo.gestante.model.GestanteUser;
import com.example.italo.gestante.model.Mensagem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private CircleImageView circleFoto;
    private TextView textViewNome;
    private EditText edtMensagemEnvio;
    private GestanteUser usuarioDestinatario;
    private RecyclerView recyclerMEnsagem;
    private MensagensAdapter adapter;
    private List<Mensagem> mensagens = new ArrayList<>();
    private DatabaseReference database;
    private DatabaseReference msgREf;
    private ChildEventListener childEventListenerMensagens;

    //identificador usuario remetente e destinatario
    private String idUserREmetente; //usuario logado
    private String iduserDestinatario;//usuario recebe msg


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //iniciando configurações
        textViewNome = (TextView) findViewById(R.id.textViewChat);
        circleFoto = (CircleImageView) findViewById(R.id.CircleImageFoto);
        edtMensagemEnvio = (EditText) findViewById(R.id.edtMEnsagem);
        recyclerMEnsagem = (RecyclerView) findViewById(R.id.recycleMensagem);

        //Recuperar dados do usuario remetente
        idUserREmetente = UsuarioFireBase.getIdentificadorUsuario();

        //Recuperando dados do usuário destinatario
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            usuarioDestinatario =(GestanteUser) bundle.getSerializable("chatContato");
            textViewNome.setText( usuarioDestinatario.getNomeC() );

            String foto = usuarioDestinatario.getFoto();

            if ( foto != null ){
                Uri url = Uri.parse(usuarioDestinatario.getFoto());
                Glide.with(ChatActivity.this).load(url).into(circleFoto);

            }else{
                circleFoto.setImageResource(R.drawable.gestanteft);
            }

            //Recuperar dados usuario destinatario
            iduserDestinatario = Base64Custom.codificarBase64(usuarioDestinatario.getEmail());
        }

        //config Adapter
        adapter = new MensagensAdapter(mensagens, getApplicationContext());
        //Config recycle
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMEnsagem.setLayoutManager(layoutManager);
        recyclerMEnsagem.setHasFixedSize(true);
        recyclerMEnsagem.setAdapter(adapter);

        database = ConfigFireBase.getFirebase();
        msgREf = database.child("mensagens")
                .child(idUserREmetente)
                .child(iduserDestinatario);


    }
//botao de enviar via onClick
    public void fabEnviar(View view){
        String txtmensagem = edtMensagemEnvio.getText().toString();

        if (!txtmensagem.isEmpty()){

            Mensagem mensagem = new Mensagem();
            mensagem.setIduser(idUserREmetente);
            mensagem.setMensagem(txtmensagem);

            //Salvar mensagem remetente
            salvarMensagem(idUserREmetente,iduserDestinatario,mensagem);
            //Salvar mensagem destinatario
            salvarMensagem(iduserDestinatario,idUserREmetente,mensagem);

        }else{
            Toast.makeText(this, "Digite uam mensagem", Toast.LENGTH_SHORT).show();
        }
    }

    private  void salvarMensagem(String idRemetente, String idDestinatario, Mensagem msg){

        DatabaseReference database = ConfigFireBase.getFirebase();
        DatabaseReference mensagemRef = database.child("mensagens");

        mensagemRef.child(idRemetente)
                .child(idDestinatario)
                .push()
                .setValue(msg);

        //limpar text
        edtMensagemEnvio.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarMensagem();
    }

    @Override
    protected void onStop() {
        super.onStop();msgREf.removeEventListener(childEventListenerMensagens);
    }

    private void recuperarMensagem(){
       childEventListenerMensagens = msgREf.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
               mensagens.add(mensagem);
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


    }

}
