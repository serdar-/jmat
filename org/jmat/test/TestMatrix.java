package jmat.test;

import jmat.data.*;
import jmat.data.matrixDecompositions.*;
import jmat.data.matrixTools.*;
import jmat.function.*;
import jmat.io.data.*;
import jmat.io.gui.*;
import jmat.io.gui.plotTools.*;

import java.io.*;

import javax.swing.*;
import java.awt.*;

import org.jdom.*;
public class TestMatrix {

  public TestMatrix() {
    testMatrix1();
    testMatrix2();
    testMatrix3();
    testMatrix4();
    testMatrix5();
    testMatrix6();
    testMatrix7();
    testMatrix8();
    testMatrix9();
    testMatrix10();
    testMatrix11();
    testMatrix12();
    testMatrix13();
    testMatrix14();
    testMatrix15();
    testMatrix16();
    testMatrix17();
    testMatrix18();
    testMatrix19();
    testMatrix20();
    testMatrix21();
    testMatrix22();
    testMatrix23();
    testMatrix24();
    testMatrix25();
    testMatrix26();
    testMatrix27();
    testMatrix28();
    testMatrix29();
    testMatrix30();
    testMatrix31();
    testMatrix32();
    testMatrix33();
    testMatrix34();
    testMatrix35();
    testMatrix36();
    testMatrix37();
    testMatrix38();
    testMatrix39();
    testMatrix40();
    testMatrix41();
    testMatrix42();
    testMatrix43();
    testMatrix44();
    testMatrix45();
    testMatrix46();
    testMatrix47();
    testMatrix48();
    testMatrix49();
    testMatrix50();
    testMatrix51();
    testMatrix52();
    testMatrix53();
    testMatrix54();
    testMatrix55();
    testMatrix56();
    testMatrix57();
    testMatrix58();
    testMatrix59();
    testMatrix60();
    testMatrix61();
    testMatrix62();
    testMatrix63();
    testMatrix64();
    testMatrix65();
    testMatrix66();
    testMatrix67();
    testMatrix68();
    testMatrix69();
    testMatrix70();
    testMatrix71();
    testMatrix72();
    testMatrix73();
    testMatrix74();
    testMatrix75();
    testMatrix76();
    testMatrix77();
    testMatrix78();
    testMatrix79();
    testMatrix80();
    testMatrix81();
    testMatrix82();
    testMatrix83();
    testMatrix84();
    testMatrix85();
    testMatrix86();
    testMatrix87();
    testMatrix88();
    testMatrix89();
    testMatrix90();
    testMatrix91();
    testMatrix92();
    testMatrix93();
    testMatrix94();
    testMatrix95();
    testMatrix96();
    testMatrix97();
    testMatrix98();
    testMatrix99();
    testMatrix100();
    testMatrix101();
    testMatrix102();
    testMatrix103();
    testMatrix104();
    testMatrix105();
    testMatrix106();
    testMatrix107();
    testMatrix108();
    testMatrix109();
    testMatrix110();
    testMatrix111();
  }
	private void title(String s) {
		System.out.println("--------------- "+s+" ---------------");
	}

	private void print(String s) {
		System.out.print(s);
	}
	private void println(String s) {
		System.out.println(s);
	}

	private void printResult(double x,String s) {
		System.out.println(s + " = " + x);
	}

	private void printResult(int x,String s) {
		System.out.println(s + " = " + x);
	}

	private void printResult(String x,String s) {
		System.out.println(s + " = " + x);
	}


	private void printResult(Matrix X,String s) {
		X.toCommandLine("Matrix " + s);
	}

	private void printResult(JPanel p,String s) {
		new FrameView(s,p);
	}

	private void printResult() {
	}

	private void printResult(double[] X,String s) {
		System.out.println("double[] " + s + " = ");
		for (int i = 0; i < X.length; i++) {
			System.out.println("" + X[i]);
		}
	}
	
