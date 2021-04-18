package Intervalo;

public class Intervalo {
    // Creación del arreglo que contendrá los intervalos
    double[][] intervalos;

    public Intervalo(double desde, double hasta, int cantIntervalos) {
        intervalos = new double[cantIntervalos][2];

        double salto = ( hasta - desde ) / cantIntervalos;
        double extremoIzquierdo = desde;

        // Método que vá cargando cada subintervalo en el arreglo original
        for (int i = 0 ; i < cantIntervalos ; i++)
        {
             intervalos[i][0] = extremoIzquierdo;
             intervalos[i][1] = extremoIzquierdo + salto;
             extremoIzquierdo = extremoIzquierdo + salto;
        }

        intervalos[intervalos.length - 1][1] = intervalos[intervalos.length - 1][1] + 0.0001;
    }


    // Uno le pasa por parámetro un número, y el método retorna el índice del subintervalo en el que se encuentra dicho número
    public int obtenerIndexIntervalo(double numero) throws Exception {
        for (int i = 0 ; i < intervalos.length ; i++)
        {
            if (numero >= intervalos[i][0] && numero < intervalos[i][1]) return i;
        }
        throw new Exception("El número no se encuentra en ningún subintervalo");
    }


    // Método que devuelve los intervalos
    public double[][] getIntervalos() {
        return intervalos;
    }


    // Método que devuelve la cantidad de intervalos
    public int getCantIntervalos()
    {
        return intervalos.length;
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("[");
        for (int i = 0 ; i < intervalos.length ; i++)
        {
            texto.append("[").append(intervalos[i][0]).append(", ").append(intervalos[i][1]).append("], ");
        }
        texto.deleteCharAt(texto.length() - 1);
        texto.deleteCharAt(texto.length() - 1);
        texto.append("]");
        return texto.toString();
    }
}
