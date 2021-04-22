package sample;

import Intervalo.Intervalo;
import distribuciones.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pruebasBondad.ChiCuadrado;
import pruebasBondad.ChiCuadradoPoisson;
import tablas.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller {
    @FXML
    private Label lblParam1;
    @FXML
    private Label lblParam2;
    @FXML
    private Label lblResultadoTest;
    @FXML
    private RadioButton rdbtnUniforme;
    @FXML
    private RadioButton rdbtnExponencial;
    @FXML
    private RadioButton rdbtnConvolucion;
    @FXML
    private RadioButton rdbtnBoxMuller;
    @FXML
    private RadioButton rdbtnPoisson;
    @FXML
    private Spinner<Double> spnMedia;
    @FXML
    private Spinner<Double> spnDes;
    @FXML
    private Spinner<Double> spnLambda;
    @FXML
    private Spinner<Double> spnA;
    @FXML
    private Spinner<Double> spnB;
    @FXML
    private Spinner<Integer> spnCant;
    @FXML
    private Spinner<Double> spnIntervalos;
    @FXML
    private Spinner<Double> spnConfianza;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChi;
    @FXML
    private Button btnHistograma;
    @FXML
    private TableView<NumRandom> tblRandoms;
    @FXML
    private TableColumn itNumero;
    @FXML
    private TableColumn itRandom;
    @FXML
    private TableView tablaChi;
    @FXML
    private TableColumn itIntervalo;
    @FXML
    private TableColumn itFo;
    @FXML
    private TableColumn itFe;
    @FXML
    private TableColumn itC;

    boolean parametro;
    Distribucion distribucion;
    Poisson distribucionPoisson;
    int distribucionNum;
    int cantidadDecimales = 4;
    int cantidadRandoms = 0;
    ObservableList<NumRandom> listaRandoms = FXCollections.observableArrayList();
    ObservableList<String> listaDatos = FXCollections.observableArrayList();
    ChiCuadrado chi;
    ChiCuadradoPoisson chiPoisson;
    ArrayList<Integer> valoresChi;
    ArrayList<Double> valores;

    public Controller() {
    }

    ;

    public void initialize() {

        this.spnMedia.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2147483647));
        this.spnDes.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2147483647));
        this.spnA.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2147483647));
        this.spnLambda.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2147483647));
        this.spnB.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2147483647));
        this.spnCant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2147483647));
        this.spnIntervalos.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 2147483647));
        this.spnConfianza.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0D, 1.0D, 0.95D, 0.01D));
        this.tblRandoms.setItems(this.listaRandoms);
        this.itNumero.setCellValueFactory(new PropertyValueFactory("i"));
        this.itRandom.setCellValueFactory(new PropertyValueFactory("num"));
        this.tablaChi.setItems(this.listaDatos);
        this.itIntervalo.setCellValueFactory(new PropertyValueFactory("inter"));
        this.itFo.setCellValueFactory(new PropertyValueFactory("fo"));
        this.itFe.setCellValueFactory(new PropertyValueFactory("fe"));
        this.itC.setCellValueFactory(new PropertyValueFactory("c"));
    }

    public void botonGenerarPresionado(ActionEvent actionEvent) {

        tblRandoms.getItems().clear();

        this.spnA.setDisable(true);
        this.spnB.setDisable(true);
        this.spnMedia.setDisable(true);
        this.spnLambda.setDisable(true);
        this.spnDes.setDisable(true);
        this.spnCant.setDisable(true);
        this.btnGenerar.setDisable(true);
        this.btnAdd.setDisable(false);
        this.spnIntervalos.setDisable(false);
        this.spnConfianza.setDisable(false);
        this.btnChi.setDisable(false);
        this.lblResultadoTest.setVisible(true);


        int cant = this.spnCant.getValue();

        if (this.distribucionNum == 0) {

            double a = this.spnA.getValue();
            double b = this.spnB.getValue();
            this.distribucion = new Uniforme(new double[]{a, b});
        }

        if (this.distribucionNum == 1) {
            double lambda = this.spnLambda.getValue();


            this.distribucion = new Exponencial(lambda);
        }

        if (this.distribucionNum == 2) {
            this.distribucionPoisson = new Poisson(spnLambda.getValue());
        }
        if (this.distribucionNum == 3) {
            double desviacion = this.spnDes.getValue();
            double media = this.spnMedia.getValue();
            this.distribucion = new NormalBoxMuller(desviacion, media);
        }
        if (this.distribucionNum == 4) {
            double desviacion = this.spnDes.getValue();
            double media = this.spnMedia.getValue();
            this.distribucion = new NormalConvolucion(desviacion, media);
        }

        int i = 0;
        if (this.distribucionNum == 2) {
            valores = this.distribucion.generarValores(cant);

            for (Iterator var8 = valores.iterator(); var8.hasNext(); ++i) {
                Double valor = (Double) var8.next();
                this.tblRandoms.getItems().add(new NumRandom(i, String.valueOf((double) ((int) (valor * Math.pow(10.0D, (double) this.cantidadDecimales))) / Math.pow(10.0D, (double) this.cantidadDecimales))));
            }
        } else {
            valoresChi = this.distribucionPoisson.generarValores(cant);

            for (Iterator var8 = valores.iterator(); var8.hasNext(); ++i) {
                Integer valor = (Integer) var8.next();
                this.tblRandoms.getItems().add(new NumRandom(i, String.valueOf((double) ((int) (valor * Math.pow(10.0D, (double) this.cantidadDecimales))) / Math.pow(10.0D, (double) this.cantidadDecimales))));
            }
        }
        this.cantidadRandoms = i;
    }

    public void botonProximoPresionado(ActionEvent actionEvent) {
        Double nuevoValor = this.distribucion.generarValorExtra();
        this.tblRandoms.getItems().add(new NumRandom(this.cantidadRandoms, String.valueOf((double) ((int) (nuevoValor * Math.pow(10.0D, (double) this.cantidadDecimales))) / Math.pow(10.0D, (double) this.cantidadDecimales))));
        ++this.cantidadRandoms;
    }

    public void btnUniformeSeleccionado(ActionEvent actionEvent) {

        this.spnA.setDisable(false);
        this.spnB.setDisable(false);
        this.spnMedia.setDisable(true);
        this.spnLambda.setDisable(true);
        this.spnDes.setDisable(true);
        this.spnCant.setDisable(false);
        this.btnGenerar.setDisable(false);

        distribucionNum = 0;
    }

    public void btnExponencialSeleccionado(ActionEvent actionEvent) {

        this.spnA.setDisable(true);
        this.spnB.setDisable(true);
        this.spnMedia.setDisable(true);
        this.spnLambda.setDisable(false);
        this.spnDes.setDisable(true);
        this.spnCant.setDisable(false);
        this.btnGenerar.setDisable(false);
        distribucionNum = 1;
    }

    public void btnPoissonSeleccionado(ActionEvent actionEvent) {

        this.spnA.setDisable(true);
        this.spnB.setDisable(true);
        this.spnMedia.setDisable(true);
        this.spnLambda.setDisable(false);
        this.spnDes.setDisable(true);
        this.spnCant.setDisable(false);
        this.btnGenerar.setDisable(false);
        distribucionNum = 2;
    }

    public void btnBoxMullerSeleccionado(ActionEvent actionEvent) {

        this.spnA.setDisable(true);
        this.spnB.setDisable(true);
        this.spnMedia.setDisable(false);
        this.spnLambda.setDisable(true);
        this.spnDes.setDisable(false);
        this.spnCant.setDisable(false);
        this.btnGenerar.setDisable(false);
        distribucionNum = 3;
    }

    public void btnConvolucionSeleccionado(ActionEvent actionEvent) {

        this.spnA.setDisable(true);
        this.spnB.setDisable(true);
        this.spnMedia.setDisable(false);
        this.spnLambda.setDisable(true);
        this.spnDes.setDisable(false);
        this.spnCant.setDisable(false);
        this.btnGenerar.setDisable(false);
        distribucionNum = 4;
    }

    public void btnChiCuadradoPresionado(ActionEvent actionEvent) throws Exception {
        btnChi.setDisable(true);
        btnHistograma.setDisable(false);
        lblResultadoTest.setVisible(true);

        ArrayList<String> intervalos;
        ArrayList<Double> frecuenciasO;
        ArrayList<Double> frecuenciasE;
        ArrayList<Double> estadisticos;

        // Si es Poisson
        if (this.distribucionNum == 2) {
            chiPoisson = new ChiCuadradoPoisson();
            chiPoisson.calcularEstadisticoPrueba(distribucionPoisson.getValores(), distribucionPoisson.getFo(), distribucionPoisson.getFe());
            intervalos = chiPoisson.getIntervalosAgrupados();
            frecuenciasO = chiPoisson.getFoAgrupados();
            frecuenciasE = chiPoisson.getFeAgrupados();
            estadisticos = chiPoisson.getEstadisticos();
        }
        // Caso contrario, no es Poisson
        else {
            Intervalo intervalosChi = new Intervalo(distribucion.getMinimo(), distribucion.getMaximo(), spnIntervalos.getValue().intValue());
            chi = new ChiCuadrado();
            chi.calcularEstadisticoPrueba(distribucion.calcularFo(intervalosChi), distribucion.calcularFe(intervalosChi), intervalosChi);
            intervalos = chi.getIntervalosAgrupados();
            frecuenciasO = chi.getFoAgrupados();
            frecuenciasE = chi.getFeAgrupados();
            estadisticos = chi.getEstadisticos();
        }


        for (int i = 0; i < intervalos.size(); i++) {
            NumChi chicuadrado;
            chicuadrado = new NumChi(intervalos.get(i)
                    , String.valueOf(frecuenciasO.get(i))
                    , String.valueOf(frecuenciasE.get(i))
                    , String.valueOf(estadisticos.get(i)));
            tablaChi.getItems().add(chicuadrado);
        }

    }


    public void mostrarHistograma(ActionEvent actionEvent) {

        Stage grafico = new Stage();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Intervalos");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frecuencias");
        BarChart barChart = new BarChart(xAxis, yAxis);
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Fo");
        barChart.getData().add(dataSeries1);
        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Fe");
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);


