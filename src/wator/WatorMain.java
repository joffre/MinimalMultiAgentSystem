package wator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Agent;
import core.AgentPanel;
import core.Environment;
import core.Position;
import core.PropertiesReader;
import core.SMA;
import particles.MMASWindow;
import wator.view.WatorNumberGraph;
import wator.view.WatorRatioGraph;

public class WatorMain {

	public static void main(String[] args) {
		
		SMA sma = new WatorSMA();
		
		AgentPanel partPanel = new AgentPanel(Color.white);
		MMASWindow win = new MMASWindow("MinimalMultiAgentsSystem - Wator", partPanel);
		sma.addObserver(partPanel);
		win.setVisible(true);


		WatorNumberGraph watorNumberGraph = new WatorNumberGraph();
		watorNumberGraph.display();

		WatorRatioGraph watorRatioGraph = new WatorRatioGraph();

		sma.addObserver(watorNumberGraph);
		sma.addObserver(watorRatioGraph);

		sma.run();


	}

}
