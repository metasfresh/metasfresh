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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.util.Properties;

@ExtendWith(AdempiereTestWatcher.class)
public class LogicExpressionCompilerTests
{
	@BeforeAll
	public static void staticInit()
	{
		// needed in case we throw AdempiereExceptions which are checking the database for translating the messages
		AdempiereTestHelper.get().staticInit();
	}

	private LogicExpressionCompiler compiler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		compiler = LogicExpressionCompiler.instance;
	}

	/**
	 * Set's <code>adClientId</code> in global context and checks if it was configured correctly
	 */
	private void setClientIdAndCheck(final int adClientId)
	{
		final int expectedClientId;
		final Properties ctx = Env.getCtx();
		if (adClientId < 0)
		{
			Env.setContext(ctx, "#AD_Client_ID", "");
			expectedClientId = 0;
		}
		else
		{
			Env.setContext(Env.getCtx(), "#AD_Client_ID", adClientId);
			expectedClientId = adClientId;
		}

		final int actualClientId = Env.getAD_Client_ID(ctx);
		Assertions.assertEquals(
				expectedClientId, actualClientId, "AD_Client_ID=" + adClientId + " was not set correctly in the context");
	}

	private void assertSetGetOperatorPrecedence(final int adClientId, final boolean useOperatorPrecedenceTarget)
	{
		setClientIdAndCheck(adClientId);

		final boolean useOperatorPrecedenceDefault = compiler.isUseOperatorPrecedence();

		compiler.setUseOperatorPrecedence(useOperatorPrecedenceTarget);
		final boolean useOperatorPrecedenceActual = compiler.isUseOperatorPrecedence();

		Assertions.assertEquals(useOperatorPrecedenceTarget, useOperatorPrecedenceActual,
				"UseOperatorPrecedence was not correctly set to '" + useOperatorPrecedenceTarget + "' (default: " + useOperatorPrecedenceDefault + ")");
	}

	private ILogicExpression compile(final String expressionStr, final boolean useOperatorPrecendence)
	{
		compiler.setUseOperatorPrecedence(useOperatorPrecendence);
		return compiler.compile(expressionStr);
	}

	private ILogicExpression recompile(final ILogicExpression expression, final boolean useOperatorPrecendence)
	{
		final String expressionStr = expression.getFormatedExpressionString();
		compiler.setUseOperatorPrecedence(useOperatorPrecendence);
		return compiler.compile(expressionStr);
	}

	private void assertCompileException(@Nullable final String expressionStr)
	{
		compiler.setUseOperatorPrecedence(false);

		try
		{
			compiler.compile(expressionStr);
			Assertions.fail("Expression should throw parse exception: " + expressionStr);
		}
		catch (final ExpressionCompileException e)
		{
			// System.out.println(e.getLocalizedMessage());
		}
		catch (final AdempiereException e)
		{
			// System.out.println(e.getLocalizedMessage());
		}
	}

	@Nested
	public class compileException
	{
		@Test
		public void blank_expressions()
		{
			assertCompileException(null);
			assertCompileException("");
			assertCompileException("     ");
		}

		@Test
		public void single_var()
		{
			assertCompileException("@b@");
		}
	}

	@Test
	public void checkUseOperatorPrecedence_set_false()
	{
		// 05199: we are using adClientId>0 because there was a bug regarding this
		final int adClientId = 1;

		assertSetGetOperatorPrecedence(adClientId, false);
	}

	@Test
	public void checkUseOperatorPrecedence_set_true()
	{
		// 05199: we are using adClientId>0 because there was a bug regarding this
		final int adClientId = 1;

		assertSetGetOperatorPrecedence(adClientId, true);
	}

	@Test
	public void test_recompileExpression()
	{
		final ILogicExpression expression = compile("(@a@='5'|@b@!@c@)&@d@>3   |   @x@<'10'&@y@!@z@", true);
		final ILogicExpression expressionRecompiled = recompile(expression, true);

		Assertions.assertEquals(expression, expressionRecompiled);
	}

	@Test
	public void test_compile_EquivalentExpressions_case01()
	{
		final ILogicExpression expression = compile("(@a@='5'|@b@!@c@)&@d@>3|@x@<'10'&@y@!@z@", true);
		final ILogicExpression expression2 = compile("(@a@='5'|@b@!@c@)&@d@>3|(@x@<'10'&@y@!@z@)", true);
		Assertions.assertEquals(expression, expression2, "Expressions shall be equivalent");
	}

	@Test
	public void test_compile_EquivalentExpressions_case02()
	{
		final ILogicExpression expression = compile("(@a@='5'|@b@!@c@)&@d@>3|@x@<'10'&@y@!@z@", true);
		final ILogicExpression expressionWrong = compile("((@a@='5'|@b@!@c@)&@d@>3|@x@<'10')&@y@!@z@", true);
		Assertions.assertNotEquals(expression, expressionWrong, "Expressions shall NOT be equivalent");

		final ILogicExpression expressionGood = compile("(@a@='5'|@b@!@c@)&@d@>3|(@x@<'10'&@y@!@z@)", true);
		Assertions.assertEquals(expression, expressionGood, "Expressions shall be equivalent");

		// Just to be sure
		Assertions.assertNotEquals(expressionGood, expressionWrong, "Good and wrong expressions cannot be equal");
	}

	private void test_compile_ConstantExpressions(final boolean expectedResult, final String expressionStr)
	{
		final boolean useOperatorPrecendence = true;
		final ILogicExpression expression = compile(expressionStr, useOperatorPrecendence);

		Assertions.assertNotNull(expression, "Not null for " + expressionStr);
		Assertions.assertTrue(expression.isConstant(), "Constant expression: " + expression);
		Assertions.assertEquals(expectedResult, expression.constantValue(),"Constant value");

		// NOTE: cannot validate the string representations because in some cases they differ with some spaces or some parenthesis.
		// Assert.assertEquals("ExpressionString", expressionStr, expression.getExpressionString());
		// Assert.assertEquals("FormatedExpressionString", expressionStr, expression.getFormatedExpressionString());
		// Assert.assertEquals("toString()", expressionStr, expression.toString());
	}

	@Test
	public void test_compile_ConstantExpressions_case01()
	{
		test_compile_ConstantExpressions(false, "X=Y");
		test_compile_ConstantExpressions(true, "X=Y|A=A");
		test_compile_ConstantExpressions(true, "5=5.0");
	}

	@Test
	public void test_compile_ExpressionsWithVariables_ReducedTo_ConstantExpressions()
	{
		test_compile_ConstantExpressions(true, "@A@=1|1=1");
		test_compile_ConstantExpressions(true, "(@A@=1 & @B@=1) & @C@=1 | 1=1");
	}

	@Test
	public void test_compile_xor()
	{
		final LogicExpression expr = (LogicExpression)compile("@A@=1 ^ @B@=2", true);
		Assertions.assertEquals( "@A@=1", expr.getLeft().getExpressionString(), "Left");
		Assertions.assertEquals( "@B@=2", expr.getRight().getExpressionString(), "Right");
		Assertions.assertEquals( ILogicExpression.LOGIC_OPERATOR_XOR, expr.getOperator(), "Operator");
	}

	@Test
	public void test_compile_from_database_class()
	{
		final boolean useOperatorPrecendence = true;
		LogicExpressionsDatabase.VALID_EXPRESSIONS.forEach(exprStr -> compile(exprStr, useOperatorPrecendence));
	}
}
