package org.adempiere.ad.expression.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionCompiler;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.compiere.util.CtxName;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * {@inheritDoc}
 * 
 * Abstract {@link IExpressionCompiler} implementations which is able to parse string expressions and build a typed {@link IExpression}.
 * 
 * Implementors shall just implement the simple factory functions like {@link #createConstantExpression(String)}, {@link #createGeneralExpression(String, List)} etc.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
public abstract class AbstractChunkBasedExpressionCompiler<V, ET extends IExpression<V>> implements IExpressionCompiler<V, ET>
{
	private static final String PARAMETER_TAG = CtxName.NAME_Marker;
	private static final int PARAMETER_TAG_LENGTH = CtxName.NAME_Marker.length();
	private static final String PARAMETER_DOUBLE_TAG = PARAMETER_TAG + PARAMETER_TAG;

	// NOTE to developer: make sure there are no variables here since we are using a shared instance

	protected AbstractChunkBasedExpressionCompiler()
	{
		super();
	}

	protected abstract ET getNullExpression();

	protected abstract ET createConstantExpression(final ExpressionContext context, final String expressionStr);

	protected abstract ET createSingleParamaterExpression(final ExpressionContext context, final String expressionStr, final CtxName parameter);

	protected abstract ET createGeneralExpression(final ExpressionContext context, final String expressionStr, final List<Object> expressionChunks);

	@Override
	public final ET compile(final ExpressionContext context, final String expressionStr)
	{
		// Check if it's an empty expression
		// NOTE: we are preserving all whitespaces from expressions, so that's why we are not trimming the string
		if (expressionStr == null || expressionStr.isEmpty())
		{
			return getNullExpression();
		}

		String inStr = expressionStr;
		int i = inStr.indexOf(PARAMETER_TAG);
		if (i < 0)
		{
			return createConstantExpression(context, expressionStr);
		}

		final List<Object> chunks = new ArrayList<>();
		while (i != -1)
		{
			// up to first parameter marker
			if (i > 0)
			{
				final String chunk = inStr.substring(0, i);
				chunks.add(chunk);
			}
			else
			{
				// expression is starting with a parameter
				// nothing to do here
			}

			inStr = inStr.substring(i + PARAMETER_TAG_LENGTH, inStr.length());	// from first marker
			final int j = inStr.indexOf(PARAMETER_TAG);						// next parameter marker
			if (j < 0)
			{
				// no second tag
				throw new ExpressionCompileException("Missing closing tag '" + PARAMETER_TAG + "' in '" + inStr + "': " + expressionStr);
			}
			else if (j == 0)
			{
				// Double marker (e.g. @@)
				final String chunk = PARAMETER_DOUBLE_TAG;
				chunks.add(chunk);
			}
			else
			{
				// Parameter
				final String token = inStr.substring(0, j);
				final CtxName parameter = CtxName.parse(token);
				chunks.add(parameter);
			}

			inStr = inStr.substring(j + PARAMETER_TAG_LENGTH, inStr.length());	// from second @ to end
			i = inStr.indexOf(PARAMETER_TAG);
		}

		// Add the remaining chunk, if any
		if (!inStr.isEmpty())
		{
			chunks.add(inStr);
		}

		//
		// Case: if we are dealing with a string expression which actually it's one parameter binding, no strings before, no strings after
		// then we have a specialized implementation for that
		if (chunks.size() == 1 && chunks.get(0) instanceof CtxName)
		{
			final CtxName parameter = (CtxName)chunks.get(0);
			return createSingleParamaterExpression(context, expressionStr, parameter);
		}

		return createGeneralExpression(context, expressionStr, chunks);
	}
}
