import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.awt.event.*;

public class Manual extends JPanel { //will contain the user manual
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel headerPanel;
	private JPanel textPanel;
	private JTextArea textArea;
	
	private JLabel titleLabel;
	private JButton ultButton;
	
	private GridBagConstraints gc;
	
	public Manual(final JPanel cardPanel) {
		this.cardPanel = cardPanel; //get the reference to be able to switch cards
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		textPanel = new JPanel();
		headerPanel = new JPanel();
		titleLabel = new JLabel("USER MANUAL");
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
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		mainPanel.add(headerPanel, gc);
		
		textPanel.setLayout(new GridBagLayout());
		GridBagConstraints tgc = new GridBagConstraints();
		tgc.insets = new Insets(0, 0, 0, 0);
		
		tgc.weightx = 0.5;
		tgc.weighty = 0.5;
		
		tgc.gridx = 0;
		tgc.gridy = 0;
		
		tgc.anchor = GridBagConstraints.CENTER;
		
		textArea = new JTextArea(200, 200);
		textArea.setFont(new Font("Serif", Font.PLAIN, 20));
		textArea.setEditable(false);
		textArea.setText("ULTIMATE OPTIMIZER\n\tFor the ultimate optimizer, the user is required to input an objective function in the space provided. \n\tThe Z variable for maximization and minimization is already provided.\n\tThe objective function should be entered as string and the coefficient and variables should not have any character between them except spaces.\n\tThe user can add constraints by clicking the Add Constraint button where a click is equal to the number of constraints to be submitted.\n\tThe constraints are divided into three parts.\n\t\t1. Left Hand Side - this is where the left hand side of the equation should be placed. The syntax is the same as the objective function syntax.\n\t\t2. Equality - this is a combo box with only two values, <= or >=.\n\t\t3. Right Hand Side - this is where the right hand side of the equation should be placed. No variables are allowed in this area.\n\tNext, the user can select whether to maximize or minimize the objective function by selecting a radio button. The default value is Maximize.\n\tAfter filling up everything, the user can now submit the input values by clicking the Submit button at the bottom of the screen.\n\tThis will show a page containing the tables of the results for each iteration and its corresponding basic solution.\n\nSMART INVESTMENT\n\tFor the smart investment, the user is required to enter the initial investment, number of years and the annual yield (%) of the investment.\n\tThere are three different securities, Security Y, Z and W.\n\tFor each security, the user should provide the Maturity level in years and the Total Yield (%).\n\tFor the Security Y only, the user should also provide a percentage for thereafter.\n\tAfter filling-up all needed information, the user can now submit the data by clicking the Submit Button.\n\tThe results will be shown on another page as same as the one in Ultimate Optimizer.\nALL INFORMATION NEEDED SHOULD BE PROVIDED BEFORE CLICKING SUBMIT.");
		
		textPanel.add(textArea, tgc);
		
		gc.anchor = GridBagConstraints.NORTH;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 1;
		mainPanel.add(textPanel, gc);
		
		this.add(mainPanel);
	}
}
