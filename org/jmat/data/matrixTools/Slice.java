package jmat.data.matrixTools;

import jmat.data.Matrix;
import java.util.*;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * @author Yann RICHET
 * @version 1.0
 */

public class Slice {

	private Matrix M;
	private Matrix slicesCenters;
	private Matrix slicesCardinals;
	private Matrix slicesWidths;

	private int[] elementsIndexes;
	private int[][] slices;

	private int numberSlices;

	/** Construct a Slice from a matrix and an orthogonale slicing.
  @param m    Matrix to slice.
  @param n    Array of number of slices per dimension of the matrix.
  */

	public Slice(Matrix m, int[] n) {
		m.checkColumnDimension(n.length);
		M = m;
		int[] numberSlicesPerDimension = n;
		numberSlices = cumProd(numberSlicesPerDimension);
		buildSlicesBounds(numberSlicesPerDimension);
		buildCardinals();
	}

	/** Construct a Slice from a matrix and a slicing.
  @param m    Matrix to slice.
  @param s    Array of groups indexes which represent each slice.
  */

	public Slice(Matrix m, int[][] s) {
		M = m;
		slices = s;
		numberSlices = slices.length;
		buildIndexes();
		buildSlicesBounds();
	}

	public Matrix getSlicedMatrix() {
		return slicesCenters.mergeColumns(slicesCardinals).mergeColumns(slicesWidths);
	}

	public Matrix getSlicesCardinal() {
		return slicesCardinals;
	}

	public Matrix getSlicesWidths() {
		return slicesWidths;
	}

	public Matrix getSlicesCenters() {
		return slicesCenters;
	}

	public int[] getIndex() {
		return elementsIndexes;
	}

	public int getIndex(int i) {
		return elementsIndexes[i];
	}

	public int[][] getSlices()  {
		return slices;
	}

	public int[] getSlice(int i)  {
		return slices[i];
	}

	/** Method used to build slices centers and widths from a pre-definied slice : int[][] .
	 */

	private void buildSlicesBounds() {

		int numDim = M.getColumnDimension();

		slicesCenters  = new Matrix(numberSlices,numDim);
		slicesWidths  = new Matrix(numberSlices,numDim);

		Matrix Mtmp;
		for (int i = 0; i < slices.length; i++) {
			Mtmp = M.getRows(slices[i]);
			slicesCenters.setColumn(i,(Mtmp.max().plus(Mtmp.min())).divide(2.0));
			slicesWidths.setColumn(i,Mtmp.max().minus(Mtmp.min()));
		}
	}

	/** Method used to build slices centers and widths from a pre-definied slice : int[][] .
	 */

	private void buildIndexes() {
		slicesCardinals = new Matrix(numberSlices,1);
		elementsIndexes = new int[M.getRowDimension()];

		for (int i = 0; i < slices.length; i++) {
			for (int j = 0; j < slices[i].length; j++) {
				slicesCardinals.set(slices[i][j],0,slicesCardinals.get(slices[i][j],0)+1);
				elementsIndexes[slices[i][j]] = i;
			}
		}
	}

	/** Method used to build slices centers and widths from a definied number of slices per dimension.
  @param numberSlicesPerDimension array of number of slices per dimension of the matrix to slice
  */


	private void buildSlicesBounds(int[] numberSlicesPerDimension) {

		int numDim = M.getColumnDimension();

		slicesCenters = new Matrix(numberSlices,numDim);
		slicesWidths = new Matrix(numberSlices,numDim);

		int[] counter = new int[numberSlicesPerDimension.length];
		for (int i = 0; i < this.numberSlices; i++) {
			for (int j = 0; j < numDim; j++) {
				double min = M.getColumn(j).min().toDouble();
				double max = M.getColumn(j).max().toDouble();
				double pitch = (max-min)/numberSlicesPerDimension[j];
				slicesWidths.set(i,j,pitch);
				slicesCenters.set(i,j,min+(counter[j]+0.5)*pitch);
			}
			if (i<(this.numberSlices-1)) {
				incCounter(counter,numberSlicesPerDimension);
			}
		}
	}

	/** Method used to build slices cardinales and indexes from pre-defined slices centers and widths.
	 */

