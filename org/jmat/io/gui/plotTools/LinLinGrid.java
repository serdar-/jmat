package jmat.io.gui.plotTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LinLinGrid implements Plotable {

	public static double borderCoeff = 0.1;

	protected Base base;
	protected int numGrid;

	protected String[] axeStrings;

	protected Line[] darkLines;
	protected Line[][][] lightLines;
	protected Label[] darkLabels;
	protected Label[][] lightLabels;

	public LinLinGrid(Base b,String[] as) {
		base = b;
		numGrid = 10;
		axeStrings = as;
		updateGrid();
	}

	public void setLegend(String[] as) {
		axeStrings = as;
		updateGrid();
	}

	public String[] getLegend() {
		return axeStrings;
	}

	public void setNumGrid(int n) {
		numGrid = n;
		updateGrid();
	}

	public void setBase(Base b) {
		base = b;
		updateGrid();
	}

	public void plot(Graphics comp) {
		updateGrid();
		Graphics2D comp2D = (Graphics2D)comp;
		for (int i = 0; i < lightLines.length; i++) {
			for (int j = 0; j < lightLines[0].length; j++) {
				for (int k = 0; k < lightLines[0][0].length; k++) {
					if (i!=j) {
						lightLines[i][j][k].plot(comp);
					}
				}
			}
		}
		for (int i = 0; i < darkLines.length; i++) {
			darkLines[i].plot(comp);
		}
		for (int i = 0; i < lightLabels.length; i++) {
			for (int j = 0; j < lightLabels[0].length; j++) {
				lightLabels[i][j].plot(comp);
			}
		}
		for (int i = 0; i < darkLabels.length; i++) {
			darkLabels[i].plot(comp);
		}

	}

	public void updateGrid() {

		int nbDim = base.getCoords().length-1;
		darkLines = new Line[nbDim];
		lightLines = new Line[nbDim][nbDim][numGrid];
		darkLabels = new Label[nbDim];
		lightLabels = new Label[nbDim][numGrid];

		for (int i = 0; i < nbDim; i++) {
			darkLines[i] = new Line(new RelativeCoord(base.getCoords()[0].getPlotCoordCopy(),base),new RelativeCoord(base.getCoords()[i+1].getPlotCoordCopy(),base),Color.black);
			darkLabels[i] = new Label(axeStrings[i],new RelativeCoord(base.getCoords()[i+1].getPlotCoordCopy(),base),Color.black);

			for (int j = 0; j < nbDim; j++) {
				double[] v = new double[nbDim];
				double pitch = (base.getCoords()[j+1].getPlotCoordCopy()[j] - base.getCoords()[0].getPlotCoordCopy()[j])/numGrid;

				for (int k = 0; k < numGrid; k++) {
					v[j] = (j==i)?(0):(pitch*(k+1));
					lightLines[i][j][k] = new Line(new RelativeCoord(base.getCoords()[0].getPlotCoordCopy(),base).addVector(v),new RelativeCoord(base.getCoords()[i+1].getPlotCoordCopy(),base).addVector(v),Color.lightGray);
				}
			}

			double[] v = new double[nbDim];
			double pitch = (base.getCoords()[i+1].getPlotCoordCopy()[i] - base.getCoords()[0].getPlotCoordCopy()[i])/numGrid;
			int ap = (int)(Math.abs(-Math.log(base.getPrecisionUnit()[i]/100)));
			for (int k = 0; k < numGrid; k++) {
				v[i] = pitch*(k+1);
				RelativeCoord c = new RelativeCoord(base.getCoords()[0].getPlotCoordCopy(),base).addVector(v);
				String lab = (base.getLogScales()[i]) ? (new String("10^"+Label.approx(c.getPlotCoordCopy()[i],ap))) : (new String(""+Label.approx(c.getPlotCoordCopy()[i],ap)));
				lightLabels[i][k] = new Label(lab,c,Color.lightGray);
			}
		}
	}

 /*private void drawLine(Coord c1,Coord c2,Graphics2D comp2D) {
  comp2D.drawLine(c1.getScreenCoordCopy()[0],c1.getScreenCoordCopy()[1],c2.getScreenCoordCopy()[0],c2.getScreenCoordCopy()[1]);
 }*/

}