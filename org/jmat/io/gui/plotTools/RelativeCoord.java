package jmat.io.gui.plotTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RelativeCoord extends Coord implements Noteable {

	protected Base base;
	protected boolean isNoteable = true;

	public RelativeCoord(double[] pC,Base b) {
		base = b;
		plotCoord = pC;
		updateScreenCoord();
	}

	/*public static RelativeCoord barycenter(RelativeCoord A,double p1,Coord B,double p2) {
		double[] pC = new double[A.getPlotCoordCopy().length];
		for (int i = 0; i < A.getPlotCoordCopy().length; i++) {
			pC[i] = (A.getPlotCoordCopy()[i]*p1 + B.getPlotCoordCopy()[i]*p2)/(p1+p2);
		}
		return new RelativeCoord(pC,A.getBase());
	}*/

	/*public static RelativeCoord vector(RelativeCoord A,Coord B) {
		double[] pC = new double[A.getPlotCoordCopy().length];
		for (int i = 0; i < A.getPlotCoordCopy().length; i++) {
			pC[i] = B.getPlotCoordCopy()[i] - A.getPlotCoordCopy()[i];
		}
		return new RelativeCoord(pC,A.getBase());
	}*/

	public int[] getScreenCoordCopy() {
		updateScreenCoord();
		return super.getScreenCoordCopy();
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base b) {
		base = b;
	}

	public RelativeCoord projection(int dimension) {
		double[] pC = this.getPlotCoordCopy();
		pC[dimension] = base.getCoords()[0].getPlotCoordCopy()[dimension];
		return new RelativeCoord(pC,this.getBase());
	}

	public RelativeCoord addVector(double[] v) {
		double[] end = this.getPlotCoordCopy();
		for (int i = 0; i < end.length; i++)
			end[i] = end[i] + v[i];
		return new RelativeCoord(end,base);
	}


	public String toString() {
		StringBuffer s = new StringBuffer("(");
		for (int i = 0; i < plotCoord.length-1;i++) {
			String coordStr = (base.getLogScales()[i]) ? (new String("10^"+approx(this.getPlotCoordCopy()[i],getPower(base.getPrecisionUnit()[i])))) : (new String(""+approx(this.getPlotCoordCopy()[i],getPower(base.getPrecisionUnit()[i]))));
			s.append(coordStr).append(",");
		}
		String coordStr = (base.getLogScales()[plotCoord.length-1]) ? (new String("10^"+approx(this.getPlotCoordCopy()[plotCoord.length-1],getPower(base.getPrecisionUnit()[plotCoord.length-1])))) : (new String(""+approx(this.getPlotCoordCopy()[plotCoord.length-1],getPower(base.getPrecisionUnit()[plotCoord.length-1]))));
		s.append(coordStr).append(")");
		return(s.toString());
	}
	public void setNotable(boolean b) {
		isNoteable = b;
	}

	public boolean tryNote(int[] sC) {
		if ((sC[0]>screenCoord[0]-5)&&(sC[0]<screenCoord[0]+5)&&(sC[1]>screenCoord[1]-5)&&(sC[1]<screenCoord[1]+5))
			return true;
		else return false;
	}

	public boolean tryNote(int[] sC,Graphics comp) {
		if (isNoteable&&tryNote(sC)) {
			note(comp);
			return true;
		}
		return false;
	}

	public void note(Graphics comp) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(Color.black);
		comp2D.drawString(toString(),screenCoord[0],screenCoord[1]);
		for (int i = 0; i < base.getCoords().length-1; i++) {
			Coord p = projection(i);
			comp2D.drawLine(this.getScreenCoordCopy()[0],this.getScreenCoordCopy()[1],p.getScreenCoordCopy()[0],p.getScreenCoordCopy()[1]);
			//new Line(this,p,Color.black).plot(comp);
		}
	}

	private void updateScreenCoord() {
		screenCoord = base.screenProjection(plotCoord);
	}

	private static int getPower(double precisionUnit) {
		return (int)(Math.abs(-Math.log(precisionUnit/100)));
	}

	private static double approx(double val,int decimal) {
		return Math.rint(val*Math.pow(10,decimal))/Math.pow(10,decimal);
	}


	public void checkSameRelativesBases(RelativeCoord B) {
		if (base!=B.base)
			throw new IllegalArgumentException("Using two Coordinates, their bases must be the same");
	}


}