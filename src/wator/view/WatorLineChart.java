package wator.view;

import core.PropertiesReader;
import core.SMA;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import wator.WatorSMA;

import java.util.Observer;


public class WatorLineChart extends Application implements Observer{

    XYChart.Series sharkSerie;
    XYChart.Series fishSerie;

    public WatorLineChart(){
        super();
        int sharkBase = PropertiesReader.getInstance().getSharkNumber();
        int fishBase = PropertiesReader.getInstance().getFishNumber();

        sharkSerie = new XYChart.Series();
        sharkSerie.setName("Shark population");
        sharkSerie.getData().add(new XYChart.Data(0, sharkBase));
        fishSerie = new XYChart.Series();
        fishSerie.setName("Fish population");
        fishSerie.getData().add(new XYChart.Data(0, fishBase));
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Fish & Shark evolution");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Tick");
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Fish & Shark evolution");

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(sharkSerie, fishSerie);

        stage.setScene(scene);
        stage.show();
    }

    public static void run(){
        launch();
    }
    @Override
    public void update(java.util.Observable o, Object arg) {
        WatorSMA sma = (WatorSMA) o;
        if(sma != null){
            int fishNumber = sma.getFishNumber();
            int sharkNumber = sma.getSharkNumber();
            int currentTick = sma.getCurrentTick();

            if(sharkSerie != null){
                System.out.print("update");
                sharkSerie.getData().add(new XYChart.Data(currentTick, sharkNumber));
            }
            if(fishSerie != null){
                fishSerie.getData().add(new XYChart.Data(currentTick, fishNumber));
                System.out.print("update");
            }
        }
    }
}