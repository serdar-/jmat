package jmat.data;

import jmat.data.Matrix;
import jmat.data.RandomVariable;

import jmat.io.gui.*;

import jmat.function.DoubleFunction;
import jmat.data.matrixTools.Shuffle;

/**
<P>
  The RandomMatrix Class provides tools for statistical simulations,it extends the Matrix Class and adds many methods.

@author Yann RICHET.
@version 2.0
*/


public class RandomMatrix extends Matrix {

/* ------------------------
  Class variables
	* ------------------------ */

	/** Is the RandomMatrix a sample or an overal population?
	 */

	private boolean isSample = true;


/* ------------------------
  Constructors
	* ------------------------ */

	/** Construct an m-by-n matrix of 0.
  @param m    Number of rows.
  @param n    Number of columns.
  */
 /*T
  <instructions>RandomMatrix M = new RandomMatrix(3,2);</instructions>
  <result>M</result>
  */

	public RandomMatrix(int m,int n) {
		super(m,n);
	}

	/** Construct an m-by-n matrix of 0.
  @param m    Number of rows.
  @param n    Number of columns.
  */

	public RandomMatrix(Matrix M) {
		super(M.getArrayCopy(),M.getRowDimension(),M.getColumnDimension());
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.set(i,j,M.get(i,j));
			}
		}
	}

	/** Construct an m-by-n constant matrix.
  @param m    Number of rows.
  @param n    Number of colums.
  @param s    Fill the matrix with this scalar value.
  */
 /*T
  <instructions>RandomMatrix M = new RandomMatrix(3,2,1.5);</instructions>
  <result>M</result>
  */

	public RandomMatrix (int m, int n, double s) {
		super(m,n,s);
	}

	/** Construct a matrix from a 2D-array.
  @param B    Two-dimensional array of doubles.
  @exception  IllegalArgumentException All rows must have the same length
  @see        #constructWithCopy
  */
 /*T
  <instructions>double[][] B = {{1,2},{3,4},{5,6}};RandomMatrix M = new RandomMatrix(B);</instructions>
  <result>B</result>
  <result>M</result>
  */

	public RandomMatrix (double[][] B) {
		super(B);
	}

	/** Construct a matrix from a 2D-array.
  @param B    Two-dimensional array of doubles.
  @param m    Number of rows.
  @param n    Number of columns.
  @exception  IllegalArgumentException All rows must have the same length
  @see        #constructWithCopy
  */
 /*T
  <instructions>double[][] B = {{1,2},{3,4},{5,6}};RandomMatrix M = new RandomMatrix(B,1,2);</instructions>
  <result>B</result>
  <result>M</result>
  */

	public RandomMatrix (double[][] B, int m, int n) {
		super(B,m,n);
	}

	/** Construct a matrix from a one-dimensional packed array
  @param vals One-dimensional array of doubles, packed by columns (ala Fortran).
  @param m    Number of rows.
  @exception  IllegalArgumentException Array length must be a multiple of m.
  */
 /*T
  <instructions>double[] vals = {1,2,3,4,5,6};RandomMatrix M = new RandomMatrix(vals,3);</instructions>
  <result>vals</result>
  <result>M</result>
  */

	public RandomMatrix (double vals[], int m) {
		super(vals,m);
	}

/* ----------------------
  Public Methods
	* ---------------------- */

////////////////////////
//Static constructors.//
////////////////////////

