package com.nikolas.pruebaTrinity.service;

import java.util.Random;

public class NumeroCuentaGenerador {
    private static final int LONGITUD_CUENTA = 10;
    public static String generarNumeroCuenta(String prefijo){
        if (prefijo.length()  != 2){
            throw new IllegalArgumentException("El prefijo debe de tener dos digitos");
        }
        Random random = new Random();
        StringBuilder numeroCuenta = new StringBuilder(prefijo);
        while (numeroCuenta.length() < LONGITUD_CUENTA){
            numeroCuenta.append(random.nextInt(10));
        }
        return numeroCuenta.toString();
    }
}
