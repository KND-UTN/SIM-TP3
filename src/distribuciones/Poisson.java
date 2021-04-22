package distribuciones;
import org.apache.commons.math3.util.ArithmeticUtils;
import java.util.*;

public class Poisson {

    double lambda;
    ArrayList<Integer> valoresGenerados;
    Hashtable<Integer, Integer> frecuenciasObservadasTemporal;
    ArrayList<Integer> fo;
    ArrayList<Integer> fe;
    double minimo = Integer.MAX_VALUE;
    double maximo = 0;

    double getMinimo(){return minimo;}

    double getMaximo(){ return maximo;}

    public ArrayList<Integer> getFo() {
        return fo;
    }

    public ArrayList<Integer> getFe() {
        return fe;
    }

    ArrayList<Integer> valores;

    Random generadorAleatorios;

    public Poisson(double lambda)
    {
        this.lambda = lambda;
        this.valoresGenerados = new ArrayList<>();
        this.generadorAleatorios = new Random();
    }

    public ArrayList<Integer> generarValores(int cant)
    {
        double U;
        double P;
        double A;
        double x;
        for ( int i = 0 ; i < cant ; i++ )
        {
            P = 1;
            A = Math.exp(-lambda);
            x = -1;
            while (P >= A ){
                U = Math.random();
                P = P * U;
                x = x + 1;
            }
            valoresGenerados.add((int) x);
        }
        return valoresGenerados;
    }

    public double generarValorExtra() {
        return 0;
    }

    public ArrayList<Integer> getValores() {
        return valoresGenerados;
    }

    public void calcularFoFe() {

        for (double valor: valoresGenerados)
        {
            if (frecuenciasObservadasTemporal.contains(valor))
            {
                frecuenciasObservadasTemporal.put((int) valor, frecuenciasObservadasTemporal.get((int) valor) + 1);
            }
            else { frecuenciasObservadasTemporal.put((int) valor, 1);}
        }

        Iterator<Map.Entry<Integer, Integer>> iterador = frecuenciasObservadasTemporal.entrySet().iterator();

        while (iterador.hasNext())
        {
            Map.Entry<Integer, Integer> actual = iterador.next();
            valores.add(actual.getKey());
            fo.add(actual.getValue());
            fe.add((int) Math.round(Math.pow(lambda, actual.getKey()) * Math.exp(-lambda) / ArithmeticUtils.factorial(actual.getKey())) * valoresGenerados.size());
        }
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
