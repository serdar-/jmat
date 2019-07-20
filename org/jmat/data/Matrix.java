package jmat.data;

import java.io.*;

import jmat.data.matrixDecompositions.*;
import jmat.data.matrixTools.*;
import jmat.function.*;
import jmat.io.*;
import jmat.io.data.*;
import jmat.io.gui.*;
import jmat.io.gui.plotTools.*;

/**
<P>
  The Matrix Class provides the fundamental operations of numerical linear algebra (from the package JAMA),
  basic manipulations, and visualization tools.
  All the operations in this version of the Matrix Class involve only real matrices.

@author Yann RICHET, matrix algebraic operations and decompositions are a copy of the package JAMA (by The MathWorks Inc. and the NIST).
@version 1.0
*/

public class Matrix implements Cloneable, Serializable, CommandLinePrintable, StringPrintable, XMLPrintable, FilePrintable {

/* ------------------------
	Class variables
* ------------------------ */

	/** Array for internal storage of elements.
	 @serial internal array storage.
	 */
	protected double[][] A;

	/** Row and column dimensions.
  @serial row dimension.
  @serial column dimension.
  */
	protected int m, n;

/* ------------------------
	Constructors
* ------------------------ */

	/** Construct an m-by-n matrix of zeros.
	 @param m    Number of rows.
	 @param n    Number of colums.
	 */
	/*T
	 <instructions>Matrix M = new Matrix(3,2);</instructions>
	 <result>M</result>
	 */

	public Matrix (int m, int n) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
	}

	/** Construct an m-by-n constant matrix.
	 @param m    Number of rows.
	 @param n    Number of colums.
	 @param s    Fill the matrix with this scalar value.
	 */
	/*T
	 <instructions>Matrix M = new Matrix(3,2,1.5);</instructions>
	 <result>M</result>
	 */

	public Matrix (int m, int n, double s) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.set(i,j,s);
			}
		}
	}

	/** Construct a matrix from a 2D-array.
	 @param B    Two-dimensional array of doubles.
	 @exception  IllegalArgumentException All rows must have the same length
	 @see        #constructWithCopy
	 */
	/*T
	 <instructions>double[][] B = {{1,2},{3,4},{5,6}};Matrix M = new Matrix(B);</instructions>
	 <result>B</result>
	 <result>M</result>
	 */

	public Matrix (double[][] B) {
		m = B.length;
		n = B[0].length;
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			if (B[i].length != n) {
				throw new IllegalArgumentException("All rows must have the same length : " + n);
			}
			for (int j = 0; j < n; j++) {
				this.set(i,j,B[i][j]);
			}
		}
	}

	/** Construct a matrix from a 2D-array.
	 @param B    Two-dimensional array of doubles.
	 @param m    Number of rows.
	 @param n    Number of columns.
	 @exception  IllegalArgumentException All rows must have the same length
	 @see        #constructWithCopy
	 */
	/*T
	 <instructions>double[][] B = {{1,2},{3,4},{5,6}};Matrix M = new Matrix(B,1,2);</instructions>
	 <result>B</result>
	 <result>M</result>
	 */

	public Matrix (double[][] B, int m, int n) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			if (B[i].length < n) {
				throw new IllegalArgumentException("All rows must have a length >= " + n);
			}
			for (int j = 0; j < n; j++) {
				this.set(i,j,B[i][j]);
			}
		}
	}

	/** Construct a matrix from a one-dimensional packed array
	 @param vals One-dimensional array of doubles, packed by columns (ala Fortran).
	 @param m    Number of rows.
	 @exception  IllegalArgumentException Array length must be a multiple of m.
	 */
	/*T
	 <instructions>double[] vals = {1,2,3,4,5,6};Matrix M = new Matrix(vals,3);</instructions>
	 <result>vals</result>
	 <result>M</result>
	 */

	public Matrix (double vals[], int m) {
		this.m = m;
		n = (m != 0 ? vals.length/m : 0);
		if (m*n != vals.length) {
			throw new IllegalArgumentException("Array length must be a multiple of " + m);
		}
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.set(i,j,vals[i+j*m]);
			}
		}
	}

/* ----------------------
Public Methods
* ---------------------- */

//////////////////////////////////////////
//Static constructors for simple matrix.//
//////////////////////////////////////////

/** Generate matrix with random elements
 @param m    Number of rows.
 @param n    Number of colums.
 @return     An m-by-n matrix with uniformly distributed random elements.
 */
/*T
 <instructions>Matrix M = Matrix.random(3,2);</instructions>
 <result>M</result>
*/

	public static Matrix random(int m, int n) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.random());
			}
		}
		return X;
	}

	/** Generate matrix with only diagonel elements
	 @param d    Diagonal.
	 @return     An m-by-n matrix.
	 */
	/*T
	 <instructions>double[] d = {1,2,3,4,5,6};Matrix M = Matrix.diagonal(d);</instructions>
	 <result>d</result>
	 <result>M</result>
	 */

	public static Matrix diagonal(double[] d) {
		Matrix X = new Matrix(d.length,d.length);
		for (int i = 0; i < d.length; i++) {
			X.set(i,i,d[i]);
		}
		return X;
	}

	/** Generate identity matrix
	 @param m    Number of rows.
	 @param n    Number of colums.
	 @return     An m-by-n matrix with ones on the diagonal and zeros elsewhere.
	 */
	/*T
	 <instructions>Matrix M = Matrix.identity(3,2);</instructions>
	 <result>M</result>
	 */

	public static Matrix identity(int m, int n) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,(i == j ? 1.0 : 0.0));
			}
		}
		return X;
	}

	/** Generate a matrix with a constant pitch beetwen each row
	 @param m    Number of rows.
	 @param n    Number of colums.
	 @param begin    begining value to increment.
	 @param pitch    pitch to add.
	 @return     An m-by-n matrix.
	 */
	/*T
	 <instructions>Matrix M = Matrix.incrementRows(3,2,1.1,0.1);</instructions>
	 <result>M</result>
	 */

	public static Matrix incrementRows(int m, int n,double begin,double pitch) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,begin+i*pitch);
			}
		}
		return X;
	}

	/** Generate a matrix with a constant pitch beetwen each column
	 @param m    Number of rows.
	 @param n    Number of colums.
	 @param begin    begining value to increment.
	 @param pitch    pitch to add.
	 @return     An m-by-n matrix.
	 */
	/*T
	 <instructions>Matrix M = Matrix.incrementColumns(3,2,1.1,0.1);</instructions>
	 <result>M</result>
	 */

	public static Matrix incrementColumns(int m, int n,double begin,double pitch) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,begin+j*pitch);
			}
		}
		return X;
	}

	/** Generate a matrix from other matrix.
	 @param Xs    Matrix to merge.
	 @return     An m1+m2+...-by-n matrix.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = new Matrix(new double[][] {{5,6},{7,8}});Matrix M = Matrix.mergeRows(new Matrix[] {A,B});</instructions>
	 <result>A</result>
	 <result>B</result>
	 <result>M</result>
	 */

	public static Matrix mergeRows(Matrix[] Xs) {
		int n = Xs[0].n;
		int M =0;
		for (int k = 0; k < Xs.length; k++) {
			M = M + Xs[k].m;
		}

		Matrix X = new Matrix(M,n);
		int ind = 0;
		for (int k = 0; k < Xs.length; k++) {
			for (int i = 0; i < Xs[k].m; i++) {
				for (int j = 0; j < n; j++) {
					X.set(i+ind,j,Xs[k].get(i,j));
				}
			}
			ind = ind + Xs[k].m;
		}
		return X;
	}

	/** Generate a matrix from other matrix.
	 @param Xs    Matrix to merge.
	 @return     An m-by-n1+n2+... matrix.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = new Matrix(new double[][] {{5,6},{7,8}});Matrix M = Matrix.mergeColumns(new Matrix[] {A,B});</instructions>
	 <result>A</result>
	 <result>B</result>
	 <result>M</result>
	 */

	public static Matrix mergeColumns(Matrix[] Xs) {
		int m = Xs[0].m;
		int N =0;
		for (int k = 0; k < Xs.length; k++) {
			N = N + Xs[k].n;
		}
		Matrix X = new Matrix(m,N);
		int ind = 0;
		for (int k = 0; k < Xs.length; k++) {
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < Xs[k].n; j++) {
					X.set(i,j+ind,Xs[k].get(i,j));
				}
			}
			ind = ind + Xs[k].n;
		}
		return X;
	}


