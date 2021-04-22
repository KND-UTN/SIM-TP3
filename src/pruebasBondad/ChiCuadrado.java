package pruebasBondad;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import java.util.ArrayList;
import Intervalo.Intervalo;


public class ChiCuadrado {
    ArrayList<Double > c;    // Estadístico
    double cAc;             // Estadístico de prueba (C acumulado)
    double valorCritico;    // Si cAc (C acumulado) es mayor al valor crítico, es posible rechazar la hipótesis nula
    boolean rechazada;

    // Arreglos que luego se mostrarán en las tablas..
    ArrayList<Double> foAgrupados;
    ArrayList<Double> feAgrupados;
    ArrayList<String> intervalosAgrupados;

    public ChiCuadrado()
    {
        // Inicializamos las variables..
        c = new ArrayList<>();
        feAgrupados = new ArrayList<>();
        foAgrupados = new ArrayList<>();
        intervalosAgrupados = new ArrayList<>();
    }

    public void procesar(double[] fo, double[] fe, int m, Intervalo intervalos)
    {
        // Obtenemos el estadístico acumulado
        calcularEstadisticoPrueba(fo, fe, intervalos);
        // Verificamos si se rechaza la hipótesis nula
        testHipotesis(m);
    }

    public ArrayList<Double> getFoAgrupados() {
        return foAgrupados;
    }

    public ArrayList<Double> getFeAgrupados() {
        return feAgrupados;
    }

    public ArrayList<String> getIntervalosAgrupados() {
        return intervalosAgrupados;
    }

    private void calcularEstadisticoPrueba(double[] fo, double[] fe, Intervalo intervalos)
    {
        double foAcumulada = 0;
        double feAcumulada = 0;
        ArrayList<double[]> intervalosAcumulados = new ArrayList<>();

        for (int i=0; i < fe.length; i++)
        {
            feAcumulada = feAcumulada + fe[i];
            foAcumulada = foAcumulada + fo[i];
            intervalosAcumulados.add(intervalos.getIntervalos()[i]);
            if (feAcumulada >= 5)
            {
                // Se van guardando los valores de foAc y feAc para luego mostrarlo en las tablas..
                foAgrupados.add(foAcumulada);
                feAgrupados.add(feAcumulada);
                intervalosAgrupados.add("[" + intervalosAcumulados.get(0)[0] + ", " + intervalosAcumulados.get(intervalosAcumulados.size() - 1)[1] + "]");

                intervalosAgrupados.add(intervalosAgrupados.toString());

                this.c.add(Math.pow(feAcumulada - foAcumulada, 2)/ feAcumulada);
                cAc += c.get(c.size() -1);

                feAcumulada = 0;
                foAcumulada = 0;
                intervalosAcumulados = new ArrayList<>();
            }
        }

        this.c.add(Math.pow(feAcumulada - foAcumulada, 2)/ feAcumulada);
        cAc += c.get(c.size() -1);
    }

    // Método que verifica si se puede rechazar o no la hipótesis nula
    private void testHipotesis(int m)
    {
        int gradosLibertad = ((intervalosAgrupados.size() - 1) - m);
        // Con este alpha definimos que existe un riesgo de 5% de concluir que la muestra no se ajusta a la
        // distribución propuesta, cuando en realidad si lo hace.
        double alpha = 0.05;

        // Esta clase proviene de una librería de Apache,
        // uno le pasa al constructor los grados de libertad como parámetro
        ChiSquaredDistribution distribucionChi = new ChiSquaredDistribution( gradosLibertad );
        // Luego para obtener el valor crítico, tenemos que llamar a esta función y pasarle como parámetro el alpha
        valorCritico = distribucionChi.inverseCumulativeProbability(1 - alpha);

        // Guardamos en la variable si es posible rechazar o no la hipótesis
        rechazada = !(valorCritico > cAc);
    }
}

