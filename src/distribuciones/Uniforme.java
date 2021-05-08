package distribuciones;
import Intervalo.Intervalo;
import java.util.ArrayList;
import java.util.Arrays;

public class Uniforme implements Distribucion {

    private ArrayList<Double> valores = new ArrayList<Double>();
    private double[] limites;
    double minimo = Integer.MAX_VALUE;
    double maximo = -Integer.MAX_VALUE;

    public Uniforme(double[] limites) {
        this.limites = limites;
    }

    @Override
    public ArrayList<Double> generarValores(int cant) {
        for (int i = 0; i < cant; i++) {
            double x = limites[0] + (Math.random()*(limites[1]-limites[0]));
            valores.add(x);
            if(x > maximo) maximo = x;
            if(x < minimo) minimo = x;
        }
        return valores;
    }

    @Override
    public double generarValorExtra() {
        double x = limites[0] + (Math.random()*(limites[1]-limites[0]));
        valores.add(x);
        if(x > maximo) maximo = x;
        if(x < minimo) minimo = x;
        return x;
    }

    @Override
    public ArrayList<Double> getValores() {
        return valores;
    }

    @Override
    public double[] calcularFo(Intervalo intervalos) throws Exception {
        double[] fo = new double[intervalos.getCantIntervalos()];

        for (double muestra: valores
        ) {
            int index = intervalos.obtenerIndexIntervalo(muestra);
            fo[index]++;
        }
        return fo;
    }

    @Override
    public double[] calcularFe(Intervalo intervalos) throws Exception {
        double[] fe = new double[intervalos.getCantIntervalos()];
        double feNumero = (double) valores.size() / (intervalos.getCantIntervalos());

        Arrays.fill(fe, feNumero);
        return fe;
    }

    @Override
    public int getDatosEmpiricos() {
        return 0;
    }

    @Override
    public double getMinimo() {
        return minimo;
    }

    @Override
    public double getMaximo() {
        return maximo;
    }
}
