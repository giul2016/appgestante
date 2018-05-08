package com.example.italo.gestante;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.italo.gestante.R;


public class Menu extends Fragment {
        ImageView imgconsult,imgsiringa;


    public Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_menu, container, false);


        imgconsult = (ImageView)v.findViewById(R.id.imgClipBoard);
        imgsiringa = (ImageView)v.findViewById(R.id.imgsiringa);

       imgconsult.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Toast
               Context context = getActivity();
               CharSequence text = "marcarConsulta";
               int duraton = Toast.LENGTH_LONG;
               Toast toast = Toast.makeText(context,text,duraton);
               //toast.setGravity(Gravity.TOP,0,0);
               toast.show();

               //Dialog
               AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(),R.style.MyCustomDialog);
               View mView = getLayoutInflater().inflate(R.layout.activity_dialog_cad_consulta,null);
               mBuilder.setView(mView);
               final AlertDialog dialog = mBuilder.create();
               dialog.show();

           }
       });

       imgsiringa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Toast
               Context context = getActivity();
               CharSequence text = "Marca Vacina";
               int duraton = Toast.LENGTH_LONG;
               Toast toast = Toast.makeText(context,text,duraton);
               //toast.setGravity(Gravity.,0,0);
               toast.show();

               //Dialog
               AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(),R.style.MyCustomDialog);
               View mView = getLayoutInflater().inflate(R.layout.fragment_cad_vacina,null);
               mBuilder.setView(mView);
               final AlertDialog dialog = mBuilder.create();
               dialog.show();
           }
       });

        return v;
    }



}
