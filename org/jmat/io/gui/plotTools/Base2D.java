package jmat.io.gui.plotTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Base2D extends Base {

	public Base2D(double[] Xmi,double[] Xma,int[] dimension,double borderCoeff) {
		super(Xmi,Xma,dimension,borderCoeff);
	}

	protected double[] baseCoordsScreenProjectionRatio(double[] xy) {
		double[] sC = new double[2];
		sC[0] = (xy[0]-Xmin[0])/(Xmax[0]-Xmin[0]);
		sC[1] = (xy[1]-Xmin[1])/(Xmax[1]-Xmin[1]);
		//System.out.println("(" + xy[0] +"," + xy[1] + ") -> (" + sC[0] + "," + sC[1] + ")");
		return sC;
	}
}