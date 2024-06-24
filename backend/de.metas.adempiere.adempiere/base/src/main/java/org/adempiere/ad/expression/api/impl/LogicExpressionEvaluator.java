package org.adempiere.ad.expression.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.ILogicExpressionEvaluator;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LogicExpressionEvaluator implements ILogicExpressionEvaluator
{
	public static final LogicExpressionEvaluator instance = new LogicExpressionEvaluator();

	private static final transient Logger logger = LogManager.getLogger(LogicExpressionEvaluator.class);

	/* Internal marker for value not found */
	@SuppressWarnings("StringOperationCanBeSimplified")
	static final transient String VALUE_NotFound = new String("<<NOT FOUND>>"); // new String to make sure it's unique

	/* package */interface BooleanValueSupplier
	{
		Boolean getValueOrNull();
	}

	/* package */interface BooleanEvaluator
	{
		Boolean evaluateOrNull(BooleanValueSupplier left, BooleanValueSupplier right);
	}

	private static final BooleanEvaluator EVALUATOR_AND = (left, right) -> {
		Boolean leftValue;
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

		Boolean rightValue;
		try
		{
			rightValue = right.getValueOrNull();
		}
		catch (final ExpressionEvaluationException rightValueError)
		{
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
		Boolean leftValue;
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

		Boolean rightValue;
		try
		{
			rightValue = right.getValueOrNull();
		}
		catch (final ExpressionEvaluationException rightValueError)
		{
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

	/* package */static final Map<String, BooleanEvaluator> EVALUATORS_ByOperator = ImmutableMap.<String, LogicExpressionEvaluator.BooleanEvaluator>builder()
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

	@Nullable
	private Boolean evaluateOrNull(final ExpressionEvaluationContext ctx, final ILogicExpression expr)
	{
		logger.trace("Evaluating {}", expr);

		if (expr == null)
		{
			throw ExpressionEvaluationException.newWithPlainMessage("Cannot evaluate null expression");
		}

		try
		{
			if (expr.isConstant())
			{
				final boolean result = expr.constantValue();
				logger.trace("constant {} => {}", expr, result);
				return result;
			}
			else if (expr instanceof LogicTuple)
			{
				final LogicTuple tuple = (LogicTuple)expr;

				final String firstEval = ctx.getValue(tuple.getOperand1());
				//noinspection StringEquality // we're using string == string instead of string.equals(string)
				if (firstEval == VALUE_NotFound)
				{
					logger.trace("tuple {} => null because first operand could not be evaluated", expr);
					return null;
				}
				final String secondEval = ctx.getValue(tuple.getOperand2());
				//noinspection StringEquality // we're using string == string instead of string.equals(string)
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
					throw ExpressionEvaluationException.newWithPlainMessage("Invalid compiled expression: " + expr + " (left expression is missing)");
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
				final BooleanEvaluator logicExprEvaluator = getBooleanEvaluatorByOperator(logicOperator);

				final Boolean result = logicExprEvaluator.evaluateOrNull(leftValueSupplier, rightValueSupplier);
				logger.trace("expression {} => {}", logicExpr, result);
				return result;
			}
			else
			{
				throw ExpressionEvaluationException.newWithPlainMessage("Unsupported ILogicExpression type: " + expr + " (class: " + expr.getClass() + ")");
			}
		}
		catch (final Exception ex)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(ex)
					.setParameter("expression", expr)
					.setParameter("context", ctx)
					.appendParametersToMessage();
		}
	}

	static BooleanEvaluator getBooleanEvaluatorByOperator(final String logicOperator)
	{
		final BooleanEvaluator logicExprEvaluator = EVALUATORS_ByOperator.get(logicOperator);
		if (logicExprEvaluator == null)
		{
			// shall not happen because expression was already compiled and validated
			throw ExpressionEvaluationException.newWithPlainMessage("Invalid operator: " + logicOperator);
		}
		return logicExprEvaluator;
	}

	/**
	 * Evaluate Logic Tuple
	 *
	 * @param valueObj1 value
	 * @param operand   operands: = ~ ^ ! > <
	 * @param valueObj2 value
	 * @return evaluation
	 */
	static boolean evaluateLogicTuple(@Nullable final String valueObj1, final String operand, @Nullable final String valueObj2)
	{
		if (valueObj1 == null || operand == null || valueObj2 == null)
		{
			return false;
		}

		boolean stringCaseAlreadyChecked = false;
		final String value1Str = stripQuotes(valueObj1);
		final String value2Str = stripQuotes(valueObj2);

		//
		// Try comparing as Strings first for Equals case (short circuit)
		if (LogicTuple.OPERATOR_Equals.equals(operand))
		{
			if (evaluateLogicTupleForComparables(value1Str, operand, value2Str))
			{
				return true;
			}
			stringCaseAlreadyChecked = true;
		}

		//
		// Try comparing BigDecimals
		// If both values are numbers there's no need to retry string comparison
		try
		{
			if (isPossibleNumber(value1Str)
					&& isPossibleNumber(value2Str))
			{
				final BigDecimal value1BD = new BigDecimal(value1Str);
				final BigDecimal value2BD = new BigDecimal(value2Str);
				return evaluateLogicTupleForComparables(value1BD, operand, value2BD);
			}
		}
		catch (final Exception ex)
		{
			logger.trace("Failed extracting BigDecimals but going forward (value1Str={}, value2Str={})", value1Str, value2Str, ex);
		}

		//
		// Try comparing as Strings first because it's faster then trying to convert to BigDecimal
		if (!stringCaseAlreadyChecked)
		{
			return evaluateLogicTupleForComparables(value1Str, operand, value2Str);
		}

		//
		// Not matched
		return false;
	}

	@VisibleForTesting
	static boolean isPossibleNumber(@Nullable final String valueStr)
	{
		if (valueStr == null || valueStr.isEmpty())
		{
			return false;
		}
		if (valueStr.startsWith("'"))
		{
			return false;
		}

		for (int i = 0, length = valueStr.length(); i < length; i++)
		{
			final char ch = valueStr.charAt(i);
			final boolean validNumberChar = ch == '+' || ch == '-'
					|| ch == '.'
					|| Character.isDigit(ch);

			if (!validNumberChar)
			{
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("IfCanBeSwitch")
	private static <T> boolean evaluateLogicTupleForComparables(@Nullable final Comparable<T> value1, final String operand, @Nullable final T value2)
	{
		if (value1 == null || operand == null || value2 == null)
		{
			return false;
		}

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
			throw ExpressionEvaluationException.newWithPlainMessage("Unknown operator '" + operand + "' while evaluating '" + value1 + " " + operand + " " + value2 + "'");
		}
	}

	/**
	 * Strips quotes (" or ') from given string
	 *
	 * @return string without quotes
	 */
	@Nullable
	@VisibleForTesting
	/* package */ static String stripQuotes(final String s)
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
	public ILogicExpression evaluatePartial(final Evaluatee params, final ILogicExpression expr)
	{
		final ExpressionEvaluationContext ctx = new ExpressionEvaluationContext(params, OnVariableNotFound.ReturnNoResult);
		return evaluatePartial(ctx, expr);
	}

	private ILogicExpression evaluatePartial(final ExpressionEvaluationContext ctx, final ILogicExpression expr)
	{
		logger.trace("Partial evaluating {}", expr);

		if (expr == null)
		{
			throw ExpressionEvaluationException.newWithPlainMessage("Cannot evaluate null expression");
		}
		else if (expr.isConstant())
		{
			return expr;
		}
		else if (expr instanceof LogicTuple)
		{
			final LogicTuple tuple = (LogicTuple)expr;

			final Object operand1 = tuple.getOperand1();
			final String operand1Resolved = ctx.getValue(operand1);
			@SuppressWarnings("StringEquality")
			final boolean isOperand1Resolved = operand1Resolved != VALUE_NotFound;
			final Object newOperand1 = isOperand1Resolved ? operand1Resolved : operand1;

			final Object operand2 = tuple.getOperand2();
			final String operand2Resolved = ctx.getValue(operand2);
			@SuppressWarnings("StringEquality")
			final boolean isOperand2Resolved = operand2Resolved != VALUE_NotFound;
			final Object newOperand2 = isOperand2Resolved ? operand2Resolved : operand2;

			if (isOperand1Resolved && isOperand2Resolved)
			{
				final boolean result = evaluateLogicTuple(operand1Resolved, tuple.getOperator(), operand2Resolved);
				return ConstantLogicExpression.of(result);
			}
			else if (Objects.equals(operand1, operand1Resolved)
					&& Objects.equals(operand2, operand2Resolved))
			{
				return tuple;
			}
			else
			{
				return LogicTuple.of(newOperand1, tuple.getOperator(), newOperand2);
			}
		}
		else if (expr instanceof LogicExpression)
		{
			final LogicExpression logicExpr = (LogicExpression)expr;

			final ILogicExpression leftExpression = logicExpr.getLeft();
			final ILogicExpression newLeftExpression = evaluatePartial(ctx, leftExpression);

			final ILogicExpression rightExpression = logicExpr.getRight();
			final ILogicExpression newRightExpression = evaluatePartial(ctx, rightExpression);

			final String logicOperator = logicExpr.getOperator();

			if (newLeftExpression.isConstant() && newRightExpression.isConstant())
			{
				final BooleanEvaluator logicExprEvaluator = getBooleanEvaluatorByOperator(logicOperator);
				final boolean result = logicExprEvaluator.evaluateOrNull(newLeftExpression::constantValue, newRightExpression::constantValue);
				return ConstantLogicExpression.of(result);
			}
			else if (Objects.equals(leftExpression, newLeftExpression)
					&& Objects.equals(rightExpression, newRightExpression))
			{
				return logicExpr;
			}
			else
			{
				return LogicExpression.of(newLeftExpression, logicOperator, newRightExpression);
			}
		}
		else
		{
			throw ExpressionEvaluationException.newWithPlainMessage("Unsupported ILogicExpression type: " + expr + " (class: " + expr.getClass() + ")");
		}
	}

	//
	//
	//
	//
	//
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
		 * @return value or {@link #VALUE_NotFound}
		 */
		@Nullable
		public String getValue(final Object operand) throws ExpressionEvaluationException
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

		private String resolveCtxName(final CtxName ctxName)
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
					throw ExpressionEvaluationException.newWithPlainMessage("Parameter '" + ctxName.getName() + "' not found in context"
							+ "\n Context: " + params
							+ "\n Evaluator: " + this);
				}
				else
				{
					throw ExpressionEvaluationException.newWithPlainMessage("Unknown " + OnVariableNotFound.class + " value: " + onVariableNotFound);
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
