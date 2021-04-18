package distribuciones;

import Intervalo.Intervalo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NormalBoxMuller implements Distribucion {
    double desviacion;
    double media;
    ArrayList<Double> valoresGenerados;
    Random generadorAleatorios;
    double rnd1;
    double rnd2;

    public NormalBoxMuller(double desviacion, double media) {
        this.desviacion = desviacion;
        this.media = media;
        this.valoresGenerados = new ArrayList<>();
        this.generadorAleatorios = new Random();
    }

    @Override
    public ArrayList<Double> generarValores(int cant) {
        double x;
        for (int i = 0; i < cant; i++) {
            if (valoresGenerados.size() % 2 == 0) {
                rnd1 = generadorAleatorios.nextDouble();
                rnd2 = generadorAleatorios.nextDouble();
                x = (Math.sqrt(-2 * Math.log(rnd1)) * Math.cos(2 * Math.PI * rnd2)) * desviacion + media;

            } else {
                x = (Math.sqrt(-2 * Math.log(rnd1)) * Math.sin(2 * Math.PI * rnd2)) * desviacion + media;
            }
            valoresGenerados.add(x);
        }
        return valoresGenerados;
    }

    @Override
    public double generarValorExtra() {
        double x;
        if (valoresGenerados.size() % 2 == 0) {
            rnd1 = generadorAleatorios.nextDouble();
            rnd2 = generadorAleatorios.nextDouble();
            x = (Math.sqrt(-2 * Math.log(rnd1)) * Math.cos(2 * Math.PI * rnd2)) * desviacion + media;
        } else {
            x = (Math.sqrt(-2 * Math.log(rnd1)) * Math.sin(2 * Math.PI * rnd2)) * desviacion + media;
        }
        valoresGenerados.add(x);

        return x;
    }

    @Override
    public ArrayList<Double> getValores() {
        return valoresGenerados;
    }


    // Calcular Frecuencias Observadas
    @Override
    public double[] calcularFo(Intervalo intervalos) throws Exception {
        double[] fo = new double[intervalos.getCantIntervalos()];

        // Vamos metiendo cada numero en el intervalo que corresponde
        for (double numero : valoresGenerados) {
            fo[intervalos.obtenerIndexIntervalo(numero)]++;
        }
        return fo;
    }


    // Calcular Frecuencias Esperadas
    @Override
    public double[] calcularFe(Intervalo intervalos) throws Exception {
        double[] fe = new double[intervalos.getCantIntervalos()];
        double marcaClase;

        for (int i = 0 ; i < fe.length ; i++)
        {
            marcaClase = ( intervalos.getIntervalos()[i][0] + intervalos.getIntervalos()[i][1] ) / 2;
            fe[i] = (1/(this.desviacion*Math.sqrt(2*Math.PI)))*Math.exp((-1.0/2)*(Math.pow(((marcaClase - this.media) / this.desviacion), 2)));
        }
        return fe;
    }

    @Override
    public int getDatosEmpiricos() {
        return 2;
    }
}
