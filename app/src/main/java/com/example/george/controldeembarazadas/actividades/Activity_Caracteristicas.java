package com.example.george.controldeembarazadas.actividades;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.george.controldeembarazadas.R;
import com.example.george.controldeembarazadas.database.DataBaseObservaciones;
import com.example.george.controldeembarazadas.database.DatabaseHelper;

import java.util.ArrayList;


public class Activity_Caracteristicas extends AppCompatActivity {
    private TextView nombre, fecha_nacimiento, calle, numero, fecha_concepcion, semanas, estado_txt;
    private TextInputEditText observacion;
    private Button btn_llamar, btn_eliminar, btn_agregar, btn_observaciones;
    private ImageView foto_embarazada;
    private Bundle bundle;
    DatabaseHelper db;
    DataBaseObservaciones db_obs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas);
        db = new DatabaseHelper(this);
        db_obs = new DataBaseObservaciones(this);
        bundle = getIntent().getExtras();

        nombre = (TextView) findViewById(R.id.txt_caract_nombre);
        fecha_nacimiento = (TextView) findViewById(R.id.txt_caract_fecha_nac);
        calle = (TextView) findViewById(R.id.txt_caract_calle);
        numero = (TextView) findViewById(R.id.txt_caract_numero);
        fecha_concepcion = (TextView) findViewById(R.id.txt_caract_fecha_con);
        semanas = (TextView) findViewById(R.id.txt_semana_caract);
        estado_txt = (TextView) findViewById(R.id.txt_estado_caract);
        observacion = (TextInputEditText) findViewById(R.id.txt_observacion);
        btn_eliminar = (Button) findViewById(R.id.btn_eliminar);
        btn_llamar = (Button) findViewById(R.id.btn_llamar);
        btn_agregar = (Button) findViewById(R.id.btn_agregar_observacion);
        btn_observaciones = (Button) findViewById(R.id.btn_ver_observaciones);
        foto_embarazada = (ImageView) findViewById(R.id.foto_emb_caract);
        bundle = getIntent().getExtras();

        btn_eliminar.setPaintFlags(btn_eliminar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btn_observaciones.setPaintFlags(btn_observaciones.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btn_llamar.setPaintFlags(btn_llamar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        annadir_textos_a_la_actividad();

        btn_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizar_llamada();
            }
        });
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annadir_observacion_a_paciente();
            }
        });
        btn_observaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar_observaciones();
            }
        });

    }

    private void annadir_textos_a_la_actividad() {
        foto_embarazada.setImageBitmap(getImage(fotos_byte()));
        nombre.setText(nombre());
        btn_llamar.setText(telefono());
        calle.setText("Calle: " + calle());
        numero.setText("#" + numero());
        fecha_nacimiento.setText(fecha_nacimiento());
        fecha_concepcion.setText("        " + fecha_concepcion());
        semanas.setText(semanas() + " semanas...");
        estado_txt.setText(estado());
    }

    private void mostrar_observaciones() {
        Cursor res = db_obs.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No hay observaciones registradas a ninguna paciente");
            return;
        }
        ArrayList<String> lista_observaciones = new ArrayList<>();
        while (res.moveToNext()) {
            if (res.getString(0).equals(id())) {
                lista_observaciones.add(res.getString(1));
            }
        }
        if (lista_observaciones.size() == 0) {
            showMessage("Error!!!", nombre() + " no tiene observaciones registradas. Inserta al menos una...");
            return;
        }
        String msg = "";
        for (int i = 0; i < lista_observaciones.size(); i++) {
            msg += (i + 1) + " - " + lista_observaciones.get(i) + ".\n";
        }
        showMessage("Observaciones de " + nombre() + "...", msg);
    }

    private void annadir_observacion_a_paciente() {
        if (observacion.getText().toString().isEmpty()) {
            showMessage("Error", "La observación a agregar no puede estar vacia");
            return;
        }
        String obs = observacion.getText().toString();
        db_obs.insertData(id(), obs);
        Toast.makeText(getApplicationContext(), "Observación registrada correctamente...", Toast.LENGTH_SHORT).show();
        observacion.setText("");
    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Eliminar embarazada...");
        builder.setMessage("Seguro que desea eliminar a la paciente " + nombre() + "?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer deleteRow = db.deleteData(id());
                eliminar_observaciones_base_datos();
                if (deleteRow > 0) {
                    Toast.makeText(getApplicationContext(), "Embarazada eliminada...", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Activity_Mostrar.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo eliminar la embarazada..", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void eliminar_observaciones_base_datos() {
        Cursor res = db_obs.getAllData();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                if (res.getString(0).equals(id())) {
                    db_obs.deleteData(id());
                }
            }
        }
    }


    private void realizar_llamada() {
        try {
            Intent intentCall = new Intent(Intent.ACTION_CALL);
            intentCall.setData(Uri.parse("tel: " + telefono()));
            if (ActivityCompat.checkSelfPermission(Activity_Caracteristicas.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Activity_Caracteristicas.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                Toast.makeText(getApplicationContext(), "Acepta el permiso de las llamadas", Toast.LENGTH_LONG).show();
            } else {
                startActivity(intentCall);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al realizar la llamada a " + telefono(), Toast.LENGTH_SHORT).show();
        }
    }


    public byte[] fotos_byte() {
        return bundle.getByteArray("byte_foto");
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

    public String estado() {
        String x = bundle.getString("estado");
        if (x.equals("VERDE")) {
            return "Seguimiento normal";
        } else if (x.equals("AMARILLO")) {
            return "Seguimiento avanzado";
        } else if (x.equals("AZUL")) {
            return "Seguimiento riguroso";
        } else {
            return "Alto riesgo";
        }
    }

    public String semanas() {
        return bundle.getString("semanas");
    }


    public String telefono() {
        return bundle.getString("telefono");
    }

    public String numero() {
        return bundle.getString("numero");
    }

    public String calle() {
        return bundle.getString("calle");
    }


    public String fecha_concepcion() {
        return bundle.getString("fecha_concepcion");
    }

    public String fecha_nacimiento() {
        return bundle.getString("fecha_nacimiento");
    }

    public String nombre() {
        return bundle.getString("nombre");
    }

    public String id() {
        return bundle.getString("id");
    }


}
