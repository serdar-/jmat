package jmat.io.gui.plotTools;

import jmat.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HistogramPlot3D extends Plot {

	private Matrix widths;

	public HistogramPlot3D(Matrix XY,Matrix W,Color c,String n,Base b) {
		super(XY,c,n,b);
		widths = W;
		base.includeInBounds(new double[] {datas.min().get(0,0)-widths.max().get(0,0)/2,datas.min().get(0,1)-widths.max().get(0,1)/2,0});
		base.includeInBounds(new double[] {datas.max().get(0,0)+widths.max().get(0,0)/2,datas.max().get(0,1)+widths.max().get(0,1)/2,0});
	}

	public void plot(Graphics comp,Color c) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(c);
		for (int i = 0; i < datas.getRowDimension(); i++) {
			double[] topNW = new double[] {datas.get(i,0)-widths.get(i,0)/2,datas.get(i,1)-widths.get(i,1)/2,datas.get(i,2)};
			double[] topNE = new double[] {datas.get(i,0)+widths.get(i,0)/2,datas.get(i,1)-widths.get(i,1)/2,datas.get(i,2)};
			double[] topSW = new double[] {datas.get(i,0)-widths.get(i,0)/2,datas.get(i,1)+widths.get(i,1)/2,datas.get(i,2)};
			double[] topSE = new double[] {datas.get(i,0)+widths.get(i,0)/2,datas.get(i,1)+widths.get(i,1)/2,datas.get(i,2)};
			double[] bottomNW = new double[] {datas.get(i,0)-widths.get(i,0)/2,datas.get(i,1)-widths.get(i,1)/2,0};
			double[] bottomNE = new double[] {datas.get(i,0)+widths.get(i,0)/2,datas.get(i,1)-widths.get(i,1)/2,0};
			double[] bottomSW = new double[] {datas.get(i,0)-widths.get(i,0)/2,datas.get(i,1)+widths.get(i,1)/2,0};
			double[] bottomSE = new double[] {datas.get(i,0)+widths.get(i,0)/2,datas.get(i,1)+widths.get(i,1)/2,0};

			drawLine(topNW,topNE,comp2D);
			drawLine(topNE,topSE,comp2D);
			drawLine(topSE,topSW,comp2D);
			drawLine(topSW,topNW,comp2D);

			drawLine(bottomNW,bottomNE,comp2D);
			drawLine(bottomNE,bottomSE,comp2D);
			drawLine(bottomSE,bottomSW,comp2D);
			drawLine(bottomSW,bottomNW,comp2D);

			drawLine(bottomNW,topNW,comp2D);
			drawLine(bottomNE,topNE,comp2D);
			drawLine(bottomSE,topSE,comp2D);
			drawLine(bottomSW,topSW,comp2D);
		}
	}

	public void note(Graphics comp) {
		plot(comp,Color.black);
//		Graphics2D comp2D = (Graphics2D)comp;
//		comp2D.setColor(Color.black);
//		RelativeCoord c = new RelativeCoord(datas.mean().getRowArrayCopy(0),base);
//		comp2D.drawString(name,c.getScreenCoordCopy()[0],c.getScreenCoordCopy()[1]);
	}
}