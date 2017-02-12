package org.adempiere.ad.expression.api.impl;

import java.util.Map;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.impl.IntegerStringExpressionSupport.IntegerStringExpression;
import org.adempiere.util.Services;
import org.compiere.util.Evaluatees;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

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

public class IntegerStringExpressionSupportTest
{
	private IExpressionFactory expressionFactory;

	@Before
	public void init()
	{
		expressionFactory = Services.get(IExpressionFactory.class);
	}

	@Test
	public void test_NullExpression()
	{
		final IntegerStringExpression expr = expressionFactory.compile("", IntegerStringExpression.class);
		Assert.assertTrue("Expect null expression: " + expr, expr.isNullExpression());
	}

	@Test
	public void test_ConstantExpression()
	{
		final IntegerStringExpression expr = expressionFactory.compile("123456", IntegerStringExpression.class);
		final Integer value = expr.evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);
		Assert.assertEquals((Integer)123456, value);
	}

	@Test
	public void test_SingleParameterExpression()
	{
		final IntegerStringExpression expr = expressionFactory.compile("@ValueBD@", IntegerStringExpression.class);
		final Integer value = expr.evaluate(Evaluatees.ofSingleton("ValueBD", "123456"), OnVariableNotFound.Fail);
		Assert.assertEquals((Integer)123456, value);
	}

	@Test
	public void test_GeneralExpression()
	{
		final IntegerStringExpression expr = expressionFactory.compile("@DecimalPart1@@DecimalPart2@", IntegerStringExpression.class);
		final Map<String, ? extends Object> ctx = ImmutableMap.of("DecimalPart1", 12, "DecimalPart2", "3456");
		final Integer value = expr.evaluate(Evaluatees.ofMap(ctx), OnVariableNotFound.Fail);
		Assert.assertEquals((Integer)123456, value);
	}

}
