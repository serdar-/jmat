package jmat.io.gui.plotTools;

import jmat.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BarPlot extends Plot {

	public BarPlot(Matrix XY,Color c,String n,Base b) {
		super(XY,c,n,b);
	}

	public void plot(Graphics comp,Color c) {
		RelativeCoord[] coords = getCoords();
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(c);
		for (int i = 0; i < datas.getRowDimension(); i++) {
			drawLine(coords[i],coords[i].projection(datas.getColumnDimension()-1),comp2D);
			drawDot(datas.getRowArrayCopy(i),comp2D);
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