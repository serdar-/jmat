package jmat.io.gui;

import jmat.data.*;
import jmat.io.gui.plotTools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Plot3DPanel extends PlotPanel {

	public static int ROTATION = 2;
	protected JToggleButton buttonRotate;

	public Plot3DPanel() {
		this("X","Y","Z");
		initToolbar3D();
	}

	public Plot3DPanel(String Xlabel,String Ylabel,String Zlabel) {
		axesLabels = new String[] {Xlabel,Ylabel,Zlabel};
		base = new Base3D(new double[] {0,0,0},new double[] {1,1,1},dimension,borderCoeff);
		grid = new LinLinGrid(base,axesLabels);
		datas = new Plot[0];
		labels = new jmat.io.gui.plotTools.Label[0];
		comments = new Comment[0];
		initToolbar3D();
	}

	public Plot3DPanel(Matrix XY,String name,int type) {
		this("X","Y","Z", XY, name, type);
	}

	public Plot3DPanel(String Xlabel,String Ylabel,String Zlabel,Matrix XY,String name,int type) {
		axesLabels = new String[] {Xlabel,Ylabel,Zlabel};
		setPlot(XY,name,type);
		grid = new LinLinGrid(base,axesLabels);
		labels = new jmat.io.gui.plotTools.Label[0];
		comments = new Comment[0];
		initToolbar3D();
	}

	protected void initToolbar3D() {
		buttonRotate = new JToggleButton(new ImageIcon(jmat.io.gui.PlotPanel.class.getResource("plotTools/rotation.gif")));
		buttonRotate.setToolTipText("Rotate axes");

		buttonRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionMode = ROTATION;
			}
		});
		buttonGroup.add(buttonRotate);
		toolBar.add(buttonRotate, null,2);
	}

	public void setAxesLabels(String Xlabel,String Ylabel,String Zlabel) {
		axesLabels = new String[] {Xlabel,Ylabel,Zlabel};
	}

	public void setPlot(Matrix XY,String name,int type) {
		base = new Base3D(XY.min().getColumns(new int[] {0,1,2}).getRowArrayCopy(0),XY.max().getColumns(new int[] {0,1,2}).getRowArrayCopy(0),dimension,borderCoeff);
		grid = new LinLinGrid(base,axesLabels);
		switch (type) {
		case 0:
			XY.checkColumnDimension(3);
			datas = new Plot[] {new ScatterPlot(XY,colorList[0],name,base)};
		break;
		case 1:
			XY.checkColumnDimension(3);
			datas = new Plot[] {new LinePlot(XY,colorList[0],name, base)};
		break;
		case 2:
			XY.checkColumnDimension(3);
			datas = new Plot[] {new BarPlot(XY,colorList[0],name, base)};
		break;
		case 3:
			XY.checkColumnDimension(5);
			datas = new Plot[] {new HistogramPlot3D(XY.getColumns(new int[] {0,1,2}),XY.getColumns(new int[] {3,4}),colorList[0],name, base)};
		break;
		case 4:
			XY.checkColumnDimension(9);
			datas = new Plot[] {new BoxPlot3D(XY.getColumns(new int[] {0,1,2}),XY.getColumns(new int[] {3,4,5,6,7,8}),colorList[0],name, base)};
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
			XY.checkColumnDimension(3);
			newPlot = new ScatterPlot(XY,colorList[datas.length % colorList.length],name, base);
		break;
		case 1:
			XY.checkColumnDimension(3);
			newPlot = new LinePlot(XY,colorList[datas.length % colorList.length],name, base);
		break;
		case 2:
			XY.checkColumnDimension(3);
			newPlot = new BarPlot(XY,colorList[datas.length % colorList.length],name, base);
		break;
		case 3:
			XY.checkColumnDimension(5);
			newPlot = new HistogramPlot3D(XY.getColumns(new int[] {0,1,2}),XY.getColumns(new int[] {3,4}),colorList[datas.length % colorList.length],name, base);
		break;
		case 4:
			XY.checkColumnDimension(9);
			newPlot = new BoxPlot3D(XY.getColumns(new int[] {0,1,2}),XY.getColumns(new int[] {3,4,5,6,7,8}),colorList[datas.length % colorList.length],name, base);
		break;
		}
		Plot[] datas_tmp = new Plot[datas.length+1];
		for (int i = 0; i < datas.length; i++) {
			datas_tmp[i] = datas[i];
		}
		datas_tmp[datas.length] = newPlot;
		datas = datas_tmp;
	}

	public void mouseDragged(MouseEvent e) {
		mouseCurent[0] = e.getX();
		mouseCurent[1] = e.getY();
		e.consume();
		int[] t;
		switch (ActionMode) {
		case 1:
			t = new int[] {mouseCurent[0] - mouseClick[0], mouseCurent[1] - mouseClick[1]};
			base.translate(t);
			mouseClick[0] = mouseCurent[0];
			mouseClick[1] = mouseCurent[1];
		break;
		case 2:
			t = new int[] {mouseCurent[0] - mouseClick[0], mouseCurent[1] - mouseClick[1]};
			((Base3D)base).rotate(t,dimension,borderCoeff);
			mouseClick[0] = mouseCurent[0];
			mouseClick[1] = mouseCurent[1];
		break;
		}
		repaint();
	}
}
