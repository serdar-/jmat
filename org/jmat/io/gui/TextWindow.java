package jmat.io.gui;

import javax.swing.*;

public class TextWindow extends JPanel {

  private String text;

  public TextWindow(String s) {
    text = s;
    toWindow();
  }

  private void toWindow() {
    JTextArea textArea = new JTextArea(text);
    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane);
  }

}