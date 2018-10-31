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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.Properties;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.service.impl.PlainSysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.util.Services;

public class LogicExpressionCompilerTests
{
	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@BeforeClass
	public static void staticInit()
	{
		// needed in case we throw AdempiereExceptions which are checking the database for translating the messages
		AdempiereTestHelper.get().staticInit();
	}

	private LogicExpressionCompiler compiler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		compiler = LogicExpressionCompiler.instance;

		// 05199: registering PlainSysConfigDAO because on some machines this service is not detected
		Services.registerService(ISysConfigDAO.class, new PlainSysConfigDAO());
	}

	/**
	 * Set's <code>adClientId</code> in global context and checks if it was configured correctly
	 *
	 * @param adClientId
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
		Assert.assertEquals("AD_Client_ID=" + adClientId + " was not set correctly in the context",
				expectedClientId, actualClientId);
	}

	private void assertSetGetOperatorPrecedence(final int adClientId, final boolean useOperatorPrecedenceTarget)
	{
		setClientIdAndCheck(adClientId);

		final boolean useOperatorPrecedenceDefault = compiler.isUseOperatorPrecedence();

		compiler.setUseOperatorPrecedence(useOperatorPrecedenceTarget);
		final boolean useOperatorPrecedenceActual = compiler.isUseOperatorPrecedence();

		Assert.assertEquals("UseOperatorPrecedence was not correctly set to '" + useOperatorPrecedenceTarget + "' (default: " + useOperatorPrecedenceDefault + ")",
				useOperatorPrecedenceTarget, useOperatorPrecedenceActual);
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

		Assert.assertEquals(expression, expressionRecompiled);
	}

	@Test
	public void test_compile_EquivalentExpressions_case01()
	{
		final ILogicExpression expression = compile("(@a@='5'|@b@!@c@)&@d@>3|@x@<'10'&@y@!@z@", true);
		final ILogicExpression expression2 = compile("(@a@='5'|@b@!@c@)&@d@>3|(@x@<'10'&@y@!@z@)", true);
		Assert.assertEquals("Expressions shall be equivalent", expression, expression2);
	}

	@Test
	public void test_compile_EquivalentExpressions_case02()
	{
		final ILogicExpression expression = compile("(@a@='5'|@b@!@c@)&@d@>3|@x@<'10'&@y@!@z@", true);
		final ILogicExpression expressionWrong = compile("((@a@='5'|@b@!@c@)&@d@>3|@x@<'10')&@y@!@z@", true);
		Assert.assertThat("Expressions shall NOT be equivalent", expression, not(equalTo(expressionWrong)));

		final ILogicExpression expressionGood = compile("(@a@='5'|@b@!@c@)&@d@>3|(@x@<'10'&@y@!@z@)", true);
		Assert.assertThat("Expressions shall NOT be equivalent", expression, equalTo(expressionGood));

		// Just to be sure
		Assert.assertThat("Good and wrong expressions cannot be equal", expressionGood, not(equalTo(expressionWrong)));
	}

	private void test_compile_ConstantExpressions(final boolean expectedResult, final String expressionStr)
	{
		final boolean useOperatorPrecendence = true;
		final ILogicExpression expression = compile(expressionStr, useOperatorPrecendence);

		Assert.assertNotNull("Not null for " + expressionStr, expression);
		Assert.assertEquals("Constant expression: " + expression, true, expression.isConstant());
		Assert.assertEquals("Constant value", expectedResult, expression.constantValue());

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
		Assert.assertEquals("Left", "@A@=1", expr.getLeft().getExpressionString());
		Assert.assertEquals("Right", "@B@=2", expr.getRight().getExpressionString());
		Assert.assertEquals("Operator", ILogicExpression.LOGIC_OPERATOR_XOR, expr.getOperator());
	}

	@Test
	public void test_compile_from_database_class()
	{
		final boolean useOperatorPrecendence = true;
		LogicExpressionsDatabase.VALID_EXPRESSIONS.forEach(exprStr -> compile(exprStr, useOperatorPrecendence));
	}
}
