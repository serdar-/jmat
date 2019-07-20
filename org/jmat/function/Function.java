package jmat.function;

import jmat.io.*;

/**
 * <p>Titre : JAva MAtrix TOols</p>
 * <p>Description : </p>
 * @author Yann RICHET
 * @version 1.0
 */

public abstract class Function implements StringPrintable, CommandLinePrintable {

	protected int argNumber;

	public Function() {
		argNumber = 0;
	}

	public Function(int n) {
		argNumber = n;
	}

	public int getArgNumber() {
		return argNumber;
	}

	public void checkArgNumber(int n) {
		if (argNumber != n) {
			throw new IllegalArgumentException("Number of arguments must equals " + argNumber);
		}
	}

	public void toCommandLine(String title) {
	}

	public String toString() {
		return new String("");
	}

}