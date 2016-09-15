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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.ILogicExpressionEvaluator;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;

public class LogicExpressionEvaluator implements ILogicExpressionEvaluator
{
	public static final LogicExpressionEvaluator instance = new LogicExpressionEvaluator();

	private static final transient Logger logger = LogManager.getLogger(LogicExpressionEvaluator.class);

	/** Internal marker for value not found */
	private static final transient String VALUE_NotFound = new String("<<NOT FOUND>>"); // new String to make sure it's unique

	/* package */static interface BooleanValueSupplier
	{
		Boolean getValueOrNull();
	};

	/* package */static interface BooleanEvaluator
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

		if (Boolean.FALSE.equals(leftValue))
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
		else if (Boolean.FALSE.equals(rightValue))
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

		if (Boolean.TRUE.equals(leftValue))
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
		else if (Boolean.TRUE.equals(rightValue))
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

	private static final BooleanEvaluator EVALUATOR_XOR = (left, right) -> {
		final Boolean leftValue = left.getValueOrNull();
		if (leftValue == null)
		{
			return null;
		}

		final Boolean rightValue = right.getValueOrNull();
		if (rightValue == null)
		{
			return null;
		}

		return leftValue.booleanValue() != rightValue.booleanValue();
	};

	/* package */static final Map<String, BooleanEvaluator> EVALUATORS_ByOperator = ImmutableMap.<String, LogicExpressionEvaluator.BooleanEvaluator> builder()
			.put(ILogicExpression.LOGIC_OPERATOR_AND, EVALUATOR_AND)
			.put(ILogicExpression.LOGIC_OPERATOR_OR, EVALUATOR_OR)
			.put(ILogicExpression.LOGIC_OPERATOR_XOR, EVALUATOR_XOR)
			.build();

	@Override
	public Boolean evaluate(final Evaluatee params, final ILogicExpression expr, final OnVariableNotFound onVariableNotFound)
	{
		final ExpressionEvaluationContext ctx = new ExpressionEvaluationContext(params, onVariableNotFound);
		final Boolean value = evaluateOrNull(ctx, expr);
		final boolean valueFinal = value == null ? false : value;
		logger.trace("Evaluated {} => {} => {}", expr, value, valueFinal);

		return valueFinal;
	}

	@Override
	public LogicExpressionResult evaluateToResult(final Evaluatee params, final ILogicExpression expr, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final ExpressionEvaluationContext ctx = new ExpressionEvaluationContext(params, onVariableNotFound);
		final Boolean value = evaluateOrNull(ctx, expr);
		final LogicExpressionResult result = LogicExpressionResult.of(value, expr, ctx.getUsedParameters());

		logger.trace("Evaluated {} => {} => {}", expr, value, result);

		return result;
	}

	private Boolean evaluateOrNull(final ExpressionEvaluationContext ctx, final ILogicExpression expr)
	{
		logger.trace("Evaluating {}", expr);

		if (expr == null)
		{
			throw new ExpressionEvaluationException("Cannot evaluate null expression");
		}
		else if (expr.isConstant())
		{
			final boolean result = expr.constantValue();
			logger.trace("constant {} => {}", expr, result);
			return result;
		}
		else if (expr instanceof LogicTuple)
		{
			final LogicTuple tuple = (LogicTuple)expr;

			final String firstEval = ctx.getValue(tuple.getOperand1());
			if (firstEval == VALUE_NotFound)
			{
				logger.trace("tuple {} => null because first operand could not be evaluated", expr);
				return null;
			}
			final String secondEval = ctx.getValue(tuple.getOperand2());
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
			final BooleanValueSupplier leftValueSupplier = () -> evaluateOrNull(ctx, leftExpression);

			// Right value
			final ILogicExpression rightExpression = logicExpr.getRight();
			if (rightExpression == null)
			{
				final Boolean leftValue = leftValueSupplier.getValueOrNull();
				logger.trace("expression {} => {} (only left expression was considered because right is missing)", expr, leftValue);
				return leftValue;
			}
			final BooleanValueSupplier rightValueSupplier = () -> evaluateOrNull(ctx, rightExpression);

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
			throw new ExpressionEvaluationException("Unsupported ILogicExpression type: " + expr + " (class: " + expr.getClass() + ")");
		}
	}