///////////////////////////////////////////////////////////////////////
//Basic methods to create, convert into array, clone, or copy matrix.//
///////////////////////////////////////////////////////////////////////

/** Clone the Matrix object.
 @return     A matrix Object.
 */

	public Object clone () {
		return this.copy();
	}

	/** Copy the Matrix object.
	 @return     A matrix.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.copy();B.set(1,1,0.5);</instructions>
	 <result>A</result>
	 <result>B</result>
	 */

	public Matrix copy () {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j));
			}
		}
		return X;
	}

	/** Copy the internal two-dimensional array.
	 @return     Two-dimensional array copy of matrix elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[][] b = A.getArrayCopy();b[1][1]=0.5;</instructions>
	 <result>A</result>
	 <result>b</result>
	 */

	public double[][] getArrayCopy () {
		double[][] C = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = this.get(i,j);
			}
		}
		return C;
	}

////////////////////////////////////////////////////////////
//Basic and advanced Get methods for matrix and submatrix.//
////////////////////////////////////////////////////////////

/** Get a single element.
 @param i    Row index.
 @param j    Column index.
 @return     A(i,j)
 @exception  ArrayIndexOutOfBoundsException
 */
/*T
 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double val = A.get(0,1);</instructions>
 <result>A</result>
 <result>val</result>
 */

	public double get (int i, int j) {
		return A[i][j];
	}

	/** Get a several elements in Column.
	 @param IJ    Row index.
	 @return     A(I(:),J(:))
	 @exception  ArrayIndexOutOfBoundsException
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.get(new int[][] {{0,1},{1,1},{1,0}});</instructions>
	 <result>A</result>
	 <result>B</result>
	 */

	public Matrix get (int[][] IJ) {
		checkIndicesLengths(IJ);
		Matrix X = new Matrix(IJ.length,1);
		for (int i = 0; i < IJ.length; i++) {
			X.set(i,0,this.get(IJ[i][0],IJ[i][1]));
		}
		return X;
	}

	/** Get a submatrix.
	 @param i0   Initial row index
	 @param i1   Final row index
	 @param j0   Initial column index
	 @param j1   Final column index
	 @return     A(i0:i1,j0:j1)
	 @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getMatrix(0,1,0,0);</instructions>
	 <result>A</result>
	 <result>B</result>
	 */

	public Matrix getMatrix (int i0, int i1, int j0, int j1) {
		Matrix X = new Matrix(i1-i0+1,j1-j0+1);
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					X.set(i-i0,j-j0,this.get(i,j));
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	/** Copy an internal one-dimensional array from a row.
	 @param i    Row index
	 @return     one-dimensional array copy of matrix elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[] b = A.getRowArrayCopy(1);</instructions>
	 <result>A</result>
	 <result>b</result>
	 */

	public double[] getRowArrayCopy(int i) {
		double[] C = new double[n];
		for (int j = 0; j < n; j++) {
			C[j] = this.get(i,j);
		}
		return C;
	}

	/** Copy an internal one-dimensional array from a row.
	 @param i    Row index
	 @return     one-dimensional array copy of matrix elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getRow(1);</instructions>
	 <result>A</result>
	 <result>B</result>
	 */

	public Matrix getRow (int i) {
		Matrix X = new Matrix(1,n);
		for (int j = 0; j < n; j++) {
			X.set(0,j,this.get(i,j));
		}
		return X;
	}

	/** Copy an internal one-dimensional array from many rows.
	 @param I    Rows indexes
	 @return     one-dimensional array copy of matrix elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getRows(new int[] {1,0});</instructions>
	 <result>A</result>
	 <result>B</result>
	 */

	public Matrix getRows (int[] I) {
		Matrix X = new Matrix(I.length,n);
		for (int i = 0; i < I.length; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(I[i],j));
			}
		}
		return X;
	}

	/** Copy an internal one-dimensional array from a column.
	 @param j    Column index
	 @return     one-dimensional array copy of matrix elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[] b = A.getColumnArrayCopy(1);</instructions>
	 <result>A</result>
	 <result>b</result>
	 */

	public double[] getColumnArrayCopy(int j) {
		double[] C = new double[m];
		for (int i = 0; i < m; i++) {
			C[i] = this.get(i,j);
		}
		return C;
	}

	/** Copy an internal one-dimensional array from a column.
	 @param j    Column index
	 @return     one-dimensional array copy of matrix elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getColumn(1);</instructions>
	 <result>A</result>
	 <result>B</result>
	 */

	public Matrix getColumn(int j) {
		Matrix X = new Matrix(m,1);
		for (int i = 0; i < m; i++) {
			X.set(i,0,this.get(i,j));
		}
		return X;
	}

	/** Copy an internal one-dimensional array from a column.
	 @param J    Columns indexes
	 @return     one-dimensional array copy of matrix elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getColumns(new int[] {1,0});</instructions>
	 <result>A</result>
	 <result>B</result>
	 */

	public Matrix getColumns(int[] J) {
		Matrix X = new Matrix(m,J.length);
		for (int j = 0; j < J.length; j++) {
			for (int i = 0; i < m; i++) {
				X.set(i,j,this.get(i,J[j]));
			}
		}
		return X;
	}

	/** Get row dimension.
	 @return     m, the number of rows.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});int m = A.getRowDimension();</instructions>
	 <result>A</result>
	 <result>m</result>
	 */

	public int getRowDimension () {
		return m;
	}

	/** Get column dimension.
	 @return     n, the number of columns.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});int n = A.getRowDimension();</instructions>
	 <result>A</result>
	 <result>n</result>
	 */

	public int getColumnDimension () {
		return n;
	}

	/** Matrix diagonal extraction.
	 @return     An d*1 Matrix of diagonal elements, d = min(m,n).
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix D = A.getDiagonal();</instructions>
	 <result>A</result>
	 <result>D</result>
	 */

	public Matrix getDiagonal() {
		int d = Math.min(m,n);
		Matrix X = new Matrix(d,1);
		for (int i = 0; i < d; i++) {
			X.set(i,0,this.get(i,i));
		}
		return X;
	}

	/** Matrix diagonal extraction.
	 @param num    diagonal number.
	 @return     Matrix of the n-th diagonal elements.
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix D = A.getDiagonal(1);</instructions>
	 <result>A</result>
	 <result>D</result>
  */

	public Matrix getDiagonal(int num) {
		int l = 0;
		if (n<m) {
			if (num>=0) {
				l = n - num;
			} else if (num<(n-m)) {
				l = m + num;
			} else {
				l = n;
			}
		} else {
			if (num<=0) {
				l = m + num;
			} else if (num>(n-m)) {
				l = n - num;
			} else {
				l = m;
			}
		}
		Matrix X = new Matrix(l,1);
		for (int i = 0; i < l; i++) {
			X.set(i,0,(num>0) ? (this.get(i,i+num)) : (this.get(i-num,i)));
		}
		return X;
	}

