package pruebasBondad;
import Intervalo.Intervalo;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import java.util.ArrayList;

public class ChiCuadradoPoisson {
    ArrayList<Double > c;    // Estadístico
    double cAc;             // Estadístico de prueba (C acumulado)
    double valorCritico;    // Si cAc (C acumulado) es mayor al valor crítico, es posible rechazar la hipótesis nula
    boolean rechazada;

    // Arreglos que luego se mostrarán en las tablas..
    ArrayList<Double> foAgrupados;
    ArrayList<Double> feAgrupados;
    ArrayList<String> intervalosAgrupados;

    public ArrayList<Double> getFoAgrupados() {
        return foAgrupados;
    }

    public ArrayList<Double> getFeAgrupados() {
        return feAgrupados;
    }

    public ArrayList<String> getIntervalosAgrupados() {
        return intervalosAgrupados;
    }

    public ArrayList<Double> getEstadisticos() {
        return c;
    }

    public ChiCuadradoPoisson()
    {
        // Inicializamos las variables
        c = new ArrayList<>();
        feAgrupados = new ArrayList<>();
        foAgrupados = new ArrayList<>();
        intervalosAgrupados = new ArrayList<>();
    }

    // Método que calcula el valor calculado (Estadístico de prueba)
    public void calcularEstadisticoPrueba(ArrayList<Integer> valores, ArrayList<Integer> fo, ArrayList<Integer> fe){
        double foAcumulada = 0;
        double feAcumulada = 0;
        String intervaloAcumulado = "";

        for (int i=0; i < valores.size(); i++)
        {
            feAcumulada = feAcumulada + fe.get(i);
            foAcumulada = foAcumulada + fo.get(i);
            intervaloAcumulado = intervaloAcumulado + valores.get(i) + "; ";

            if (feAcumulada >= 5)
            {
                // Se van guardando los valores de foAc y feAc para luego mostrarlo en las tablas..
                foAgrupados.add(foAcumulada);
                feAgrupados.add(feAcumulada);
                intervalosAgrupados.add(intervaloAcumulado);

                this.c.add(Math.pow(feAcumulada - foAcumulada, 2)/ feAcumulada);
                cAc += c.get(c.size() -1);

                intervaloAcumulado = "";
                feAcumulada = 0;
                foAcumulada = 0;
            }
        }

        if (feAcumulada > 0)
        {
            // Se van guardando los valores de foAc y feAc para luego mostrarlo en las tablas..
            foAgrupados.add(foAcumulada);
            feAgrupados.add(feAcumulada);
            intervalosAgrupados.add(intervaloAcumulado);

            intervalosAgrupados.add(intervalosAgrupados.toString());

            this.c.add(Math.pow(feAcumulada - foAcumulada, 2)/ feAcumulada);
            cAc += c.get(c.size() -1);
        }

    }


    public double getcAc() {
        return cAc;
    }

    public double getValorCritico() {
        return valorCritico;
    }

    // Método que verifica si se puede rechazar o no la hipótesis nula
    public void testHipotesis(int m)
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

    public boolean isRechazada() {
        return rechazada;
    }
}
