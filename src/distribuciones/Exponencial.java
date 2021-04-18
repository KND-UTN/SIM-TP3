package distribuciones;
import Intervalo.Intervalo;
import java.util.ArrayList;
import java.util.Random;

public class Exponencial implements Distribucion {
    ArrayList<Double> valoresGenerados;
    Random generadorAleatorios;
    double rnd;
    double lambda;

    public Exponencial(double lambda) {
        this.lambda = lambda;
        this.valoresGenerados = new ArrayList<>();
        this.generadorAleatorios = new Random();
    }

    @Override
    public ArrayList<Double> generarValores(int cant) {
        double x;
        for (int i = 0; i < cant; i++) {
            rnd = generadorAleatorios.nextDouble();
            x = ( - 1 / lambda) * Math.log( 1 - rnd );
            valoresGenerados.add(x);
        }
        return valoresGenerados;
    }

    @Override
    public double generarValorExtra() {
        double x;
        rnd = generadorAleatorios.nextDouble();
        x = ( - 1 / lambda) * Math.log( 1 - rnd );
        valoresGenerados.add(x);
        return x;
    }

    @Override
    public ArrayList<Double> getValores() {
        return valoresGenerados;
    }

    @Override
    public double[] calcularFo(Intervalo intervalos) throws Exception {
        double[] fo = new double[intervalos.getCantIntervalos()];

        // Vamos metiendo cada numero en el intervalo que corresponde
        for (double numero : valoresGenerados) {
            fo[intervalos.obtenerIndexIntervalo(numero)]++;
        }

        return fo;
    }

    @Override
    public double[] calcularFe(Intervalo intervalos) throws Exception {
        double[] fe = new double[intervalos.getCantIntervalos()];
        double limIzquierdo;
        double limDerecho;

        for (int i = 0 ; i < fe.length ; i++)
        {
            limIzquierdo = intervalos.getIntervalos()[i][0];
            limDerecho =  intervalos.getIntervalos()[i][1];
            fe[i] = (lambda * Math.exp(- lambda * limDerecho)) - (lambda * Math.exp(- lambda * limIzquierdo));
        }

        return fe;
    }

    @Override
    public int getDatosEmpiricos() {
        return 1;
    }

}