////////////////////////////////////////////////////////////
//Basic and advanced Set methods for matrix and submatrix.//
////////////////////////////////////////////////////////////

/** Set a single element.
 @param i    Row index.
 @param j    Column index.
 @param s    A(i,j).
 @exception  ArrayIndexOutOfBoundsException
 */
/*T
 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.set(1,0,0.5);</instructions>
 <result>A</result>
 */

	public void set (int i, int j, double s) {
		A[i][j] = s;
	}

	/** Set several elements.
	 @param IJ    Row index.
	 @param s    A(I(:),J(:)).
	 @exception  ArrayIndexOutOfBoundsException
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.set(new int[][] {{0,1},{1,1}},0.5);</instructions>
	 <result>A</result>
	 */

	public void set (int[][] IJ, double s) {
		checkIndicesLengths(IJ);
		for (int i = 0; i < IJ.length; i++) {
			this.set(IJ[i][0],IJ[i][1],s);
		}
	}

	/** Set a submatrix.
	 @param i0   Initial row index
	 @param j0   Initial column index
	 @param X   subMatrix to set
	 @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0,0},{0,0}});A.setMatrix(0,1,B);</instructions>
	 <result>B</result>
	 <result>A</result>
	 */

	public void setMatrix (int i0, int j0, Matrix X) {
		try {
			for (int i = i0; i < i0+X.m; i++) {
				for (int j = j0; j < j0+X.n; j++) {
					set(i,j,X.get(i-i0,j-j0));
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	/** Set a submatrix.
	 @param IJ0   Initial row indexes
	 @param X   subMatrix to set
	 @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0,0},{0,0}});A.setMatrix(new int[][] {{0,1},{0,0}},B);</instructions>
	 <result>B</result>
	 <result>A</result>
	 */

	public void setMatrix (int[][] IJ0, Matrix X) {
		checkIndicesLengths(IJ0);
		for (int k = 0; k < IJ0.length; k++) {
			int i0 = IJ0[k][0];
			int j0 = IJ0[k][1];
			try {
				for (int i = i0; i < i0+X.m; i++) {
					for (int j = j0; j < j0+X.n; j++) {
						this.set(i,j,X.get(i-i0,j-j0));
					}
				}
			} catch(ArrayIndexOutOfBoundsException e) {
				throw new ArrayIndexOutOfBoundsException("Submatrix indices");
			}
		}
	}

	/** Set a submatrix.
	 @param i0   Initial row index
	 @param i1   Final row index
	 @param j0   Initial column index
	 @param j1   Final column index
	 @param v    Value to set in the submatrix
	 @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.setMatrix(0,1,1,1,0.5);</instructions>
	 <result>A</result>
	 */

	public void setMatrix (int i0, int i1, int j0, int j1, double v) {
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					this.set(i,j,v);
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	/** Copy an internal one-dimensional array from a row.
	 @param i    Row index
	 @param B    Row-matrix
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.5,0.5}});A.setRow(1,B);</instructions>
	 <result>B</result>
	 <result>A</result>
	 */

	public void setRow (int i,Matrix B) {
		B.checkMatrixDimensions(1,n);
		for (int j = 0; j < n; j++) {
			this.set(i,j,B.get(0,j));
		}
	}

	/** Copy an internal one-dimensional array from many rows.
	 @param I    Rows indexes
	 @param B    Rows-matrix
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.5,0.5},{0.6,0.6,0.6}});A.setRows(new int[] {0,1},B);</instructions>
	 <result>B</result>
	 <result>A</result>
	 */

	public void setRows (int[] I,Matrix B) {
		B.checkMatrixDimensions(I.length,n);
		for (int i = 0; i < I.length; i++) {
			for (int j = 0; j < n; j++) {
				this.set(I[i],j,B.get(i,j));
			}
		}
	}

	/** Set a column to an internal one-dimensional Column.
	 @param j    Column index
	 @param B    Column-matrix
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5},{0.5}});A.setColumn(1,B);</instructions>
	 <result>B</result>
	 <result>A</result>
	 */

	public void setColumn(int j,Matrix B) {
		B.checkMatrixDimensions(m,1);
		for (int i = 0; i < m; i++) {
			this.set(i,j,B.get(i,0));
		}
	}

	/** Copy an internal one-dimensional array from a column.
	 @param J    Columns indexes
	 @param B    Columns-matrix
	 */
	/*T
	 <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.6},{0.5,0.6}});A.setColumns(new int[] {0,1},B);</instructions>
	 <result>B</result>
	 <result>A</result>
	 */

	public void setColumns(int[] J,Matrix B) {
		B.checkMatrixDimensions(m,J.length);
		for (int j = 0; j < J.length; j++) {
			for (int i = 0; i < m; i++) {
				this.set(i,J[j],B.get(i,j));
			}
		}
	}


////////////////////////////////
//Modify dimensions of matrix.//
////////////////////////////////

/** Matrix resize.
  @param m2    number of rows
  @param n2    number of columns
  @return    resized matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.resize(2,2);</instructions>
  <result>B</result>
  <result>A</result>
  */

	public Matrix resize(int m2, int n2) {
		Matrix X = new Matrix(m2,n2);
		for (int i = 0; i < m2; i++) {
			for (int j = 0; j < n2; j++) {
				X.set(i,j,((i < m) && (j < n)) ? (A[i][j]) : (0.0));
			}
		}
		return X;
	}

	/** Matrix reshape by Row.
  @param m2    number of rows
  @param n2    number of columns
  @return    reshaped matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.reshapeRows(3,2);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix reshapeRows (int m2, int n2) {
		if ((m2*n2) != (m*n)) {
			throw new IllegalArgumentException("Matrix dimensions products must be equals.");
		}
		Matrix X = new Matrix(m2,n2);
		for (int i = 0; i < m2; i++) {
			for (int j = 0; j < n2; j++) {
				X.set(i,j,this.get((int)(Math.floor((double)(j+n2*i)/n)),(int)((double)(j+n2*i) - n*Math.floor((double)(j+n2*i)/n))));
			}
		}
		return X;
	}

	/** Matrix reshape by Column.
  @param m2    number of rows
  @param n2    number of columns
  @return    reshaped matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.reshapeColumns(3,2);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix reshapeColumns (int m2, int n2) {
		if ((m2*n2) != (m*n)) {
			throw new IllegalArgumentException("Matrix dimensions products must be equals.");
		}
		Matrix X = new Matrix(m2,n2);
		for (int i = 0; i < m2; i++) {
			for (int j = 0; j < n2; j++) {
				X.set(i,j,this.get((int)((i+m2*j) - m*Math.floor((i+m2*j)/m)),(int)(Math.floor((i+m2*j)/m))));
			}
		}
		return X;
	}

	/** Matrix transpose.
  @return    A'
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.transpose();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix transpose () {
		Matrix X = new Matrix(n,m);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(j,i,this.get(i,j));
			}
		}
		return X;
	}

	/** Matrix merge.
  @param B    matrix to merge
  @return     An m.B+m-by-n matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{7,8,9},{10,11,12}});Matrix M = A.mergeRows(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>M</result>
  */

	public Matrix mergeRows(Matrix B) {
		checkColumnDimension(B);
		Matrix X = new Matrix(m+B.m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j));
			}
		}
		for (int i = 0; i < B.m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i+m,j,B.get(i,j));
			}
		}
		return X;
	}

	/** Matrix merge.
  @param B    matrix to merge
  @return     An m-by-n+B.n matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{7,8,9},{10,11,12}});Matrix M = A.mergeColumns(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>M</result>
  */

	public Matrix mergeColumns(Matrix B) {
		checkRowDimension(B);
		Matrix X = new Matrix(m,n+B.n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j));
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < B.n; j++) {
				X.set(i,j+n,B.get(i,j));
			}
		}
		return X;
	}


	/** Insert a Row into Matrix.
  @param I    first row to add indice
  @return     An m+1-by-n matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.insertRow(1);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix insertRow(int I) {
		if (I > m) {
			throw new IllegalArgumentException("Matrix Rows dimensions must be > " + I);
		}
		Matrix X = new Matrix(m+1,n);
		int i = 0;
		for (int i2 = 0; i2 < m+1; i2++) {
			if (i2==I) {
				for (int j = 0; j < n; j++) {
					X.set(i2,j,0.0);
				}
			} else {
				for (int j = 0; j < n; j++) {
					X.set(i2,j,this.get(i,j));
				}
				i ++;
			}
		}
		return X;
	}

	/** Insert a Column into Matrix.
  @param J    first column to add indice
  @return     An m-by-n+1 matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.insertColumn(1);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix insertColumn(int J) {
		if (J > n) {
			throw new IllegalArgumentException("Matrix Columns dimensions must be > " + J);
		}
		Matrix X = new Matrix(m,n+1);
		int j = 0;
		for (int j2 = 0; j2 < n+1; j2++) {
			if (j2==J) {
				for (int i = 0; i < m; i++) {
					X.set(i,j2,0.0);
				}
			} else {
				for (int i = 0; i < m; i++) {
					X.set(i,j2,this.get(i,j));
				}
				j ++;
			}
		}
		return X;
	}

	/** delete a Row to Matrix.
  @param I    row number to delete
  @return     An m-1-by-n matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});Matrix B = A.deleteRow(1);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix deleteRow(int I) {
		if (I > m) {
			throw new IllegalArgumentException("Matrix Rows dimensions must be > " + I);
		}
		Matrix X = new Matrix(m-1,n);
		int i = 0;
		for (int i2 = 0; i2 < m-1; i2++) {
			if (i==I) {
				i++;
			}
			for (int j = 0; j < n; j++) {
				X.set(i2,j,this.get(i,j));
			}
			i++;
		}
		return X;
	}

	/** Delete a Column to Matrix.
  @param J    column number to delete
  @return     An m-by-n-1 matrix
  */
 /*T
  <instructions>Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});Matrix B = A.deleteColumn(1);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix deleteColumn(int J) {
		if (J > n) {
			throw new IllegalArgumentException("Matrix Columns dimensions must be > " + J);
		}
		Matrix X = new Matrix(m,n-1);
		int j = 0;
		for (int j2 = 0; j2 < n-1; j2++) {
			if (j==J) {
				j ++;
			}
			for (int i = 0; i < m; i++) {
				X.set(i,j2,this.get(i,j));
			}
			j ++;
		}
		return X;
	}


////////////////////////////////////////
//Norms and characteristics of Matrix.//
////////////////////////////////////////

/** One norm
  @return    maximum column sum.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.norm1();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public double norm1 () {
		double f = 0;
		for (int j = 0; j < n; j++) {
			double s = 0;
			for (int i = 0; i < m; i++) {
				s = s + Math.abs(this.get(i,j));
			}
			f = Math.max(f,s);
		}
		return f;
	}

	/** Two norm
  @return    maximum singular value.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.norm2();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public double norm2 () {
		return (new SingularValueDecomposition(this).norm2());
	}

	/** Infinity norm
  @return    maximum row sum.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.normInfinity();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public double normInfinity () {
		double f = 0;
		for (int i = 0; i < m; i++) {
			double s = 0;
			for (int j = 0; j < n; j++) {
				s = s + Math.abs(this.get(i,j));
			}
			f = Math.max(f,s);
		}
		return f;
	}

	/** Frobenius norm
  @return    sqrt of sum of squares of all elements.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.normFrobenius();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public double normFrobenius () {
		double f = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				f = Mathfun.hypot(f,this.get(i,j));
			}
		}
		return f;
	}

	/** Matrix determinant
  @return     determinant
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.determinant();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public double determinant() {
		return new LUDecomposition(this).det();
	}

	/** Matrix rank
  @return     effective numerical rank, obtained from SVD.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.rank();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public int rank() {
		return new SingularValueDecomposition(this).rank();
	}

	/** Matrix condition (2 norm)
  @return     ratio of largest to smallest singular value.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.condition();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public double condition() {
		return new SingularValueDecomposition(this).cond();
	}

	/** Matrix trace.
  @return     sum of the diagonal elements.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);double n = A.trace();</instructions>
  <result>A</result>
  <result>n</result>
  */

	public double trace() {
		double t = 0;
		for (int i = 0; i < Math.min(m,n); i++) {
			t  = t + this.get(i,i);
		}
		return t;
	}

	/** Generate a row matrix, each column contents the minimum value of the columns.
  @return     An 1-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.min();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix min() {
		Matrix X = new Matrix(1,n);
		for (int j = 0; j < n; j++) {
			double minval = get(0,j);
			for (int i = 0; i < m; i++)
	   minval = Math.min(minval,this.get(i,j));
   X.set(0,j,minval);
		}
		return X;
	}

	/** Generate a row matrix, each column contents the maximum value of the columns.
  @return     An 1-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.max();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix max() {
		Matrix X = new Matrix(1,n);
		for (int j = 0; j < n; j++) {
			double maxval = A[0][j];
			for (int i = 0; i < m; i++)
	   maxval = Math.max(maxval,this.get(i,j));
   X.set(0,j,maxval);
		}
		return X;
	}


	/** Generate a row matrix, each column contents the sum value of the columns.
  @return     An 1-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.sum();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix sum() {
		Matrix X = new Matrix(1,n);
		double s;
		for (int j = 0; j < n; j++) {
			s = 0;
			for (int i = 0; i < m; i++) {
				s = s + this.get(i,j);
			}
			X.set(0,j,s);
		}
		return X;
	}


	/** Generate a row matrix, each column contents the product value of the columns.
  @return     An 1-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.product();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix product() {
		Matrix X = new Matrix(1,n);
		for (int j = 0; j < n; j++) {
			double p = 1;
			for (int i = 0; i < m; i++) {
				p = p * this.get(i,j);
			}
			X.set(0,j,p);
		}
		return X;
	}

	/** Generate a matrix, each column contents the N-distance between the columns.
  @param pow    N
  @param B    Matrix
  @return     An m-by-B.m matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.distance(Matrix.random(4,4),2);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix distance(Matrix B, double pow) {
		checkColumnDimension(B);
		Matrix X = new Matrix(m,B.m);
		for (int i = 0; i < m; i++) {
			for (int k = 0; k < B.m; k++) {
				double s = 0;
				for (int j = 0; j < n; j++) {
					s = s + Math.pow(this.get(i,j) - B.get(k,j),pow);
				}
				X.set(i,k,Math.pow(s,1/pow));
			}
		}
		return X;
	}

	/** Generate a row matrix, each column contents the mean value of the columns.
  @return     An 1-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.mean();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix mean() {
		Matrix X = new Matrix(1,n);
		double s ;
		for (int j = 0; j < n; j++) {
			s = 0;
			for (int k = 0; k < m; k++) {
				s = s + this.get(k,j);
			}
			X.set(0,j,s/m);
		}
		return X;
	}

	/** Generate a covariance matrix, each column contains values of a pulling.
  @return     An n-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.covariance();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix covariance() {
		Matrix X = new Matrix(n,n);
		int degrees = (m-1);
		double c ;
		double s1 ;
		double s2 ;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c = 0;
				s1 = 0;
				s2 = 0;
				for (int k = 0; k < m; k++) {
					s1 = s1 + this.get(k,i);
					s2 = s2 + this.get(k,j);
				}
				s1 = s1/m;
				s2 = s2/m;
				for (int k = 0; k < m; k++) {
					c = c + (this.get(k,i) - s1)*(this.get(k,j) - s2);
				}
				X.set(i,j,c/degrees);
			}
		}
		return X;
	}

	/** Generate a correlation matrix, each column contains values of a pulling.
  @return     An n-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.correlation();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix correlation() {
		Matrix X = new Matrix(n,n);
		int degrees =(m-1);
		Matrix V = new Matrix(n,n);
		double c ;
		double s1 ;
		double s2 ;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c = 0;
				s1 = 0;
				s2 = 0;
				for (int k = 0; k < m; k++) {
					s1 = s1 + this.get(k,i);
					s2 = s2 + this.get(k,j);
				}
				s1 = s1/m;
				s2 = s2/m;
				for (int k = 0; k < m; k++) {
					c = c + (this.get(k,i) - s1)*(this.get(k,j) - s2);
				}
				V.set(i,j,c/degrees);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,V.get(i,j)/Math.sqrt(V.get(i,i)*V.get(j,j)));
			}
		}
		return X;
	}

	/** Generate a variance matrix, each column contains values of a pulling.
  @return     An 1-by-n matrix.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.variance();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix variance() {
		Matrix X = new Matrix(1,n);
		int degrees = (m-1);
		double c ;
		double s ;
		for (int j = 0; j < n; j++) {
			c = 0;
			s = 0;
			for (int k = 0; k < m; k++) {
				s = s + this.get(k,j);
			}
			s = s/m;
			for (int k = 0; k < m; k++) {
				c = c + (this.get(k,j) - s)*(this.get(k,j) - s);
			}
			X.set(0,j,c/degrees);
		}
		return X;
	}

///////////////////////////////////
//Algebraic Functions for Matrix.//
///////////////////////////////////

/**  Unary minus
  @return    -A
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.uminus();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix uminus () {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,-this.get(i,j));
			}
		}
		return X;
	}

	/** C = A + B
  @param B    another matrix
  @return     A + B
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.plus(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix plus (Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j) + B.get(i,j));
			}
		}
		return X;
	}

	/** C = A - B
  @param B    another matrix
  @return     A - B
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.minus(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix minus (Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j) - B.get(i,j));
			}
		}
		return X;
	}

	/** Multiply a matrix by a scalar, C = s*A
  @param s    scalar
  @return     s*A
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.times(2);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix times (double s) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,s*this.get(i,j));
			}
		}
		return X;
	}

	/** Linear algebraic matrix multiplication, A * B
  @param B    another matrix
  @return     Matrix product, A * B
  @exception  IllegalArgumentException Matrix inner dimensions must agree.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.times(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix times (Matrix B) {
		if (B.m != n) {
			throw new IllegalArgumentException("Matrix inner dimensions must agree.");
		}
		Matrix X = new Matrix(m,B.n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < B.n; j++) {
				double s = 0;
				for (int k = 0; k < n; k++) {
					s = s + this.get(i,k) * B.get(k,j);
				}
				X.set(i,j,s);
			}
		}
		return X;
	}

	/** Divide a matrix by a scalar, C = A/s
  @param s    scalar
  @return     A/s
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.divide(2);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix divide(double s) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j)/s);
			}
		}
		return X;
	}

	/** Linear algebraic matrix division, A / B
  @param B    another matrix
  @return     Matrix division, A / B
  @exception  IllegalArgumentException Matrix inner dimensions must agree.
  @exception  IllegalArgumentException Matrix inner dimensions must agree.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.divide(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix divide(Matrix B) {
		if (B.m != n) {
			throw new IllegalArgumentException("Matrix inner dimensions must agree.");
		}
		return this.times(B.inverse());
	}

	/** Solve A*X = B
  @param B    right hand side
  @return     solution if A is square, least squares solution otherwise
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,1);Matrix C = A.solve(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix solve (Matrix B) {
//		Problem in JAMA!!!
//		the LU Decomposition doesn't solve L*U = A, but L*U = P*A,
//		so if you use X = new LUDecomposition(A)).solve(B)
//		you don't have A*X = B but P*A*X = B...
//		So I decided to use QR Decomposition even if m = n, it's less precise but works well.
//		Here is the JMAT version :
//		return (m == n ? (new LUDecomposition(this)).solve(B) :
//				 (new QRDecomposition(this)).solve(B));
//		And now here is mine :
		return new QRDecomposition(this).solve(B);

	}

	/** Matrix inverse or pseudoinverse
  @return     inverse(A) if A is square, pseudoinverse otherwise.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.inverse();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix inverse () {
		return solve(identity(m,m));
	}


/////////////////////////////////////////////
//Element-by-elements Functions for Matrix.//
/////////////////////////////////////////////

/** Add a scalar to each element of a matrix, C = A .+ s
  @param s    double
  @return     A .+ s
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.plus(1);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix plus (double s) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j) + s);
			}
		}
		return X;
	}

	/** Sub a scalar to each element of a matrix, C = A .- B
  @param s    double
  @return     A .- s
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.minus(1);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix minus (double s) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j) - s);
			}
		}
		return X;
	}


	/** Element-by-element multiplication, C = A.*B
  @param B    another matrix
  @return     A.*B
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebeTimes(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix ebeTimes (Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j) * B.get(i,j));
			}
		}
		return X;
	}

	/** Element-by-element right division, C = A./B
  @param B    another matrix
  @return     A./B
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebeDivide(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix ebeDivide (Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,this.get(i,j) / B.get(i,j));
			}
		}
		return X;
	}

	/**Element-by-element cosinus
  @return     cos.(A)
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeCos();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeCos() {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.cos(this.get(i,j)));
			}
		}
		return X;
	}

	/**Element-by-element sinus
  @return     sin.(A)
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeSin();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeSin() {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.sin(this.get(i,j)));
			}
		}
		return X;
	}

	/**Element-by-element exponential
  @return     exp.(A)
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeExp();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeExp() {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.exp(this.get(i,j)));
			}
		}
		return X;
	}

	/**Element-by-element power
  @param p    double
  @return     A.^p
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebePow(2);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebePow(double p) {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.pow(this.get(i,j),p));
			}
		}
		return X;
	}

	/**Element-by-element power
  @param B    another matrix
  @return     A.^B
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebePow(B);</instructions>
  <result>A</result>
  <result>B</result>
  <result>C</result>
  */

	public Matrix ebePow(Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.pow(this.get(i,j),B.get(i,j)));
			}
		}
		return X;
	}

	/**Element-by-element neperian logarithm
  @return     log.(A)
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeLog();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeLog() {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.log(this.get(i,j)));
			}
		}
		return X;
	}

	/**Element-by-element inverse
  @return     A.^-1
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeInverse();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeInverse() {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,1/(this.get(i,j)));
			}
		}
		return X;
	}

	/**Square root of each element
  @return     sqrt.(A)
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeSqrt();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeSqrt() {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.sqrt(this.get(i,j)));
			}
		}
		return X;
	}

	/**Absolute value of each element
  @return     |A|
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4).minus(0.5);Matrix B = A.abs();</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix abs() {
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X.set(i,j,Math.abs(this.get(i,j)));
			}
		}
		return X;
	}

	/**Element-by-element function evaluation
  @param fun    function to apply : f(Aij)
  @return     f.(A)
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeFunction(new DoubleFunctionExpression("log(1/x)","x"));</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeFunction(DoubleFunction fun) {
		fun.checkArgNumber(1);
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
//				double[] arg = {get(i,j)};
				X.set(i,j,fun.eval(new Matrix(1,1,get(i,j))));
			}
		}
		return X;
	}

	/**Element-by-element indicial function evaluation
  @param fun    function to apply : f(Aij,i,j)
  @return     f.(A)
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Matrix B = A.ebeIndicesFunction(new DoubleFunctionExpression("x+i+j",new String[] {"x","i","j"}));</instructions>
  <result>A</result>
  <result>B</result>
  */

	public Matrix ebeIndicesFunction(DoubleFunction fun) {
		fun.checkArgNumber(3);
		Matrix X = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				double[] args = {get(i,j),i,j};
				X.set(i,j,fun.eval(new Matrix(args,1)));
			}
		}
		return X;
	}


