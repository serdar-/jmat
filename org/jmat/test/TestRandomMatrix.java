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
public class TestRandomMatrix {

  public TestRandomMatrix() {
    testRandomMatrix1();
    testRandomMatrix2();
    testRandomMatrix3();
    testRandomMatrix4();
    testRandomMatrix5();
    testRandomMatrix6();
    testRandomMatrix7();
    testRandomMatrix8();
    testRandomMatrix9();
    testRandomMatrix10();
    testRandomMatrix11();
    testRandomMatrix12();
    testRandomMatrix13();
    testRandomMatrix14();
    testRandomMatrix15();
    testRandomMatrix16();
    testRandomMatrix17();
    testRandomMatrix18();
    testRandomMatrix19();
    testRandomMatrix20();
    testRandomMatrix21();
    testRandomMatrix22();
    testRandomMatrix23();
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


  public void testRandomMatrix1() {
    println("RandomMatrix M = new RandomMatrix(3,2);");
    RandomMatrix M = new RandomMatrix(3,2);
    printResult(M,"M");
  }

  public void testRandomMatrix2() {
    println("RandomMatrix M = new RandomMatrix(3,2,1.5);");
    RandomMatrix M = new RandomMatrix(3,2,1.5);
    printResult(M,"M");
  }

  public void testRandomMatrix3() {
    println("double[][] B = {{1,2},{3,4},{5,6}};RandomMatrix M = new RandomMatrix(B);");
    double[][] B = {{1,2},{3,4},{5,6}};RandomMatrix M = new RandomMatrix(B);
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testRandomMatrix4() {
    println("double[][] B = {{1,2},{3,4},{5,6}};RandomMatrix M = new RandomMatrix(B,1,2);");
    double[][] B = {{1,2},{3,4},{5,6}};RandomMatrix M = new RandomMatrix(B,1,2);
    printResult(B,"B");
    printResult(M,"M");
  }

  public void testRandomMatrix5() {
    println("double[] vals = {1,2,3,4,5,6};RandomMatrix M = new RandomMatrix(vals,3);");
    double[] vals = {1,2,3,4,5,6};RandomMatrix M = new RandomMatrix(vals,3);
    printResult(vals,"vals");
    printResult(M,"M");
  }

  public void testRandomMatrix6() {
    println("RandomMatrix M = RandomMatrix.uniform(3,2,5,6);");
    RandomMatrix M = RandomMatrix.uniform(3,2,5,6);
    printResult(M,"M");
  }

  public void testRandomMatrix7() {
    println("RandomMatrix M = RandomMatrix.dirac(3,2,Matrix.random(10,2));");
    RandomMatrix M = RandomMatrix.dirac(3,2,Matrix.random(10,2));
    printResult(M,"M");
  }

  public void testRandomMatrix8() {
    println("RandomMatrix M = RandomMatrix.normal(3,2,10,2);");
    RandomMatrix M = RandomMatrix.normal(3,2,10,2);
    printResult(M,"M");
  }

  public void testRandomMatrix9() {
    println("RandomMatrix M = RandomMatrix.logNormal(3,2,10,2);");
    RandomMatrix M = RandomMatrix.logNormal(3,2,10,2);
    printResult(M,"M");
  }

  public void testRandomMatrix10() {
    println("RandomMatrix M = RandomMatrix.exponential(3,2,10);");
    RandomMatrix M = RandomMatrix.exponential(3,2,10);
    printResult(M,"M");
  }

  public void testRandomMatrix11() {
    println("RandomMatrix M = RandomMatrix.triangular(3,2,10,15);");
    RandomMatrix M = RandomMatrix.triangular(3,2,10,15);
    printResult(M,"M");
  }

  public void testRandomMatrix12() {
    println("RandomMatrix M = RandomMatrix.triangular(3,2,10,15,16);");
    RandomMatrix M = RandomMatrix.triangular(3,2,10,15,16);
    printResult(M,"M");
  }

  public void testRandomMatrix13() {
    println("RandomMatrix M = RandomMatrix.beta(3,2,10,15);");
    RandomMatrix M = RandomMatrix.beta(3,2,10,15);
    printResult(M,"M");
  }

  public void testRandomMatrix14() {
    println("RandomMatrix M = RandomMatrix.cauchy(3,2,10,1);");
    RandomMatrix M = RandomMatrix.cauchy(3,2,10,1);
    printResult(M,"M");
  }

  public void testRandomMatrix15() {
    println("RandomMatrix M = RandomMatrix.weibull(3,2,0.5,0.5);");
    RandomMatrix M = RandomMatrix.weibull(3,2,0.5,0.5);
    printResult(M,"M");
  }

  public void testRandomMatrix16() {
    println("RandomMatrix M = RandomMatrix.rejection(3,2,new DoubleFunctionExpression( sin(x) , x ),0,1);");
    RandomMatrix M = RandomMatrix.rejection(3,2,new DoubleFunctionExpression("sin(x)","x"),0,1);
    printResult(M,"M");
  }

  public void testRandomMatrix17() {
    println("Matrix A = Matrix.random(2,2);RandomMatrix M = RandomMatrix.sampleWithReplacement(3,2,A);");
    Matrix A = Matrix.random(2,2);RandomMatrix M = RandomMatrix.sampleWithReplacement(3,2,A);
    printResult(A,"A");
    printResult(M,"M");
  }

  public void testRandomMatrix18() {
    println("Matrix A = Matrix.random(3,3);RandomMatrix M = RandomMatrix.sampleWithoutReplacement(3,2,A);");
    Matrix A = Matrix.random(3,3);RandomMatrix M = RandomMatrix.sampleWithoutReplacement(3,2,A);
    printResult(A,"A");
    printResult(M,"M");
  }

  public void testRandomMatrix19() {
    println("RandomMatrix A = RandomMatrix.normal(20,1,10,1);Plot2DPanel pp = A.toHist2DPanel( A ,5);");
    RandomMatrix A = RandomMatrix.normal(20,1,10,1);Plot2DPanel pp = A.toHist2DPanel("A",5);
    printResult(pp,"pp");
  }

  public void testRandomMatrix20() {
    println("RandomMatrix A = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toHist3DPanel( A ,5);");
    RandomMatrix A = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toHist3DPanel("A",5);
    printResult(pp,"pp");
  }

  public void testRandomMatrix21() {
    println("Matrix A = Matrix.random(10,2);RandomMatrix B = RandomMatrix.normal(20,1,10,1);Plot2DPanel pp = A.toPlot2DPanel( A ,2);B.addHistToPlot2DPanel(pp, B ,5);");
    Matrix A = Matrix.random(10,2);RandomMatrix B = RandomMatrix.normal(20,1,10,1);Plot2DPanel pp = A.toPlot2DPanel("A",2);B.addHistToPlot2DPanel(pp,"B",5);
    printResult(pp,"pp");
  }

  public void testRandomMatrix22() {
    println("Matrix A = Matrix.random(10,3);RandomMatrix B = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toPlot3DPanel( A ,2);B.addHistToPlot3DPanel(pp, B ,5);");
    Matrix A = Matrix.random(10,3);RandomMatrix B = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toPlot3DPanel("A",2);B.addHistToPlot3DPanel(pp,"B",5);
    printResult(pp,"pp");
  }

  public void testRandomMatrix23() {
    println("Matrix A = Matrix.random(10,3);RandomMatrix B = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toPlot3DPanel( A ,2);B.addHistToPlot3DPanel(pp, B ,5);");
    Matrix A = Matrix.random(10,3);RandomMatrix B = RandomMatrix.normal(100,2,10,1);Plot3DPanel pp = A.toPlot3DPanel("A",2);B.addHistToPlot3DPanel(pp,"B",5);
    printResult(pp,"pp");
  }
}