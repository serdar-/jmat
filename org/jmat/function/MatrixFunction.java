package jmat.function;

import jmat.data.Matrix;

/**
 * <p>Titre : JAva MAtrix TOols</p>
 * <p>Description : </p>
 * @author Yann RICHET
 * @version 1.0
 */

public abstract class MatrixFunction extends Function {

	public abstract Matrix eval(Matrix[] values);

}