package jmat.io.data;

import jmat.data.Matrix;
import java.io.*;
import java.util.*;

import org.jdom.*;


public class MatrixMathML {

  private Matrix M;
  private Element E;

  public MatrixMathML(Matrix m) {
    M = m;
    E = printMatrix(M);
  }

  public MatrixMathML(Element e) {
    E = e;
    M = readMatrix(E);
  }

  public static Element printMatrix(Matrix m) {
    Element e = new Element("matrix");
    for (int i = 0; i < m.getRowDimension(); i++) {
      Element ei = new Element("matrixrow");
      for (int j = 0; j < m.getColumnDimension(); j++) {
        Element ej = new Element("cn");
        ej.addContent(new String(""+ m.get(i,j)));
        ei.addContent(ej);
      }
      e.addContent(ei);
    }
    return e;
  }

  public static Matrix readMatrix (Element e) {
    List allRows = e.getChildren();
    List firstRow = ((Element)allRows.get(0)).getChildren();
    Matrix m = new Matrix(allRows.size(),firstRow.size());
    for (int i = 0; i < m.getRowDimension(); i++) {
      List currentRow = ((Element)allRows.get(i)).getChildren();
      for (int j = 0; j < m.getColumnDimension(); j++) {
        Element current = ((Element)currentRow.get(j));
        m.set(i,j,Double.parseDouble(current.getText()));
      }
    }
    return m;
  }

  public Matrix getMatrix() {
    return M;
  }

  public Element getElement() {
    return E;
  }
}