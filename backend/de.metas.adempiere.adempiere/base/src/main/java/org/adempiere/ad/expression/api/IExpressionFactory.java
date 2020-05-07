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


import org.adempiere.util.ISingletonService;

/**
 * Expressions factory. Used for compiling various types of expressions.
 * 
 * @author tsa
 * 
 */
public interface IExpressionFactory extends ISingletonService
{
	<V, ET extends IExpression<V>> void registerCompiler(final Class<ET> expressionType, final IExpressionCompiler<V, ET> compiler);

	<V, ET extends IExpression<V>, CT extends IExpressionCompiler<V, ET>> CT getCompiler(final Class<ET> expressionType);

	/**
	 * Compiles given string expression
	 * 
	 * @param expressionStr The expression to be compiled
	 * @return compiled string expression; never returns null
	 */
	<V, ET extends IExpression<V>> ET compile(String expressionStr, final Class<ET> expressionType);

	/**
	 * Compiles given string expression
	 * 
	 * If the expression cannot be evaluated, returns the given default expression.
	 * 
	 * This method does not throw any exception, but in case of error that error will be logged.
	 * 
	 * @param expressionStr The expression to be compiled
	 * @return compiled string expression; never returns null
	 */
	<V, ET extends IExpression<V>> ET compileOrDefault(String expressionStr, ET defaultExpr, final Class<ET> expressionType);

	<V, ET extends IExpression<V>> ET compile(String expressionStr, Class<ET> expressionType, ExpressionContext context);
}