///////////////////////////////////////////////
//Advanced Decompositions methods for Matrix.//
///////////////////////////////////////////////

/** LU Decomposition
  @return     LUDecomposition
  @see LUDecomposition
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);LUDecomposition lu = A.lu();</instructions>
  <result>lu.getL()</result>
  <result>lu.getU()</result>
  */

	public LUDecomposition lu () {
		return new LUDecomposition(this);
	}

	/** QR Decomposition
  @return     QRDecomposition
  @see QRDecomposition
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);QRDecomposition qr = A.qr();</instructions>
  <result>qr.getQ()</result>
  <result>qr.getR()</result>
  */

	public QRDecomposition qr () {
		return new QRDecomposition(this);
	}

	/** Cholesky Decomposition
  @return     CholeskyDecomposition
  @see CholeskyDecomposition
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);CholeskyDecomposition c = A.cholesky();</instructions>
  <result>c.getL()</result>
  */

	public CholeskyDecomposition cholesky () {
		return new CholeskyDecomposition(this);
	}

	/** Singular Value Decomposition
  @return     SingularValueDecomposition
  @see SingularValueDecomposition
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);SingularValueDecomposition svd = A.svd();</instructions>
  <result>svd.getS()</result>
  <result>svd.getV()</result>
  <result>svd.getU()</result>
  */

	public SingularValueDecomposition svd () {
		return new SingularValueDecomposition(this);
	}

	/** Eigenvalue Decomposition
  @return     EigenvalueDecomposition
  @see EigenvalueDecomposition
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);EigenvalueDecomposition e = A.eig();</instructions>
  <result>e.getV()</result>
  <result>e.getD()</result>
  */

	public EigenvalueDecomposition eig () {
		return new EigenvalueDecomposition(this);
	}


