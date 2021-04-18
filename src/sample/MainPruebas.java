package sample;
import Intervalo.Intervalo;

public class MainPruebas {
    public static void main(String[] args) throws Exception {
        Intervalo intervalos = new Intervalo(0, 10, 10);
        System.out.println(intervalos);
        System.out.println(intervalos.obtenerIndexIntervalo(1));
    }
}
