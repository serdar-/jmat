package jmat.data.matrixTools;

import jmat.data.Matrix;

	 /** Find a value or a verified condition in a 2D-Array of double.
	 */

public class Find {

/* ------------------------
	 Class variables
 * ------------------------ */

	 /** 2D-Array to test.
	 */
	private Matrix A;

	/** Indices of elements found.
	 */
	private int[][] indices;

	 /** Value to find or to compare.
	 */
	private double value, min, max;

	 /** String of the test.
	 */
	private String test;


/* ------------------------
	 Constructors
 * ------------------------ */

	 /** Find a value.
	 @param a    Double array to test.
	 @param min    Value to compare.
	 @param max    Value to compare.
	 */

	public Find(Matrix a, double min, double max) {
	A = a;
	this.max = max;
	this.min = min;
	indices =  find(A,min,max);
	}

	 /** Find elements verifying a test.
	 @param a    Double array to test.
	 @param t    Test : "==", "<=", ">=", "<", ">", "!=".
	 @param v    Value to compare.
	 */

	public Find(Matrix a, String t, double v) {
	A = a;
	value = v;
	test = t;
	indices =  find(A,test,value);
	}


/* ------------------------
	 Public Methods
 * ------------------------ */

	/** Get the indices verifying the test.
	 @return  2D-indices.
	 */

	public int[][] getIndex() {
	return indices;
	}

	 /** Get the boolean array of the test (true / false).
	 @return  2D-boolean array.
	 */

	public boolean[][] getBooleanArray() {
	int m = A.getRowDimension();
	int n = A.getColumnDimension();
	boolean[][] b = new boolean[m][n];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		b[i][j] = false;
		}
	}
	for (int i = 0; i < indices.length; i++) {
		b[indices[i][0]][indices[i][1]] = true;
	}
	return b;
	}

	 /** Get the double array of the test (1D / 0D).
	 @return  2D-double array of 1 or 0.
	 */

	public Matrix getBinaryMatrix() {
	int m = A.getRowDimension();
	int n = A.getColumnDimension();
	Matrix M = new Matrix(m,n);
	for (int i = 0; i < indices.length; i++) {
		M.set(indices[i][0],indices[i][1],1.0);
	}
	return M;
	}

/* ------------------------
	 Private Methods
 * ------------------------ */

	private int[][] find(Matrix a, double min, double max) {
	int m = a.getRowDimension();
	int n = a.getColumnDimension();
	int[][] ind = new int[0][2];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		if ((a.get(i,j) >= min)&&(a.get(i,j) <= max))
			ind = put(ind,i,j);
		}
	}
	return ind;
	}

	/** Find elements verifying the test.
	 @param a    Double array to test.
	 @param t    String of the test.
	 @param v    Value to test
	 @return  Double array.
	 */

	private int[][] find(Matrix a, String t, double v) {
	if (t.equals("==")) {
		return findEqual(a,v);
				} else if (t.equals("<=")) {
		return findInfEqual(a,v);
	} else if (t.equals(">=")) {
		return findSupEqual(a,v);
	} else if (t.equals("<")) {
		return findInf(a,v);
	} else if (t.equals(">")) {
		return findSup(a,v);
	} else if (t.equals("!=")) {
		return findDiff(a,v);
	} else {
		throw new IllegalArgumentException("Test String " + t + " is unknown.");
	}
	}


	private int[][] findEqual(Matrix a, double v) {
	int m = a.getRowDimension();
	int n = a.getColumnDimension();
	int[][] ind = new int[0][2];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		if (a.get(i,j) == v)
			ind = put(ind,i,j);
		}
	}
	return ind;
	}

	private int[][] findInfEqual(Matrix a, double v) {
	int m = a.getRowDimension();
	int n = a.getColumnDimension();
	int[][] ind = new int[0][2];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		if (a.get(i,j) <= v)
			ind = put(ind,i,j);
		}
	}
	return ind;
	}

	private int[][] findSupEqual(Matrix a, double v) {
	int m = a.getRowDimension();
	int n = a.getColumnDimension();
	int[][] ind = new int[0][2];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		if (a.get(i,j) >= v)
			ind = put(ind,i,j);
		}
	}
	return ind;
	}

	private int[][] findInf(Matrix a, double v) {
	int m = a.getRowDimension();
	int n = a.getColumnDimension();
	int[][] ind = new int[0][2];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		if (a.get(i,j) < v)
			ind = put(ind,i,j);
		}
	}
	return ind;
	}

	private int[][] findSup(Matrix a, double v) {
	int m = a.getRowDimension();
	int n = a.getColumnDimension();
	int[][] ind = new int[0][2];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		if (a.get(i,j) > v)
			ind = put(ind,i,j);
		}
	}
	return ind;
	}

	private int[][] findDiff(Matrix a, double v) {
	int m = a.getRowDimension();
	int n = a.getColumnDimension();
	int[][] ind = new int[0][2];
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		if (a.get(i,j) != v)
			ind = put(ind,i,j);
		}
	}
	return ind;
	}

	private int[][] put(int[][] ind, int i0, int j0) {
	int[][] new_ind = new int[ind.length+1][2];
	for (int i = 0; i < ind.length; i++) {
		for (int j = 0; j < 2; j++) {
		new_ind[i][j] = ind[i][j];
		}
	}
	new_ind[ind.length][0] = i0;
	new_ind[ind.length][1] = j0;
	return new_ind;
	}

}