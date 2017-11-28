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
	private Simplex simplexPanel;
	private Plotter graphPanel;
	
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
	
	private int years;
	private int rows;
	private int cols;
	private double initInvestment;
	private double annualYield;
	private String security[] = {"X","Y","Z","W"};
	private ArrayList<String> constraints;
	private int maturity[] = new int[4];
	private double yield[] = new double[4];
	private double now;
	
	private static ArrayList<String> allVars = new ArrayList<String>();
	private static ArrayList<Double> zValues = new ArrayList<Double>();
	private static ArrayList<String> zVars = new ArrayList<String>();
	private static ArrayList<ArrayList<Double>> lhsValues = new ArrayList<ArrayList<Double>>();
	private static ArrayList<Integer> eqMultiplier = new ArrayList<Integer>();
	private static ArrayList<ArrayList<String>> lhsVars = new ArrayList<ArrayList<String>>();
	private static ArrayList<Double> rhsValues = new ArrayList<Double>();
	private static ArrayList<String> lhsList = new ArrayList<String>();
	private static ArrayList<String> rhsList = new ArrayList<String>();
	
	
	private String arr[][];
	private String objFxn;
	
	public SmartInv(final JPanel cardPanel, final Simplex simplexPanel) {
		this.cardPanel = cardPanel;
		this.simplexPanel = simplexPanel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		graphPanel = new Plotter(cardPanel);
		
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
		submitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//get inputs supplied by the user
				years = Integer.parseInt(yearField.getText());
				initInvestment = Double.parseDouble(initField.getText());
				annualYield = Double.parseDouble(yieldField.getText());
				
				maturity[0] = 1; //initial
				maturity[1] = Integer.parseInt(matYField.getText());
				maturity[2] = Integer.parseInt(matZField.getText());
				maturity[3] = Integer.parseInt(matWField.getText());
				
				//convert to decimals
				yield[0] = annualYield * 0.01;
				yield[1] = Double.parseDouble(thereYField.getText()) * 0.01;
				yield[2] = Double.parseDouble(totZField.getText()) * 0.01;
				yield[3] = Double.parseDouble(totWField.getText()) * 0.01;
				
				now = Double.parseDouble(totYField.getText()) * 0.01;
				
				rows = security.length;
				cols = years;
				arr = new String[rows][cols+1]; //cols+1 for getting the objective function
				constraints = new ArrayList<String>();
				
				createMatrix();
				formConstraints();
				parseObjFxn();
				parseConstraints();
				
				simplexPanel.setValues(allVars, zValues, zVars, lhsValues, lhsVars, eqMultiplier, rhsValues, true);
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
        
		this.add(mainPanel);
	}
	
	public void createMatrix() {
		for (int i=0;i<rows;i++) {
			for (int j=0;j<years;j++) {
				if (i == 1 && j == 2) { //can buy security Y any year but year 3
					continue;
				}
				if (i == 2 && j == 0) { //can buy security Z anytime after the first year
					continue;
				}
				if (maturity[i] + j <= years) { //still possible to mature
					arr[i][j] = security[i] + (j+1);
					if (i == 3) break; ///W is a one-time opportunity 
				}
			}
		}
		
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}
	
	public void formConstraints() {
		for (int i=0;i<cols+1;i++) {
			if (i == 0) { //the first investment
				String eqn = "";
				for (int j=0;j<rows;j++) {
					if (arr[j][i] != null) {
						if (j+1 != rows) { //get all the terms from the matrix which are not null
							eqn += arr[j][i] + "+";
						}
						else { //last security, equate to the initial investment
							eqn += arr[j][i] + " = " + initInvestment;
						}
					}
				}
				constraints.add(eqn);
			}
			else {
				String eqn = "";
				for (int j=0;j<rows;j++) {
					if (arr[j][i] != null) { //get all terms not equal to null
						eqn += arr[j][i] + "+";
					}
					if (j+1 == rows) {
						if (arr[j][i] != null) eqn += arr[j][i]; //all terms has been added
						for (int k=0;k<rows;k++) {
							if (i - maturity[k] >= 0 && arr[k][i-maturity[k]] != null) {
								double interest;
								if (i==2 && k==1) { //first yield of y, use now
									interest = ((initInvestment + (initInvestment * now))/initInvestment);
								}
								else {
									interest = ((initInvestment + (initInvestment * yield[k]))/initInvestment);
								}
								eqn += "-" + interest + arr[k][i-maturity[k]];
							}
							if (k+1 == rows) {
								eqn += " = 0";
							}
						}
					}
				}
				System.out.println(eqn);
				if (i == cols) {
					objFxn = eqn;
					System.out.println(objFxn);
				}
				else constraints.add(eqn);
			}
		}
	}
	
	public void parseObjFxn() {
		//parse the objective function
		objFxn = objFxn.split("=")[0];
		objFxn = objFxn.replace("-", "+"); //there are no negative yields
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
		for (int i=0;i<constraints.size();i++) {
			String constraint = constraints.get(i);
			String tokens[] = constraint.split("=");
			lhsList.add(tokens[0]);
			rhsList.add(tokens[1]);
		}
		parseLHSConst();
		parseEquality();
		parseRHSConst();
	}
	
	public void parseLHSConst() {
		for (String lhs: lhsList) {
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
		for (int i=0;i<years;i++) {
			eqMultiplier.add(1);
		}
	}
	
	public void parseRHSConst() {
		for (String rhs: rhsList) {
			rhsValues.add(Double.parseDouble(rhs));
		}
	}
}