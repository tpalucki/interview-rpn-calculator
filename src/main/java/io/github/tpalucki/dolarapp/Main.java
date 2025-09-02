package io.github.tpalucki.dolarapp;


public class Main {
    private static final String DOLAR_APP = "DolarApp";

    public static void main(String[] args) {
        System.out.printf("Hello %s%n!", DOLAR_APP);
    }

    public String getAppName() {
        return DOLAR_APP;
    }

}