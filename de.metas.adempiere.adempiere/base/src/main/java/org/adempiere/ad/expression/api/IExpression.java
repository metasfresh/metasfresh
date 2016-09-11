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

import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.Evaluatee;

/**
 * Root interface for all kind of expressions
 * 
 * @author tsa
 * 
 * @param <V> expression evaluation result type
 */
public interface IExpression<V>
{
	/**
	 * @return the type of evaluation result
	 */
	Class<V> getValueClass();

	/**
	 * Gets string representation of underlying expression. Usually this shall be EXACTLY the same as the string from where the expression was compiled
	 * 
	 * @return string representation of underlying expression
	 */
	String getExpressionString();

	/**
	 * Similar with {@link #getExpressionString()} but in this case the string representation is build from compiled expression respecting all formating
	 * 
	 * @return
	 */
	String getFormatedExpressionString();

	/**
	 * Gets the list of parameter names
	 * 
	 * @return list of parameter names or empty list; never return NULL
	 */
	List<String> getParameters();

	/**
	 * Consider using {@link #evaluate(Evaluatee, OnVariableNotFound)}. This method will be deprecated in future.
	 * 
	 * @param ctx
	 * @param ignoreUnparsable
	 * @return
	 */
	default V evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		// backward compatibility
		final OnVariableNotFound onVariableNotFound = ignoreUnparsable ? OnVariableNotFound.Empty : OnVariableNotFound.ReturnNoResult;
		return evaluate(ctx, onVariableNotFound);
	}

	/**
	 * Evaluates expression in given context.
	 * 
	 * @param ctx
	 * @param onVariableNotFound
	 * @return evaluation result
	 * @throws ExpressionEvaluationException if evaluation fails and we were adviced to throw exception
	 */
	V evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException;

	/**
	 * @param result
	 * @return true if the given <code>result</code> shall be considered as "NO RESULT"
	 */
	boolean isNoResult(Object result);

	/**
	 * @return true if this expression is constant and always evaluated "NO RESULT"
	 * @see #isNoResult(Object)
	 */
	boolean isNullExpression();
}
