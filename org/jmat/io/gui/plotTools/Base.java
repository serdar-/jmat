package jmat.io.gui.plotTools;

import jmat.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Base implements CommandLinePrintable {

	protected Coord[] baseCoords;
	protected double[] precisionUnit;

	protected double[] Xmin;
	protected double[] Xmax;

	protected int[] panelSize;
	protected int dimension;
	protected double borderCoeff;

	protected boolean[] isLog;

	public Base(Coord[] b) {
		baseCoords = b;
	}

	public Base(double[] Xmi,double[] Xma,int[] dim,double bC) {
		dimension = Xmi.length;
		isLog = new boolean[dimension];
		panelSize = dim;
		borderCoeff = bC;
		setPrecisionUnit(Xmi,Xma);
		setBounds(Xmi,Xma);
		setDefaultsCoords();
	}

	public void setPanelSize(int[] dim) {
		panelSize = dim;
	}

	public Coord[] getCoords() {
		return baseCoords;
	}

	public double getDimension() {
		return dimension;
	}

	public boolean[] getLogScales() {
		return isLog;
	}

	public double[] getPrecisionUnit() {
		return precisionUnit;
	}

	protected void setPrecisionUnit(double[] Xmi,double[] Xma) {
		precisionUnit = new double[Xmi.length];
		for (int i = 0; i < precisionUnit.length; i++) {
			if (Xma[i]-Xmi[i]>0)
				precisionUnit[i] = Math.pow(10,Math.rint(Math.log(Xma[i]-Xmi[i])/Math.log(10)));
			else
				precisionUnit[i] = 1;
		}
	}

	public void includeInBounds(double[] XY) {
		double[] Xmi = new double[Xmin.length];
		for (int i = 0; i < Xmin.length; i++) {
			Xmi[i] = Math.min(XY[i],Xmin[i]);
		}
		double[] Xma = new double[Xmax.length];
		for (int i = 0; i < Xmax.length; i++) {
			Xma[i] = Math.max(XY[i],Xmax[i]);
		}
		setPrecisionUnit(Xmi,Xma);
		setBounds(Xmi,Xma);
		setDefaultsCoords();
	}

	protected void setBounds(double[] Xmi,double[] Xma) {
		Xmin = new double[Xmi.length];
		Xmax = new double[Xma.length];
		for (int i = 0; i < Xmi.length; i++) {
			Xmin[i] = precisionUnit[i]*(Math.floor(Xmi[i]/precisionUnit[i]));
			Xmax[i] = precisionUnit[i]*(Math.ceil(Xma[i]/precisionUnit[i]));
			if ((Xma[i]-Xmi[i])<0) {
				Xmax[i] = Xmin[i] + 1;
				Xmin[i] = Xmin[i] - 1;
			}
		}
	}

	public void updateScreenCoord() {
		double[] ratio;
		int[] sC;
		for (int i = 0; i < baseCoords.length; i++) {
			ratio = baseCoordsScreenProjectionRatio(baseCoords[i].getPlotCoordCopy());
			sC = new int[] {(int)(panelSize[0]*(borderCoeff+(1-2*borderCoeff)*ratio[0])),(int)(panelSize[1]-panelSize[1]*(borderCoeff+(1-2*borderCoeff)*ratio[1]))};
			baseCoords[i] = new AbsoluteCoord(baseCoords[i].getPlotCoordCopy(),sC);
		}
	}

	public void setDefaultsCoords() {
		baseCoords = new AbsoluteCoord[Xmin.length+1];

		for (int i = 0; i < Xmin.length+1; i++) {
			double[] pC = (double[])(Xmin.clone());
			if (i>0)
				pC[i-1] = Xmax[i-1];
			double[] ratio = baseCoordsScreenProjectionRatio(pC);
			int[] sC = new int[] {(int)(panelSize[0]*(borderCoeff+(1-2*borderCoeff)*ratio[0])),(int)(panelSize[1]-panelSize[1]*(borderCoeff+(1-2*borderCoeff)*ratio[1]))};
			baseCoords[i] = new AbsoluteCoord(pC,sC);
		}
	}


	public int[] screenProjection(double[] pC) {
		double[] sC = new double[2];
		sC[0] = baseCoords[0].getScreenCoordCopy()[0];
		for (int i = 0; i < baseCoords[0].getPlotCoordCopy().length; i++) {
//		  if (baseCoords[i+1].getPlotCoordCopy()[i]!=baseCoords[0].getPlotCoordCopy()[i])
				 sC[0] = sC[0] + ((pC[i]-baseCoords[0].getPlotCoordCopy()[i])/(baseCoords[i+1].getPlotCoordCopy()[i]-baseCoords[0].getPlotCoordCopy()[i]))*(baseCoords[i+1].getScreenCoordCopy()[0]-baseCoords[0].getScreenCoordCopy()[0]);
		}
		sC[1] = baseCoords[0].getScreenCoordCopy()[1];
		for (int i = 0; i < baseCoords[0].getPlotCoordCopy().length; i++) {
//		  if (baseCoords[i+1].getPlotCoordCopy()[i]!=baseCoords[0].getPlotCoordCopy()[i])
				sC[1] = sC[1] + ((pC[i]-baseCoords[0].getPlotCoordCopy()[i])/(baseCoords[i+1].getPlotCoordCopy()[i]-baseCoords[0].getPlotCoordCopy()[i]))*(baseCoords[i+1].getScreenCoordCopy()[1]-baseCoords[0].getScreenCoordCopy()[1]);
		}
		return new int[] {(int)sC[0],(int)sC[1]};
	}

	protected abstract double[] baseCoordsScreenProjectionRatio(double[] xyz);

	public void translate(int[] screenTranslation) {
		for (int i = 0; i < baseCoords.length; i++)
			baseCoords[i].setScreenCoord(new int[] {baseCoords[i].getScreenCoordCopy()[0]+screenTranslation[0],baseCoords[i].getScreenCoordCopy()[1]+screenTranslation[1]});
	}

	public RelativeCoord getGravCenter() {
		double[] g = baseCoords[0].getPlotCoordCopy();
		for (int i = 0; i < baseCoords.length-1; i++) {
			g[i] = g[i] + baseCoords[i].getPlotCoordCopy()[i]/2;
		}
		return new RelativeCoord(g,this);
	}

	public void dilate(int[] screenOrigin,double[] screenRatio) {
		for (int i = 0; i < baseCoords.length; i++) {
			baseCoords[i].setScreenCoord(new int[] {(int)((baseCoords[i].getScreenCoordCopy()[0]-screenOrigin[0])/screenRatio[0]),(int)((baseCoords[i].getScreenCoordCopy()[1]-screenOrigin[1])/screenRatio[1])});
		}
	}

	public void toCommandLine(String title) {
		System.out.println(title + " : ");
		for (int i = 0; i < baseCoords.length; i++)
			baseCoords[i].toCommandLine("point "+i);
	}
}