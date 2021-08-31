package com.example.george.controldeembarazadas.actividades;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.george.controldeembarazadas.R;
import com.example.george.controldeembarazadas.adaptadores.Adaptador_Embarazadas;
import com.example.george.controldeembarazadas.database.DatabaseHelper;
import com.example.george.controldeembarazadas.modelo.Embarazada;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class Activity_Mostrar extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Embarazada> lista_embarazada = new ArrayList<>();
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        db = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_embarazadas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_Mostrar.this));
        annadir_embarazadas_a_la_lista_embarazadas();
        final Adaptador_Embarazadas adaptador = new Adaptador_Embarazadas(getApplicationContext(), lista_embarazada);
        recyclerView.setAdapter(adaptador);
        adaptador.setClickListener(new Adaptador_Embarazadas.RecyclerTouchListener() {
            @Override
            public void onClickItem(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), Activity_Caracteristicas.class);
                intent.putExtra("id", lista_embarazada.get(position).getId());
                intent.putExtra("byte_foto", getBytes(lista_embarazada.get(position).getFoto()));
                intent.putExtra("nombre", lista_embarazada.get(position).getNombre());
                String fecha_nac = lista_embarazada.get(position).getDia_nacimiento() + "/" + lista_embarazada.get(position).getMes_nacimiento() + "/" + lista_embarazada.get(position).getAnno_nacimiento();
                String fecha_con = lista_embarazada.get(position).getDia_concepcion() + "/" + lista_embarazada.get(position).getMes_concepcion() + "/" + lista_embarazada.get(position).getAnno_concepcion();
                intent.putExtra("fecha_nacimiento", fecha_nac);
                intent.putExtra("fecha_concepcion", fecha_con);
                intent.putExtra("calle", lista_embarazada.get(position).getCalle());
                intent.putExtra("numero", lista_embarazada.get(position).getNumero());
                intent.putExtra("telefono", lista_embarazada.get(position).getNumero_telefono());
                intent.putExtra("semanas", lista_embarazada.get(position).getCantidadSemanas());
                intent.putExtra("estado", lista_embarazada.get(position).getEstado());
                startActivity(intent);
            }
        });

    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private void annadir_embarazadas_a_la_lista_embarazadas() {
        Cursor res = db.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error!!!", "No hay embarazadas ingresadas en el sistema...");
            return;
        }
        while (res.moveToNext()) {
            String id = res.getString(0);
            Bitmap foto = getImage(res.getBlob(1));
            String nombre = res.getString(2);
            int dia_nac = Integer.parseInt(res.getString(3)), mes_nac = Integer.parseInt(res.getString(4)), anno_nac = Integer.parseInt(res.getString(5));
            String calle = res.getString(6), numero = res.getString(7), telefono = res.getString(8);
            int dia_con = Integer.parseInt(res.getString(9)), mes_con = Integer.parseInt(res.getString(10)), anno_con = Integer.parseInt(res.getString(11));
            Embarazada embarazada = new Embarazada(id, foto, nombre, dia_nac, mes_nac, anno_nac, calle, numero, telefono, dia_con, mes_con, anno_con);
            lista_embarazada.add(embarazada);
        }
    }


    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
