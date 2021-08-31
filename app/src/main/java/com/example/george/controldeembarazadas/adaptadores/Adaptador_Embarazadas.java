package com.example.george.controldeembarazadas.adaptadores;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.george.controldeembarazadas.R;
import com.example.george.controldeembarazadas.modelo.Embarazada;

import java.util.ArrayList;

public class Adaptador_Embarazadas extends RecyclerView.Adapter<Adaptador_Embarazadas.MyHolder> {
    RecyclerTouchListener listener;

    public interface RecyclerTouchListener {
        void onClickItem(View v, int position);
    }

    Context context;
    LayoutInflater inflater;
    ArrayList<Embarazada> model;

    public Adaptador_Embarazadas(Context context, ArrayList<Embarazada> model) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.model = model;
    }

    public int getItemCount() {
        return model.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_layout, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        Embarazada emb = model.get(position);
        holder.foto_embarazada.setImageBitmap(emb.getFoto());
        holder.nombre.setText(emb.getNombre());
        holder.semanas.setText(emb.getCantidadSemanas());
        if (emb.getEstado().equals("VERDE")) {
            holder.layout_estado.setBackgroundColor(Color.GREEN);
            holder.estado.setText("Estado de seguimiento normal");
        } else if (emb.getEstado().equals("AMARILLO")) {
            holder.layout_estado.setBackgroundColor(Color.YELLOW);
            holder.estado.setText("Seguimiento avanzado");
        } else if (emb.getEstado().equals("AZUL")) {
            holder.layout_estado.setBackgroundColor(Color.BLUE);
            holder.estado.setText("Seguimiento riguroso");
        } else if (emb.getEstado().equals("ROJO")) {
            holder.layout_estado.setBackgroundColor(Color.RED);
            holder.estado.setText("Alto riesgo");
        }
    }

    public void setClickListener(RecyclerTouchListener value) {
        this.listener = value;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView foto_embarazada;
        TextView nombre, semanas, estado;
        LinearLayout layout_estado;

        public MyHolder(final View view) {
            super(view);
            foto_embarazada = (ImageView) view.findViewById(R.id.foto_emb_row);
            nombre = (TextView) view.findViewById(R.id.txt_row_nombre);
            semanas = (TextView) view.findViewById(R.id.txt_row_semanas);
            estado = (TextView) view.findViewById(R.id.txt_row_estado);
            layout_estado = (LinearLayout) view.findViewById(R.id.layout_color_estado);

            Animation animFadein = AnimationUtils.loadAnimation(context, R.anim.move_right);
            view.setVisibility(View.VISIBLE);
            view.startAnimation(animFadein);
            view.setTag(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onClickItem(view, getAdapterPosition());
                }
            });
        }
    }
}
