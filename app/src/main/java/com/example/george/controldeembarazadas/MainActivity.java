package com.example.george.controldeembarazadas;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.george.controldeembarazadas.actividades.Activity_Annadir;
import com.example.george.controldeembarazadas.actividades.Activity_Mostrar;
import com.example.george.controldeembarazadas.database.DatabaseHelper;
import com.example.george.controldeembarazadas.modelo.Embarazada;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<Embarazada> lista_embarazada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(this);
        annadir_embarazadas_a_la_lista_embarazadas();
        mostrar_notificacion();


    }

    private void mostrar_notificacion() {
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.fondo3);
        Intent intentresult = new Intent(MainActivity.this, Activity_Mostrar.class);

        intentresult.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, (int) Calendar.getInstance().getTimeInMillis(), intentresult, 0);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Embarazadas con estado de alto riesgo...");
        for (Embarazada i : lista_embarazada) {
            inboxStyle.addLine(i.getNombre());
        }
        inboxStyle.setSummaryText("+" + lista_embarazada.size() + " embarazadas...");

        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.fondo3)
                .setContentTitle("Embarazadas con estado de alto riesgo...")
                .setStyle(inboxStyle)
                .setLargeIcon(icon1)
                .setVibrate(new long[]{0, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .addAction(R.drawable.fondo3, "Mostrar embarazadas", pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.insertar) {
            Intent i = new Intent(getApplicationContext(), Activity_Annadir.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Llene todos los campos requeridos", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.mostrar_lista) {
            Intent i = new Intent(getApplicationContext(), Activity_Mostrar.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Seleccione una embarazada para ver sus caracteristicas", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.informacion_app) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Información de la app: ");
            String Message = "Programadores de la apk:\n1- Susana Zayas Fleitas\n2- Reiner Saroza";
            builder.setMessage(Message);
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void annadir_embarazadas_a_la_lista_embarazadas() {
        Cursor res = db.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error!!!", "No hay embarazadas ingresadas en el sistema...");
            return;
        }
        while (res.moveToNext()) {
            String id = res.getString(0);
            String nombre = res.getString(2);
            int dia_con = Integer.parseInt(res.getString(9)), mes_con = Integer.parseInt(res.getString(10)), anno_con = Integer.parseInt(res.getString(11));
            Embarazada embarazada = new Embarazada(id, null, nombre, 0, 0, 0, null, null, null, dia_con, mes_con, anno_con);
            if (embarazada.getEstado().equals("ROJO")) {
                lista_embarazada.add(embarazada);
            }
        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
