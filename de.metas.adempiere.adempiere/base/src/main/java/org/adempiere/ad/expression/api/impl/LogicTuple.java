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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.json.JsonLogicExpressionSerializer;
import org.adempiere.util.Check;
import org.compiere.util.CtxName;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;

@JsonSerialize(using = JsonLogicExpressionSerializer.class)
/* package */final class LogicTuple extends AbstractLogicExpression
{
	private final String expressionStr;

	private final Object operand1;
	private final boolean isParameter1;

	private final Object operand2;
	private final boolean isParameter2;

	private final String operator;

	private ImmutableList<String> _parameters;

	public LogicTuple(final String operand1, final String operator, final String operand2)
	{
		super();

		Check.assumeNotNull(operand1, "operand1 not null");
		Check.assumeNotNull(operator, "operator not null");
		Check.assumeNotNull(operand2, "operand2 not null");

		expressionStr = operand1 + operator + operand2;
		this.operator = operator;
		if (operand1.indexOf(CtxName.NAME_Marker) != -1)
		{
			this.operand1 = CtxName.parseWithMarkers(operand1);
			isParameter1 = true;
		}
		else
		{
			this.operand1 = operand1;
			isParameter1 = false;
		}
		if (operand2.indexOf(CtxName.NAME_Marker) != -1)
		{
			this.operand2 = CtxName.parseWithMarkers(operand2);
			isParameter2 = true;
		}
		else
		{
			this.operand2 = operand2;
			isParameter2 = false;
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expressionStr == null) ? 0 : expressionStr.hashCode());
		result = prime * result + (isParameter1 ? 1231 : 1237);
		result = prime * result + (isParameter2 ? 1231 : 1237);
		result = prime * result + ((operand1 == null) ? 0 : operand1.hashCode());
		result = prime * result + ((operand2 == null) ? 0 : operand2.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
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
		LogicTuple other = (LogicTuple)obj;
		if (expressionStr == null)
		{
			if (other.expressionStr != null)
				return false;
		}
		else if (!expressionStr.equals(other.expressionStr))
			return false;
		if (isParameter1 != other.isParameter1)
			return false;
		if (isParameter2 != other.isParameter2)
			return false;
		if (operand1 == null)
		{
			if (other.operand1 != null)
				return false;
		}
		else if (!operand1.equals(other.operand1))
			return false;
		if (operand2 == null)
		{
			if (other.operand2 != null)
				return false;
		}
		else if (!operand2.equals(other.operand2))
			return false;
		if (operator == null)
		{
			if (other.operator != null)
				return false;
		}
		else if (!operator.equals(other.operator))
			return false;
		return true;
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
	public String getExpressionString()
	{
		return expressionStr;
	}

	@Override
	public List<String> getParameters()
	{
		if (_parameters == null)
		{
			final Set<String> result = new HashSet<String>();
			if (operand1 instanceof CtxName)
			{
				result.add(((CtxName)operand1).getName());
			}
			if (operand2 instanceof CtxName)
			{
				result.add(((CtxName)operand2).getName());
			}
			_parameters = ImmutableList.copyOf(result);
		}
		return _parameters;
	}

	/**
	 * @return {@link CtxName} or {@link String}
	 */
	public Object getOperand1()
	{
		return operand1;
	}

	/**
	 * @return {@link CtxName} or {@link String}
	 */
	public Object getOperand2()
	{
		return operand2;
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

	@Override
	public String getFormatedExpressionString()
	{
		return getExpressionString();
	}
}
