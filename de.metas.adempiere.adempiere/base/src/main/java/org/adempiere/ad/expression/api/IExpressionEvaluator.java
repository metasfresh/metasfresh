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


import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.Evaluatee;

/**
 * Expression Evaluator
 * 
 * @author tsa
 * 
 * @param <ET> expression type
 * @param <V> expression evaluation result type
 */
public interface IExpressionEvaluator<ET extends IExpression<V>, V>
{
	public enum OnVariableNotFound
	{
		/**
		 * Use default value for unparsed token. This means:
		 * <ul>
		 * <li>in case of {@link IStringExpression} it will replace the unparsed token with empty string
		 * <li>in case of {@link ILogicExpression} this is not supported and {@link ExpressionEvaluationException} will be thrown
		 * </ul>
		 */
		Empty
		/**
		 * Preserve context variable as it was. This means:
		 * <ul>
		 * <li>in case of {@link IStringExpression} it will preserve the unparsed token EXACTLY how it is (together with it's markers, e.g. <code>@MyVariable@</code>)
		 * <li>in case of {@link ILogicExpression} this is not supported and {@link ExpressionEvaluationException} will be thrown
		 * </ul>
		 */
		, Preserve
		/**
		 * Immediately return no result. This means:
		 * <ul>
		 * <li>in case of {@link IStringExpression} it will return {@link IStringExpression#EMPTY_RESULT}
		 * <li>in case of {@link ILogicExpression} it will return <code>false</code>
		 * </ul>
		 */
		, ReturnNoResult
		/**
		 * Fail and throw {@link ExpressionEvaluationException}
		 */
		, Fail
		//
		;
	}

	/**
	 * 
	 * @param ctx
	 * @param expression
	 * @param onVariableNotFound
	 * @return resulting value or "no result" (which you could check it with {@link #isNoResult(Object)}.
	 * @throws ExpressionEvaluationException in case evaluation failed.
	 */
	V evaluate(final Evaluatee ctx, final ET expression, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException;
}
