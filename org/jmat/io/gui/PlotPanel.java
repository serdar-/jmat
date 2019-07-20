package jmat.io.gui;

import jmat.data.*;
import jmat.io.gui.plotTools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class PlotPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {

	public static int ZOOM = 0;
	public static int TRANSLATION = 1;
	protected int ActionMode;

	public static int LINEAR = 0;
	public static int LOG = 1;
	protected int[] axesScales;
	protected String[] axesLabels;


	public static int SCATTER = 0;
	public static int LINE = 1;
	public static int BAR = 2;
	public static int HISTOGRAM = 3;
	public static int BOX = 4;

	public static Color[] colorList = {Color.blue,Color.red,Color.green,Color.yellow,Color.orange,Color.pink,Color.cyan,Color.magenta};
	public static double borderCoeff = 0.15;
	public static int[] dimension = new int[] {400,400};

	protected Base base;
	protected Plot[] datas;
	protected LinLinGrid grid;
	protected jmat.io.gui.plotTools.Label[] labels;
	protected Comment[] comments;
	protected int[] mouseCurent = new int[2];
	protected int[] mouseClick = new int[2];

	protected JToolBar toolBar;
	protected ButtonGroup buttonGroup;
	protected JToggleButton buttonCenter;
	protected JToggleButton buttonZoom;
	protected JToggleButton buttonViewCoords;
	protected JButton buttonReset;
	protected BorderLayout layout;


	public PlotPanel() {
		setSize(dimension[0],dimension[1]);
		setPreferredSize(new Dimension(dimension[0],dimension[1]));
		initToolbar();
		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	protected void initToolbar() {
		toolBar = new JToolBar();
		buttonGroup = new ButtonGroup();
		toolBar.setFloatable(false);
		buttonCenter = new JToggleButton(new ImageIcon(jmat.io.gui.PlotPanel.class.getResource("plotTools/center.gif")));
		buttonCenter.setToolTipText("Center axes");

		buttonZoom = new JToggleButton(new ImageIcon(jmat.io.gui.PlotPanel.class.getResource("plotTools/zoom.gif")));
		buttonZoom.setToolTipText("Zoom");

		buttonViewCoords = new JToggleButton(new ImageIcon(jmat.io.gui.PlotPanel.class.getResource("plotTools/position.gif")));
		buttonViewCoords.setToolTipText("View coordinates/View entire plot");

		buttonReset = new JButton(new ImageIcon(jmat.io.gui.PlotPanel.class.getResource("plotTools/back.gif")));
		buttonReset.setToolTipText("Reset axes");

		layout = new BorderLayout();

		ActionMode = ZOOM;
		buttonZoom.setSelected(true);
		buttonZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionMode = ZOOM;
			}
		});
		buttonCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionMode = TRANSLATION;
			}
		});
		buttonViewCoords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNoteCoords(buttonViewCoords.isSelected());
			}
		});
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetBase();
			}
		});

		buttonGroup.add(buttonCenter);
		buttonGroup.add(buttonZoom);

		this.setLayout(layout);
		this.add(toolBar, BorderLayout.NORTH);
		toolBar.add(buttonCenter, null);
		toolBar.add(buttonZoom, null);
		toolBar.add(buttonViewCoords, null);
		toolBar.add(buttonReset, null);
	}

	public void resetBase() {
		base.setDefaultsCoords();
		repaint();
	}

	public void setBase(Base b) {
		base = b;
		repaint();
	}

	public Base getBase() {
		return base;
	}

	public LinLinGrid getGrid() {
		return grid;
	}

	public Plot[] getPlots() {
		return datas;
	}

	public void setActionMode(int am) {
		ActionMode = am;
	}

	public void setNoteCoords(boolean b) {
		if (b) {
			for (int i = 0; i < datas.length; i++) {
				datas[i].setNoteEachCoord(true);
			}
		} else {
			for (int i = 0; i < datas.length; i++) {
				datas[i].setNoteEachCoord(false);
			}
		}

	}

	/////////////////////////////////////////////
	//////// Listeners //////////////////////////
	/////////////////////////////////////////////


	public void paint(Graphics comp) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(getBackground());
		comp2D.fillRect(0,0,getSize().width,getSize().height);
		grid.plot(comp);
		for (int i = 0; i < labels.length; i++) {
			labels[i].plot(comp);
		}
		for (int i = 0; i < datas.length; i++) {
			datas[i].plot(comp);
			datas[i].tryNote(mouseCurent,comp);
		}
		for (int i = 0; i < comments.length; i++) {
			comments[i].tryNote(mouseCurent,comp);
		}
		switch (ActionMode) {
			case 0:
				comp2D.setColor(Color.black);
				comp2D.drawRect(Math.min(mouseClick[0],mouseCurent[0]),Math.min(mouseClick[1],mouseCurent[1]),Math.abs(mouseCurent[0]-mouseClick[0]),Math.abs(mouseCurent[1]-mouseClick[1]));
				break;
		}
		setBackground(Color.white);
		toolBar.update(comp);
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
		}
		repaint();
	}

	public void mousePressed(MouseEvent e) {
		mouseClick[0] = e.getX();
		mouseClick[1] = e.getY();
		e.consume();
	}

	public void mouseClicked(MouseEvent e) {
		mouseCurent[0] = e.getX();
		mouseCurent[1] = e.getY();
		e.consume();
		mouseClick[0] = mouseCurent[0];
		mouseClick[1] = mouseCurent[1];
		int[] origin;
		double[] ratio;
		switch (ActionMode) {
			case 0:
				if (e.getModifiers()==16) {
					origin = new int[] {(int)(mouseCurent[0]-dimension[0]/4),(int)(mouseCurent[1]-dimension[1]/4)};
					ratio = new double[] {0.5,0.5};
				} else {
					origin = new int[] {(int)(mouseCurent[0]-dimension[0]),(int)(mouseCurent[1]-dimension[1])};
					ratio = new double[] {2,2};
				}
				base.dilate(origin,ratio);
				break;
		}
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		mouseCurent[0] = e.getX();
		mouseCurent[1] = e.getY();
		e.consume();
		switch (ActionMode) {
			case 0:
				if ((e.getModifiers()==16)&&(mouseCurent[0]!=mouseClick[0])&&(mouseCurent[1]!=mouseClick[1])) {
					int[] origin = {Math.min(mouseClick[0],mouseCurent[0]),Math.min(mouseClick[1],mouseCurent[1])};
					double[] ratio = {Math.abs((double)(mouseCurent[0]-mouseClick[0])/(double)dimension[0]),Math.abs((double)(mouseCurent[1]-mouseClick[1])/(double)dimension[1])};
					base.dilate(origin,ratio);
				}
				break;
		}
		mouseClick[0] = mouseCurent[0];
		mouseClick[1] = mouseCurent[1];
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
		mouseCurent[0] = e.getX();
		mouseCurent[1] = e.getY();
		e.consume();
		mouseClick[0] = mouseCurent[0];
		mouseClick[1] = mouseCurent[1];
		repaint();
	}


	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {}

	public void componentResized(ComponentEvent e) {
		dimension = new int[] {(int)(this.getSize().getWidth()),(int)(this.getSize().getHeight())};
		base.setPanelSize(dimension);
		base.updateScreenCoord();
		repaint();
	}

	public void componentShown(ComponentEvent e) {}
}