/** Construct an m-by-n matrix of random numbers from a uniform random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param min    Min of the random variable.
  @param max    Max of the random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.uniform(3,2,5,6);</instructions>
 <result>M</result>
 */

	public static RandomMatrix uniform(int m,int n,double min,double max) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.uniform(min, max);
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a discrete random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param val_prob    Matrix of the discrete value and their probabilities.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.dirac(3,2,Matrix.random(10,2));</instructions>
 <result>M</result>
 */

	public static RandomMatrix dirac(int m,int n,Matrix val_prob) {
		val_prob.checkColumnDimension(2);
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		double y;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.dirac(val_prob.getColumnArrayCopy(0),val_prob.getColumnArrayCopy(1));
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a Gaussian (Normal) random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param mu    Mean of the random variable.
  @param sigma    Standard deviation of the random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.normal(3,2,10,2);</instructions>
 <result>M</result>
 */

	public static RandomMatrix normal(int m,int n,double mu,double sigma) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.normal(mu,sigma);
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a LogNormal random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param mu    Mean of the Normal random variable.
  @param sigma    Standard deviation of the Normal random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.logNormal(3,2,10,2);</instructions>
 <result>M</result>
 */

	public static RandomMatrix logNormal(int m,int n,double mu,double sigma) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.logNormal(mu,sigma);
				X.set(i,j,Math.exp(x));
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from an exponantial random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param lambda    Parmaeter of the exponential random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.exponential(3,2,10);</instructions>
 <result>M</result>
 */

	public static RandomMatrix exponential(int m,int n,double lambda) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.exponential(lambda);
				X.set(i,j,x);
			}
		}
		return X;
	}


	/** Construct an m-by-n matrix of random numbers from a symetric triangular random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param min    Min of the random variable.
  @param max    Max of the random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.triangular(3,2,10,15);</instructions>
 <result>M</result>
 */

	public static RandomMatrix triangular(int m,int n,double min,double max) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.triangular(min,max);
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a non-symetric triangular random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param min    Min of the random variable.
  @param med    Value of the random variable with max density.
  @param max    Max of the random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.triangular(3,2,10,15,16);</instructions>
 <result>M</result>
 */

	public static RandomMatrix triangular(int m,int n,double min,double med,double max) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.triangular(min,med,max);
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a Beta random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param a    First parameter of the Beta random variable.
  @param b    Second parameter of the Beta random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.beta(3,2,10,15);</instructions>
 <result>M</result>
 */

	public static RandomMatrix beta(int m,int n,double a,double b) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.beta(a,b);
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a Cauchy random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param mu    Median of the Weibull random variable
  @param sigma    Second parameter of the Cauchy random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.cauchy(3,2,10,1);</instructions>
 <result>M</result>
 */

	public static RandomMatrix cauchy(int m,int n,double mu,double sigma) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.cauchy(mu,sigma);
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a Weibull random variable.
  @param m    Number of rows.
  @param n    Number of columns.
  @param lambda    First parameter of the Weibull random variable.
  @param c    Second parameter of the Weibull random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.weibull(3,2,0.5,0.5);</instructions>
 <result>M</result>
 */

	public static RandomMatrix weibull(int m,int n,double lambda,double c) {
		RandomMatrix X = new RandomMatrix(m,n);
		double x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				x = RandomVariable.weibull(lambda,c);
				X.set(i,j,x);
			}
		}
		return X;
	}

	/** Construct an m-by-n matrix of random numbers from a random variable definied by its density function, using the rejection technic.
	 *  ! WARNING : this simulation technic can take a very long time !
  @param m    Number of rows.
  @param n    Number of columns.
  @param fun    Density function of the random variable.
  @param min    Min of the random variable.
  @param max    Max of the random variable.
  @return      A RandomMatrix.
  */
/*T
 <instructions>RandomMatrix M = RandomMatrix.rejection(3,2,new DoubleFunctionExpression("sin(x)","x"),0,1);</instructions>
 <result>M</result>
 */

	public static RandomMatrix rejection(int m,int n,DoubleFunction fun, double min, double max) {
		RandomMatrix X = new RandomMatrix(m,n);
		double maxFun = 0;
		for (int i = 0; i < (10*m*n); i++) {
			double[] val = {min + i*(max-min)/(10*m*n-1)};
			maxFun = Math.max(maxFun,fun.eval(new Matrix(val,1)));
		}
		double try_x;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				try_x = RandomVariable.rejection(fun,maxFun,min,max);
				X.set(i,j,try_x);
			}
		}
		return X;
	}

	/** Construct a sample with replacement of a matrix.
  @param m    Number of rows.
  @param n    Number of columns.
  @param B    Matrix to sample.
  @return      A RandomMatrix.
  */
/*T
 <instructions>Matrix A = Matrix.random(2,2);RandomMatrix M = RandomMatrix.sampleWithReplacement(3,2,A);</instructions>
 <result>A</result>
 <result>M</result>
 */

	public static RandomMatrix sampleWithReplacement(int m, int n,Matrix B) {
		RandomMatrix X = new RandomMatrix(m,n);
		Matrix b = B.reshapeRows(B.m*B.n,1);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int num = RandomVariable.randInt(0,B.n*B.m-1);
				X.set(i,j,b.get(num,0));
			}
		}
		return X;
	}

	/** Construct a sample without replacement of a matrix.
  @param m    Number of rows.
  @param n    Number of columns.
  @param B    Matrix to sample.
  @return      A RandomMatrix.
  */
/*T
 <instructions>Matrix A = Matrix.random(3,3);RandomMatrix M = RandomMatrix.sampleWithoutReplacement(3,2,A);</instructions>
 <result>A</result>
 <result>M</result>
 */

	public static RandomMatrix sampleWithoutReplacement(int m, int n,Matrix B) {
		RandomMatrix X = new RandomMatrix(m*n,1);
		if ((m*n) > ((B.m)*(B.n))) {
			throw new IllegalArgumentException("Matrix number of elements must be < " + ((B.m)*(B.n)));
		}
		Shuffle order = new Shuffle(B.m*B.n);
		Matrix b = B.reshapeRows(B.m*B.n,1);
		for (int i = 0; i < m*n; i++) {
			X.set(i,0,b.get(order.getOrder(i),0));
		}
		return new RandomMatrix(X.reshapeRows(m,n));
	}


