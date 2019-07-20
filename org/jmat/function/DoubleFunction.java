package jmat.function;

import jmat.data.*;
import jmat.io.gui.*;

/**
 * <p>Titre : JAva MAtrix TOols</p>
 * <p>Description : </p>
 * @author Yann RICHET
 * @version 1.0
 */

public abstract class DoubleFunction extends Function {

	public abstract double eval(Matrix values);

/*	public double eval(double[] values) {
  return this.eval(new Matrix(values,values.length));
 }*/

 /** Plot the DoubleFunction in a JPanel
  @param Xmin  Min bounds.
  @param Xmax  Max bounds.
  @param name  Name of the plot.
  @param type  type of the plot.
  @return      A PlotPanel (extends a JPanel)
  */

	public Plot2DPanel toPlot2DPanel(double Xmin, double Xmax,String name,int type) {
		int numOfDots = 100;
		checkArgNumber(1);
		Matrix X = new Matrix(numOfDots,argNumber);
		Matrix Y = new Matrix(numOfDots,1);
		for (int i = 0; i < numOfDots; i++) {
			X.set(i,0,Xmin+(double)i/(numOfDots-1)*(Xmax-Xmin));
			Y.set(i,0,eval(X.getRow(i)));
		}

		return new Plot2DPanel(X.mergeColumns(Y),name,type);
	}

	/** Plot the DoubleFunction in a JFrame
  @param frameName  frame name.
  @param Xmin  Min value in X.
  @param Xmax  Max value in X.
  @param name  Name of the plot.
  @param type  type of the plot.
  @return  a Plot2DPanel.
  */

	public Plot2DPanel toPlot2DFrame(String frameName,double Xmin, double Xmax,String name,int type) {
		Plot2DPanel p = toPlot2DPanel(Xmin,Xmax,name,type);
		FrameView fv = new FrameView(frameName,p);
		return p;
	}

	/** Plot the DoubleFunction in a JPanel
  @param Xmin  Min bounds.
  @param Xmax  Max bounds.
  @param name  Name of the plot.
  @param type  type of the plot.
  @return      A PlotPanel (extends a JPanel)
  */

	public Plot3DPanel toPlot3DPanel(double[] Xmin, double[] Xmax,String name,int type) {
		int numOfDotsX = 20;
		int numOfDotsY = 20;
		checkArgNumber(2);
		Matrix X = new Matrix(numOfDotsX*numOfDotsY,argNumber);
		Matrix Y = new Matrix(numOfDotsX*numOfDotsY,1);
		double pasX = (Xmax[0]-Xmin[0])/(numOfDotsX-1);
		double pasY = (Xmax[1]-Xmin[1])/(numOfDotsY-1);
		int n = 0;
		for (int i = 0; i < numOfDotsX; i++) {
			for (int j = 0; j < numOfDotsY; j++) {
				X.set(n,0,Xmin[0]+i*pasX);
				X.set(n,1,Xmin[1]+j*pasY);
				Y.set(n,0,eval(X.getRow(i)));
				n++;
			}
		}

		return new Plot3DPanel(X.mergeColumns(Y),name,type);
	}

	/** Plot the DoubleFunction in a JFrame
  @param frameName  frame name.
  @param Xmin  Min value in X.
  @param Xmax  Max value in X.
  @param name  Name of the plot.
  @param type  type of the plot.
  @return  a Plot3DPanel.
  */

	public Plot3DPanel toPlot3DFrame(String frameName,double[] Xmin, double[] Xmax,String name,int type) {
		Plot3DPanel p = toPlot3DPanel(Xmin,Xmax,name,type);
		FrameView fv = new FrameView(frameName,p);
		return p;
	}

}