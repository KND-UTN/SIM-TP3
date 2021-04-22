package distribuciones;
import Intervalo.Intervalo;

import java.util.ArrayList;

public interface Distribucion {
    // Devuelve N valores que correspondan con la distribucion elegida
    public ArrayList<Double> generarValores(int cant);

    // Genera un valor extra y lo devuelve
    public double generarValorExtra();

    // Devuelve los valores generados (tanto los que se hicieron con el metodo generarValores como todos los que se
    // obtuvieron con el metodo generarValorExtra, todo en un ArrayList
    public ArrayList<Double> getValores();

    // Calcula las frecuencuas observadas y las devuelve en un arreglo del mismo tamaño que la cantidad de intervalos
    public double[] calcularFo(Intervalo intervalos) throws Exception ;

    // Calcula las frecuencuas esperadas y las devuelve en un arreglo del mismo tamaño que la cantidad de intervalos
    public double[] calcularFe(Intervalo intervalos) throws Exception ;

    // Metodo que retorna la cantidad de datos empiricos que tiene la distribucion
    public int getDatosEmpiricos();

    public double getMinimo();

    public double getMaximo();

}
