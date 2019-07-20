package jmat.io.gui.plotTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Label implements Plotable {

	protected Coord coord;
	protected String label;
	protected Color color;

	public Label(String l,Coord c,Color col) {
		label = l;
		coord = c;
		color = col;
	}

	public Label(String l,Coord c) {
		this(l,c,Color.black);
	}

	public Label(Coord c) {
		this(coordToString(c),c,Color.black);
	}

	public void plot(Graphics comp) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(color);
		comp2D.drawString(label,coord.getScreenCoordCopy()[0],coord.getScreenCoordCopy()[1]);
	}

	public static double approx(double val,int decimal) {
		return Math.rint(val*Math.pow(10,decimal))/Math.pow(10,decimal);
	}

	public static String coordToString(Coord c) {
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < c.getPlotCoordCopy().length-1; i++) {
			sb.append(approx(c.getPlotCoordCopy()[i],2)).append(",");
		}
		sb.append(approx(c.getPlotCoordCopy()[c.getPlotCoordCopy().length-1],2)).append(")");
		return sb.toString();
	}

}
