import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class SmartInv extends JPanel {
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel headerPanel;
	private JPanel initialPanel;
	private JPanel securityPanel;
	
	private JLabel titleLabel;
	private JLabel yearLabel;
	private JLabel initLabel;
	private JLabel yieldLabel;
	private JLabel secYLabel;
	private JLabel secZLabel;
	private JLabel secWLabel;
	private JLabel maturityLabel;
	private JLabel totYieldLabel;
	private JLabel thereafterLabel;
	
	private JButton ultButton;
	private JButton submitButton;
	
	private JTextField yearField;
	private JTextField initField;
	private JTextField yieldField;
	private JTextField matYField;
	private JTextField matZField;
	private JTextField matWField;
	private JTextField totYField;
	private JTextField totZField;
	private JTextField totWField;
	private JTextField thereYField;
	
	public SmartInv(final JPanel cardPanel) {
		this.cardPanel = cardPanel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		headerPanel = new JPanel();
		titleLabel = new JLabel("SMART INVESTMENT");
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
		
		headerPanel.add(titleLabel, hgc);
		
		hgc.gridx = 0;
		hgc.gridy = 1;
		
		headerPanel.add(ultButton, hgc);
	
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 20, 0);
		
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		mainPanel.add(headerPanel, gc);
		
		initialPanel = new JPanel();
		initialPanel.setLayout(new GridBagLayout());
		
		yearLabel = new JLabel("Years: ");
		yearLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		yearField = new JTextField(5);
		
		initLabel = new JLabel("Initial Investment ($): ");
		initLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		initField = new JTextField(10);
		
		yieldLabel = new JLabel("Annual Yield (%): ");
		yieldLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		yieldField = new JTextField(5);
		
		GridBagConstraints initLabelGc = new GridBagConstraints();
        GridBagConstraints initFieldGc = new GridBagConstraints();
		
        initLabelGc.gridx = 0;
        initLabelGc.gridy = 0;
        initLabelGc.anchor = GridBagConstraints.EAST;
        
        initFieldGc.gridx = 1;
        initFieldGc.gridy = 0;
        initFieldGc.anchor = GridBagConstraints.WEST;
        
        initialPanel.add(yearLabel, initLabelGc);
        initialPanel.add(yearField, initFieldGc);
        
        initLabelGc.gridx = 0;
        initLabelGc.gridy = 1;
        
        initFieldGc.gridx = 1;
        initFieldGc.gridy = 1;
        
        initialPanel.add(initLabel, initLabelGc);
        initialPanel.add(initField, initFieldGc);
        
        initLabelGc.gridx = 0;
        initLabelGc.gridy = 2;
        
        initFieldGc.gridx = 1;
        initFieldGc.gridy = 2;
        
        initialPanel.add(yieldLabel, initLabelGc);
        initialPanel.add(yieldField, initFieldGc);
		
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		
		gc.gridx = 0;
		gc.gridy = 1;
		
		mainPanel.add(initialPanel, gc);
		
		securityPanel = new JPanel();
		securityPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints sgc = new GridBagConstraints();
		
		secYLabel = new JLabel("     Security Y     ");
		secYLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		secZLabel = new JLabel("Security Z");
		secZLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		secWLabel = new JLabel("Security W");
		secWLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		
		maturityLabel = new JLabel("Maturity");
		maturityLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		matYField = new JTextField(5);
		matZField = new JTextField(5);
		matWField = new JTextField(5);
		
		totYieldLabel = new JLabel("Total Yield (%)");
		totYField = new JTextField(5);
		totZField = new JTextField(5);
		totWField = new JTextField(5);
		totYieldLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		
		thereafterLabel = new JLabel("Thereafter (%)");
		thereafterLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		thereYField = new JTextField(5);
		
		GridBagConstraints secCol = new GridBagConstraints();
        GridBagConstraints matCol = new GridBagConstraints();
        GridBagConstraints totCol = new GridBagConstraints();
        GridBagConstraints thereCol = new GridBagConstraints();
		
        secCol.insets = new Insets(10, 5, 10, 5);
        matCol.insets = new Insets(10, 5, 10, 5);
        totCol.insets = new Insets(10, 5, 10, 5);
        thereCol.insets = new Insets(10, 5, 10, 5);
        
        secCol.gridx = 0;
        secCol.gridy = 1;
        
        matCol.gridx = 1;
        matCol.gridy = 0;
        
        totCol.gridx = 2;
        totCol.gridy = 0;
        
        thereCol.gridx = 3;
        thereCol.gridy = 0;
        
        securityPanel.add(secYLabel, secCol);
        securityPanel.add(maturityLabel, matCol);
        securityPanel.add(totYieldLabel, totCol);
        securityPanel.add(thereafterLabel, thereCol);
        
        secCol.gridx = 0;
        secCol.gridy = 2;
        
        matCol.gridx = 1;
        matCol.gridy = 1;
        
        totCol.gridx = 2;
        totCol.gridy = 1;
        
        thereCol.gridx = 3;
        thereCol.gridy = 1;
        
        securityPanel.add(secZLabel, secCol);
        securityPanel.add(matYField, matCol);
        securityPanel.add(totYField, totCol);
        securityPanel.add(thereYField, thereCol);
        
        secCol.gridx = 0;
        secCol.gridy = 3;
        
        matCol.gridx = 1;
        matCol.gridy = 2;
        
        totCol.gridx = 2;
        totCol.gridy = 2;
        
        securityPanel.add(secWLabel, secCol);
        securityPanel.add(matZField, matCol);
        securityPanel.add(totZField, totCol);
        
        matCol.gridx = 1;
        matCol.gridy = 3;
        
        totCol.gridx = 2;
        totCol.gridy = 3;
        
        securityPanel.add(matWField, matCol);
        securityPanel.add(totWField, totCol);

        gc.weightx = 1.0;
		gc.weighty = 1.0;
		
		gc.gridx = 0;
		gc.gridy = 1;
		
		mainPanel.add(initialPanel, gc);
		
		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		
		gc.gridx = 0;
		gc.gridy = 2;
		
		mainPanel.add(securityPanel, gc);
		
		submitButton = new JButton("SUBMIT");
		submitButton.setFont(new Font("Serif", Font.PLAIN, 30));
		
		gc.weighty = 15.0;
		gc.gridx = 0;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.NORTH;
		
		mainPanel.add(submitButton, gc);
        
		this.add(mainPanel);
	}
}