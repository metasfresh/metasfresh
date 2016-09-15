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

/**
 * Expression compiler
 *
 * @author tsa
 *
 * @param <V> expression evaluation result type
 * @param <ET> expression type
 */
public interface IExpressionCompiler<V, ET extends IExpression<? extends V>>
{
	/**
	 * Compiles given expression.
	 * 
	 * @param context
	 * @param expressionStr
	 * @return compiled expression
	 */
	ET compile(ExpressionContext context, String expressionStr);

	/**
	 * Same as {@link #compile(ExpressionContext, String)} but using no context.
	 * 
	 * @param expressionStr
	 * @return compiled expression
	 */
	default ET compile(final String expressionStr)
	{
		return compile(ExpressionContext.EMPTY, expressionStr);
	}
}
