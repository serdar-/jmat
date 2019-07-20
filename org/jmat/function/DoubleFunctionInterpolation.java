package jmat.function;

import jmat.data.Matrix;

public class DoubleFunctionInterpolation extends DoubleFunction {

	private Matrix X;
	private Matrix Y;

	public DoubleFunctionInterpolation(Matrix in, Matrix out) {
		if (in.getRowDimension() != out.getRowDimension()) {
			throw new IllegalArgumentException("in = Matrix(m,n) and out = Matrix(m,1)");
		}
		argNumber = in.getColumnDimension();
		X = in;
		Y = out;
	}

	public double eval(Matrix values) {
		checkArgNumber(values.getColumnDimension());
		return interpolate(values);
	}

	public double eval(double value) {
		checkArgNumber(1);
		Matrix values = new Matrix(1,1);
		values.set(0,0,value);
		return interpolate(values);
	}

	private double interpolate(Matrix x) {
		int[] close = closest(x,argNumber+1);
		Matrix Xc = X.getRows(close);
		Matrix Yc = Y.getRows(close);
//		Xc.toCommandLine("Xc");
//		Yc.toCommandLine("Yc");
		Matrix W = Xc.solve(Yc);
//		W.toCommandLine("W");
		Matrix y = x.times(W);
		return y.toDouble();
	}

	private int[] closest(Matrix x, int num) {
		double[] d = new double[X.getRowDimension()];
		for (int i = 0; i < d.length; i++) {
			d[i] = dist(x.getRowArrayCopy(0),X.getRowArrayCopy(i));
		}
		int[] c = new int[num];
		double dMin;
		for (int j = 0; j < num; j++) {
		dMin = Double.MAX_VALUE;
			for (int i = 0; i < X.getRowDimension(); i++) {
				if ((d[i] < dMin)) {
					dMin = d[i];
					c[j] = i;
				}
			}
			d[c[j]] = Double.MAX_VALUE;
		}
		return c;
	}

	private double dist(double[] x, double[] y) {
		double d = 0;
		for (int i = 0; i < x.length; i++) {
			 d = d + (x[i] - y[i])*(x[i] - y[i]);
		}
		return /*Math.sqrt(*/d/*)*/;
	}

}
