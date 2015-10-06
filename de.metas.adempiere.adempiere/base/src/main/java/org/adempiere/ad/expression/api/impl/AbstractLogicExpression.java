package org.adempiere.ad.expression.api.impl;

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


import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.util.Evaluatee;

public abstract class AbstractLogicExpression implements ILogicExpression
{
	@Override
	public Boolean evaluate(Evaluatee ctx, boolean ignoreUnparsable)
	{
		// backward compatibility
		final OnVariableNotFound onVariableNotFound = ignoreUnparsable ? OnVariableNotFound.ReturnNoResult : OnVariableNotFound.Fail;
		return evaluate(ctx, onVariableNotFound);
	}

	@Override
	public Boolean evaluate(Evaluatee ctx, OnVariableNotFound onVariableNotFound)
	{
		final LogicExpressionEvaluator evaluator = getEvaluator();
		return evaluator.evaluate(ctx, this, onVariableNotFound);
	}

	@Override
	public final LogicExpressionEvaluator getEvaluator()
	{
		return LogicExpressionEvaluator.instance;
	}

}
