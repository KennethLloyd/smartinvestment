import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class MainOpt extends JFrame {
	private Container container;
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel smartPanel;
	private JPanel headerPanel;
	private JPanel constHeadPanel;
	private JPanel objPanel;
	private static JPanel constPanel;
	private JPanel buttonPanel;
	private Simplex simplexPanel;
	private Plotter graphPanel;
	
	private JLabel titleLabel;
	private JLabel zLabel;
	private JLabel constLabel;
	
	private static JTextField zField;
	
	private JButton smartButton;
	private JButton constButton;
	private JButton submitButton;
	
	private JScrollPane sp;
	
	private ButtonGroup optRadio;
	private JRadioButton maxButton;
	private JRadioButton minButton;
	
	private static ArrayList<JTextField> lhsList = new ArrayList<JTextField>();
	private static ArrayList<JTextField> rhsList = new ArrayList<JTextField>();
	private static ArrayList<JComboBox> eqList = new ArrayList<JComboBox>();
	
	private static ArrayList<String> allVars = new ArrayList<String>();
	private static ArrayList<Double> zValues = new ArrayList<Double>();
	private static ArrayList<String> zVars = new ArrayList<String>();
	private static ArrayList<ArrayList<Double>> lhsValues = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<String>> lhsVars = new ArrayList<ArrayList<String>>();
	private static ArrayList<Integer> eqMultiplier = new ArrayList<Integer>();
	private static ArrayList<Double> rhsValues = new ArrayList<Double>();
	
	private static int numConstraints;
	private boolean doMaximize = true;
	
	public MainOpt() {
		super("Ultimate Optimizer");
		
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		mainPanel = new JPanel();
		sp = new JScrollPane(mainPanel);
		smartPanel = new SmartInv(cardPanel);
		
		graphPanel = new Plotter(cardPanel);
		JScrollPane gsp = new JScrollPane(graphPanel);
		
		simplexPanel = new Simplex(cardPanel, graphPanel);
		JScrollPane ssp = new JScrollPane(simplexPanel);
		
		mainPanel.setLayout(new GridBagLayout());
		
		headerPanel = new JPanel();
		titleLabel = new JLabel("ULTIMATE OPTIMIZER");
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 50));
		smartButton = new JButton("Go to Smart Investment");
		smartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cardPanel.getLayout());
                cl.show(cardPanel, "SMART");
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
		
		headerPanel.add(smartButton, hgc);
		
		objPanel = new JPanel();
		objPanel.setLayout(new FlowLayout());
		zLabel = new JLabel("Z = ");
		zLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		zField = new JTextField("Input the objective function here...",30);
		zField.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
                zField.setText("");
            }
		});
		objPanel.add(zLabel);
		objPanel.add(zField);
		
		constPanel = new JPanel();
		constPanel.setLayout(new GridBagLayout());
		
		constLabel = new JLabel("CONSTRAINTS");
		constLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		
		constButton = new JButton("Add constraint");
		constButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numConstraints++;
				constPanel.removeAll(); //only for refreshing the ui
				
				String comboItems[] = {"<=",">="};
				lhsList.add(new JTextField(15));
				eqList.add(new JComboBox(comboItems));
				rhsList.add(new JTextField(5));
				
				GridBagConstraints lhsgc = new GridBagConstraints();
	            GridBagConstraints eqgc = new GridBagConstraints();
	            GridBagConstraints rhsgc = new GridBagConstraints();
	            
	            for(int i=0;i<numConstraints;i++)
	            {
	                lhsgc.gridx = 0;
	                lhsgc.gridy = i;
	                
	                eqgc.gridx = 1;
	                eqgc.gridy = i;
	                
	                rhsgc.gridx = 2;
	                rhsgc.gridy = i;

	                constPanel.add(lhsList.get(i), lhsgc);
	                constPanel.add(eqList.get(i), eqgc);
	                constPanel.add(rhsList.get(i), rhsgc);
	            }
	            constPanel.revalidate();
			}
		});
		
		cardPanel.add(sp,"MAIN");
		cardPanel.add(smartPanel,"SMART");
		cardPanel.add(ssp, "RESULTS");
		cardPanel.add(gsp, "GRAPH");
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		mainPanel.add(headerPanel, gc);
		
		gc.weighty = 2.0;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.NORTH;
		
		mainPanel.add(objPanel, gc);
		
		gc.weighty = 15.0;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.NORTH;
		
		constHeadPanel = new JPanel();
		constHeadPanel.setLayout(new GridBagLayout());
		GridBagConstraints chgc = new GridBagConstraints();

		chgc.insets = new Insets(20, 0, 0, 0);
		chgc.weightx = 0.5;
		chgc.weighty = 0.5;
		
		chgc.gridx = 0;
		chgc.gridy = 0;
		
		constHeadPanel.add(constLabel, chgc);
		
		chgc.gridx = 0;
		chgc.gridy = 1;
		
		constHeadPanel.add(constButton, chgc);

		chgc.gridx = 0;
		chgc.gridy = 2;
		
		constHeadPanel.add(constPanel, chgc);
		
		mainPanel.add(constHeadPanel, gc);
		
		buttonPanel = new JPanel();
		maxButton = new JRadioButton("Maximize");
		maxButton.setFont(new Font("Serif", Font.PLAIN, 30));
		maxButton.setSelected(true);
		minButton = new JRadioButton("Minimize");
		minButton.setFont(new Font("Serif", Font.PLAIN, 30));
		optRadio = new ButtonGroup();
		optRadio.add(maxButton);
		optRadio.add(minButton);
		buttonPanel.add(maxButton);
		buttonPanel.add(minButton);
		
		gc.weighty = 15.0;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.NORTH;
		
		mainPanel.add(buttonPanel, gc);
		
		submitButton = new JButton("SUBMIT");
		submitButton.setFont(new Font("Serif", Font.PLAIN, 30));
		submitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				parseObjFxn();
				parseConstraints();
				if (!maxButton.isSelected()) {
					doMaximize = false;
				}
				System.out.println(allVars);
				simplexPanel.setValues(allVars, zValues, zVars, lhsValues, lhsVars, eqMultiplier, rhsValues, doMaximize);
				simplexPanel.startSolving();
				CardLayout cl = (CardLayout)(cardPanel.getLayout());
                cl.show(cardPanel, "RESULTS");
			}
		});
		
		gc.weighty = 15.0;
		gc.gridx = 0;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.NORTH;
		
		mainPanel.add(submitButton, gc);
		
		this.setContentPane(cardPanel);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void parseObjFxn() {
		//parse the objective function
		String objFxn = zField.getText();
		String objNoSpace = objFxn.replaceAll(" ","");
		String[] objToken = objNoSpace.split("(?=[-+])");
		for (int i=0;i<objToken.length;i++) {
			objToken[i] = objToken[i].replaceAll("\\+","");
		}
		ArrayList<String> objTokens = new ArrayList<String>(Arrays.asList(objToken));
		//remove empty elements
		for (int i=0;i<objTokens.size();i++) {
			if (objTokens.get(i).length() == 0) {
				objTokens.remove(i);
			}
		}
		//separate coefficients and variables
		for (String elem: objTokens) {
			boolean hasDigit = false;
			String value;
			String variable;
			for (int j=0;j<elem.length();j++) {
				if (elem.charAt(j) == '-') continue;
				
				if (elem.charAt(j) == '.') continue;

				if (Character.isDigit(elem.charAt(j))) {
					hasDigit = true;
					continue;
				}
				else {
					if (j <= 1) { //no coefficient
						if (j == 0) { //variable already
							value = "1";
						}
						else if (hasDigit) {
							value = elem.substring(0,j);
						}
						else { //sign then variable
							value = "-1";
						}
					}
					else { //with coefficient
						value = elem.substring(0,j);
					}
					variable = elem.substring(j,elem.length());
					zValues.add(-1 * Double.parseDouble(value));
					zVars.add(variable);
					allVars.add(variable);
					break;
				}
			}
		}

	}
	
	public void parseConstraints() {
		parseLHSConst();
		parseEquality();
		parseRHSConst();
	}
	
	public void parseLHSConst() {
		for (JTextField lhsField: lhsList) {
			String lhs = lhsField.getText();
			String lhsNoSpace = lhs.replaceAll(" ","");
			String[] lhsToken = lhsNoSpace.split("(?=[-+])");
			for (int i=0;i<lhsToken.length;i++) {
				lhsToken[i] = lhsToken[i].replaceAll("\\+","");
			}
			ArrayList<String> lhsTokens = new ArrayList<String>(Arrays.asList(lhsToken));
			//remove empty elements
			for (int i=0;i<lhsTokens.size();i++) {
				if (lhsTokens.get(i).length() == 0) {
					lhsTokens.remove(i);
				}
			}
			//separate coefficients and variables
			ArrayList<Double> lhsValue = new ArrayList<Double>();
			ArrayList<String> lhsVar = new ArrayList<String>();
			for (String elem: lhsTokens) {
				String value;
				String variable;
				boolean foundCoeff = false;
				for (int j=0;j<elem.length();j++) {
					if (elem.charAt(j) == '-') continue;
					
					if (elem.charAt(j) == '.') continue;

					if (Character.isDigit(elem.charAt(j))) {
						foundCoeff = true;
						continue;
					}
					if (j <= 1 && foundCoeff == false) { //no coefficient
						if (j == 0) { //variable already
							value = "1";
						}
						else { //sign then variable
							value = "-1";
						}
					}
					else { //with coefficient
						value = elem.substring(0,j);
					}
					variable = elem.substring(j,elem.length());
					lhsValue.add(Double.parseDouble(value));
					lhsVar.add(variable);
					if (!allVars.contains(variable)) {
						allVars.add(variable);
					}
					break;
				}
			}
			lhsValues.add(lhsValue);
			lhsVars.add(lhsVar);
		}
	}
	
	public void parseEquality() {
		for (JComboBox eq: eqList) {
			String selected = (String) eq.getSelectedItem();
			//set multiplier for the slack variables
			if (selected.equals("<=")) {
				eqMultiplier.add(1);
			}
			else {
				eqMultiplier.add(-1);
			}
		}
	}
	
	public void parseRHSConst() {
		for (JTextField rhsField: rhsList) {
			String rhs = rhsField.getText();
			rhsValues.add(Double.parseDouble(rhs));
		}
	}
	
	public static void clearEverything() {
		zField.setText("");
		constPanel.removeAll();
		numConstraints = 0;
		lhsList.clear();
		lhsList.trimToSize();
		eqList.clear();
		eqList.trimToSize();
		rhsList.clear();
		rhsList.trimToSize();
		zValues.clear();
		zValues.trimToSize();
		zVars.clear();
		zVars.trimToSize();
		lhsValues.clear();
		lhsValues.trimToSize();
		lhsVars.clear();
		lhsValues.trimToSize();
		eqMultiplier.clear();
		eqMultiplier.trimToSize();
		rhsValues.clear();
		rhsValues.trimToSize();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainOpt main = new MainOpt();
	}

}
