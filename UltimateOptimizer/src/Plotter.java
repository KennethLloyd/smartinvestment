import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class Plotter extends JPanel {
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel headerPanel;
	private ChartPanel chartPanel;
	
	private JLabel titleLabel;
	private JButton ultButton;
	
	private GridBagConstraints gc;
	
	public Plotter(final JPanel cardPanel) {
		this.cardPanel = cardPanel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		headerPanel = new JPanel();
		titleLabel = new JLabel("GRAPH");
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 50));
		ultButton = new JButton("Go to Ultimate Optimizer");
		ultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cardPanel.getLayout());
                cl.show(cardPanel, "MAIN");
			}
		});
		
		headerPanel.setLayout(new GridBagLayout());
		GridBagConstraints hgc = new GridBagConstraints();
		hgc.insets = new Insets(0, 0, 0, 0);
		
		hgc.weightx = 0.5;
		hgc.weighty = 0.5;
		
		hgc.gridx = 0;
		hgc.gridy = 0;
		
		hgc.anchor = GridBagConstraints.CENTER;
		headerPanel.add(titleLabel, hgc);
		
		hgc.gridx = 0;
		hgc.gridy = 1;
		
		headerPanel.add(ultButton, hgc);
		
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 10, 20, 0);
		
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		mainPanel.add(headerPanel, gc);
		
		XYDataset dataset = createDataset();
		 
	    JFreeChart chart = ChartFactory.createXYLineChart("XY Series Demo",
	            "X", "Y", dataset);
	    
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    
	    gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 1;
		
		mainPanel.add(chartPanel, gc);

		this.add(mainPanel);
		
	}
	
	public XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();
	    XYSeries series1 = new XYSeries("Object 1");
	    XYSeries series2 = new XYSeries("Object 2");
	    XYSeries series3 = new XYSeries("Object 3");
	 
	    series1.add(0.0, 160.0);
	    series1.add(60.0, 0.0);
	 
	    series2.add(0.0, 80.0);
	    series2.add(70.0, 0.0);
	 
	    series3.add(0.0, 0.0);
	    series3.add(20.0, 80.0);
	 
	    dataset.addSeries(series1);
	    dataset.addSeries(series2);
	    dataset.addSeries(series3);
	 
	    return dataset;
	}
}
