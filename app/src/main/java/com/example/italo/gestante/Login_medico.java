package com.example.italo.gestante;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_medico extends Fragment {
    private EditText login;

    public Login_medico() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login_medico, container, false);

        TextView reset = (TextView) v.findViewById(R.id.recSenha);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Reset_password_Medico.class));
            }
        });


        login = (EditText) v.findViewById(R.id.crm);

        SimpleMaskFormatter fmCRM = new SimpleMaskFormatter("NNNNNN/LL");
        MaskTextWatcher maskCRM = new MaskTextWatcher(login, fmCRM);
        login.addTextChangedListener(maskCRM);

        return  v;
    }

}
