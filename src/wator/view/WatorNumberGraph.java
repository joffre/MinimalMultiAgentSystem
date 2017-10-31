package wator.view;

import java.util.List;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import core.Agent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
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


public class WatorNumberGraph extends Application implements Observer{

    private static final int MAX_DATA_POINTS = 50;
    private int xSeriesData = 0;
    private XYChart.Series series1;
    private XYChart.Series series2;
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<Number>();

    private NumberAxis xAxis ;
    private NumberAxis yAxis ;
    private AreaChart<Number,Number> lineChart;

    private void init(Stage primaryStage) {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new AreaChart<Number,Number>(xAxis,yAxis);


        xAxis = new NumberAxis(0,MAX_DATA_POINTS,MAX_DATA_POINTS/10);
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
        series2 = new XYChart.Series<Number, Number>();

        lineChart.getData().addAll(series1, series2);

        series1.setName("Shark population");
        series2.setName("Fish population");


        primaryStage.setScene(new Scene(lineChart));
    }


    @Override public void start(Stage stage) {
        stage.setTitle("Fishes & Sharks Number");
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
    public void update(java.util.Observable o, Object arg) {
        WatorSMA wSMA = (WatorSMA) o;
        if(wSMA != null) update(wSMA.getSharkNumber(), wSMA.getFishNumber());
    }

    private class AddToQueue implements Runnable {
        @Override
        public void run() {
            try {
                // add a item of random data to queue
                dataQ1.add(nbShark);
                dataQ2.add(nbFish);

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
        for (int i = 0; i < MAX_DATA_POINTS; i++) { //-- add 40 numbers to the plot+
            if (dataQ1.isEmpty()) break;
            series1.getData().add(new AreaChart.Data(xSeriesData++, dataQ1.remove()));
            series2.getData().add(new AreaChart.Data(xSeriesData++, dataQ2.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }
        if (series2.getData().size() > MAX_DATA_POINTS) {
            series2.getData().remove(0, series2.getData().size() - MAX_DATA_POINTS);
        }
        // update
        lineChart.getXAxis().setAutoRanging(false);
        ((NumberAxis) lineChart.getXAxis()).setForceZeroInRange(false);

        lineChart.getYAxis().setAutoRanging(true);

        if(xSeriesData-MAX_DATA_POINTS > 0){
            ((ValueAxis<Number>) lineChart.getXAxis()).setLowerBound(xSeriesData-MAX_DATA_POINTS);
        }
        ((ValueAxis<Number>) lineChart.getXAxis()).setUpperBound(xSeriesData-1);


    }


    public void display(){
        new JFXPanel();
        Platform.runLater(new Runnable() {
            public void run() {
                new WatorRatioGraph().start(new Stage());
            }
        });

        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(WatorNumberGraph.class);
                //javafx.application.Platform.runLater(WatorNumberGraph.class);
            }
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int nbShark = 0;
    public static int nbFish = 0;
    public void update(int nbShark, int nbFish){
        this.nbShark = nbShark;
        this.nbFish = nbFish;

    }
}
