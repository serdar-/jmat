package jmat.io.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Progress extends JFrame {

	private JProgressBar progress;
	private int min;
	private int max;
	private int val;
	private JPanel pane;

	public Progress(String title,int m,int M) {
		super(title);

		min = m;
		max = M;

		val = min;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pane = new JPanel();

		progress = new JProgressBar(min,max);
		progress.setValue(val);
		progress.setString(null);

		pane.add(progress);
		this.setContentPane(pane);
		this.pack();
		this.setVisible(true);
	}

	public Progress(int m,int M) {

		min = m;
		max = M;

		val = min;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pane = new JPanel();

		progress = new JProgressBar(min,max);
		progress.setValue(val);
		progress.setString(null);

		pane.add(progress);
		this.setContentPane(pane);
		this.pack();
		this.setVisible(true);
	}

	public void setValue(int n) {
		val = n;
		progress.setValue(val);
		if (val>=max) setVisible(false);
	}

}