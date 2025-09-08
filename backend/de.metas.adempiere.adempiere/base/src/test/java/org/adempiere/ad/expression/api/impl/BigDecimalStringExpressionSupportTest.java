package org.adempiere.ad.expression.api.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Services;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.impl.BigDecimalStringExpressionSupport.BigDecimalStringExpression;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

public class BigDecimalStringExpressionSupportTest
{
	private IExpressionFactory expressionFactory;

	@BeforeEach
	public void init()
	{
		expressionFactory = Services.get(IExpressionFactory.class);
	}

	@Test
	public void test_NullExpression()
	{
		final BigDecimalStringExpression expr = expressionFactory.compile("", BigDecimalStringExpression.class);
		Assertions.assertTrue(expr.isNullExpression(), "Expect null expression: " + expr);
	}

	@Test
	public void test_ConstantExpression()
	{
		final BigDecimalStringExpression expr = expressionFactory.compile("12.3456", BigDecimalStringExpression.class);
		final BigDecimal value = expr.evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);
		Assertions.assertEquals(new BigDecimal("12.3456"), value);
	}

	@Test
	public void test_SingleParameterExpression()
	{
		final BigDecimalStringExpression expr = expressionFactory.compile("@ValueBD@", BigDecimalStringExpression.class);
		final BigDecimal value = expr.evaluate(Evaluatees.ofSingleton("ValueBD", "12.3456"), OnVariableNotFound.Fail);
		Assertions.assertEquals(new BigDecimal("12.3456"), value);
	}

	@Test
	public void test_GeneralExpression()
	{
		final BigDecimalStringExpression expr = expressionFactory.compile("@DecimalValue@.@FractionValue@", BigDecimalStringExpression.class);
		final Map<String, ?> ctx = ImmutableMap.of("DecimalValue", 12, "FractionValue", "3456");
		final BigDecimal value = expr.evaluate(Evaluatees.ofMap(ctx), OnVariableNotFound.Fail);
		Assertions.assertEquals(new BigDecimal("12.3456"), value);
	}

}