/////////////////////////////////////////
//Advanced functions like sort or find.//
/////////////////////////////////////////

/** Generate a row-permuted matrix, rows are permuted in order to sort the column 'c'
  @param j    Number of the colum which leads the permuation
  @return     A Sort.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Sort s = A.sort(0);</instructions>
  <result>s.getIndex()</result>
  <result>s.getSortedMatrix()</result>
  */

	public Sort sort(int j) {
		return new Sort(this,j);
	}

	/** Find an element
  @param min    Inf bound of values to find
  @param max    Sup bound of values to find
  @return     A Find.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Find f = A.find(0.25,0.5);</instructions>
  <result>f.getBinaryMatrix()</result>
  <result>f.getIndex()</result>
  */

	public Find find(double min,double max) {
		return new Find(this,min,max);
	}

	/** Find an element
  @param test    Test
  @param val    Element (value) to compare
  @return     A Find.
  */
 /*T
  <instructions>Matrix A = Matrix.random(4,4);Find f = A.find("<=",0.5);</instructions>
  <result>f.getBinaryMatrix()</result>
  <result>f.getIndex()</result>
  */

	public Find find(String test,double val) {
		return new Find(this,test,val);
	}

	/** Slice the matrix
  @param n    Array of number of slices for each column.
  @return     A Slice.
  */
 /*T
  <instructions>Matrix A = Matrix.random(10,4);Slice s = A.slice(new int[] {2,3,5,2});</instructions>
  <result>s.getSlicedMatrix()</result>
  */

	public Slice slice(int[] n) {
		return new Slice(this,n);
	}

	/** Slice the matrix
@param n    Number of slices for each column.
@return     A Slice.
*/
/*T
<instructions>Matrix A = Matrix.random(10,4);Slice s = A.slice(3);</instructions>
<result>s.getSlicedMatrix()</result>
*/

	public Slice slice(int n) {
		int[] N = new int[getColumnDimension()];
		for (int i = 0; i < getColumnDimension(); i++) {
			N[i] = n;
		}
		return new Slice(this,N);
	}

