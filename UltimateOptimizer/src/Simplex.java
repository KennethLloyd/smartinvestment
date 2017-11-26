import java.util.ArrayList;


public class Simplex {
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
	
	public Simplex(ArrayList<Double> zValues, ArrayList<String> zVars, ArrayList<ArrayList<Double>> lhsValues, ArrayList<ArrayList<String>> lhsVars, ArrayList<Integer> eqMultiplier, ArrayList<Double> rhsValues, boolean doMaximize) {
		this.zValues = zValues;
		this.zVars = zVars;
		this.lhsValues = lhsValues;
		this.lhsVars = lhsVars;
		this.eqMultiplier = eqMultiplier;
		this.rhsValues = rhsValues;
		this.doMaximize = doMaximize;
		
		this.cols = zVars.size() + eqMultiplier.size() + 2; //num of variables + num of slack variables + z + solution
		this.rows = eqMultiplier.size() + 1; //number of slack variables + z
		this.colNames = new ArrayList<String>();
		this.rowNames = new ArrayList<String>();
		fillColNames();
		fillRowNames();
		
		this.matrix = new ArrayList<ArrayList<Double>>(); //create empty matrix
		for (int i=0;i<rows;i++) {
			matrix.add(new ArrayList<Double>());
		}
		
		setInitialTableu();
	}
	
	public void fillColNames() {
		for (String var: zVars) {
			colNames.add(var);
		}
		for (int i=0;i<eqMultiplier.size();i++) {
			colNames.add("S" + (i+1));
		}
		colNames.add("Z");
		colNames.add("Solution");
		
	}
	
	public void fillRowNames() {
		for (int i=0;i<eqMultiplier.size();i++) {
			rowNames.add("S" + (i+1));
		}
		rowNames.add("Z");
	}
	
	public void setInitialTableu() {	
		int sPos = zVars.size();
		
		for (int i=0;i<lhsValues.size();i++) { //per constraint
			for (int j=0;j<cols;j++) {
				if (zVars.size() > j) { //limit to variable numbers only
					int ind = lhsVars.get(i).indexOf(colNames.get(j));
					if (ind != -1) { //search for occurence
						matrix.get(i).add(j, lhsValues.get(i).get(ind));
					}
					else {
						matrix.get(i).add(j, 0.0);
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
			sPos++;
		}
		
		int zIndex = rows - 1;
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
					matrix.get(zIndex).add(i, 0.0);
				}
			}
		}
		
		for (ArrayList<Double> mat: matrix) {
			System.out.println(mat);
		}
	}
}
