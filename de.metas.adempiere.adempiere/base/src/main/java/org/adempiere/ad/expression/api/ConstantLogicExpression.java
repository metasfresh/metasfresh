package org.adempiere.ad.expression.api;

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


import java.util.Collections;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.LogicExpressionEvaluator;
import org.compiere.util.Evaluatee;

public final class ConstantLogicExpression implements ILogicExpression
{
	private final boolean value;

	public ConstantLogicExpression(boolean value)
	{
		this.value = value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
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
		ConstantLogicExpression other = (ConstantLogicExpression)obj;
		if (value != other.value)
			return false;
		return true;
	}

	public boolean booleanValue()
	{
		return value;
	}

	@Override
	public String getExpressionString()
	{
		return null;
	}

	@Override
	public List<String> getParameters()
	{
		return Collections.emptyList();
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@Override
	public String getFormatedExpressionString()
	{
		return null;
	}

	@Override
	public Boolean evaluate(Evaluatee ctx, boolean ignoreUnparsable)
	{
		return value;
	}

	@Override
	public Boolean evaluate(Evaluatee ctx, OnVariableNotFound onVariableNotFound)
	{
		return value;
	}

	@Override
	public final IExpressionEvaluator<ILogicExpression, Boolean> getEvaluator()
	{
		return LogicExpressionEvaluator.instance;
	}
}
