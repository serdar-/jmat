package jmat.function;

import jmat.function.expressionParser.Evaluator;
import jmat.data.Matrix;

public class TestDoubleFunctionExpression extends TestDoubleFunction {

	private String expression;
	private String[] variables;
	private Evaluator E = new Evaluator();

	public TestDoubleFunctionExpression(String exp,String[] vars) {
		argNumber = vars.length;
		setFunction(exp,vars);
	}

	public TestDoubleFunctionExpression(String exp,String vars) {
		argNumber = 1;
		String[] variable = new String[1];
		variable[0] = vars;
		setFunction(exp,variable);
	}

	private void setFunction(String exp,String[] vars) {
		expression = exp;
		variables = vars;
	}


	private void setVariableValues(double value) {
		E.addVariable(variables[0],value);
		E.setExpression(expression);
	}

	private void setVariableValues(Matrix values) {
		for (int i = 0;i<variables.length;i++) {
			E.addVariable(variables[i],values.get(i,0));
		}
		E.setExpression(expression);
	}

	public boolean eval(double value) {
		checkArgNumber(1);
		setVariableValues(value);
		boolean bool = (((Double)(E.getValue())).doubleValue()>0.5) ? (true) : (false);
		return bool;
	}

	public boolean eval(Matrix values) {
		checkArgNumber(values.getColumnDimension());
		setVariableValues(values);
		boolean bool = (((Double)(E.getValue())).doubleValue()>0.5) ? (true) : (false);
		return bool;
	}
}
