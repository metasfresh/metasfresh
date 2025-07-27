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

import java.util.Set;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.compiere.util.MockedEvaluatee;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringExpressionCompilerTests
{
	private StringExpressionCompiler compiler;

	@BeforeEach
	public void init()
	{
		compiler = StringExpressionCompiler.instance;
	}

	@Test
	public void test_compileStringExpression_getParameters()
	{
		{
			final String sql = "C_BPartner_ID=@C_BPartner_Override_ID/-1@ AND C_BPartner_Location_ID=@C_BPartner_Location_Override_ID/-1@";
			final IStringExpression expression = compiler.compile(sql);
			final Set<String> dependsActual = expression.getParameterNames();
			final Set<String> dependsExpected = ImmutableSet.of("C_BPartner_Override_ID", "C_BPartner_Location_Override_ID");
			assertEquals(dependsExpected, dependsActual);
		}
		{
			// test if is also works with strings and with nested '-signs
			final String sql = "Type='@StringVar/'NONE'@' AND C_BPartner_Location_ID=@IntVar_ID/-1@ AND Type2='@NoDefaultStringVar@'";
			final IStringExpression expression = compiler.compile(sql);
			final Set<String> dependsActual = expression.getParameterNames();
			final Set<String> dependsExpected = ImmutableSet.of("StringVar", "IntVar_ID", "NoDefaultStringVar");
			assertEquals(dependsExpected, dependsActual);
		}
	}

	@Test
	public void test_compileStringExpression_emptyTag()
	{
		final String expressionStr = "C_BPartner_ID=@C_BPartner_ID@ AND Text='@@'";
		final IStringExpression expression = compiler.compile(expressionStr);
		final Set<String> expectedParams = ImmutableSet.of("C_BPartner_ID");
		assertEquals(expectedParams, expression.getParameterNames(), "Invalid params");

		assertEquals("Formated expression shall be equal to initial expression", expressionStr, expression.getFormatedExpressionString());

		// Try to evaluate it
		final MockedEvaluatee ctx = new MockedEvaluatee();
		ctx.put("C_BPartner_ID", "123");
	}

	@Test
	public void test_compileStringExpression_noClosingTag()
	{
		final String expressionStr = "C_BPartner_ID=@C_BPartner_ID and closing tag is missing";
		final IStringExpression expression = compiler.compile(expressionStr);
		Assertions.assertThrows(ExpressionCompileException.class, () -> compiler.compile(expressionStr));
	}

	@Test
	public void test_compileStringExpression_NullExpression()
	{
		Assertions.assertSame(IStringExpression.NULL, compiler.compile(null));
		Assertions.assertSame(IStringExpression.NULL, compiler.compile(""));

		// empty expressions with wildcards shall be compiled to a regular expression
		Assertions.assertNotSame(IStringExpression.NULL, compiler.compile("   "));
	}

	@Test
	public void test_compileStringExpression_EmptyExpressionWithSpaces()
	{
		// empty expressions with wildcards shall be compiled to a regular expression
		final String expressionStr = "     ";
		final IStringExpression expression = compiler.compile(expressionStr);
		Assertions.assertNotSame(IStringExpression.NULL, expression, "Empty expressions with wildcards shall be compiled to a regular expression");
		Assertions.assertEquals(expressionStr, expression.getFormatedExpressionString());
	}

	@Test
	public void test_compileStringExpression_WithNoParameters()
	{
		final String expressionStr = "C_BPartner_Location.C_BPartner_ID=bp.C_BPartner_ID and no parameters";
		final ConstantStringExpression expression = (ConstantStringExpression)compiler.compile(expressionStr);
		Assertions.assertEquals(expressionStr, expression.getFormatedExpressionString());
	}

	@Test
	public void test_compileStringExpression_StartingWithParameter()
	{
		final String expressionStr = "@C_BPartner_ID@=C_BPartner_ID";
		final IStringExpression expression = compiler.compile(expressionStr);
		Assertions.assertEquals(expressionStr, expression.getFormatedExpressionString());
	}

}
