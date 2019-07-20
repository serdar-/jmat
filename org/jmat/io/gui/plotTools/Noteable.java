package jmat.io.gui.plotTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public interface Noteable {
	public boolean tryNote(int[] screenCoord,Graphics comp);
	public boolean tryNote(int[] screenCoord);
	public void note(Graphics comp);
}