package distribuciones;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Poisson {

    double lambda;
    ArrayList<Integer> valoresGenerados;
    Hashtable<Integer, Integer> frecuenciasObservadasTemporal;
    ArrayList<Integer> fo;
    ArrayList<Integer> fe;
    double minimo = Integer.MAX_VALUE;
    double maximo = -Integer.MAX_VALUE;
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

    public void calcularFoFe() throws Exception {

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
            double expLambda = Math.exp(-lambda);
            BigDecimal bigX = new BigDecimal(parClave.get(i));
            BigDecimal bigLambda = new BigDecimal(lambda);
            BigDecimal lambdaExponencial = bigLambda.pow(parClave.get(i));
            BigDecimal eulerExponencial = new BigDecimal(expLambda);
            BigDecimal dividendo = lambdaExponencial.multiply(eulerExponencial);
            BigDecimal divisor = factorial(bigX);
            // 30 decimales, redondeo para arriba
            BigDecimal division = dividendo.divide(divisor, 30, RoundingMode.HALF_UP);
            BigDecimal multiplicador = new BigDecimal(valoresGenerados.size());
            BigDecimal multiplicacion = division.multiply(multiplicador);
            System.out.println("------");
            System.out.println("lambdaExponencial: " + lambdaExponencial);
            System.out.println("eulerExponencial: " + eulerExponencial);
            System.out.println("Dividendo: " + dividendo);
            System.out.println("------");
            frecuenciaEsperada = multiplicacion.intValue();
            fe.add(frecuenciaEsperada);
        }
    }

    // Metodo para calcular factoriales de un numero
    private static BigDecimal factorial(BigDecimal entrada) throws Exception {
        if (entrada.compareTo(new BigDecimal(2)) > 0) return entrada.multiply(factorial(entrada.subtract(BigDecimal.ONE)));
        if (entrada.intValue() == 2) return new BigDecimal(2);
        if (entrada.intValue() == 1) return new BigDecimal(1);
        if (entrada.intValue() == 0) return new BigDecimal(1);
        throw new Exception("Valores negativos...");
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
