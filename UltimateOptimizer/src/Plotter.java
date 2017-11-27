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
		
		//sample
		final XYSeries series = new XYSeries("Random Data");
	    series.add(1.0, 500.2);
	    series.add(5.0, 694.1);
	    series.add(4.0, 100.0);
	    series.add(12.5, 734.4);
	    series.add(17.3, 453.2);
	    series.add(21.2, 500.2);
	    series.add(21.9, null);
	    series.add(25.6, 734.4);
	    series.add(30.0, 453.2);
	    final XYSeriesCollection data = new XYSeriesCollection(series);
	    final JFreeChart chart = ChartFactory.createXYLineChart(
	        "XY Series Demo",
	        "X", 
	        "Y", 
	        data,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );
	    
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    
	    gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 1;
		
		mainPanel.add(chartPanel, gc);

		this.add(mainPanel);
		
	}
}
