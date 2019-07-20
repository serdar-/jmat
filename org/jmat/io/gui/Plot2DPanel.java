package jmat.io.gui;

import jmat.data.*;
import jmat.io.gui.plotTools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Plot2DPanel extends PlotPanel {

	public Plot2DPanel() {
		this("X","Y");
	}

	public Plot2DPanel(String Xlabel,String Ylabel) {
		axesLabels = new String[] {Xlabel,Ylabel};
		base = new Base2D(new double[] {0,0},new double[] {1,1},dimension,borderCoeff);
		grid = new LinLinGrid(base,axesLabels);
		datas = new Plot[0];
		labels = new jmat.io.gui.plotTools.Label[0];
		comments = new Comment[0];
	}

	public Plot2DPanel(Matrix XY,String name,int type) {
		this("X","Y", XY, name, type);
	}

	public Plot2DPanel(String Xlabel,String Ylabel,Matrix XY,String name,int type) {
		axesLabels = new String[] {Xlabel,Ylabel};
		setPlot(XY,name,type);
		grid = new LinLinGrid(base,axesLabels);
		labels = new jmat.io.gui.plotTools.Label[0];
		comments = new Comment[0];
	}

	public void setAxesLabels(String Xlabel,String Ylabel) {
		axesLabels = new String[] {Xlabel,Ylabel};
	}

	public void setPlot(Matrix XY,String name,int type) {
		base = new Base2D(XY.min().getColumns(new int[] {0,1}).getRowArrayCopy(0),XY.max().getColumns(new int[] {0,1}).getRowArrayCopy(0),dimension,borderCoeff);
		grid = new LinLinGrid(base,axesLabels);
		switch (type) {
			case 0:
				XY.checkColumnDimension(2);
				datas = new Plot[] {new ScatterPlot(XY,colorList[0],name,base)};
				break;
			case 1:
				XY.checkColumnDimension(2);
				datas = new Plot[] {new LinePlot(XY,colorList[0],name, base)};
				break;
			case 2:
				XY.checkColumnDimension(2);
				datas = new Plot[] {new BarPlot(XY,colorList[0],name, base)};
				break;
			case 3:
				XY.checkColumnDimension(3);
				datas = new Plot[] {new HistogramPlot2D(XY.getColumns(new int[] {0,1}),XY.getColumn(2),colorList[0],name, base)};
				break;
			case 4:
				XY.checkColumnDimension(6);
				datas = new Plot[] {new BoxPlot2D(XY.getColumns(new int[] {0,1}),XY.getColumns(new int[] {2,3,4,5}),colorList[0],name, base)};
				break;
		}
	}

	public void addPlot(Matrix XY,String name,int type) {
		base.includeInBounds(XY.min().getRowArrayCopy(0));
		base.includeInBounds(XY.max().getRowArrayCopy(0));
		grid = new LinLinGrid(base,axesLabels);
		Plot newPlot = null;
		switch (type) {
			case 0:
				XY.checkColumnDimension(2);
				newPlot = new ScatterPlot(XY,colorList[datas.length % colorList.length],name, base);
				break;
			case 1:
				XY.checkColumnDimension(2);
				newPlot = new LinePlot(XY,colorList[datas.length % colorList.length],name, base);
				break;
			case 2:
				XY.checkColumnDimension(2);
				newPlot = new BarPlot(XY,colorList[datas.length % colorList.length],name, base);
				break;
			case 3:
				XY.checkColumnDimension(3);
				newPlot = new HistogramPlot2D(XY.getColumns(new int[] {0,1}),XY.getColumn(2),colorList[datas.length % colorList.length],name, base);
				break;
			case 4:
				XY.checkColumnDimension(6);
				newPlot = new BoxPlot2D(XY.getColumns(new int[] {0,1}),XY.getColumns(new int[] {2,3,4,5}),colorList[datas.length % colorList.length],name, base);
				break;
		}
		Plot[] datas_tmp = new Plot[datas.length+1];
		for (int i = 0; i < datas.length; i++) {
			datas_tmp[i] = datas[i];
		}
		datas_tmp[datas.length] = newPlot;
		datas = datas_tmp;
	}
}