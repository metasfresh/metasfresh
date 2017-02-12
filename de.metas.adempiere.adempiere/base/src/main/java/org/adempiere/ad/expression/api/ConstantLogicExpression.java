package org.adempiere.ad.expression.api;

import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonLogicExpressionSerializer;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableSet;

@JsonSerialize(using = JsonLogicExpressionSerializer.class)
public final class ConstantLogicExpression implements ILogicExpression
{
	public static final ILogicExpression of(final boolean value)
	{
		return value ? TRUE : FALSE;
	}
	
	public static final ILogicExpression TRUE = new ConstantLogicExpression(true);
	public static final ILogicExpression FALSE = new ConstantLogicExpression(false);

	private final boolean value;

	private final String toStringValue;
	private final String expressionString;
	private final int hashcode;

	private ConstantLogicExpression(final boolean value)
	{
		super();
		this.value = value;
		expressionString = value ? "1=1" : "1=0";
		toStringValue = value ? "TRUE" : "FALSE";
		hashcode = Objects.hash(31, value);
	}

	@Override
	public int hashCode()
	{
		return hashcode;
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
		final ConstantLogicExpression other = (ConstantLogicExpression)obj;
		return value == other.value;
	}

	@Override
	public boolean isConstant()
	{
		return true;
	}

	@Override
	public boolean constantValue()
	{
		return value;
	}

	@Override
	public ILogicExpression toConstantExpression(final boolean constantValue)
	{
		if (constantValue != value)
		{
			throw new ExpressionCompileException("Cannot convert a constant expression to a constant expression of opposite value"
					+ "\n Expression: " + this
					+ "\n Target value: " + constantValue);
		}
		return this;
	}

	@Override
	public String getExpressionString()
	{
		return expressionString;
	}

	@Override
	public Set<String> getParameters()
	{
		return ImmutableSet.of();
	}

	@Override
	public String toString()
	{
		return toStringValue;
	}

	@Override
	public String getFormatedExpressionString()
	{
		return expressionString;
	}

	@Override
	public Boolean evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		return value;
	}

	@Override
	public Boolean evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		return value;
	}

	@Override
	public LogicExpressionResult evaluateToResult(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		return value ? LogicExpressionResult.TRUE : LogicExpressionResult.FALSE;
	}
}
