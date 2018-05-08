package com.example.italo.gestante;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Cad_login extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView photo, btnphoto;
    private static final int REQUEST_CAMERA = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_login);

        //CRIANDO TOOLBAR;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registro login");
        setSupportActionBar(toolbar);
        //FIM TOOLBAR;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        photo = (ImageView) findViewById(R.id.imgFoto);
        btnphoto = (ImageView) findViewById(R.id.btncamera);

        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cad_login.this, "pronto para tira foto :D", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } else {
                        String[] permissionRequest = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissionRequest, REQUEST_CAMERA);
                    }
                }
            }
        });
    }
    //camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            photo.setImageBitmap(bitmap);
        }
    }
}
