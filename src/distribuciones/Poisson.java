package distribuciones;
import Intervalo.Intervalo;

import java.util.ArrayList;

public class Poisson implements Distribucion {
    @Override
    public ArrayList<Double> generarValores(int cant) {
        return null;
    }

    @Override
    public double generarValorExtra() {
        return 0;
    }

    @Override
    public ArrayList<Double> getValores() {
        return null;
    }

    @Override
    public double[] calcularFo(Intervalo intervalos) throws Exception {
        return null;
    }

    @Override
    public double[] calcularFe(Intervalo intervalos) throws Exception {
        return null;
    }

    @Override
    public int getDatosEmpiricos() {
        return 0;
        // TODO: Corregir esto, Poisson NO tiene 0 datos empiricos (Creo) - Igna
    }
}