//        for(int i = 0; i < this.chi.getIntervalos().length; ++i) {
//            double extremoDerecho;
//            if (i == 0) {
//                extremoDerecho = new Double(df.format(this.chi.getIntervalos()[i]));
//                dataSeries1.getData().add(new Data("[ 0.0, " + extremoDerecho + "]", this.chi.getFrecuenciasObservadas()[i]));
//                dataSeries2.getData().add(new Data("[ 0.0, " + extremoDerecho + "]", this.chi.getFrecuenciasEsperadas()[i]));
//            } else {
//                double extremoIzquierdo = new Double(df.format(this.chi.getIntervalos()[i - 1]));
//                extremoDerecho = new Double(df.format(this.chi.getIntervalos()[i]));
//                dataSeries1.getData().add(new Data("[ " + extremoIzquierdo + ", " + extremoDerecho + "]", this.chi.getFrecuenciasObservadas()[i]));
//                dataSeries2.getData().add(new Data("[ " + extremoIzquierdo + ", " + extremoDerecho + "]", this.chi.getFrecuenciasEsperadas()[i]));
//            }
//        }

        barChart.getData().add(dataSeries2);
        VBox vbox = new VBox(new Node[]{barChart});
        grafico.setTitle("Histograma");
        grafico.setScene(new Scene(vbox, 600.0D, 400.0D));
        grafico.show();
    }

}
