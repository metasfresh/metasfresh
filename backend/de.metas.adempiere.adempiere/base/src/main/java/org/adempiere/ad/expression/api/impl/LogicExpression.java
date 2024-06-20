package org.adempiere.ad.expression.api.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.impl.LogicExpressionEvaluator.BooleanEvaluator;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonLogicExpressionSerializer;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@JsonSerialize(using = JsonLogicExpressionSerializer.class)
/* package */final class LogicExpression extends AbstractLogicExpression
{
	public static LogicExpression of(
			@NonNull final ILogicExpression left,
			@NonNull final String operator,
			@NonNull final ILogicExpression right)
	{
		final Boolean constantValue = null;
		return new LogicExpression(constantValue, left, operator, right);
	}

	private final ILogicExpression left;
	private final ILogicExpression right;
	private final String operator;
	private final Boolean constantValue;

	private ImmutableSet<CtxName> _parameters;

	private Integer _hashcode; // lazy
	private String _expressionString; // lazy
	private String _formatedExpressionString; // lazy

	/* package */ LogicExpression(
			final Boolean constantValue,
			@NonNull final ILogicExpression left,
			@NonNull final String operator,
			@NonNull final ILogicExpression right)
	{
		Check.assumeNotEmpty(operator, "operator not empty");

		this.left = left;
		this.operator = operator;
		this.right = right;
		this.constantValue = constantValue;
	}

	/** Constant LogicExpression constructor */
	private LogicExpression(final Boolean constantValue, final LogicExpression from)
	{
		left = from.left;
		right = from.right;
		operator = from.operator;
		this.constantValue = constantValue;
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(left, operator, right, constantValue);
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final LogicExpression other = (LogicExpression)obj;

		return Objects.equals(operator, other.operator)
				&& Objects.equals(left, other.left)
				&& Objects.equals(right, other.right)
				&& Objects.equals(constantValue, other.constantValue);
	}

	@Override
	public boolean isConstant()
	{
		return constantValue != null;
	}

	@Override
	public boolean constantValue()
	{
		if (constantValue == null)
		{
			throw ExpressionEvaluationException.newWithPlainMessage("Not a constant expression: " + this);
		}
		return constantValue;
	}

	@Override
	public ILogicExpression toConstantExpression(final boolean constantValue)
	{
		if (this.constantValue != null && this.constantValue == constantValue)
		{
			return this;
		}

		return new LogicExpression(constantValue, this);
	}

	@Override
	public String getExpressionString()
	{
		if (_expressionString == null)
		{
			_expressionString = buildExpressionString(left, operator, right);
		}
		return _expressionString;
	}

	@Override
	public String getFormatedExpressionString()
	{
		if (_formatedExpressionString == null)
		{
			_formatedExpressionString = buildFormatedExpressionString(left, operator, right);
		}
		return _formatedExpressionString;
	}

	/* package */static final String buildExpressionString(final ILogicExpression left, final String operator, final ILogicExpression right)
	{
		return left.getExpressionString() + operator + right.getExpressionString();
	}

	/* package */static final String buildFormatedExpressionString(final ILogicExpression left, final String operator, final ILogicExpression right)
	{
		final StringBuilder result = new StringBuilder();

		final int operatorStrength = getOperatorStrength(operator);

		//
		// Left expression
		final String leftStr = left.getFormatedExpressionString();
		final String leftOperator = getLogicOperatorOrNull(left);
		if (leftOperator != null && operatorStrength > getOperatorStrength(leftOperator))
		{
			result.append("(").append(leftStr).append(")");
		}
		else
		{
			result.append(leftStr);
		}

		//
		// Operator
		result.append(operator);

		//
		// Right expression
		final String rightStr = right.getFormatedExpressionString();
		final String rightOperator = getLogicOperatorOrNull(right);

		if (rightOperator != null && operatorStrength > getOperatorStrength(rightOperator))
		{
			result.append("(").append(rightStr).append(")");
		}
		else
		{
			result.append(rightStr);
		}

		return result.toString();
	}

	private static final String getLogicOperatorOrNull(final ILogicExpression expression)
	{
		if (expression instanceof LogicExpression)
		{
			final LogicExpression logicExpression = (LogicExpression)expression;
			return logicExpression.getOperator();
		}
		return null;
	}

	private static final int getOperatorStrength(final String operator)
	{
		if (operator == null)
		{
			return -100;
		}
		else if (LOGIC_OPERATOR_AND.equals(operator))
		{
			return 20;
		}
		else if (LOGIC_OPERATOR_OR.equals(operator))
		{
			return 10;
		}
		else
		{
			// unknown operator
			throw ExpressionEvaluationException.newWithTranslatableMessage("Unknown operator: " + operator);
		}
	}

	@Override
	public Set<CtxName> getParameters()
	{
		if (_parameters == null)
		{
			if (isConstant())
			{
				_parameters = ImmutableSet.of();
			}
			else
			{
				final Set<CtxName> result = new LinkedHashSet<>(left.getParameters());
				result.addAll(right.getParameters());
				_parameters = ImmutableSet.copyOf(result);
			}
		}
		return _parameters;
	}

	/**
	 * @return left expression; never null
	 */
	public ILogicExpression getLeft()
	{
		return left;
	}

	/**
	 * @return right expression; never null
	 */
	public ILogicExpression getRight()
	{
		return right;
	}

	/**
	 * @return logic operator; never null
	 */
	public String getOperator()
	{
		return operator;
	}

	@Override
	public String toString()
	{
		return getExpressionString();
	}

	@Override
	public ILogicExpression evaluatePartial(final Evaluatee ctx)
	{
		if (isConstant())
		{
			return this;
		}

		final ILogicExpression leftExpression = getLeft();
		final ILogicExpression newLeftExpression = leftExpression.evaluatePartial(ctx);

		final ILogicExpression rightExpression = getRight();
		final ILogicExpression newRightExpression = rightExpression.evaluatePartial(ctx);

		final String logicOperator = getOperator();

		if (newLeftExpression.isConstant() && newRightExpression.isConstant())
		{
			final BooleanEvaluator logicExprEvaluator = LogicExpressionEvaluator.getBooleanEvaluatorByOperator(logicOperator);
			final boolean result = logicExprEvaluator.evaluateOrNull(newLeftExpression::constantValue, newRightExpression::constantValue);
			return ConstantLogicExpression.of(result);
		}
		else if (Objects.equals(leftExpression, newLeftExpression)
				&& Objects.equals(rightExpression, newRightExpression))
		{
			return this;
		}
		else
		{
			return LogicExpression.of(newLeftExpression, logicOperator, newRightExpression);
		}
	}
}
