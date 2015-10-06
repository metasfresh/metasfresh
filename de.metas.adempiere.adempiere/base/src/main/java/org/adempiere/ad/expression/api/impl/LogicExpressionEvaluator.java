package org.adempiere.ad.expression.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

public class LogicExpressionEvaluator implements IExpressionEvaluator<ILogicExpression, Boolean>
{
	public static final LogicExpressionEvaluator instance = new LogicExpressionEvaluator();

	private static final transient CLogger logger = CLogger.getCLogger(LogicExpressionEvaluator.class);

	/** Internal marker for value not found */
	private static final transient String VALUE_NotFound = new String("<<NOT FOUND>>"); // new String to make sure it's unique

	@Override
	public Boolean evaluate(final Evaluatee params, final ILogicExpression expr, final OnVariableNotFound onVariableNotFound)
	{
		final Boolean value = evaluateOrNull(params, expr, onVariableNotFound);
		return value == null ? false : value;
	}

	private Boolean evaluateOrNull(final Evaluatee params, final ILogicExpression expr, final OnVariableNotFound onVariableNotFound)
	{
		if (expr instanceof ConstantLogicExpression)
		{
			ConstantLogicExpression constant = (ConstantLogicExpression)expr;
			return constant.booleanValue();
		}
		else if (expr instanceof LogicTuple)
		{
			final LogicTuple tuple = (LogicTuple)expr;

			final String firstEval = getValue(tuple.getOperand1(), tuple.isParameter1(), params, onVariableNotFound);
			if (firstEval == VALUE_NotFound)
			{
				return null;
			}
			final String secondEval = getValue(tuple.getOperand2(), tuple.isParameter2(), params, onVariableNotFound);
			if (secondEval == VALUE_NotFound)
			{
				return null;
			}

			final boolean result = evaluateLogicTuple(firstEval, tuple.getOperator(), secondEval);

			if (CLogMgt.isLevelFinest())
			{
				logger.finest(expr.getExpressionString() + " => \"" + firstEval + "\" " + tuple.getOperator() + " \"" + secondEval + "\" => " + result);
			}

			return result;
		}
		else if (expr instanceof LogicExpression)
		{
			final LogicExpression logicExpr = (LogicExpression)expr;

			if (logicExpr.getLeft() == null)
			{
				throw new ExpressionEvaluationException("Invalid compiled expression: " + expr);
			}

			final Boolean leftValue = evaluateOrNull(params, logicExpr.getLeft(), onVariableNotFound);
			if (leftValue == null)
			{
				return null;
			}

			if (logicExpr.getRight() != null)
			{
				final Boolean rightValue = evaluateOrNull(params, logicExpr.getRight(), onVariableNotFound);
				if (rightValue == null)
				{
					return null;
				}
				if ("&".equals(logicExpr.getOperator()))
				{
					return leftValue && rightValue;
				}
				else if ("|".equals(logicExpr.getOperator()))
				{
					return leftValue || rightValue;
				}
				else
				{
					throw new ExpressionEvaluationException("Invalid operator: " + logicExpr.getOperator());
				}
			}
			else
			{
				return leftValue;
			}
		}
		else
		{
			throw new ExpressionEvaluationException("Unsupported ILogicExpression type: " + expr + " (class: " + (expr == null ? null : expr.getClass()) + ")");
		}
	}