////////////////////////////////////////
//Norms and characteristics of Matrix.//
////////////////////////////////////////

/** Specify if the RandomMatrix is a sample of an overall population, or if it's an overall population.
 *  This information is needed to calculate unbiaised estimtors of variance for instance.
  @param is    Is sample?.
  */

	public void setIsSample(boolean is) {
		isSample = is;
	}

	/** Specify if the RandomMatrix is a sample of an overall population, or if it's an overall population.
	 *  This information is needed to calculate unbiaised estimtors of variance for instance.
  @return     is Sample?
  */

	public boolean getIsSample() {
		return isSample;
	}

	/** Generate a row matrix, each column contents the mean value of the columns.
  @return     An 1-by-n matrix.
  */

	public Matrix mean() {
		return super.mean();
	}

	/** Generate a covariance matrix, each column contains values of a pulling.
  @return     An n-by-n matrix.
  */

	public Matrix covariance() {
		int degrees = (isSample) ? (m-1) : (m);
		return super.covariance().times((m-1)/degrees);
	}

	/** Generate a correlation matrix, each column contains values of a pulling.
  @return     An n-by-n matrix.
  */

	public Matrix correlation() {
		int degrees = (isSample) ? (m-1) : (m);
		return super.correlation().times((m-1)/degrees);
	}

	/** Generate a variance matrix, each column contains values of a pulling.
  @return     An 1-by-n matrix.
  */

	public Matrix variance() {
		int degrees = (isSample) ? (m-1) : (m);
		return super.variance().times((m-1)/degrees);
	}


/////////////////////////////////////////////////////////
//Matrix io methods, in panels, frames or command line.//
/////////////////////////////////////////////////////////


/** Print the Matrix in an histogram in a JPanel.
@param name    name of the plot.
@param num    number of slices.
@return    A JPanel.
*/
/*T
<instructions>RandomMatrix A = RandomMatrix.normal(20,1,10,1);Plot2DPanel pp = A.toHist2DPanel("A",5);</instructions>
<result>pp</result>
*/

	public Plot2DPanel toHist2DPanel(String name,int num) {
		return new Plot2DPanel(this.slice(num).getSlicedMatrix(),name,PlotPanel.HISTOGRAM);
	}

	/** Print the Matrix in a JFrame.
@param title    title of the JFrame.
@param name    name of the plot.
@param num    number of slices.
@return    A JFrame.
*/

	public Plot2DPanel toHist2DFrame(String title,String name,int num) {
		Plot2DPanel p = toHist2DPanel(name,num);
		new FrameView(title,p);
		return p;
	}

	/** Print the Matrix in a JPanel.
@param type    type of displaying.
@param name    name of the plot.
@return    A JPanel.
*/
/*T
<instructions>RandomMatrix A = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toHist3DPanel("A",5);</instructions>
<result>pp</result>
*/

	public Plot3DPanel toHist3DPanel(String name,int num) {
		return new Plot3DPanel(this.slice(num).getSlicedMatrix(),name,PlotPanel.HISTOGRAM);
	}

	/** Print the Matrix in a JFrame.
@param title    title of the JFrame.
@param name    name of the plot.
@param num    number of slices.
@return    A JFrame.
*/

	public Plot3DPanel toHist3DFrame(String title,String name,int num) {
		Plot3DPanel p = toHist3DPanel(name,num);
		new FrameView(title,p);
		return p;
	}

	/** Print the Matrix in a JPanel.
@param panel    Panel to modify.
@param name    name of the plot.
@param num    number of slices.
*/
/*T
<instructions>Matrix A = Matrix.random(10,2);RandomMatrix B = RandomMatrix.normal(20,1,10,1);Plot2DPanel pp = A.toPlot2DPanel("A",2);B.addHistToPlot2DPanel(pp,"B",5);</instructions>
<result>pp</result>
*/

	public void addHistToPlot2DPanel(Plot2DPanel panel,String name,int num) {
		panel.addPlot(this.slice(num).getSlicedMatrix(),name,PlotPanel.HISTOGRAM);
	}

	/** Print the Matrix in a JPanel.
@param panel    Panel to modify.
@param name    name of the plot.
@param type    type of displaying.
*/
/*T
<instructions>Matrix A = Matrix.random(10,3);RandomMatrix B = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toPlot3DPanel("A",2);B.addHistToPlot3DPanel(pp,"B",5);</instructions>
<result>pp</result>
*/

	public void addHistToPlot3DPanel(Plot3DPanel panel,String name,int num) {
		panel.addPlot(this.slice(num).getSlicedMatrix(),name,PlotPanel.HISTOGRAM);
	}

}