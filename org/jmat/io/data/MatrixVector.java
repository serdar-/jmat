package jmat.io.data;

import jmat.data.Matrix;
import java.util.*;

public class MatrixVector {

	private Matrix M;
	private Vector vector;

	public MatrixVector(Matrix m) {
		M = m;
		vector = printMatrix(M);
	}

	public MatrixVector(Vector v) {
		vector = v;
		M = readMatrix(vector);
	}

	public static Vector printMatrix(Matrix m) {
		m.checkColumnDimension(1);
		Vector v = new Vector();
		for (int i =0; i < m.getRowDimension(); i++)
			v.add(new Double(m.get(i,0)));

		return v;
	}

	public static Matrix readMatrix (Vector v) {
		Matrix X = new Matrix(v.size(),1);
		for (int i = 0; i < v.size(); i++) {
			if (v.get(i) instanceof Double)
				X.set(i,0,((Double)v.get(i)).doubleValue());
			else
				throw new IllegalArgumentException("Vector element at position "+ i +" is not a double.");
		}
		return X;
	}

	public Matrix getMatrix() {
		return M;
	}

	public Vector getVector() {
		return vector;
	}
}