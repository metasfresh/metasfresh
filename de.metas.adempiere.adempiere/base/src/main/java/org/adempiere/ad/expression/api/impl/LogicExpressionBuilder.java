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


import org.adempiere.ad.expression.api.ILogicExpression;

/**
 * Internal helper used to compile {@link ILogicExpression}s
 * 
 * @author tsa
 * 
 */
/* package */class LogicExpressionBuilder
{
	private ILogicExpression left;
	private ILogicExpression right;
	private String operator;

	public LogicExpressionBuilder()
	{
		super();
	}

	public LogicExpressionBuilder(ILogicExpression left, String operator, ILogicExpression right)
	{
		this();
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	public ILogicExpression build()
	{
		//
		// Only left side of expression was set, returning it
		if (right == null && operator == null)
		{
			return left;
		}
		//
		// Only right side of expression was set, returning it
		if (left == null && operator == null)
		{
			return right;
		}

		// Everything was set, building the expression
		return new LogicExpression(left, operator, right);
	}

	public void setLeft(ILogicExpression left)
	{
		this.left = left;
	}

	public void setRight(ILogicExpression right)
	{
		this.right = right;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
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
}