	/**
	 * Gets parameter value from context
	 * 
	 * @param parameterName
	 * @param isParameter
	 * @param source
	 * @param onVariableNotFound
	 * @return value or {@link #VALUE_NotFound}
	 */
	private final String getValue(final String parameterName,
			final boolean isParameter,
			final Evaluatee source,
			final OnVariableNotFound onVariableNotFound)
	{
		String value;

		//
		// Case: we deal with with a parameter (which we will need to get it from context/source)
		if (isParameter)
		{
			final CtxName name = CtxName.parse(parameterName);
			value = name.getValueAsString(source);
			final boolean valueNotFound = Env.isPropertyValueNull(parameterName, value);
			
			// Give it another try in case it's and ID (backward compatibility)
			// Handling of ID compare (null => 0)
			if (valueNotFound && Env.isNumericPropertyName(parameterName))
			{
				return "0";
			}

			if (valueNotFound)
			{
				if (onVariableNotFound == OnVariableNotFound.ReturnNoResult) // i.e. !ignoreUnparsable
				{
					return VALUE_NotFound;
				}
				else if (onVariableNotFound == OnVariableNotFound.Fail)
				{
					throw new ExpressionEvaluationException("Parameter '" + name.getName() + "' not found int context"
							+ "\n Context: " + source
							+ "\n Evaluator: " + this);
				}
				else
				{
					throw new ExpressionEvaluationException("Unknown " + OnVariableNotFound.class + " value: " + onVariableNotFound);
				}
			}
		}
		//
		// Case: we deal with a constant value
		else
		{
			value = parameterName;
			// we can trim whitespaces in this case; if user really wants to have spaces at the beginning/ending of the
			// string, he/she shall quote it
			value = value.trim();
			value = stripQuotes(value);
		}

		return value;
	}

	/**
	 * Evaluate Logic Tuple
	 * 
	 * @param value1 value
	 * @param operand operand = ~ ^ ! > <
	 * @param value2
	 * @return evaluation
	 */
	private boolean evaluateLogicTuple(final String value1, final String operand, final String value2)
	{
		if (value1 == null || operand == null || value2 == null)
		{
			return false;
		}

		final String value1Str = stripQuotes(value1);
		final String value2Str = stripQuotes(value2);

		BigDecimal value1bd = null;
		BigDecimal value2bd = null;
		try
		{
			if (!value1.startsWith("'"))
				value1bd = new BigDecimal(value1);
			if (!value2.startsWith("'"))
				value2bd = new BigDecimal(value2);
		}
		catch (Exception e)
		{
			value1bd = null;
			value2bd = null;
		}
		//
		if (operand.equals("="))
		{
			if (value1bd != null && value2bd != null)
				return value1bd.compareTo(value2bd) == 0;
			return value1Str.compareTo(value2Str) == 0;
		}
		else if (operand.equals("<"))
		{
			if (value1bd != null && value2bd != null)
				return value1bd.compareTo(value2bd) < 0;
			return value1Str.compareTo(value2Str) < 0;
		}
		else if (operand.equals(">"))
		{
			if (value1bd != null && value2bd != null)
				return value1bd.compareTo(value2bd) > 0;
			return value1Str.compareTo(value2Str) > 0;
		}
		else if (operand.equals("!")
				|| operand.equals("^") // metas: cg: support legacy NOT operator
				|| operand.equals("~") // metas: cg: support legacy NOT operator
		)
		{
			if (value1bd != null && value2bd != null)
				return value1bd.compareTo(value2bd) != 0;
			return value1Str.compareTo(value2Str) != 0;
		}
		else
		{
			// shall not happen because expression was already compiled
			throw new ExpressionEvaluationException("Unknown operator '" + operand + "' while evaluating '" + value1 + " " + operand + " " + value2 + "'");
		}
	} // evaluateLogicTuple

	/**
	 * Strips quotes (" or ') from given string
	 * 
	 * @param s
	 * @return string without quotes
	 */
	// set as protected to be able to test it
	protected final String stripQuotes(final String s)
	{
		if (s == null || s.isEmpty())
		{
			return s;
		}

		final int len = s.length();
		if (len <= 1)
		{
			return s;
		}

		if (s.startsWith("'") && s.endsWith("'"))
		{
			return s.substring(1, len - 1);
		}
		else if (s.startsWith("\"") && s.endsWith("\""))
		{
			return s.substring(1, len - 1);
		}

		return s;
	}

	@Override
	public boolean isNoResult(Object result)
	{
		// because evaluation is throwing exception in case of failure, the only "no result" would be the NULL
		return result == null;
	}

}
