package org.adempiere.ad.expression.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Map;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;

public class LogicExpressionEvaluator implements IExpressionEvaluator<ILogicExpression, Boolean>
{
	public static final LogicExpressionEvaluator instance = new LogicExpressionEvaluator();

	private static final transient Logger logger = LogManager.getLogger(LogicExpressionEvaluator.class);

	/** Internal marker for value not found */
	private static final transient String VALUE_NotFound = new String("<<NOT FOUND>>"); // new String to make sure it's unique

	private static interface BooleanValueSupplier
	{
		Boolean getValueOrNull();
	};

	private static interface BooleanEvaluator
	{
		Boolean evaluateOrNull(BooleanValueSupplier left, BooleanValueSupplier right);
	};

	private static final BooleanEvaluator EVALUATOR_AND = (left, right) -> {
		Boolean leftValue = null;
		ExpressionEvaluationException leftValueError = null;
		try
		{
			leftValue = left.getValueOrNull();
		}
		catch (final ExpressionEvaluationException ex)
		{
			leftValue = null;
			leftValueError = ex;
		}

		if (leftValue == Boolean.FALSE)
		{
			// does not matter the value of right expression
			return Boolean.FALSE;
		}

		Boolean rightValue = null;
		try
		{
			rightValue = right.getValueOrNull();
		}
		catch (final ExpressionEvaluationException rightValueError)
		{
			rightValue = null;
			if (leftValueError != null)
			{
				rightValueError.addSuppressed(leftValueError);
			}
			throw rightValueError;
		}

		if (rightValue == null)
		{
			if (leftValueError != null)
			{
				throw leftValueError;
			}
			return null;
		}
		else if (rightValue == Boolean.FALSE)
		{
			return Boolean.FALSE;
		}
		else // rightValue == TRUE
		{
			if (leftValueError != null)
			{
				throw leftValueError;
			}
			else if (leftValue == null)
			{
				return null;
			}
			else // leftValue == TRUE and rightValue=TRUE
			{
				return Boolean.TRUE;
			}
		}
	};

	private static final BooleanEvaluator EVALUATOR_OR = (left, right) -> {
		Boolean leftValue = null;
		ExpressionEvaluationException leftValueError = null;
		try
		{
			leftValue = left.getValueOrNull();
		}
		catch (final ExpressionEvaluationException ex)
		{
			leftValue = null;
			leftValueError = ex;
		}

		if (leftValue == Boolean.TRUE)
		{
			// does not matter the value of right expression
			return Boolean.TRUE;
		}

		Boolean rightValue = null;
		try
		{
			rightValue = right.getValueOrNull();
		}
		catch (final ExpressionEvaluationException rightValueError)
		{
			rightValue = null;
			if (leftValueError != null)
			{
				rightValueError.addSuppressed(leftValueError);
			}
			throw rightValueError;
		}

		if (rightValue == null)
		{
			if (leftValueError != null)
			{
				throw leftValueError;
			}
			return null;
		}
		else if (rightValue == Boolean.TRUE)
		{
			return Boolean.TRUE;
		}
		else // rightValue == FALSE
		{
			if (leftValueError != null)
			{
				throw leftValueError;
			}
			else if (leftValue == null)
			{
				return null;
			}
			else // leftValue == FALSE and rightValue=FALSE
			{
				return Boolean.FALSE;
			}
		}
	};

	private static final Map<String, BooleanEvaluator> EVALUATORS_ByOperator = ImmutableMap.<String, LogicExpressionEvaluator.BooleanEvaluator> builder()
			.put(AbstractLogicExpression.LOGIC_OPERATOR_AND, EVALUATOR_AND)
			.put(AbstractLogicExpression.LOGIC_OPERATOR_OR, EVALUATOR_OR)
			.build();

	@Override
	public Boolean evaluate(final Evaluatee params, final ILogicExpression expr, final OnVariableNotFound onVariableNotFound)
	{
		final Boolean value = evaluateOrNull(params, expr, onVariableNotFound);
		final boolean valueFinal = value == null ? false : value;
		logger.trace("Evaluated {} => {} => {}", expr, value, valueFinal);

		return valueFinal;
	}

