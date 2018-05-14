package com.example.italo.gestante.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.italo.gestante.R;
import com.example.italo.gestante.model.GestanteUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder>{

    private List<GestanteUser>contatos;
    private Context context;

    public ContatosAdapter(List<GestanteUser> listaContatos, Context c) {
        this.contatos = listaContatos;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contatos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GestanteUser gestanteUser = contatos.get(position);

        holder.nome.setText((CharSequence) gestanteUser.getNomeC());
        holder.email.setText((CharSequence) gestanteUser.getEmail());

        if ( gestanteUser.getFoto() != null){
            Uri uri = Uri.parse(gestanteUser.getFoto());
            Glide.with(context).load(uri).into(holder.foto);
        }else {
            holder.foto.setImageResource(R.drawable.doctorman);
        }

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public  class  MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView foto;
        TextView nome, email;

        public  MyViewHolder(View itemView){
            super(itemView);
            foto =(CircleImageView) itemView.findViewById(R.id.imgContato);
            nome = (TextView) itemView.findViewById(R.id.textViewNome);
            email = (TextView) itemView.findViewById(R.id.textViewEmail);

        }
    }
}
