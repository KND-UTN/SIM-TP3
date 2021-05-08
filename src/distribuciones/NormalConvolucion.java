package distribuciones;
import Intervalo.Intervalo;

import java.util.ArrayList;
import java.util.Random;

public class NormalConvolucion implements Distribucion {

    double desviacion;
    double media;
    ArrayList<Double> valoresGenerados;
    Random generadorAleatorios;
    double minimo = Integer.MAX_VALUE;
    double maximo = -Integer.MAX_VALUE;

    public NormalConvolucion(double desviacion, double media) {
        this.desviacion = desviacion;
        this.media = media;
        this.valoresGenerados = new ArrayList<>();
        this.generadorAleatorios = new Random();
    }

    @Override
    public ArrayList<Double> generarValores(int cant)  {
        double x;
        for (int i = 0; i < cant; i++) {
            double randAcumulados = 0;

            for (int k = 0; k < 12; k++) {
                randAcumulados += generadorAleatorios.nextDouble();
            }

            x = (randAcumulados - 6) * desviacion + media;
            valoresGenerados.add(x);
            if(x > maximo) maximo = x;
            if(x < minimo) minimo = x;
        }
        return valoresGenerados;
    }

    @Override
    public double generarValorExtra() {
        double x;
        double randAcumulados = 0;
        for (int k = 0; k < 12; k++) {
            randAcumulados += generadorAleatorios.nextDouble();
        }

        x = (randAcumulados - 6) * desviacion + media;
        valoresGenerados.add(x);
        return x;
    }

    @Override
    public ArrayList<Double> getValores() {
        return valoresGenerados;
    }

    @Override
    public double[] calcularFo(Intervalo intervalos) throws Exception  {
        double[] fo = new double[intervalos.getCantIntervalos()];

        // Vamos metiendo cada numero en el intervalo que corresponde
        for (double numero : valoresGenerados) {
            fo[intervalos.obtenerIndexIntervalo(numero)]++;
        }
        return fo;
    }

    @Override
    public double[] calcularFe(Intervalo intervalos) throws Exception  {
        double[] fe = new double[intervalos.getCantIntervalos()];
        double marcaClase;

        for (int i = 0 ; i < fe.length ; i++)
        {
            marcaClase = ( intervalos.getIntervalos()[i][0] + intervalos.getIntervalos()[i][1] ) / 2;
            fe[i] = (1/(this.desviacion*Math.sqrt(2*Math.PI)))*Math.exp((-1.0/2)*(Math.pow(((marcaClase - this.media) / this.desviacion), 2))) * ( intervalos.getIntervalos()[i][1] - intervalos.getIntervalos()[i][0] ) * this.valoresGenerados.size();
        }
        return fe;
    }

    @Override
    public int getDatosEmpiricos() {
        return 2;
    }

    @Override
    public double getMaximo() {
        return maximo;
    }

    @Override
    public double getMinimo() {
        return minimo;
    }
}