	private void printResult(double[][] X,String s) {
		System.out.println("double[][] " + s + " = ");
		for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < X[i].length; j++) {
				System.out.print(" " + X[i][j]);
			}
			System.out.println("");
		}
	}

	private void printResult(int[] X,String s) {
		System.out.println("int[] " + s + " = ");
		for (int i = 0; i < X.length; i++) {
			System.out.println("" + X[i]);
		}
	}
	
	private void printResult(int[][] X,String s) {
		System.out.println("int[][] " + s + " = ");
		for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < X[i].length; j++) {
				System.out.print(" " + X[i][j]);
			}
			System.out.println("");
		}
	}


  public void testMatrix1() {
    println("Matrix M = new Matrix(3,2);");
    Matrix M = new Matrix(3,2);
    printResult(M,"M");
  }

  public void testMatrix2() {
    println("Matrix M = new Matrix(3,2,1.5);");
    Matrix M = new Matrix(3,2,1.5);
    printResult(M,"M");
  }

  public void testMatrix3() {
    println("double[][] B = {{1,2},{3,4},{5,6}};Matrix M = new Matrix(B);");
    double[][] B = {{1,2},{3,4},{5,6}};Matrix M = new Matrix(B);
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testMatrix4() {
    println("double[][] B = {{1,2},{3,4},{5,6}};Matrix M = new Matrix(B,1,2);");
    double[][] B = {{1,2},{3,4},{5,6}};Matrix M = new Matrix(B,1,2);
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testMatrix5() {
    println("double[] vals = {1,2,3,4,5,6};Matrix M = new Matrix(vals,3);");
    double[] vals = {1,2,3,4,5,6};Matrix M = new Matrix(vals,3);
    printResult(vals,"vals");
    printResult(M,"M");
  }

  public void testMatrix6() {
    println("Matrix M = Matrix.random(3,2);");
    Matrix M = Matrix.random(3,2);
    printResult(M,"M");
  }

  public void testMatrix7() {
    println("double[] d = {1,2,3,4,5,6};Matrix M = Matrix.diagonal(d);");
    double[] d = {1,2,3,4,5,6};Matrix M = Matrix.diagonal(d);
    printResult(d,"d");
    printResult(M,"M");
  }

  public void testMatrix8() {
    println("Matrix M = Matrix.identity(3,2);");
    Matrix M = Matrix.identity(3,2);
    printResult(M,"M");
  }

  public void testMatrix9() {
    println("Matrix M = Matrix.incrementRows(3,2,1.1,0.1);");
    Matrix M = Matrix.incrementRows(3,2,1.1,0.1);
    printResult(M,"M");
  }

  public void testMatrix10() {
    println("Matrix M = Matrix.incrementColumns(3,2,1.1,0.1);");
    Matrix M = Matrix.incrementColumns(3,2,1.1,0.1);
    printResult(M,"M");
  }

  public void testMatrix11() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = new Matrix(new double[][] {{5,6},{7,8}});Matrix M = Matrix.mergeRows(new Matrix[] {A,B});");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = new Matrix(new double[][] {{5,6},{7,8}});Matrix M = Matrix.mergeRows(new Matrix[] {A,B});
    printResult(A,"A");
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testMatrix12() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = new Matrix(new double[][] {{5,6},{7,8}});Matrix M = Matrix.mergeColumns(new Matrix[] {A,B});");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = new Matrix(new double[][] {{5,6},{7,8}});Matrix M = Matrix.mergeColumns(new Matrix[] {A,B});
    printResult(A,"A");
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testMatrix13() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.copy();B.set(1,1,0.5);");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.copy();B.set(1,1,0.5);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix14() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[][] b = A.getArrayCopy();b[1][1]=0.5;");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[][] b = A.getArrayCopy();b[1][1]=0.5;
    printResult(A,"A");
    printResult(b,"b");
  }

  public void testMatrix15() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double val = A.get(0,1);");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double val = A.get(0,1);
    printResult(A,"A");
    printResult(val,"val");
  }

  public void testMatrix16() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.get(new int[][] {{0,1},{1,1},{1,0}});");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.get(new int[][] {{0,1},{1,1},{1,0}});
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix17() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getMatrix(0,1,0,0);");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getMatrix(0,1,0,0);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix18() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[] b = A.getRowArrayCopy(1);");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[] b = A.getRowArrayCopy(1);
    printResult(A,"A");
    printResult(b,"b");
  }

  public void testMatrix19() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getRow(1);");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getRow(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix20() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getRows(new int[] {1,0});");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getRows(new int[] {1,0});
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix21() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[] b = A.getColumnArrayCopy(1);");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});double[] b = A.getColumnArrayCopy(1);
    printResult(A,"A");
    printResult(b,"b");
  }

  public void testMatrix22() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getColumn(1);");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getColumn(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix23() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getColumns(new int[] {1,0});");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix B = A.getColumns(new int[] {1,0});
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix24() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});int m = A.getRowDimension();");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});int m = A.getRowDimension();
    printResult(A,"A");
    printResult(m,"m");
  }

  public void testMatrix25() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});int n = A.getRowDimension();");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});int n = A.getRowDimension();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix26() {
    println("Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix D = A.getDiagonal();");
    Matrix A = new Matrix(new double[][] {{1,2},{3,4}});Matrix D = A.getDiagonal();
    printResult(A,"A");
    printResult(D,"D");
  }

  public void testMatrix27() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix D = A.getDiagonal(1);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix D = A.getDiagonal(1);
    printResult(A,"A");
    printResult(D,"D");
  }

  public void testMatrix28() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.set(1,0,0.5);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.set(1,0,0.5);
    printResult(A,"A");
  }

  public void testMatrix29() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.set(new int[][] {{0,1},{1,1}},0.5);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.set(new int[][] {{0,1},{1,1}},0.5);
    printResult(A,"A");
  }

  public void testMatrix30() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0,0},{0,0}});A.setMatrix(0,1,B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0,0},{0,0}});A.setMatrix(0,1,B);
    printResult(B,"B");
    printResult(A,"A");
  }

  public void testMatrix31() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0,0},{0,0}});A.setMatrix(new int[][] {{0,1},{0,0}},B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0,0},{0,0}});A.setMatrix(new int[][] {{0,1},{0,0}},B);
    printResult(B,"B");
    printResult(A,"A");
  }

  public void testMatrix32() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.setMatrix(0,1,1,1,0.5);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});A.setMatrix(0,1,1,1,0.5);
    printResult(A,"A");
  }

  public void testMatrix33() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.5,0.5}});A.setRow(1,B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.5,0.5}});A.setRow(1,B);
    printResult(B,"B");
    printResult(A,"A");
  }

  public void testMatrix34() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.5,0.5},{0.6,0.6,0.6}});A.setRows(new int[] {0,1},B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.5,0.5},{0.6,0.6,0.6}});A.setRows(new int[] {0,1},B);
    printResult(B,"B");
    printResult(A,"A");
  }

  public void testMatrix35() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5},{0.5}});A.setColumn(1,B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5},{0.5}});A.setColumn(1,B);
    printResult(B,"B");
    printResult(A,"A");
  }

  public void testMatrix36() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.6},{0.5,0.6}});A.setColumns(new int[] {0,1},B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{0.5,0.6},{0.5,0.6}});A.setColumns(new int[] {0,1},B);
    printResult(B,"B");
    printResult(A,"A");
  }

  public void testMatrix37() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.resize(2,2);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.resize(2,2);
    printResult(B,"B");
    printResult(A,"A");
  }

  public void testMatrix38() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.reshapeRows(3,2);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.reshapeRows(3,2);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix39() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.reshapeColumns(3,2);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.reshapeColumns(3,2);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix40() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.transpose();");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.transpose();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix41() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{7,8,9},{10,11,12}});Matrix M = A.mergeRows(B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{7,8,9},{10,11,12}});Matrix M = A.mergeRows(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testMatrix42() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{7,8,9},{10,11,12}});Matrix M = A.mergeColumns(B);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = new Matrix(new double[][] {{7,8,9},{10,11,12}});Matrix M = A.mergeColumns(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testMatrix43() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.insertRow(1);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.insertRow(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix44() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.insertColumn(1);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});Matrix B = A.insertColumn(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix45() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});Matrix B = A.deleteRow(1);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});Matrix B = A.deleteRow(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix46() {
    println("Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});Matrix B = A.deleteColumn(1);");
    Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});Matrix B = A.deleteColumn(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix47() {
    println("Matrix A = Matrix.random(4,4);double n = A.norm1();");
    Matrix A = Matrix.random(4,4);double n = A.norm1();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix48() {
    println("Matrix A = Matrix.random(4,4);double n = A.norm2();");
    Matrix A = Matrix.random(4,4);double n = A.norm2();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix49() {
    println("Matrix A = Matrix.random(4,4);double n = A.normInfinity();");
    Matrix A = Matrix.random(4,4);double n = A.normInfinity();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix50() {
    println("Matrix A = Matrix.random(4,4);double n = A.normFrobenius();");
    Matrix A = Matrix.random(4,4);double n = A.normFrobenius();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix51() {
    println("Matrix A = Matrix.random(4,4);double n = A.determinant();");
    Matrix A = Matrix.random(4,4);double n = A.determinant();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix52() {
    println("Matrix A = Matrix.random(4,4);double n = A.rank();");
    Matrix A = Matrix.random(4,4);double n = A.rank();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix53() {
    println("Matrix A = Matrix.random(4,4);double n = A.condition();");
    Matrix A = Matrix.random(4,4);double n = A.condition();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix54() {
    println("Matrix A = Matrix.random(4,4);double n = A.trace();");
    Matrix A = Matrix.random(4,4);double n = A.trace();
    printResult(A,"A");
    printResult(n,"n");
  }

  public void testMatrix55() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.min();");
    Matrix A = Matrix.random(4,4);Matrix B = A.min();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix56() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.max();");
    Matrix A = Matrix.random(4,4);Matrix B = A.max();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix57() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.sum();");
    Matrix A = Matrix.random(4,4);Matrix B = A.sum();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix58() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.product();");
    Matrix A = Matrix.random(4,4);Matrix B = A.product();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix59() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.distance(Matrix.random(4,4),2);");
    Matrix A = Matrix.random(4,4);Matrix B = A.distance(Matrix.random(4,4),2);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix60() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.mean();");
    Matrix A = Matrix.random(4,4);Matrix B = A.mean();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix61() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.covariance();");
    Matrix A = Matrix.random(4,4);Matrix B = A.covariance();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix62() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.correlation();");
    Matrix A = Matrix.random(4,4);Matrix B = A.correlation();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix63() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.variance();");
    Matrix A = Matrix.random(4,4);Matrix B = A.variance();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix64() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.uminus();");
    Matrix A = Matrix.random(4,4);Matrix B = A.uminus();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix65() {
    println("Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.plus(B);");
    Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.plus(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix66() {
    println("Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.minus(B);");
    Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.minus(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix67() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.times(2);");
    Matrix A = Matrix.random(4,4);Matrix B = A.times(2);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix68() {
    println("Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.times(B);");
    Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.times(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix69() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.divide(2);");
    Matrix A = Matrix.random(4,4);Matrix B = A.divide(2);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix70() {
    println("Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.divide(B);");
    Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,4);Matrix C = A.divide(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix71() {
    println("Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,1);Matrix C = A.solve(B);");
    Matrix A = Matrix.random(4,4);Matrix B =Matrix.random(4,1);Matrix C = A.solve(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix72() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.inverse();");
    Matrix A = Matrix.random(4,4);Matrix B = A.inverse();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix73() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.plus(1);");
    Matrix A = Matrix.random(4,4);Matrix B = A.plus(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix74() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.minus(1);");
    Matrix A = Matrix.random(4,4);Matrix B = A.minus(1);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix75() {
    println("Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebeTimes(B);");
    Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebeTimes(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix76() {
    println("Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebeDivide(B);");
    Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebeDivide(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix77() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeCos();");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeCos();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix78() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeSin();");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeSin();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix79() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeExp();");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeExp();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix80() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebePow(2);");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebePow(2);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix81() {
    println("Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebePow(B);");
    Matrix A = Matrix.random(4,4);Matrix B = Matrix.random(4,4);Matrix C = A.ebePow(B);
    printResult(A,"A");
    printResult(B,"B");
    printResult(C,"C");
  }

  public void testMatrix82() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeLog();");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeLog();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix83() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeInverse();");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeInverse();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix84() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeSqrt();");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeSqrt();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix85() {
    println("Matrix A = Matrix.random(4,4).minus(0.5);Matrix B = A.abs();");
    Matrix A = Matrix.random(4,4).minus(0.5);Matrix B = A.abs();
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix86() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeFunction(new DoubleFunctionExpression( log(1/x) , x ));");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeFunction(new DoubleFunctionExpression("log(1/x)","x"));
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix87() {
    println("Matrix A = Matrix.random(4,4);Matrix B = A.ebeIndicesFunction(new DoubleFunctionExpression( x+i+j ,new String[] { x , i , j }));");
    Matrix A = Matrix.random(4,4);Matrix B = A.ebeIndicesFunction(new DoubleFunctionExpression("x+i+j",new String[] {"x","i","j"}));
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix88() {
    println("Matrix A = Matrix.random(4,4);LUDecomposition lu = A.lu();");
    Matrix A = Matrix.random(4,4);LUDecomposition lu = A.lu();
    printResult(lu.getL(),"lu.getL()");
    printResult(lu.getU(),"lu.getU()");
  }

  public void testMatrix89() {
    println("Matrix A = Matrix.random(4,4);QRDecomposition qr = A.qr();");
    Matrix A = Matrix.random(4,4);QRDecomposition qr = A.qr();
    printResult(qr.getQ(),"qr.getQ()");
    printResult(qr.getR(),"qr.getR()");
  }

  public void testMatrix90() {
    println("Matrix A = Matrix.random(4,4);CholeskyDecomposition c = A.cholesky();");
    Matrix A = Matrix.random(4,4);CholeskyDecomposition c = A.cholesky();
    printResult(c.getL(),"c.getL()");
  }

  public void testMatrix91() {
    println("Matrix A = Matrix.random(4,4);SingularValueDecomposition svd = A.svd();");
    Matrix A = Matrix.random(4,4);SingularValueDecomposition svd = A.svd();
    printResult(svd.getS(),"svd.getS()");
    printResult(svd.getV(),"svd.getV()");
    printResult(svd.getU(),"svd.getU()");
  }

  public void testMatrix92() {
    println("Matrix A = Matrix.random(4,4);EigenvalueDecomposition e = A.eig();");
    Matrix A = Matrix.random(4,4);EigenvalueDecomposition e = A.eig();
    printResult(e.getV(),"e.getV()");
    printResult(e.getD(),"e.getD()");
  }

  public void testMatrix93() {
    println("Matrix A = Matrix.random(4,4);Sort s = A.sort(0);");
    Matrix A = Matrix.random(4,4);Sort s = A.sort(0);
    printResult(s.getIndex(),"s.getIndex()");
    printResult(s.getSortedMatrix(),"s.getSortedMatrix()");
  }

  public void testMatrix94() {
    println("Matrix A = Matrix.random(4,4);Find f = A.find(0.25,0.5);");
    Matrix A = Matrix.random(4,4);Find f = A.find(0.25,0.5);
    printResult(f.getBinaryMatrix(),"f.getBinaryMatrix()");
    printResult(f.getIndex(),"f.getIndex()");
  }

  public void testMatrix95() {
    println("Matrix A = Matrix.random(4,4);Find f = A.find( <= ,0.5);");
    Matrix A = Matrix.random(4,4);Find f = A.find("<=",0.5);
    printResult(f.getBinaryMatrix(),"f.getBinaryMatrix()");
    printResult(f.getIndex(),"f.getIndex()");
  }

  public void testMatrix96() {
    println("Matrix A = Matrix.random(10,4);Slice s = A.slice(new int[] {2,3,5,2});");
    Matrix A = Matrix.random(10,4);Slice s = A.slice(new int[] {2,3,5,2});
    printResult(s.getSlicedMatrix(),"s.getSlicedMatrix()");
  }

  public void testMatrix97() {
    println("Matrix A = Matrix.random(10,4);Slice s = A.slice(3);");
    Matrix A = Matrix.random(10,4);Slice s = A.slice(3);
    printResult(s.getSlicedMatrix(),"s.getSlicedMatrix()");
  }

  public void testMatrix98() {
    println("Matrix A = Matrix.random(1,1);double d = A.toDouble();");
    Matrix A = Matrix.random(1,1);double d = A.toDouble();
    printResult(A,"A");
    printResult(d,"d");
  }

  public void testMatrix99() {
    println("Matrix A = Matrix.random(5,4);String s = A.toString();");
    Matrix A = Matrix.random(5,4);String s = A.toString();
    printResult(A,"A");
    printResult(s,"s");
  }

  public void testMatrix100() {
    println("Matrix A = Matrix.random(5,4);String s = A.toString('|');");
    Matrix A = Matrix.random(5,4);String s = A.toString('|');
    printResult(A,"A");
    printResult(s,"s");
  }

  public void testMatrix101() {
    println("Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();");
    Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();
    printResult(A,"A");
    printResult(e.toString(),"e.toString()");
  }

  public void testMatrix102() {
    println("Matrix A = Matrix.random(5,4);A.toFile(new java.io.File( A.txt ));");
    Matrix A = Matrix.random(5,4);A.toFile(new java.io.File("A.txt"));
    printResult(A,"A");
  }

  public void testMatrix103() {
    println("Matrix A = Matrix.random(5,4);MatrixTable mt = A.toTablePanel();");
    Matrix A = Matrix.random(5,4);MatrixTable mt = A.toTablePanel();
    printResult(mt,"mt");
  }

  public void testMatrix104() {
    println("Matrix A = Matrix.random(5,2);Plot2DPanel pp = A.toPlot2DPanel( A ,0);");
    Matrix A = Matrix.random(5,2);Plot2DPanel pp = A.toPlot2DPanel("A",0);
    printResult(pp,"pp");
  }

  public void testMatrix105() {
    println("Matrix A = Matrix.random(5,3);Plot3DPanel pp = A.toPlot3DPanel( A ,1);");
    Matrix A = Matrix.random(5,3);Plot3DPanel pp = A.toPlot3DPanel("A",1);
    printResult(pp,"pp");
  }

  public void testMatrix106() {
    println("Matrix A = Matrix.random(10,2);Matrix B = Matrix.random(10,2);Plot2DPanel pp = A.toPlot2DPanel( A ,2);B.addToPlot2DPanel(pp, B ,0);");
    Matrix A = Matrix.random(10,2);Matrix B = Matrix.random(10,2);Plot2DPanel pp = A.toPlot2DPanel("A",2);B.addToPlot2DPanel(pp,"B",0);
    printResult(pp,"pp");
  }

  public void testMatrix107() {
    println("Matrix A = Matrix.random(10,3);Matrix B = Matrix.random(10,3);Plot3DPanel pp = A.toPlot3DPanel( A ,2);B.addToPlot3DPanel(pp, B ,0);");
    Matrix A = Matrix.random(10,3);Matrix B = Matrix.random(10,3);Plot3DPanel pp = A.toPlot3DPanel("A",2);B.addToPlot3DPanel(pp,"B",0);
    printResult(pp,"pp");
  }

  public void testMatrix108() {
    println("Matrix A = Matrix.random(5,4);A.toFile(new java.io.File( A.txt ));Matrix B = Matrix.fromFile(new java.io.File( A.txt ));");
    Matrix A = Matrix.random(5,4);A.toFile(new java.io.File("A.txt"));Matrix B = Matrix.fromFile(new java.io.File("A.txt"));
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix109() {
    println("Matrix A = Matrix.random(5,4);String s = A.toString();Matrix B = Matrix.fromString(s);");
    Matrix A = Matrix.random(5,4);String s = A.toString();Matrix B = Matrix.fromString(s);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix110() {
    println("Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();Matrix B = Matrix.fromXMLElement(e);");
    Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();Matrix B = Matrix.fromXMLElement(e);
    printResult(A,"A");
    printResult(B,"B");
  }

  public void testMatrix111() {
    println("Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();Matrix B = Matrix.fromXMLElement(e);");
    Matrix A = Matrix.random(5,4);org.jdom.Element e = A.toXMLElement();Matrix B = Matrix.fromXMLElement(e);
    printResult(A,"A");
    printResult(B,"B");
  }
}