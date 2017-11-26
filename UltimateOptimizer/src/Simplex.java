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
		doSimplexMethod();
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
		int counter = 1;
		System.out.println("Iteration: " + counter);
	    for (ArrayList<Double> mat: matrix) {
	    	System.out.println(mat);
	    }
		while (hasNegative(matrix.get(rows-1))) {
			Double max = 0.0;
			int pivotCol = -1; //as null
			ArrayList<Double> bottomRow = matrix.get(rows-1);
			for (int i=0;i<bottomRow.size()-1;i++) { //get the pivot column by choosing the negative number with the highest magnitude in the bottom row
				if (bottomRow.get(i) < 0 && Math.abs(bottomRow.get(i)) > max) {
					max = Math.abs(bottomRow.get(i));
					pivotCol = i; //save pivot index
				}
			}
			System.out.println("Pivotcol: " + pivotCol);
			Double min = -1.0;
			int minIndex = -1; //index of the smallest positive test ratio
			for (int j=0;j<rows-1;j++) { //exclude the last row
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
			}
			Double pivotElem = matrix.get(minIndex).get(pivotCol); //get the pivot element
			System.out.println("Pivot elem: " + pivotElem);
			
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
		    counter++;
		    System.out.println("Iteration: " + counter);
		    for (ArrayList<Double> mat: matrix) {
		    	System.out.println(mat);
		    }
		}
	}
}
