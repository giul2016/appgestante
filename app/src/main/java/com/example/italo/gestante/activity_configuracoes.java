package com.example.italo.gestante;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.italo.gestante.config.ConfigFireBase;
import com.example.italo.gestante.helper.Base64Custom;
import com.example.italo.gestante.helper.Permissao;
import com.example.italo.gestante.helper.UsuarioFireBase;
import com.example.italo.gestante.model.GestanteUser;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_configuracoes extends AppCompatActivity {
    private Toolbar toolbar;

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private ImageButton imgbtCamera, imgbtGaleria;
    private CircleImageView imgPerfil;
    private EditText edtNome, edtNum, edtNasc, edtIdade;
    private Spinner spnSangue; //ver com recupera dados do spinner
    private Button btnSalvar;
    private StorageReference storageReference;
    private String identUser;

    private static final int selecao_camera = 100;
    private static final int selecao_galeria = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        imgbtCamera = (ImageButton) findViewById(R.id.imgButtonCamera);
        imgbtGaleria = (ImageButton) findViewById(R.id.imgButtonGaleria);
        imgPerfil = (CircleImageView) findViewById(R.id.circleImagePerfil);
        edtNome = (EditText) findViewById(R.id.confNome);
        edtNum = (EditText) findViewById(R.id.numCelular);
        edtNasc = (EditText) findViewById(R.id.data_nasci);
        edtIdade = (EditText) findViewById(R.id.idade);
        spnSangue = (Spinner) findViewById(R.id.sangue);

        btnSalvar = (Button) findViewById(R.id.btn_alterGestante);

        //mascaras
        SimpleMaskFormatter fmDataNasc = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskData = new MaskTextWatcher(edtNasc, fmDataNasc);
        edtNasc.addTextChangedListener(maskData);

        SimpleMaskFormatter fmNumCel = new SimpleMaskFormatter("(NN) N NNNN-NNNN");
        MaskTextWatcher maskCel = new MaskTextWatcher(edtNum, fmNumCel);
        edtNum.addTextChangedListener(maskCel);

        //referencia storege
        storageReference = ConfigFireBase.getFirebaseStorage();
        identUser = UsuarioFireBase.getIdentificadorUsuario();

        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //FIM TOOLBAR;

        //recuperar dados usuario
        FirebaseUser usuario = UsuarioFireBase.getUserAtual();
        Uri url = usuario.getPhotoUrl();

        if (url != null) {
            Glide.with(activity_configuracoes.this).load(url).into(imgPerfil);
        } else {
            imgPerfil.setImageResource(R.drawable.gestanteft);
        }

        edtNome.setText(usuario.getDisplayName());

        pegandoDadosFireBase();


        imgbtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, selecao_camera);
                }

            }
        });
        imgbtGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, selecao_galeria);
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updadteDadosFireBase();
            }
        });

        //validar permisoes
        Permissao.validarPermission(permissoesNecessarias, this, 1);
    }

    public void pegandoDadosFireBase() {
        DatabaseReference userGestante = ConfigFireBase.getFirebase();
        DatabaseReference gestante = userGestante.child("usuarios").child(UsuarioFireBase.getIdentificadorUsuario());

        gestante.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GestanteUser gtUser = dataSnapshot.getValue(GestanteUser.class);
                //Log.i("Firebase",dataSnapshot.getValue().toString());
                edtNum.setText(gtUser.getCelular());
                edtIdade.setText(gtUser.getIdade());
                edtNasc.setText(gtUser.getDataNAsc());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updadteDadosFireBase() {


        /*
        DatabaseReference userGestante = ConfigFireBase.getFirebase();

        DatabaseReference gestante = userGestante.child("usuarios").child(UsuarioFireBase.getIdentificadorUsuario());

        */


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;
            try {
                switch (requestCode) {
                    case selecao_camera:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case selecao_galeria:
                        Uri localImgSelect = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImgSelect);
                        break;
                }
                if (imagem != null) {
                    imgPerfil.setImageBitmap(imagem);

                    //recuperar img FireBase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();
                    //salvar img firebase
                    StorageReference imgRef = storageReference
                            .child("imagens")
                            .child("perfil")
                            //.child(identUser)
                            .child(identUser + ".jpeg");

                    UploadTask uploadTask = imgRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity_configuracoes.this, "Erro ao salvar imagem", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(activity_configuracoes.this, "Imagem salva com sucesso", Toast.LENGTH_SHORT).show();

                            Uri url = taskSnapshot.getDownloadUrl();
                            atualizaFotouser(url);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

        }

    }

    public void atualizaFotouser(Uri url) {

        UsuarioFireBase.atualizarFotoUser(url);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResult : grantResults) {
            if (permissaoResult == PackageManager.PERMISSION_DENIED) {
                alertvalidadePermission();
            }
        }
    }

    private void alertvalidadePermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyCustomDialog);
        builder.setTitle("Permissões Negadas");
        builder.setCancelable(false);
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
