package org.adempiere.ad.expression.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

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


import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.MockedEvaluatee;

import de.metas.util.Services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringExpressionEvaluatorTests
{
	private static final String expression1_Str =
			"select coalesce(("
					+ "		select case when ( case when bomQtyOnHand(@M_Product_ID@ ,@M_Warehouse_ID@,0) = 99999 then 99999 "
					+ "													else COALESCE(SUM(QtyOnHand), 0) end) < @QtyOrdered@ "
					+ "													and ( case when bomQtyOnHand(@M_Product_ID@ ,@M_Warehouse_ID@,0) = 99999 then 99999 "
					+ "														  else COALESCE(SUM(QtyOnHand), 0) end) > 0 then 540001"
					+ "	    when coalesce(( case when bomQtyOnHand(@M_Product_ID@ ,@M_Warehouse_ID@,0) = 99999 then 99999 "
					+ "						else COALESCE(SUM(QtyOnHand), 0) end ),0) <= 0 then 540000"
					+ "	   else -1 end as ad_color_id"
					+ "from M_Storage s where s.M_Product_ID=@M_Product_ID@ "
					+ "and (select M_Warehouse_ID from M_Locator where s.M_Locator_ID = M_Locator.M_Locator_ID)=@M_Warehouse_ID@ ), 540000)";
	private IStringExpression expression1;

	private IExpressionFactory expressionFactory;
	private MockedEvaluatee ctx;

	@BeforeEach
	public void init()
	{
		this.expressionFactory = Services.get(IExpressionFactory.class);

		this.expression1 = expressionFactory.compile(expression1_Str, IStringExpression.class);
		this.ctx = new MockedEvaluatee();
	}

	@Test
	public void test_evaluate_missing_IDParameter_DoNotIgnoreUnparsable()
	{
		ctx.put("M_Product_ID", "1234");
		final boolean ignoreUnparsable = false;
		final String expressionEvaluatedActual = expression1.evaluate(ctx, ignoreUnparsable);

		Assertions.assertEquals(IStringExpression.EMPTY_RESULT, expressionEvaluatedActual, "Empty string shall be returned because M_Warehouse_ID is missing");
	}

	@Test
	public void test_evaluate_missing_IDParameter_OnVariableNotFound_ReturnNoResult()
	{
		ctx.put("M_Product_ID", "1234");
		final String expressionEvaluatedActual = expression1.evaluate(ctx, OnVariableNotFound.ReturnNoResult);
		Assertions.assertEquals(IStringExpression.EMPTY_RESULT, expressionEvaluatedActual, "Empty string shall be returned because M_Warehouse_ID is missing");
	}

	@Test
	public void test_evaluate_missing_IDParameter_OnVariableNotFound_Fail()
	{
		Assertions.assertThrows(ExpressionEvaluationException.class, () -> expression1.evaluate(ctx, OnVariableNotFound.Fail));
	}

	@Test
	public void test_evaluate_missing_IDParameter_OnVariableNotFound_Return_NULL_default()
	{
		final String sqlWithNULLDefault = "select @M_Product_ID/NULL@ from M_Storage";
		final IStringExpression expression = expressionFactory.compile(sqlWithNULLDefault, IStringExpression.class);

		final String expressionEvaluated = expression.evaluate(ctx, OnVariableNotFound.Fail);
		assertThat(expressionEvaluated).isEqualTo("select NULL from M_Storage");
	}

	@Test
	public void test_evaluate_missing_Parameter_OnVariableNotFound_Preserve()
	{
		final String expressionStr = "Some text with a @variable@ which is missing.";
		final IStringExpression expression = expressionFactory.compile(expressionStr, IStringExpression.class);
		final String expressionEvaluated = expression.evaluate(ctx, OnVariableNotFound.Preserve);
		Assertions.assertEquals(expressionStr, expressionEvaluated, "Invalid evaluated expression");
	}

	@Test
	public void test_evaluate_missing_Parameter_OnVariableNotFound_Empty()
	{
		final String expressionStr = "Some text with a @variable@ which is missing.";
		final String expressionEvaluatedExpected = "Some text with a  which is missing.";
		final IStringExpression expression = expressionFactory.compile(expressionStr, IStringExpression.class);
		final String expressionEvaluated = expression.evaluate(ctx, OnVariableNotFound.Empty);
		Assertions.assertEquals(expressionEvaluatedExpected, expressionEvaluated, "Invalid evaluated expression");
	}

	@Test
	public void test_evaluate_missing_Parameter_OnVariableNotFound_ReturnNoResult()
	{
		test_evaluate_OnVariableNotFound_ReturnNoResult("Some text with a @variable@ which is missing.");
		test_evaluate_OnVariableNotFound_ReturnNoResult("");
		test_evaluate_OnVariableNotFound_ReturnNoResult(null);
	}

	@Test
	public void test_evaluate_OnVariableNotFound_ReturnNoResult_NullTests()
	{
		test_evaluate_OnVariableNotFound_ReturnNoResult("");
		test_evaluate_OnVariableNotFound_ReturnNoResult(null);
	}

	private void test_evaluate_OnVariableNotFound_ReturnNoResult(final String expressionStr)
	{
		final String expressionEvaluatedExpected = IStringExpression.EMPTY_RESULT;
		final IStringExpression expression = expressionFactory.compile(expressionStr, IStringExpression.class);
		final String expressionEvaluated = expression.evaluate(ctx, OnVariableNotFound.ReturnNoResult);
		Assertions.assertSame(expressionEvaluatedExpected, expressionEvaluated, "Invalid evaluated expression for: " + expressionStr);
	}
}
