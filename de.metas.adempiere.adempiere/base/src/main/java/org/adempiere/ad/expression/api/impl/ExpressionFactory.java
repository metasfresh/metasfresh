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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionCompiler;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.BigDecimalStringExpressionSupport.BigDecimalStringExpression;
import org.adempiere.ad.expression.api.impl.BooleanStringExpressionSupport.BooleanStringExpression;
import org.adempiere.ad.expression.api.impl.DateStringExpressionSupport.DateStringExpression;
import org.adempiere.ad.expression.api.impl.IntegerStringExpressionSupport.IntegerStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class ExpressionFactory implements IExpressionFactory
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * Compilers registry.
	 *
	 * NOTE: the only methods that are allowed to operate with this map are:
	 * <ul>
	 * <li>{@link #registerCompiler(Class, IExpressionCompiler)}
	 * <li>{@link #getCompiler(Class)}
	 * </ul>
	 */
	private final Map<Class<?>, Object> compilers = new ConcurrentHashMap<>();

	public ExpressionFactory()
	{
		// Register standard(known) compilers
		registerCompiler(IStringExpression.class, StringExpressionCompiler.instance);
		registerCompiler(IntegerStringExpression.class, IntegerStringExpressionSupport.instance.getCompiler());
		registerCompiler(ILogicExpression.class, LogicExpressionCompiler.instance);
		registerCompiler(BigDecimalStringExpression.class, BigDecimalStringExpressionSupport.instance.getCompiler());
		registerCompiler(DateStringExpression.class, DateStringExpressionSupport.instance.getCompiler());
		registerCompiler(BooleanStringExpression.class, BooleanStringExpressionSupport.instance.getCompiler());
	}

	@Override
	public <V, ET extends IExpression<V>> void registerCompiler(final Class<ET> expressionType, final IExpressionCompiler<V, ET> compiler)
	{
		Check.assumeNotNull(expressionType, "expressionType not null");
		Check.assumeNotNull(compiler, "compiler not null");

		compilers.put(expressionType, compiler);
	}

	public <V, ET extends IExpression<? extends V>> void registerGenericCompiler(final Class<ET> expressionType, final IExpressionCompiler<V, ?> compiler)
	{
		Check.assumeNotNull(expressionType, "Parameter expressionType is not null");
		Check.assumeNotNull(compiler, "compiler not null");

		compilers.put(expressionType, compiler);
	}

	@Override
	public <V, ET extends IExpression<V>, CT extends IExpressionCompiler<V, ET>> CT getCompiler(final Class<ET> expressionType)
	{
		// Look for the particular compiler
		final Object compilerObj = compilers.get(expressionType);

		// No compiler found => fail
		if (compilerObj == null)
		{
			throw new ExpressionCompileException("No compiler found for expressionType=" + expressionType
					+ "\n Available compilers are: " + compilers.keySet());
		}

		// Assume this is always correct because we enforce the type when we add to map
		@SuppressWarnings("unchecked")
		final CT compiler = (CT)compilerObj;
		return compiler;
	}

	@Override
	public <V, ET extends IExpression<V>> ET compile(final String expressionStr, final Class<ET> expressionType)
	{
		final IExpressionCompiler<V, ET> compiler = getCompiler(expressionType);
		return compiler.compile(ExpressionContext.EMPTY, expressionStr);
	}

	@Override
	public <V, ET extends IExpression<V>> ET compileOrDefault(final String expressionStr, final ET defaultExpr, final Class<ET> expressionType)
	{
		if (Check.isEmpty(expressionStr, true))
		{
			return defaultExpr;
		}

		try
		{
			return compile(expressionStr, expressionType);
		}
		catch (final Exception e)
		{
			logger.warn(e.getLocalizedMessage(), e);
			return defaultExpr;
		}
	}
	
	@Override
	public <V, ET extends IExpression<V>> ET compile(final String expressionStr, final Class<ET> expressionType, final ExpressionContext context)
	{
		final IExpressionCompiler<V, ET> compiler = getCompiler(expressionType);
		return compiler.compile(context, expressionStr);
	}
}