/////////////////////////////////////////////////////////
//Matrix io methods, in panels, frames or command line.//
/////////////////////////////////////////////////////////

/** convert the Matrix into a double value if the matrix is 1*1.
  @return A(0,0).
  */
 /*T
  <instructions>Matrix A = Matrix.random(1,1);double d = A.toDouble();</instructions>
  <result>A</result>
  <result>d</result>
  */

	public double toDouble() {
		checkMatrixDimensions(1,1);
		return this.get(0,0);
	}

	/** Convert the Matrix into a String
  @return      A String
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);String s = A.toString();</instructions>
  <result>A</result>
  <result>s</result>
  */

	public String toString() {
		String s = MatrixString.printMatrix(this);
		return s;
	}

	/** Convert the Matrix into a String
  @param delimiter    delimiter of colmumns.
  @return      A String
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);String s = A.toString('|');</instructions>
  <result>A</result>
  <result>s</result>
  */

	public String toString(char delimiter) {
		String s = MatrixString.printMatrix(this,delimiter);
		return s;
	}

	/** Convert the Matrix into a MathML (XML) Element
  @return      An XML Element <matrix>
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();</instructions>
  <result>A</result>
  <result>e.toString()</result>
  */

	public org.jdom.Element toXMLElement() {
		org.jdom.Element e = MatrixMathML.printMatrix(this);
		return e;
	}

	/** Save the Matrix in a file.
  @param file    file to save in.
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);A.toFile(new java.io.File("A.txt"));</instructions>
  <result>A</result>
  */

	public void toFile(java.io.File file) {
		MatrixFile.printMatrix(file,this);
	}

	/** Print the Matrix in the Command Line.
  @param title    title to display in the command line.
  */

	public void toCommandLine(String title) {
		System.out.println(title + " =");
		System.out.println(toString());
	}

	/** Print the Matrix in a JPanel.
  @return    A JPanel.
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);MatrixTable mt = A.toTablePanel();</instructions>
  <result>mt</result>
  */

	public MatrixTable toTablePanel() {
		return new MatrixTable(this);
	}

	/** Print the Matrix in a JFrame.
  @param title    title of the JFrame.
  @return    A JFrame.
  */

	public MatrixTable toTableFrame(String title) {
		MatrixTable t = toTablePanel();
		new FrameView(title,t);
		return t;
	}

	/** Print the Matrix in a JPanel.
  @param name    name of the plot.
  @param type    type of displaying.
  @return    A JPanel.
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,2);Plot2DPanel pp = A.toPlot2DPanel("A",0);</instructions>
  <result>pp</result>
  */

	public Plot2DPanel toPlot2DPanel(String name,int type) {
		return new Plot2DPanel(this,name,type);
	}

	/** Print the Matrix in a JFrame.
  @param title    title of the JFrame.
  @param name    name of the plot.
  @param type    type of displaying.
  @return    A JFrame.
  */

	public Plot2DPanel toPlot2DFrame(String title,String name,int type) {
		Plot2DPanel p = toPlot2DPanel(name,type);
		new FrameView(title,p);
		return p;
	}

	/** Print the Matrix in a JPanel.
  @param type    type of displaying.
  @param name    name of the plot.
  @return    A JPanel.
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,3);Plot3DPanel pp = A.toPlot3DPanel("A",1);</instructions>
  <result>pp</result>
  */

	public Plot3DPanel toPlot3DPanel(String name,int type) {
		return new Plot3DPanel(this,name,type);
	}

	/** Print the Matrix in a JFrame.
  @param title    title of the JFrame.
  @param name    name of the plot.
  @param type    type of displaying.
  @return    A JFrame.
  */

	public Plot3DPanel toPlot3DFrame(String title,String name,int type) {
		Plot3DPanel p = toPlot3DPanel(name,type);
		new FrameView(title,p);
		return p;
	}

	/** Print the Matrix in a JPanel.
  @param panel    Panel to modify.
  @param name    name of the plot.
  @param type    type of displaying.
  */
 /*T
  <instructions>Matrix A = Matrix.random(10,2);Matrix B = Matrix.random(10,2);Plot2DPanel pp = A.toPlot2DPanel("A",2);B.addToPlot2DPanel(pp,"B",0);</instructions>
  <result>pp</result>
  */

	public void addToPlot2DPanel(Plot2DPanel panel,String name,int type) {
		panel.addPlot(this,name,type);
	}

	/** Print the Matrix in a JPanel.
  @param panel    Panel to modify.
  @param name    name of the plot.
  @param type    type of displaying.
  */
 /*T
  <instructions>Matrix A = Matrix.random(10,3);Matrix B = Matrix.random(10,3);Plot3DPanel pp = A.toPlot3DPanel("A",2);B.addToPlot3DPanel(pp,"B",0);</instructions>
  <result>pp</result>
  */

	public void addToPlot3DPanel(Plot3DPanel panel,String name,int type) {
		panel.addPlot(this,name,type);
	}

	/** Load the Matrix from a file
  @param file    file to load
  @return      A matrix
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);A.toFile(new java.io.File("A.txt"));Matrix B = Matrix.fromFile(new java.io.File("A.txt"));</instructions>
  <result>A</result>
  <result>B</result>
  */

	public static Matrix fromFile(java.io.File file) {
		Matrix m = MatrixFile.readMatrix(file);
		return m;
	}

	/** Load the Matrix from a String
  @param s    String to load
  @return      A matrix
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);String s = A.toString();Matrix B = Matrix.fromString(s);</instructions>
  <result>A</result>
  <result>B</result>
  */

	public static Matrix fromString(String s) {
		Matrix m = MatrixString.readMatrix(s);
		return m;
	}

	/** Load the Matrix from a MathML (XML) Element
  @param e    Element <matrix> to load
  @return      A matrix
  */
 /*T
  <instructions>Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();Matrix B = Matrix.fromXMLElement(e);</instructions>
  <result>A</result>
  <result>B</result>
  */


	public static Matrix fromXMLElement(org.jdom.Element e) {
		Matrix m = MatrixMathML.readMatrix(e);
		return m;
	}