	/**
	 * Evaluate Logic Tuple
	 *
	 * @param value1 value
	 * @param operand operand = ~ ^ ! > <
	 * @param value2
	 * @return evaluation
	 */
	private boolean evaluateLogicTuple(final String valueObj1, final String operand, final String valueObj2)
	{
		if (valueObj1 == null || operand == null || valueObj2 == null)
		{
			return false;
		}

		//
		// Try comparing BigDecimals
		try
		{
			if (!valueObj1.startsWith("'"))
			{
				final BigDecimal value1bd = new BigDecimal(valueObj1);

				if (!valueObj2.startsWith("'"))
				{
					final BigDecimal value2bd = new BigDecimal(valueObj2);

					return evaluateLogicTupleForComparables(value1bd, operand, value2bd);
				}
			}
		}
		catch (final Exception ex)
		{
			logger.trace("Failed extracting BigDecimals but going forward", ex);
		}

		//
		// Try comparing as Strings
		{
			final String value1Str = stripQuotes(valueObj1);
			final String value2Str = stripQuotes(valueObj2);
			return evaluateLogicTupleForComparables(value1Str, operand, value2Str);
		}
	}

	private final <T> boolean evaluateLogicTupleForComparables(final Comparable<T> value1, final String operand, final T value2)
	{
		//
		if (operand.equals(LogicTuple.OPERATOR_Equals))
		{
			return value1.compareTo(value2) == 0;
		}
		else if (operand.equals(LogicTuple.OPERATOR_LessThan))
		{
			return value1.compareTo(value2) < 0;
		}
		else if (operand.equals(LogicTuple.OPERATOR_GreaterThan))
		{
			return value1.compareTo(value2) > 0;
		}
		else if (operand.equals(LogicTuple.OPERATOR_NotEquals))
		{
			return value1.compareTo(value2) != 0;
		}
		else
		{
			// shall not happen because expression was already compiled
			throw new ExpressionEvaluationException("Unknown operator '" + operand + "' while evaluating '" + value1 + " " + operand + " " + value2 + "'");
		}
	}

	/**
	 * Strips quotes (" or ') from given string
	 *
	 * @param s
	 * @return string without quotes
	 */
	@VisibleForTesting
	/* package */static final String stripQuotes(final String s)
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

	private static final class ExpressionEvaluationContext
	{
		private final Evaluatee params;
		private final OnVariableNotFound onVariableNotFound;

		private Map<CtxName, String> ctxNameValues = null; // lazy

		private ExpressionEvaluationContext(final Evaluatee params, final OnVariableNotFound onVariableNotFound)
		{
			super();

			// NOTE: null is OK in case we really don't need the params
			this.params = params;

			this.onVariableNotFound = onVariableNotFound;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("onVariableNotFound", onVariableNotFound)
					.add("params", params)
					.toString();
		}

		/**
		 * Gets parameter value from context
		 *
		 * @param operand
		 * @return value or {@link #VALUE_NotFound}
		 */
		public final String getValue(final Object operand) throws ExpressionEvaluationException
		{
			//
			// Case: we deal with with a parameter (which we will need to get it from context/source)
			if (operand instanceof CtxName)
			{
				final CtxName ctxName = (CtxName)operand;
				if (ctxNameValues == null)
				{
					ctxNameValues = new LinkedHashMap<>();
				}
				final String value = ctxNameValues.computeIfAbsent(ctxName, this::resolveCtxName);
				logger.trace("Evaluated context variable {}={}", ctxName, value);
				return value;
			}
			//
			// Case: we deal with a constant value
			else
			{
				String value = operand.toString();
				// we can trim whitespaces in this case; if user really wants to have spaces at the beginning/ending of the
				// string, he/she shall quote it
				value = value.trim();
				value = stripQuotes(value);
				return value;
			}
		}

		private final String resolveCtxName(final CtxName ctxName)
		{
			final String value = ctxName.getValueAsString(params);
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
							+ "\n Context: " + params
							+ "\n Evaluator: " + this);
				}
				else
				{
					throw new ExpressionEvaluationException("Unknown " + OnVariableNotFound.class + " value: " + onVariableNotFound);
				}
			}

			return value;
		}

		@Nullable
		public Map<CtxName, String> getUsedParameters()
		{
			return ctxNameValues;
		}
	}
}
