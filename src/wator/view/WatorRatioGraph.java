package wator.view;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import core.Agent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import wator.WatorSMA;


public class WatorRatioGraph extends Application implements Observer{

    private static final int MAX_DATA_POINTS = 25;
    private int xSeriesData = 0;
    private XYChart.Series series1;
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<Number>();

    private NumberAxis xAxis ;
    private NumberAxis yAxis ;
    private LineChart<Number,Number> lineChart;

    private void init(Stage primaryStage) {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        //lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);

        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);

        //-- Chart
        final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis) {
            // Override to remove symbols on each data point
            @Override protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
        };
        sc.setAnimated(false);
        sc.setId("liveLineeChart");
        sc.setTitle("Wa Tor");

        //-- Chart Series
        series1 = new XYChart.Series<Number, Number>();

        lineChart.getData().addAll(series1);

        series1.setName("Ratio Fish/Shark");


        primaryStage.setScene(new Scene(lineChart));
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Fishes & Sharks ratio");
        init(stage);
        stage.show();


        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();


    }

    @Override
    public void update(Observable o, Object arg) {
        WatorSMA wSMA = (WatorSMA) o;
        if(wSMA != null) update(wSMA.getSharkNumber(), wSMA.getFishNumber());
    }

    private class AddToQueue implements Runnable {
        @Override
        public void run() {
            try {
                // add a item of random data to queue
                dataQ1.add((float)nbFish/(float)nbShark);

                Thread.sleep(1000);
                executor.execute(this);
            } catch (InterruptedException ex) {
                //ERROR
            }
        }
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < MAX_DATA_POINTS; i++) {
            if (dataQ1.isEmpty()) break;
            series1.getData().add(new AreaChart.Data(nbFish, nbShark));
            dataQ1.remove();
        }
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }
    }

    public static int nbShark = 0;
    public static int nbFish = 0;
    public void update(int nbShark, int nbFish){
        this.nbShark = nbShark;
        this.nbFish = nbFish;

    }
}
