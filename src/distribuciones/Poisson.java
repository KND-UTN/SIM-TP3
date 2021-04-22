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
    ArrayList<Integer> valores;
    Random generadorAleatorios;

    double getMinimo(){return minimo;}

    double getMaximo(){ return maximo;}

    public ArrayList<Integer> getFo() {
        return fo;
    }

    public ArrayList<Integer> getFe() {
        return fe;
    }



    public Poisson(double lambda)
    {
        this.lambda = lambda;
        this.valoresGenerados = new ArrayList<>();
        this.generadorAleatorios = new Random();
        this.frecuenciasObservadasTemporal = new Hashtable<>();
        this.valores = new ArrayList<>();
        this.fo = new ArrayList<>();
        this.fe = new ArrayList<>();
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

    public ArrayList<Integer> getValoresChi() {
        return valores;
    }

    public void calcularFoFe() {

        for (double valor: valoresGenerados)
        {
            if (frecuenciasObservadasTemporal.containsKey((int) valor))
            {
                frecuenciasObservadasTemporal.put((int) valor, frecuenciasObservadasTemporal.get((int) valor) + 1);
            }
            else { frecuenciasObservadasTemporal.put((int) valor, 1);}
        }

        Iterator<Map.Entry<Integer, Integer>> iterador = frecuenciasObservadasTemporal.entrySet().iterator();
        ArrayList<Integer> parClave = new ArrayList<>();
        ArrayList<Integer> parValor = new ArrayList<>();
        while (iterador.hasNext())
        {
            Map.Entry<Integer, Integer> actual = iterador.next();
            parClave.add(actual.getKey());
            parValor.add(actual.getValue());
        }

        Collections.reverse(parClave);
        Collections.reverse(parValor);

        int frecuenciaEsperada;
        for ( int i = 0 ; i < parClave.size() ; i++ )
        {
            valores.add(parClave.get(i));
            fo.add(parValor.get(i));
            frecuenciaEsperada = (int) (Math.pow(lambda, parClave.get(i)) * Math.exp(-lambda) / ArithmeticUtils.factorial(parClave.get(i)) * valoresGenerados.size());
            fe.add(frecuenciaEsperada);
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
