package org.adempiere.ad.expression.api.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Services;
import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.impl.BooleanStringExpressionSupport.BooleanStringExpression;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

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

public class BooleanStringExpressionSupportTest
{
	private IExpressionFactory expressionFactory;
	private ExpressionContext context;

	@BeforeEach
	public void init()
	{
		expressionFactory = Services.get(IExpressionFactory.class);

		context = ExpressionContext.EMPTY;
	}

	@Test
	public void test_NullExpression()
	{
		final BooleanStringExpression expr = expressionFactory.compile("", BooleanStringExpression.class, context);
		Assertions.assertTrue(expr.isNullExpression(), "Expect null expression: " + expr);
	}

	@Test
	public void test_ConstantExpression()
	{
		final BooleanStringExpression expr = expressionFactory.compile("Y", BooleanStringExpression.class, context);
		final Boolean value = expr.evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);
		Assertions.assertEquals(Boolean.TRUE, value);
	}

	@Test
	public void test_SingleParameterExpression()
	{
		final BooleanStringExpression expr = expressionFactory.compile("@Processed@", BooleanStringExpression.class, context);
		final Boolean value = expr.evaluate(Evaluatees.ofSingleton("Processed", "N"), OnVariableNotFound.Fail);
		Assertions.assertEquals(Boolean.FALSE, value);

	}

	@Test
	public void test_GeneralExpression()
	{
		// weird and pointless test... but just to have coverage
		final BooleanStringExpression expr = expressionFactory.compile("@1@@2@", BooleanStringExpression.class);
		final Map<String, ? extends Object> ctx = ImmutableMap.of("1", "tr", "2", "ue");
		final Boolean value = expr.evaluate(Evaluatees.ofMap(ctx), OnVariableNotFound.Fail);
		Assertions.assertEquals(Boolean.TRUE, value);
	}

}
