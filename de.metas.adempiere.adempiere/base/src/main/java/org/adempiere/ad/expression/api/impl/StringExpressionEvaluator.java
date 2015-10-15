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


import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;
import org.compiere.util.Util;

/**
 * String expression evaluator
 * 
 * @author tsa
 * 
 */
public class StringExpressionEvaluator implements IExpressionEvaluator<IStringExpression, String>
{
	public static final StringExpressionEvaluator instance = new StringExpressionEvaluator();

	public static final String EMPTY_RESULT = "";

	@Override
	public String evaluate(final Evaluatee ctx, final IStringExpression expression, final OnVariableNotFound onVariableNotFound)
	{
		final StringBuilder result = new StringBuilder();
		for (final Object chunk : expression.getExpressionChunks())
		{
			if (chunk == null)
			{
				continue;
			}
			else if (chunk instanceof CtxName)
			{
				final CtxName token = (CtxName)chunk;
				String value = token.getValueAsString(ctx);
				if (value == null || CtxName.VALUE_NULL == value)
				{
					// NOTE: we are not logging a warning because it could be a standard case
					// s_log.log(ignoreUnparsable ? Level.CONFIG : Level.WARNING, "No Context for: " + token); // metas: tsa: log as warning if ignoreUnparsable=false

					if (onVariableNotFound == OnVariableNotFound.ReturnNoResult) // i.e. !ignoreUnparsable
					{
						return EMPTY_RESULT;
					}
					else if (onVariableNotFound == OnVariableNotFound.Preserve)
					{
						value = token.toString(true); // includeTagMarkers=true
					}
					else if (onVariableNotFound == OnVariableNotFound.Empty) // i.e. ignoreUnparsable
					{
						value = "";
					}
					else if (onVariableNotFound == OnVariableNotFound.Fail)
					{
						throw new ExpressionEvaluationException("@NotFound@: " + token);
					}
					else
					{
						throw new IllegalArgumentException("Unknown " + OnVariableNotFound.class + " value: " + onVariableNotFound);
					}
				}

				result.append(value);
			}
			else
			{
				final String chunkStr = chunk.toString();
				result.append(chunkStr);
			}
		}

		return result.toString();
	}

	@Override
	public boolean isNoResult(Object result)
	{
		return result == null
				|| Util.same(result, EMPTY_RESULT);
	}

}
