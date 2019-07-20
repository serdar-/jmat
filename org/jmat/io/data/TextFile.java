package jmat.io.data;

import jmat.data.Matrix;
import jmat.io.data.fileTools.CharFile;

import java.io.File;
import java.io.IOException;

public class TextFile {

  private StringBuffer text;
  private File file;

  public TextFile(File f) {
    file = f;
    if (file.exists())
      text = new StringBuffer(CharFile.fromFile(file));
    else
      text = new StringBuffer("");
  }

  public TextFile(File f, String s) {
    this(f);
    this.append(s);
  }

  public void append(String s) {
    text = text.append(s);
    CharFile.toFile(file,text.toString());
  }

  public String toString() {
    return text.toString();
  }

  public File getFile() {
    return file;
  }
}