package jmat.io.gui.plotTools;

import jmat.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HistogramPlot2D extends Plot {

	private Matrix widths;

	public HistogramPlot2D(Matrix XY,Matrix W,Color c,String n,Base b) {
		super(XY,c,n,b);
		widths = W;
		base.includeInBounds(new double[] {datas.min().get(0,0)-widths.max().get(0,0)/2,0});
		base.includeInBounds(new double[] {datas.max().get(0,0)+widths.max().get(0,0)/2,0});
	}

	public void plot(Graphics comp,Color c) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(c);
		for (int i = 0; i < datas.getRowDimension(); i++) {
			double[] topLeft = {datas.get(i,0)-widths.get(i,0)/2,datas.get(i,1)};
			double[] topRight = {datas.get(i,0)+widths.get(i,0)/2,datas.get(i,1)};
			double[] bottomLeft = {datas.get(i,0)-widths.get(i,0)/2,0};
			double[] bottomRight = {datas.get(i,0)+widths.get(i,0)/2,0};

			drawLine(bottomLeft,topLeft,comp2D);
			drawLine(topLeft,topRight,comp2D);
			drawLine(topRight,bottomRight,comp2D);
			drawLine(bottomRight,bottomLeft,comp2D);
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