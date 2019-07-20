package jmat.io.gui.plotTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AbsoluteCoord extends Coord {

	public AbsoluteCoord(double[] pC,int[] sC) {
		plotCoord = pC;
		screenCoord = sC;
		if (sC.length!=2)
			throw new IllegalArgumentException("Screen coordinates must be in 2D!");
		//isNoteable = false;
	}

	public AbsoluteCoord(Coord A,double p1,Coord B,double p2) {
		plotCoord = new double[A.getPlotCoordCopy().length];
		for (int i = 0; i < A.getPlotCoordCopy().length; i++) {
			plotCoord[i] = (A.getPlotCoordCopy()[i]*p1 + B.getPlotCoordCopy()[i]*p2)/(p1+p2);
		}
		screenCoord = new int[2];
		screenCoord[0] = (int)((A.getScreenCoordCopy()[0]*p1 + B.getScreenCoordCopy()[0]*p2)/(p1+p2));
		screenCoord[1] = (int)((A.getScreenCoordCopy()[1]*p1 + B.getScreenCoordCopy()[1]*p2)/(p1+p2));
	}

}