	private void buildCardinals() {
		slicesCardinals = new Matrix(numberSlices,1);
		elementsIndexes = new int[M.getRowDimension()];

		Vector[] slicesVector = new Vector[numberSlices];
		for (int i = 0; i < slicesVector.length; i++) {
			slicesVector[i] = new Vector();
		}

		int numOE = M.getRowDimension();
		for (int i = 0; i < numOE; i++) {
			int I = findPosition(slicesCenters,slicesWidths,M.getRow(i));
			if (I==-1) {
				throw new IllegalArgumentException("Element "+i+" not included in the slicing.");
			}
			slicesCardinals.set(I,0,slicesCardinals.get(I,0)+1);
			elementsIndexes[i] = I;
			slicesVector[I].add(new Integer(i));
		}
		slices = vectorToIntArray(slicesVector);

	}

	/** Convert a vextor array : Vector[] into an int[][]
	 * 	 @param vectorArray vector array to convert
	 * @return inetger array of array : int[][]
	 */

	private int[][] vectorToIntArray(Vector[] vectorArray) {
		int numSlices = vectorArray.length;
		int[][] slices = new int[numSlices][0];
		for (int i = 0; i < numSlices; i++) {
			slices[i] = new int[vectorArray[i].size()];
			for (int j = 0; j < slices[i].length; j++) {
				slices[i][j] = ((Integer)vectorArray[i].get(j)).intValue();
			}
		}
		return slices;
	}

	/** Method used to give the index of the group of an element in 1D.
	 * 	 @param centers Matrix of centers of slices
	 * 	 @param widths Matrix of centers of slices
	 * 	 @param element coordinate to analyse
	 * 	 @return index of the group
	 */

	private static int findPosition(Matrix centers, Matrix widths, double element) {
//		if (centers.getColumnDimension() != 1) {
//			throw new IllegalArgumentException("Number of coordinates must equals 1");
//		}
		for (int i  = 0; i < centers.getRowDimension(); i++) {
			if ((element >= (centers.get(i,0) - widths.get(i,0)/2))&&(element <= (centers.get(i,0) - widths.get(i,0)/2))) {
				return i;
			}
		}
		return -1;
	}

	/** Method used to give the index of the group of an element in xD.
	 * 	 @param centers Matrix of centers of slices
	 * 	 @param widths Matrix of centers of slices
	 * 	 @param element Matrix of coordinate to analyse
	 * 	 @return index of the group
	 */

	private static int findPosition(Matrix centers, Matrix widths, Matrix element) {
//		if (centers.getColumnDimension() != element.getColumnDimension()) {
//			throw new IllegalArgumentException("Number of coordinates must equals " + element.getColumnDimension());
//		}
		for (int i  = 0; i < centers.getRowDimension(); i++) {
			int isIn = 0;
			for (int j = 0; j < centers.getColumnDimension(); j++) {
				if ((element.get(0,j) >= (centers.get(i,j) - widths.get(i,j)))&&(element.get(0,j) <= (centers.get(i,j) + widths.get(i,j)))) {
					isIn = isIn + 1;
				}
			}
//			System.out.println("centre " + i + " : isIn = " + isIn);
			if (isIn == centers.getColumnDimension()) {
				return i;
			}
		}
		return -1;
	}

	/** Returns the cumulative product of an integer array.
	 * 	 @param a array of integer to product
	 * 	 @return a[0]*a[1]*...*a[n]
	 */


	private static int cumProd(int[] a) {
		int res = 1;
		for (int i = 0; i < a.length; i++) {
			res = res * a[i];
		}
		return res;
	}

	/** Returns the cumulative product of an integer array excluding one element of the array.
	 * 	 @param a array of integer to product
	 * 	 @param ind index to ignore
	 * 	 @return a[0]*a[1]*...*a[ind-1]*a[ind+1]*...*a[n]
	 */

	private static int cumProdLess(int[] a, int ind) {
		int res = 1;
		for (int i = 0; i < a.length; i++) {
			res = res * ((i != ind) ? (a[i]) : (1));
		}
		return res;
	}

	/** Incrementation of a counter in a non decimal base.
	 * 	 @param counter counter to increment
	 * 	 @param counterMaxs base of each dimension of the counter
	 */

	private void incCounter(int[] counter,int[] counterMaxs) {
		int decToInc = 0;
		for (int i = 0; i < counter.length; i++) {
			if (counter[i] < counterMaxs[i]-1) {
				decToInc = i;
				break;
			} else {
				decToInc ++;
			}
		}

		counter[decToInc] ++;
		for (int i = 0; i < decToInc; i++) {
			counter[i] = 0;
		}
	}

	public void toCommandLine(String title) {
		System.out.println("Slice "+title+" :");
		for (int i = 0; i < slices.length; i++) {
			System.out.println("  slice "+i+" :");
			for (int j = 0; j < slices[i].length; j++) {
				System.out.print(" "+slices[i][j]);
			}
			System.out.print("\n");
		}
	}

}