	private Boolean evaluateOrNull(final Evaluatee params, final ILogicExpression expr, final OnVariableNotFound onVariableNotFound)
	{
		logger.trace("Evaluating {}", expr);

		if (expr instanceof ConstantLogicExpression)
		{
			final ConstantLogicExpression constant = (ConstantLogicExpression)expr;
			final boolean result = constant.booleanValue();
			logger.trace("tuple {} => {} (constant)", expr, result);
			return result;
		}
		else if (expr instanceof LogicTuple)
		{
			final LogicTuple tuple = (LogicTuple)expr;

			final String firstEval = getValue(tuple.getOperand1(), params, onVariableNotFound);
			if (firstEval == VALUE_NotFound)
			{
				logger.trace("tuple {} => null because first operand could not be evaluated", expr);
				return null;
			}
			final String secondEval = getValue(tuple.getOperand2(), params, onVariableNotFound);
			if (secondEval == VALUE_NotFound)
			{
				logger.trace("tuple {} => null because second operand could not be evaluated", expr);
				return null;
			}

			final String operator = tuple.getOperator();
			final boolean result = evaluateLogicTuple(firstEval, operator, secondEval);
			logger.trace("tuple {} => \"{}\" {} \"{}\" => {}", expr, firstEval, operator, secondEval, result);

			return result;
		}
		else if (expr instanceof LogicExpression)
		{
			final LogicExpression logicExpr = (LogicExpression)expr;

			// Left value
			final ILogicExpression leftExpression = logicExpr.getLeft();
			if (leftExpression == null)
			{
				throw new ExpressionEvaluationException("Invalid compiled expression: " + expr + " (left expression is missing)");
			}
			final BooleanValueSupplier leftValueSupplier = () -> evaluateOrNull(params, leftExpression, onVariableNotFound);

			// Right value
			final ILogicExpression rightExpression = logicExpr.getRight();
			if (rightExpression == null)
			{
				final Boolean leftValue = leftValueSupplier.getValueOrNull();
				logger.trace("expression {} => {} (only left expression was considered because right is missing)", expr, leftValue);
				return leftValue;
			}
			final BooleanValueSupplier rightValueSupplier = () -> evaluateOrNull(params, rightExpression, onVariableNotFound);

			// Boolean evaluator
			final String logicOperator = logicExpr.getOperator();
			final BooleanEvaluator logicExprEvaluator = EVALUATORS_ByOperator.get(logicOperator);
			if (logicExprEvaluator == null)
			{
				// shall not happen because expression was already compiled and validated
				throw new ExpressionEvaluationException("Invalid operator: " + logicOperator);
			}

			final Boolean result = logicExprEvaluator.evaluateOrNull(leftValueSupplier, rightValueSupplier);
			logger.trace("expression {} => {}", logicExpr, result);
			return result;
		}
		else
		{
			throw new ExpressionEvaluationException("Unsupported ILogicExpression type: " + expr + " (class: " + (expr == null ? null : expr.getClass()) + ")");
		}
	}

	/**
	 * Gets parameter value from context
	 *
	 * @param operand
	 * @param source
	 * @param onVariableNotFound
	 * @return value or {@link #VALUE_NotFound}
	 */
	private final String getValue(final Object operand, final Evaluatee source, final OnVariableNotFound onVariableNotFound)
	{
		String value;

		//
		// Case: we deal with with a parameter (which we will need to get it from context/source)
		if (operand instanceof CtxName)
		{
			final CtxName ctxName = (CtxName)operand;
			value = ctxName.getValueAsString(source);
			final boolean valueNotFound = Env.isPropertyValueNull(ctxName.getName(), value);

			// Give it another try in case it's and ID (backward compatibility)
			// Handling of ID compare (null => 0)
			if (valueNotFound && Env.isNumericPropertyName(ctxName.getName()))
			{
				final String defaultValue = "0";
				logger.trace("Evaluated {}={} (default value)", ctxName, defaultValue);
				return defaultValue;
			}

			if (valueNotFound)
			{
				if (onVariableNotFound == OnVariableNotFound.ReturnNoResult)
				{
					// i.e. !ignoreUnparsable
					logger.trace("Evaluated {}=<value not found>", ctxName);
					return VALUE_NotFound;
				}
				else if (onVariableNotFound == OnVariableNotFound.Fail)
				{
					throw new ExpressionEvaluationException("Parameter '" + ctxName.getName() + "' not found in context"
							+ "\n Context: " + source
							+ "\n Evaluator: " + this);
				}
				else
				{
					throw new ExpressionEvaluationException("Unknown " + OnVariableNotFound.class + " value: " + onVariableNotFound);
				}
			}

			logger.trace("Evaluated context variable {}={}", ctxName, value);
		}
		//
		// Case: we deal with a constant value
		else
		{
			value = operand.toString();
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
			{
				value1bd = new BigDecimal(value1);
			}
			if (!value2.startsWith("'"))
			{
				value2bd = new BigDecimal(value2);
			}
		}
		catch (final Exception e)
		{
			value1bd = null;
			value2bd = null;
		}
		//
		if (operand.equals("="))
		{
			if (value1bd != null && value2bd != null)
			{
				return value1bd.compareTo(value2bd) == 0;
			}
			return value1Str.compareTo(value2Str) == 0;
		}
		else if (operand.equals("<"))
		{
			if (value1bd != null && value2bd != null)
			{
				return value1bd.compareTo(value2bd) < 0;
			}
			return value1Str.compareTo(value2Str) < 0;
		}
		else if (operand.equals(">"))
		{
			if (value1bd != null && value2bd != null)
			{
				return value1bd.compareTo(value2bd) > 0;
			}
			return value1Str.compareTo(value2Str) > 0;
		}
		else if (operand.equals("!")
				|| operand.equals("^") // metas: cg: support legacy NOT operator
				|| operand.equals("~") // metas: cg: support legacy NOT operator
		)
		{
			if (value1bd != null && value2bd != null)
			{
				return value1bd.compareTo(value2bd) != 0;
			}
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
	public boolean isNoResult(final Object result)
	{
		// because evaluation is throwing exception in case of failure, the only "no result" would be the NULL
		return result == null;
	}

}
