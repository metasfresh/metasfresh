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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

/* package */class LogicExpression extends AbstractLogicExpression
{
	private final ILogicExpression left;
	private final ILogicExpression right;
	private final String operator;
	
	private ImmutableList<String> _parameters;

	public LogicExpression(ILogicExpression left, String operator, ILogicExpression right)
	{
		super();

		Check.assumeNotNull(left, "left expression not null");
		Check.assumeNotNull(operator, "operator not null");
		Check.assumeNotNull(right, "right expression not null");

		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogicExpression other = (LogicExpression)obj;
		if (left == null)
		{
			if (other.left != null)
				return false;
		}
		else if (!left.equals(other.left))
			return false;
		if (operator == null)
		{
			if (other.operator != null)
				return false;
		}
		else if (!operator.equals(other.operator))
			return false;
		if (right == null)
		{
			if (other.right != null)
				return false;
		}
		else if (!right.equals(other.right))
			return false;
		return true;
	}

	@Override
	public String getExpressionString()
	{
		if (right != null)
		{
			return left.getExpressionString() + operator + right.getExpressionString();
		}
		return left.getExpressionString();
	}

	@Override
	public String getFormatedExpressionString()
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
		else if ("&".equals(operator))
		{
			return 20;
		}
		else if ("|".equals(operator))
		{
			return 10;
		}
		else
		{
			// unknown operator
			throw new ExpressionEvaluationException("Unknown operator: " + operator);
		}
	}

	@Override
	public List<String> getParameters()
	{
		if (_parameters == null)
		{
			final List<String> result = new ArrayList<>(left.getParameters());
			if (right != null)
			{
				for (String x : right.getParameters())
				{
					if (!result.contains(x))
					{
						result.add(x);
					}
				}
			}
			_parameters = ImmutableList.copyOf(result);
		}
		return _parameters;
	}

	public ILogicExpression getLeft()
	{
		return left;
	}

	public ILogicExpression getRight()
	{
		return right;
	}

	public String getOperator()
	{
		return operator;
	}

	@Override
	public String toString()
	{
		return getExpressionString();
	}
}
