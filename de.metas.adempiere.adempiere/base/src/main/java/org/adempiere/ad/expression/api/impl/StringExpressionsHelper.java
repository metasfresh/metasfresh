package org.adempiere.ad.expression.api.impl;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

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
 * Contains miscellaneous helper methods for handling and evaluating string expressions.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class StringExpressionsHelper
{

	/**
	 * Evaluates given parameter.
	 *
	 * @param name
	 * @param ctx
	 * @param onVariableNotFound
	 * @return parameter value or {@link IStringExpression#EMPTY_RESULT}
	 * @throws ExpressionEvaluationException
	 */
	public static final String evaluateParam(final CtxName name, final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final String value = name.getValueAsString(ctx);

		if (value != null && value != CtxName.VALUE_NULL)
		{
			return value;
		}

		return evaluateMissingParameter(name, onVariableNotFound);
	}

	public static final String evaluateMissingParameter(final CtxName token, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		if (onVariableNotFound == OnVariableNotFound.ReturnNoResult)
		{
			return IStringExpression.EMPTY_RESULT;
		}
		else if (onVariableNotFound == OnVariableNotFound.Preserve)
		{
			return token.toStringWithMarkers();
		}
		else if (onVariableNotFound == OnVariableNotFound.Empty)
		{
			return "";
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

	private StringExpressionsHelper()
	{
		throw new AssertionError();
	}
}
