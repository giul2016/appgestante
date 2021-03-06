package com.example.italo.gestante;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.italo.gestante.R;
import com.example.italo.gestante.config.ConfigFireBase;
import com.example.italo.gestante.model.GestanteUser;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.*;

public class Dados_Pessoais_Gestante extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText datnasc, numCel, nomeCompleto, sexo,idade,email,senha;
    private Spinner tipoSangue;
    private Button cadgestante;


    private FirebaseAuth autentic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados__pessoais__gestante);

        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dados gestante");
        setSupportActionBar(toolbar);
        //FIM TOOLBAR;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        datnasc = (EditText) findViewById(R.id.data_nasci);
        numCel = (EditText) findViewById(R.id.numCelular);
        nomeCompleto = (EditText) findViewById(R.id.nomeCompleto);
        sexo = (EditText) findViewById(R.id.tipo_sexo);
        idade = (EditText) findViewById(R.id.idade);
        email = (EditText) findViewById(R.id.cademail);
        senha = (EditText) findViewById(R.id.cadsenha);
        //spinner
        tipoSangue = (Spinner) findViewById(R.id.sangue);



        sexo.setEnabled(false);
        sexo.setFocusable(false);
        sexo.setFocusableInTouchMode(true);


        cadgestante = (Button) findViewById(R.id.cadGestante);

        SimpleMaskFormatter fmDataNasc = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskData = new MaskTextWatcher(datnasc, fmDataNasc);
        datnasc.addTextChangedListener(maskData);

        SimpleMaskFormatter fmNumCel = new SimpleMaskFormatter("(NN) N NNNN-NNNN");
        MaskTextWatcher maskCel = new MaskTextWatcher(numCel, fmNumCel);
        numCel.addTextChangedListener(maskCel);



        cadgestante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = nomeCompleto.getText().toString();
                String textoDatnasc = datnasc.getText().toString();
                String textoSexo = sexo.getText().toString();
                String textoNumCel = numCel.getText().toString();
                String textoIdade = idade.getText().toString();
                String textoTipoSangue = tipoSangue.getSelectedItem().toString();
                String textoEmail = email.getText().toString();
                String textoSenha = senha.getText().toString();

                if (!textoNome.isEmpty()){
                    if (!textoDatnasc.isEmpty()){
                        if (!textoSexo.isEmpty()){
                            if (!textoIdade.isEmpty()){
                                if (!textoTipoSangue.isEmpty()){
                                    if (!textoNumCel.isEmpty()){
                                        if (!textoEmail.isEmpty()){
                                            if (!textoSenha.isEmpty()){
                                                //instancia de GestanteUser;
                                                GestanteUser gestanteCad =  new GestanteUser();
                                                gestanteCad.setNomeC(textoNome);
                                                gestanteCad.setDataNAsc(textoDatnasc);
                                                gestanteCad.setSexo(textoSexo);
                                                gestanteCad.setCelular(textoNumCel);
                                                gestanteCad.setIdade(textoIdade);
                                                gestanteCad.setSangue(textoTipoSangue);
                                                gestanteCad.setEmail(textoEmail);
                                                gestanteCad.setSenha(textoSenha);

                                                //chamando metodo cadastro gestante;
                                                cadastroGestante(gestanteCad);

                                            }else {
                                                Toast.makeText(Dados_Pessoais_Gestante.this, "Preencha o campo Senha", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(Dados_Pessoais_Gestante.this, "Preencha o campo Email", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(Dados_Pessoais_Gestante.this, "Preenchao o campo Numero do celular", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Dados_Pessoais_Gestante.this, "Preencha o campo Tipo sanquinio", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Dados_Pessoais_Gestante.this, "Preencha o campo Idade", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Dados_Pessoais_Gestante.this, "Preencha o campo Sexo", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Dados_Pessoais_Gestante.this, "Preencha  o campo Data nascimento", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Dados_Pessoais_Gestante.this, "Preencha o  campo Nome", Toast.LENGTH_SHORT).show();
                }

                /*
                userGestante = new GestanteUser();
                userGestante.setNomeC(nomeCompleto.getText().toString());
                userGestante.setDataNAsc(datnasc.getText().toString());
                userGestante.setSexo(sexo.getText().toString());
                userGestante.setCelular(numCel.getText().toString());
                userGestante.setIdade(idade.getText().toString());
                userGestante.setSangue(tipoSangue.getSelectedItem().toString());
                userGestante.setEmail(email.getText().toString());
                userGestante.setSenha(senha.getText().toString());
                cadastroGestante();
                */
            }
        });
    }



    //Firebase
    private void cadastroGestante(GestanteUser gestanteCad) {

        autentic = ConfigFireBase.getFireBaseAutenticacao();
        autentic.createUserWithEmailAndPassword(
                    gestanteCad.getEmail(), gestanteCad.getSenha()
        ).addOnCompleteListener(
                Dados_Pessoais_Gestante.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //uploadIamge();
                            Toast.makeText(Dados_Pessoais_Gestante.this, "Sucesso ao cadastra dados", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(Dados_Pessoais_Gestante.this, MainActivity.class);
                            startActivity(intent);
                            //FirebaseUser userFire = task.getResult().getUser();
                            //userGestante.setId(userFire.getUid());
                            //userGestante.salvar();
                            //autentic.signOut();
                        } else {
                            String erroExcecao =" ";
                            try{
                                throw  task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Digite um email válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "E-mail já se encontra cadastrado, ´em uso no app";
                            }catch (Exception e){
                                erroExcecao = "Erro ao cadastra dados";
                                e.printStackTrace();
                            }
                            Toast.makeText(Dados_Pessoais_Gestante.this, "Erro: "+ erroExcecao, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }




}