/////////////////////////////////////
//Matrix Test and checking methods.//
/////////////////////////////////////

/** Test if A == B.
  @param B    Matrix to compare.
  @return boolean
  */

	public boolean isEqual(Matrix B) {
		this.checkMatrixDimensions(B);
		for (int i = 0; i < m; i++)
	  for (int j = 0; j < n; j++)
	   if (this.get(i,j)!=B.get(i,j))
		return false;
  return true;
	}

	/** Test if A is Diagonal.
  @return boolean
  */

	public boolean isDiagonal() {
		for (int i = 0; i < m; i++)
	  for (int j = 0; j < n; j++)
	   if ((this.get(i,j)!=0)&&(i!=j))
		return false;
  return true;
	}

	/** Check if A is Diagonal.
	 */

	public void checkDiagonal() {
		for (int i = 0; i < m; i++)
	  for (int j = 0; j < n; j++)
	   if ((this.get(i,j)!=0)&&(i!=j))
		throw new IllegalArgumentException("Matrix is not diagonal : element ("+i+","+j+") is not = 0.");
	}

	/** Test if A is Symetric.
  @return boolean
  */

	public boolean isSymetric() {
		this.checkColumnDimension(m);
		for (int i = 0; i < m; i++)
	  for (int j = i+1; j < n; j++)
	   if (this.get(i,j)!=this.get(j,i))
		return false;
  return true;
	}

	/** Check if A is Symetric.
	 */

	public void checkSymetric() {
		this.checkColumnDimension(m);
		for (int i = 0; i < m; i++)
	  for (int j = i+1; j < n; j++)
	   if (this.get(i,j)!=this.get(j,i))
		throw new IllegalArgumentException("Matrix is not symetrix at ("+i+","+j+").");
	}

	/** Check if size(A) == size(B).
  @param B    Matrix to test.
  */

	public void checkMatrixDimensions (Matrix B) {
		if (B.m != m || B.n != n) {
			throw new IllegalArgumentException("Matrix dimensions must equals " + B.m + " x " + B.n);
		}
	}

	/** Check if size(A) == m2*n2.
  @param m2    Number of rows.
  @param n2    Number of columns.
  */

	public void checkMatrixDimensions (int m2, int n2) {
		if (m2 != m || n2 != n) {
			throw new IllegalArgumentException("Matrix dimensions must equals " + m2 + " x " + n2);
		}
	}

	/** Check if indices have the same length.
  @param IJ    Indices.
  */

	public static void checkIndicesLengths (int[][] IJ) {
		for (int i = 0; i < IJ.length; i++) {
			if (IJ[i].length != 2) {
				throw new IllegalArgumentException("Indices row " + i + " lenght don't equals 2");
			}
		}
	}

	/** Check if number of Rows(A) == number of Rows(B).
  @param B    Matrix to test.
  */

	public void checkRowDimension (Matrix B) {
		if (B.m != m) {
			throw new IllegalArgumentException("Matrix Rows dimensions must equals " + B.m);
		}
	}

	/** Check if number of Rows(A) == row.
  @param row    number of rows.
  */

	public void checkRowDimension (int row) {
		if (row != m) {
			throw new IllegalArgumentException("Matrix Rows dimensions must equals " + row);
		}
	}

	/** Check if number of Columns(A) == number of Columns(B).
  @param B    Matrix to test.
  */

	public void checkColumnDimension (Matrix B) {
		if (B.n != n) {
			throw new IllegalArgumentException("Matrix Columns dimensions must equals " + B.n);
		}
	}

	/** Check if number of Columns(A) == column.
  @param column    number of columns.
  */

	public void checkColumnDimension (int column) {
		if (column != n) {
			throw new IllegalArgumentException("Matrix Columns dimensions must equals " + column);
		}
	}

}
