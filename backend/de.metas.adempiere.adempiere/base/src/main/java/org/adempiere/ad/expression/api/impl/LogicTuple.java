package org.adempiere.ad.expression.api.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonLogicExpressionSerializer;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonSerialize(using = JsonLogicExpressionSerializer.class)
/* package */final class LogicTuple extends AbstractLogicExpression
{
	public static LogicTuple of(@Nullable final Object operand1, final String operator, @Nullable final Object operand2)
	{
		final Boolean constantValue = null;
		return new LogicTuple(constantValue, operand1, operator, operand2);
	}

	public static LogicTuple parseFrom(final String operand1Str, final String operator, final String operand2Str)
	{
		final Object operand1;
		if (operand1Str.indexOf(CtxNames.NAME_Marker) != -1)
		{
			operand1 = CtxNames.parseWithMarkers(operand1Str);
		}
		else
		{
			operand1 = operand1Str;
		}

		final Object operand2;
		if (operand2Str.indexOf(CtxNames.NAME_Marker) != -1)
		{
			operand2 = CtxNames.parseWithMarkers(operand2Str);
		}
		else
		{
			operand2 = operand2Str;
		}

		final Boolean constantValue = null;
		return new LogicTuple(constantValue, operand1, operator, operand2);
	}

	public static final String OPERATOR_Equals = "=";
	public static final String OPERATOR_NotEquals = "!";
	public static final String OPERATOR_GreaterThan = ">";
	public static final String OPERATOR_LessThan = "<";
	public static final List<String> OPERATORS = ImmutableList.of(OPERATOR_Equals, OPERATOR_NotEquals, OPERATOR_LessThan, OPERATOR_GreaterThan);

	private final Object operand1;
	private final boolean isParameter1;

	private final Object operand2;
	private final boolean isParameter2;

	private final String operator;

	private final Boolean constantValue;

	private ImmutableSet<CtxName> _parameters; // lazy

	private final String expressionStr;
	private Integer _hashcode; // lazy

	private LogicTuple(
			final Boolean constantValue,
			final Object operand1,
			final String operator,
			final Object operand2)
	{
		Check.assumeNotNull(operand1, "operand1 not null");
		Check.assumeNotEmpty(operator, "operator not empty");
		Check.assumeNotNull(operand2, "operand2 not null");

		this.operator = operator;
		this.operand1 = operand1;
		this.isParameter1 = operand1 instanceof CtxName;
		this.operand2 = operand2;
		this.isParameter2 = operand2 instanceof CtxName;

		this.constantValue = constantValue;

		expressionStr = (operand1 instanceof CtxName ? ((CtxName)operand1).toStringWithMarkers() : operand1.toString())
				+ operator
				+ (operand2 instanceof CtxName ? ((CtxName)operand2).toStringWithMarkers() : operand2.toString());
	}

	/** Constant LogicTuple constructor */
	private LogicTuple(final boolean constantValue, final LogicTuple from)
	{
		super();
		expressionStr = from.expressionStr;
		operand1 = from.operand1;
		isParameter1 = from.isParameter1;
		operand2 = from.operand2;
		isParameter2 = from.isParameter2;
		operator = from.operator;

		this.constantValue = constantValue;
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(isParameter1, operand1, operator, isParameter2, operand2);
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
		final LogicTuple other = (LogicTuple)obj;

		return isParameter1 == other.isParameter1
				&& Objects.equals(operand1, other.operand1)
				//
				&& Objects.equals(operator, other.operator)
				//
				&& isParameter2 == other.isParameter2
				&& Objects.equals(operand2, other.operand2)
				&& Objects.equals(constantValue, other.constantValue);
	}

	public boolean isParameter1()
	{
		return isParameter1;
	}

	public boolean isParameter2()
	{
		return isParameter2;
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
			throw ExpressionEvaluationException.newWithTranslatableMessage("Not a constant expression: " + this);
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

		return new LogicTuple(constantValue, this);
	}

	@Override
	public String getExpressionString()
	{
		return expressionStr;
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
				final Set<CtxName> result = new LinkedHashSet<>();
				if (operand1 instanceof CtxName)
				{
					result.add((CtxName)operand1);
				}
				if (operand2 instanceof CtxName)
				{
					result.add((CtxName)operand2);
				}
				_parameters = ImmutableSet.copyOf(result);
			}
		}
		return _parameters;
	}

	/**
	 * @return {@link CtxName} or {@link String}; never returns null
	 */
	public Object getOperand1()
	{
		return operand1;
	}

	/**
	 * @return {@link CtxName} or {@link String}; never returns null
	 */
	public Object getOperand2()
	{
		return operand2;
	}

	/**
	 * @return operator; never returns null
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
	public String getFormatedExpressionString()
	{
		return getExpressionString();
	}
}
