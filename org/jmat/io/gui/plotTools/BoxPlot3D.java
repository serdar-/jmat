package jmat.io.gui.plotTools;

import jmat.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoxPlot3D extends Plot {

	private Matrix widths;

	public BoxPlot3D(Matrix XY,Matrix W,Color c,String n,Base b) {
		super(XY,c,n,b);
		widths = W;
		base.includeInBounds(new double[] {datas.min().get(0,0)-widths.max().get(0,0),datas.min().get(0,1)-widths.max().get(0,2),datas.min().get(0,2)-widths.max().get(0,4)});
		base.includeInBounds(new double[] {datas.max().get(0,0)+widths.max().get(0,1),datas.max().get(0,1)+widths.max().get(0,3),datas.max().get(0,2)+widths.max().get(0,5)});
	}

	public void plot(Graphics comp,Color c) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(c);
		for (int i = 0; i < datas.getRowDimension(); i++) {
			double[] Xmin = {datas.get(i,0)-widths.get(i,0),datas.get(i,1),datas.get(i,2)};
			double[] Xmax = {datas.get(i,0)+widths.get(i,1),datas.get(i,1),datas.get(i,2)};
			double[] Ymin = {datas.get(i,0),datas.get(i,1)-widths.get(i,2),datas.get(i,2)};
			double[] Ymax = {datas.get(i,0),datas.get(i,1)+widths.get(i,3),datas.get(i,2)};
			double[] Zmin = {datas.get(i,0),datas.get(i,1),datas.get(i,2)-widths.get(i,4)};
			double[] Zmax = {datas.get(i,0),datas.get(i,1),datas.get(i,2)+widths.get(i,5)};
			double[] Center = {datas.get(i,0),datas.get(i,1),datas.get(i,2)};

			drawLine(Xmin,Xmax,comp2D);
			drawLine(Ymin,Ymax,comp2D);
			drawLine(Zmin,Zmax,comp2D);
			drawDot(Center,comp2D);
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