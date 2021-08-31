package com.example.george.controldeembarazadas.actividades;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.george.controldeembarazadas.R;
import com.example.george.controldeembarazadas.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class Activity_Annadir extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 500;
    private TextInputEditText nombre, dia_nac, mes_nac, anno_nac, calle, numero, numero_telefono, dia_con, mes_con, anno_con;
    private Button btn_foto, btn_annadir;
    private ImageView foto_embarazada;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        db = new DatabaseHelper(this);

        nombre = (TextInputEditText) findViewById(R.id.txt_nombre);
        dia_nac = (TextInputEditText) findViewById(R.id.txt_dia_nac);
        mes_nac = (TextInputEditText) findViewById(R.id.txt_mes_mac);
        anno_nac = (TextInputEditText) findViewById(R.id.txt_anno_nac);
        calle = (TextInputEditText) findViewById(R.id.txt_calle);
        numero = (TextInputEditText) findViewById(R.id.txt_numero);
        numero_telefono = (TextInputEditText) findViewById(R.id.txt_telefono);
        dia_con = (TextInputEditText) findViewById(R.id.txt_dia_con);
        mes_con = (TextInputEditText) findViewById(R.id.txt_mes_con);
        anno_con = (TextInputEditText) findViewById(R.id.txt_anno_con);
        btn_annadir = (Button) findViewById(R.id.btn_annadir);
        btn_foto = (Button) findViewById(R.id.btn_foto);
        foto_embarazada = (ImageView) findViewById(R.id.foto_embarazada);

        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btn_annadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    annadir_embarazada_al_sistema(nombre.getText().toString(), dia_nac.getText().toString(), mes_nac.getText().toString(), anno_nac.getText().toString(), calle.getText().toString(), numero.getText().toString(), numero_telefono.getText().toString(), dia_con.getText().toString(), mes_con.getText().toString(), anno_con.getText().toString());
                } catch (Exception e) {
                    showMessage("Error!!!", e.getMessage());
                }
            }
        });

    }

    public void annadir_embarazada_al_sistema(String nomb, String dia, String mes, String anno, String call, String num, String telefono, String dias, String mess, String annos) throws Exception {
        analizar_valores(nomb, dia, mes, anno, call, num, telefono, dias, mess, annos);
        foto_embarazada.buildDrawingCache();
        Bitmap bmap = foto_embarazada.getDrawingCache();
        byte[] foto = getBytes(bmap);
        db.insertData(foto, nomb, dia, mes, anno, call, num, telefono, dias, mess, annos);
        Toast.makeText(getApplicationContext(), "Se ha insertado la embarazada correctamente", Toast.LENGTH_SHORT).show();
        reestablecer_valores();

    }


    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            foto_embarazada.setImageBitmap(photo);
        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void analizar_valores(String nomb, String dia, String mes, String anno, String call, String num, String telefono, String dias, String mess, String annos) throws Exception {
        Calendar c = Calendar.getInstance();
        String act_a = Integer.toString(c.get(Calendar.YEAR));
        if (nomb.trim().length() == 0) {
            throw new Exception("El valor del nombre no puede estar vacio...");
        }
        if (dia.isEmpty()) {
            throw new Exception("El día de nacimiento no puede estar vacío");
        }
        if (mes.isEmpty()) {
            throw new Exception("El mes de nacimiento no puede estar vacío");
        }
        if (anno.isEmpty()) {
            throw new Exception("El año de nacimiento no puede estar vacío");
        }
        if (Integer.parseInt(dia) > 31 || Integer.parseInt(dia) < 1) {
            throw new Exception("Introduce el dia de nacimiento en el rango 1 - 31");
        }
        if (Integer.parseInt(mes) > 12 || Integer.parseInt(mes) < 1) {
            throw new Exception("Introduce el mes de nacimiento en el rango 1 - 12");
        }
        if (Integer.parseInt(anno) > Integer.parseInt(act_a) || Integer.parseInt(anno) < 1920) {
            throw new Exception("Introduce el año de nacimiento en el rango 1920 - " + act_a);
        }
        if (call.trim().length() == 0) {
            throw new Exception("El valor de la calle no puede estar vacio...");
        }
        if (num.trim().length() == 0) {
            throw new Exception("El valor del número de la dirección no puede estar vacio...");
        }
        if (telefono.trim().length() == 0) {
            throw new Exception("El valor del número de teléfono no puede estar vacio...");
        }
        if (dias.isEmpty()) {
            throw new Exception("El día de concepción no puede estar vacío");
        }
        if (mess.isEmpty()) {
            throw new Exception("El mes de concepción no puede estar vacío");
        }
        if (annos.isEmpty()) {
            throw new Exception("El año de concepción no puede estar vacío");
        }
        if (Integer.parseInt(dias) > 31 || Integer.parseInt(dias) < 1) {
            throw new Exception("Introduce el dia de concepción en el rango 1 - 31");
        }
        if (Integer.parseInt(mess) > 12 || Integer.parseInt(mess) < 1) {
            throw new Exception("Introduce el mes de concepción en el rango 1 - 12");
        }
        if (Integer.parseInt(annos) > Integer.parseInt(act_a) || Integer.parseInt(annos) < 1920) {
            throw new Exception("Introduce el año de concepción en el rango 1920 - " + act_a);
        }

        if (Integer.parseInt(annos) > Integer.parseInt(act_a) || Integer.parseInt(annos) < Integer.parseInt(act_a) - 1) {
            throw new Exception("El año de la ultima mentruación debe estar entre " + Integer.toString(Integer.parseInt(act_a) - 1) + "-" + act_a + ".");
        }

    }

    public void reestablecer_valores() {
        foto_embarazada.setImageResource(R.drawable.fondo3);
        nombre.setText("");
        dia_nac.setText("");
        mes_nac.setText("");
        anno_nac.setText("");
        calle.setText("");
        numero.setText("");
        numero_telefono.setText("");
        dia_con.setText("");
        mes_con.setText("");
        anno_con.setText("");
    }

}
