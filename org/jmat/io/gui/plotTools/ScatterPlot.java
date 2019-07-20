package jmat.io.gui.plotTools;

import jmat.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScatterPlot extends Plot {

	public ScatterPlot(Matrix XY,Color c,String n,Base b) {
		super(XY,c,n,b);
	}

	public void plot(Graphics comp,Color c) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(c);
		for (int i = 0; i < datas.getRowDimension(); i++) {
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