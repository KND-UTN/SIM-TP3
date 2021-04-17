package distribuciones;
import java.util.ArrayList;

public interface Distribucion {

    // Devuelve N valores que correspondan con la distribucion elegida
    public ArrayList<Double> generarValores(int cant);

    // Genera un valor extra y lo devuelve
    public double generarValorExtra();

    // Devuelve los valores generados (tanto los que se hicieron con el metodo generarValores como todos los que se
    // obtuvieron con el metodo generarValorExtra, todo en un ArrayList
    public ArrayList<Double> getValores();

}
