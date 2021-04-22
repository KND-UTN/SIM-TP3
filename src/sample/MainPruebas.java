package sample;
import Intervalo.Intervalo;

import java.util.Hashtable;

public class MainPruebas {
    public static void main(String[] args) throws Exception {
        Hashtable<Integer, String> tablita = new Hashtable<>();

        tablita.put(8, "Sopa");
        tablita.put(7, "Hola");
        tablita.put(9, "Chau");

        System.out.println(tablita);
    }
}
