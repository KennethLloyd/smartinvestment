import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.TableView.TableRow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simplex extends JPanel {
	ArrayList<String> allVars;
	private ArrayList<Double> zValues;
	private ArrayList<String> zVars;
	private ArrayList<ArrayList<Double>> lhsValues;
	private ArrayList<ArrayList<String>> lhsVars;
	private ArrayList<Integer> eqMultiplier;
	private ArrayList<Double> rhsValues;
	private boolean doMaximize;
	private int cols;
	private int rows;
	private ArrayList<String> colNames;
	private ArrayList<String> rowNames;
	private ArrayList<ArrayList<Double>> matrix;
	private ArrayList<JTable> tables;
	private ArrayList<JTable> solTables;
	private String[] cNames;
	private String[] solVars;
	private Double[] sol;
	
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel headerPanel;
	private JPanel tablePanel;
	
	private JLabel titleLabel;
	private JButton ultButton;
	private JButton graphButton;
	
	private TableModel model;
	
	private JTable table;
	
	private GridBagConstraints gc;
	private GridBagConstraints tgc;
	
	private Double mat[][];
	
	private Plotter graphPanel;
	
	public Simplex(final JPanel cardPanel, Plotter graphPanel) {
		this.cardPanel = cardPanel; //get the reference to be able to switch cards
		this.graphPanel = graphPanel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		tables = new ArrayList<JTable>(); //for the tableu
		solTables = new ArrayList<JTable>(); //for the basic solution
		
		headerPanel = new JPanel();
		titleLabel = new JLabel("RESULTS");
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 50));
		ultButton = new JButton("Go to Ultimate Optimizer");
		ultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainOpt.clearEverything();
				CardLayout cl = (CardLayout)(cardPanel.getLayout()); //switch back to ultimate optimizer
                cl.show(cardPanel, "MAIN");
			}
		});
		
		graphButton = new JButton("Show Graph");
		graphButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cardPanel.getLayout()); //switch to graph
				cl.show(cardPanel, "GRAPH");
			}
		});
		headerPanel.setLayout(new GridBagLayout());
		GridBagConstraints hgc = new GridBagConstraints();
		hgc.insets = new Insets(0, 5, 0, 0);
		//layout stuff
		hgc.weightx = 0.5;
		hgc.weighty = 0.5;
		
		hgc.gridx = 0;
		hgc.gridy = 0;
		
		headerPanel.add(titleLabel, hgc);
		
		hgc.weightx = 0.5;
		hgc.weighty = 1.0;
		
		hgc.gridx = 0;
		hgc.gridy = 1;
		
		headerPanel.add(ultButton, hgc);
		
		hgc.weightx = 0.5;
		hgc.weighty = 1.0;
		
		hgc.gridx = 0;
		hgc.gridy = 2;
		
		headerPanel.add(graphButton, hgc);
		
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 5, 20, 0);
		
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		mainPanel.add(headerPanel, gc);
		
		tablePanel = new JPanel();
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 1;
		
		mainPanel.add(tablePanel, gc);
		
		this.add(mainPanel);
	}
	
	public void setValues(ArrayList<String> allVars, ArrayList<Double> zValues, ArrayList<String> zVars, ArrayList<ArrayList<Double>> lhsValues, ArrayList<ArrayList<String>> lhsVars, ArrayList<Integer> eqMultiplier, ArrayList<Double> rhsValues, boolean doMaximize) {
		tables.clear(); //refresh the tables
		solTables.clear();
		//set the passed values
		this.allVars = allVars;
		this.zValues = zValues;
		this.zVars = zVars;
		this.lhsValues = lhsValues;
		this.lhsVars = lhsVars;
		this.eqMultiplier = eqMultiplier;
		this.rhsValues = rhsValues;
		this.doMaximize = doMaximize;
		
		if (!this.doMaximize) { //minimization problem
			for (int i=0;i<zValues.size();i++) {
				Double oldVal = zValues.get(i);
				zValues.set(i, oldVal*-1); //negate the objective function
			}
		}
		
		this.cols = allVars.size() + eqMultiplier.size() + 2; //num of variables + num of slack variables + z + solution
		this.rows = eqMultiplier.size() + 1; //number of slack variables + z
		this.colNames = new ArrayList<String>();
		this.rowNames = new ArrayList<String>();
		fillColNames();
		fillRowNames();
		
		this.matrix = new ArrayList<ArrayList<Double>>(); //create empty matrix
		for (int i=0;i<rows;i++) {
			matrix.add(new ArrayList<Double>());
		}
	}
	
	public void startSolving() {
		//step by step
		setInitialTableu();
		doSimplexMethod();
		renderTables();
	}
	
	public void fillColNames() {
		//get all the variables and treat them as column names
		for (String var: allVars) {
			colNames.add(var);
		}
		for (int i=0;i<eqMultiplier.size();i++) {
			colNames.add("S" + (i+1));
		}
		//add Z and Solution column as the last two columns
		colNames.add("Z");
		colNames.add("Solution");
		
		cNames = new String[colNames.size()]; //store to static array for table
		for (int i=0;i<colNames.size();i++) {
			cNames[i] = colNames.get(i);
		}
		
		solVars = new String[colNames.size()]; //store to static array for basic solution table
		for (int i=0;i<colNames.size()-1;i++) {
			solVars[i] = colNames.get(i);
		}
	}
	
	public void fillRowNames() {
		//get all the slack variables
		for (int i=0;i<eqMultiplier.size();i++) {
			rowNames.add("S" + (i+1));
		}
		rowNames.add("Z");
	}
	
	public void setInitialTableu() {	
		int sPos = allVars.size(); //starting position of the slack variables are right after the variables columns
		
		for (int i=0;i<lhsValues.size();i++) { //per constraint
			for (int j=0;j<cols;j++) {
				if (allVars.size() > j) { //limit to variable numbers only
					int ind = lhsVars.get(i).indexOf(colNames.get(j));
					if (ind != -1) { //search for occurence
						matrix.get(i).add(j, lhsValues.get(i).get(ind));
					}
					else {
						matrix.get(i).add(j, 0.0); //no match
					}
				}
				else if (j == sPos) { //set value for slack variables
					matrix.get(i).add(j, eqMultiplier.get(i).doubleValue());
				}
				else if (j == cols - 1) { //set value for solution
					matrix.get(i).add(j, rhsValues.get(i));
				}
				else {
					matrix.get(i).add(j, 0.0);
				}
			}
			sPos++; //go to next slack variable
		}
		
		int zIndex = rows - 1; //for filling up the row of z
		for (int i=0;i<cols;i++) {
			if (colNames.get(i).equals("Z")) {
				matrix.get(zIndex).add(i, 1.0);
			}
			else {
				int ind = zVars.indexOf(colNames.get(i));
				if (ind != -1) { //search for occurence
					matrix.get(zIndex).add(i, zValues.get(ind));
				}
				else {
					matrix.get(zIndex).add(i, 0.0); //no match
				}
			}
		}
	}
	
	public boolean hasNegative(ArrayList<Double> row) { //check if there are still negative numbers in the bottom row
		for (int i=0;i<row.size();i++) {
			if (row.get(i) < 0) {
				return true;
			}
		}
		return false;
	}
	
	public void doSimplexMethod() {
		mat = new Double[rows][cols]; //static 2d array for rendering tables
		int counter = 1;
		//for initial tableu
		getBasicSolution(); 
		addTables();
		System.out.println("Iteration: " + counter);
	    for (ArrayList<Double> mat: matrix) {
	    	System.out.println(mat);
	    }
		while (hasNegative(matrix.get(rows-1))) { //if there are still negative numbers in the bottom row
			Double max = 0.0;
			int pivotCol = -1; //as null
			ArrayList<Double> bottomRow = matrix.get(rows-1);
			for (int i=0;i<bottomRow.size()-1;i++) { //get the pivot column by choosing the negative number with the highest magnitude in the bottom row
				if (bottomRow.get(i) < 0 && Math.abs(bottomRow.get(i)) > max) {
					max = Math.abs(bottomRow.get(i));
					pivotCol = i; //save pivot index
					System.out.println(pivotCol);
				}
			}
			Double min = -1.0;
			int minIndex = -1; //index of the smallest positive test ratio
			for (int j=0;j<rows-1;j++) { //exclude the last row
				if (matrix.get(j).get(pivotCol) > 0) {
					//a/b where a is the rightmost value in its particular row
					Double testRatio = (matrix.get(j).get(cols-1))/(matrix.get(j).get(pivotCol));
					if (testRatio > 0 && min == -1.0) { //first value of min
						min = testRatio;
						minIndex = j;
					}
					else if (testRatio > 0 && testRatio < min) { //get the smallest positive test ratio
						min = testRatio; //update value of min
						minIndex = j;
					}
					else if (min == -1.0) {
						min = testRatio; //update value of min
						minIndex = j;
					}
				}
			}
			Double pivotElem = matrix.get(minIndex).get(pivotCol); //get the pivot element
			System.out.println("MI: " + minIndex);
			System.out.println(pivotElem);
			
			//perform steps in gauss-jordan
		    for (int i=0;i<cols;i++) {
		    	//do normalization
		    	Double valToBeRounded = matrix.get(minIndex).get(i)/pivotElem;
		    	Double rounded = Math.round(valToBeRounded * 10000.0)/10000.0; //round to four decimal places
		    	matrix.get(minIndex).set(i, rounded);
		    }
		    int pivotRow = minIndex;
		    
		    for (int i=0;i<rows;i++) {
		    	if (i != pivotRow) { //for each row except the normalized row
		    		Double multiplier = matrix.get(i).get(pivotCol);
		    		for (int j=0;j<cols;j++) { //for each column
		    			//current element - (multiplier * value to be eliminated)
		    			Double valToBeRounded = matrix.get(i).get(j) - (multiplier * matrix.get(pivotRow).get(j));
		    			Double rounded = Math.round(valToBeRounded * 10000.0)/10000.0; //round to four decimal places
		    			matrix.get(i).set(j, rounded);
		    		}
		    	}
		    }
		    getBasicSolution();
		    addTables();
		    //next iteration
		    counter++;
		    System.out.println("Iteration: " + counter);
		    for (ArrayList<Double> mat: matrix) {
		    	System.out.println(mat);
		    }
		}
	}
	
	public void getBasicSolution() {
		sol = new Double[colNames.size()-1]; //basic solution columns are equal to the matrix columns minus the solution column
		for (int i=0;i<cols-1;i++) {
			sol[i] = 0.0; //initial values
		}
		
		for (int i=0;i<cols-1;i++) {
			int numZero = 0;
			int numOne = 0;
			int oneIndex = 0;
			for (int j=0;j<rows;j++) {
				//count the numbers of 0s and 1s
				if (matrix.get(j).get(i) == 0.0) {
					numZero++;
				}
				else if (matrix.get(j).get(i) == 1.0) {
					numOne++;
					oneIndex = j;	
				}
			}
			if (numZero == rows-1 && numOne == 1) { //there should only be one 1 and the rest should be zeros
				sol[i] = matrix.get(oneIndex).get(cols-1); //get the value from the solution column using the index of 1
			}
		}
		
		//storage for data
		model = new DefaultTableModel(null,solVars);
	    
	    ((DefaultTableModel) model).addRow(solVars);
	    //add the column names and data
	    ((DefaultTableModel) model).addRow(sol);
	    
	    table = new JTable(model);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment( JLabel.CENTER ); //align center the text
	    TableColumnModel tcm = table.getColumnModel();
	    for (int i=0;i<tcm.getColumnCount();i++) {
	    	tcm.getColumn(i).setPreferredWidth(170);	//adjust the width of all cells
	    	tcm.getColumn(i).setCellRenderer( centerRenderer );
	    }
	    
	    table.setRowHeight(0,30); //adjust the height
	    table.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));
	    solTables.add(table);
	}
	
	public void addTables() { //this is for each iteration
		for (int i=0;i<rows;i++) { //convert arraylist to static 2d array
	    	for (int j=0;j<cols;j++) {
	    		mat[i][j] = (Double) matrix.get(i).get(j);
	    	}
	    }
		
		model = new DefaultTableModel(null, cNames);
	    ((DefaultTableModel) model).insertRow(0, cNames);
	    
	    for (int i=0;i<rows;i++) {
	    	((DefaultTableModel) model).addRow(mat[i]);
	    } 
	    
	    table = new JTable(model);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	    TableColumnModel tcm = table.getColumnModel();
	    for (int i=0;i<tcm.getColumnCount();i++) {
	    	tcm.getColumn(i).setPreferredWidth(170);
	    	tcm.getColumn(i).setCellRenderer( centerRenderer );
	    }
	    
	    table.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));
	    table.setRowHeight(0,30);
	    tables.add(table); //add to the array of tables
	}
	
	public void renderTables() {
		//show tables on the ui
		gc.gridx = 0;
		gc.gridy = 2;
		
		for (int i=0;i<tables.size();i++) {
			gc.weightx = 0.5;
			gc.weighty = 0.5;
			
			
			//arrange the components vertically
			gc.gridy += 1;
			JLabel iterationLabel = new JLabel("Iteration " + (i+1));
			iterationLabel.setFont(new Font("Serif", Font.BOLD, 20));
			mainPanel.add(iterationLabel, gc);
			gc.gridy += 1;
			mainPanel.add(tables.get(i),gc);
			
			gc.gridy += 1;
			JLabel basicSolLabel = new JLabel("Basic Solution");
			basicSolLabel.setFont(new Font("Serif", Font.BOLD, 20));
			mainPanel.add(basicSolLabel, gc);
			gc.gridy += 1;
			mainPanel.add(solTables.get(i),gc);
			
			mainPanel.revalidate();
			//refresh
		}
	}
}
