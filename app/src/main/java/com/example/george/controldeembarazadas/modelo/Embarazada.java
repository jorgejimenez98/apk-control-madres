package com.example.george.controldeembarazadas.modelo;


import android.graphics.Bitmap;

import java.util.Calendar;

public class Embarazada {
    private String id;
    private Bitmap foto;
    private String nombre;
    private int dia_nacimiento;
    private int mes_nacimiento;
    private int anno_nacimiento;
    private String calle;
    private String numero;
    private String numero_telefono;
    private int dia_concepcion;
    private int mes_concepcion;
    private int anno_concepcion;

    public Embarazada(String id, Bitmap foto, String nombre, int dia_nacimiento, int mes_nacimiento, int anno_nacimiento, String calle, String numero, String numero_telefono, int dia_concepcion, int mes_concepcion, int anno_concepcion) {
        this.id = id;
        this.foto = foto;
        this.nombre = nombre;
        this.dia_nacimiento = dia_nacimiento;
        this.mes_nacimiento = mes_nacimiento;
        this.anno_nacimiento = anno_nacimiento;
        this.calle = calle;
        this.numero = numero;
        this.numero_telefono = numero_telefono;
        this.dia_concepcion = dia_concepcion;
        this.mes_concepcion = mes_concepcion;
        this.anno_concepcion = anno_concepcion;
    }


    public String getEstado() {
        int a = Integer.parseInt(getCantidadSemanas());
        if (a >= 0 && a < 10) {
            return "VERDE";
        } else if (a >= 10 && a < 20) {
            return "AMARILLO";
        } else if (a >= 20 && a < 30) {
            return "AZUL";
        } else {
            return "ROJO";
        }
    }


    public String getCantidadSemanas() {
        Calendar c = Calendar.getInstance();
        String dia_actual = Integer.toString(c.get(Calendar.DATE)), mes_actual = Integer.toString(c.get(Calendar.MONTH)), anno_actual = Integer.toString(c.get(Calendar.YEAR));
        int dia = getDia_concepcion(), mes = getMes_concepcion(), anno = getAnno_concepcion();
        int dia_actual_aux = Integer.parseInt(dia_actual), mes_actual_aux = Integer.parseInt(mes_actual) + 1, anno_actual_aux = Integer.parseInt(anno_actual);
        int tiempo_final = ((((anno_actual_aux * 12) + mes_actual_aux) * 30) + dia_actual_aux) / 7;
        int tiempo_inicial = ((((anno * 12) + mes) * 30) + dia) / 7;
        return Integer.toString(tiempo_final - tiempo_inicial);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDia_nacimiento() {
        return dia_nacimiento;
    }

    public void setDia_nacimiento(int dia_nacimiento) {
        this.dia_nacimiento = dia_nacimiento;
    }

    public int getMes_nacimiento() {
        return mes_nacimiento;
    }

    public void setMes_nacimiento(int mes_nacimiento) {
        this.mes_nacimiento = mes_nacimiento;
    }

    public int getAnno_nacimiento() {
        return anno_nacimiento;
    }

    public void setAnno_nacimiento(int anno_nacimiento) {
        this.anno_nacimiento = anno_nacimiento;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero_telefono() {
        return numero_telefono;
    }

    public void setNumero_telefono(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public int getDia_concepcion() {
        return dia_concepcion;
    }

    public void setDia_concepcion(int dia_concepcion) {
        this.dia_concepcion = dia_concepcion;
    }

    public int getMes_concepcion() {
        return mes_concepcion;
    }

    public void setMes_concepcion(int mes_concepcion) {
        this.mes_concepcion = mes_concepcion;
    }

    public int getAnno_concepcion() {
        return anno_concepcion;
    }

    public void setAnno_concepcion(int anno_concepcion) {
        this.anno_concepcion = anno_concepcion;
    }

}
