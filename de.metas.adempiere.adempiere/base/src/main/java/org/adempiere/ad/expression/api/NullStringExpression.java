package org.adempiere.ad.expression.api;

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


import java.util.Collections;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.StringExpressionEvaluator;
import org.compiere.util.Evaluatee;

/**
 * NULL {@link IStringExpression}
 * 
 * @author tsa
 * 
 */
public final class NullStringExpression implements IStringExpression
{
	public static final NullStringExpression instance = new NullStringExpression();

	private NullStringExpression()
	{
		super();
	}

	@Override
	public String getExpressionString()
	{
		return "";
	}

	@Override
	public String getFormatedExpressionString()
	{
		return "";
	}

	@Override
	public List<String> getParameters()
	{
		return Collections.emptyList();
	}

	@Override
	public List<Object> getExpressionChunks()
	{
		return Collections.emptyList();
	}

	@Override
	public String evaluate(Evaluatee ctx, boolean ignoreUnparsable)
	{
		return StringExpressionEvaluator.EMPTY_RESULT;
	}

	@Override
	public String evaluate(Evaluatee ctx, OnVariableNotFound onVariableNotFound)
	{
		return StringExpressionEvaluator.EMPTY_RESULT;
	}

	@Override
	public final IExpressionEvaluator<IStringExpression, String> getEvaluator()
	{
		return StringExpressionEvaluator.instance;
	}
}
