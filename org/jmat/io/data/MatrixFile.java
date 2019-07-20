package jmat.io.data;

import jmat.data.Matrix;

import jmat.io.data.fileTools.CharFile;

import java.io.File;
import java.io.IOException;

public class MatrixFile {

	private Matrix M;
	private File file;

	public MatrixFile(File f,Matrix m) {
		M = m;
		file = f;
		printMatrix(file,M);
	}

	public MatrixFile(File f) {
		file = f;
		M = readMatrix(file);
	}

	public static void printMatrix(File f,Matrix m) {
		CharFile.toFile(f,MatrixString.printMatrix(m));
	}

	public static Matrix readMatrix (File f) {
		if (f.exists()) {
			return MatrixString.readMatrix(CharFile.fromFile(f));
		} else {
			throw new IllegalArgumentException("File does not exist.");
		}
	}

	public Matrix getMatrix() {
		return M;
	}

	public File getFile() {
		return file;
	}
}