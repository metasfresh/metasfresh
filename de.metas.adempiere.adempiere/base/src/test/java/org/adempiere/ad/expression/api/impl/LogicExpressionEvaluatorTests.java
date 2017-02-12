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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.exceptions.ExpressionException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.service.impl.PlainSysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Evaluator;
import org.compiere.util.MockedEvaluatee;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ad
 * @author tsa
 * @see <a
 *      href="http://dewiki908/mediawiki/index.php/03093:_Introduce_paranthesis_support_for_our_logic_expressions_%282012080710000021%29">http://dewiki908/mediawiki/index.php/03093:
 *      _Introduce_paranthesis_support_for_our_logic_expressions_%282012080710000021%29</a>
 */
public class LogicExpressionEvaluatorTests
{
	private static final boolean FAIL_ON_MISSING = true;
	private static final boolean IGNORE_MISSING = false;

	private static final class Params implements Evaluatee2
	{
		public static final Params singleton(final String variableName, final String value)
		{
			return new Params()
					.addParam(variableName, value);
		}

		private final Map<String, String> map = new HashMap<String, String>();

		@Override
		public String get_ValueAsString(final String variableName)
		{
			return map.get(variableName);
		}

		public Params addParam(final String variableName, final String value)
		{
			map.put(variableName, value);
			return this;
		}

		@Override
		public String toString()
		{
			return map.toString();
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			return true;
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			return null;
		}
	}

	@BeforeClass
	public static void staticInit()
	{
		// needed in case we throw AdempiereExceptions which are checking the database for translating the messages
		AdempiereTestHelper.get().staticInit();
	}

	private LogicExpressionCompiler compiler;

	// private LogicExpressionEvaluator evaluator;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		compiler = LogicExpressionCompiler.instance;

		// 05199: registering PlainSysConfigDAO because on some machines this service is not detected
		Services.registerService(ISysConfigDAO.class, new PlainSysConfigDAO());
	}

	@After
	public void afterTest()
	{
		// POJOLookupMap.get().dumpStatus();
	}

	private void assertExpression(final boolean expectedValue, final String expressionStr, final Params params)
	{
		final boolean failOnMissingParam = true;
		assertExpression(expectedValue, expressionStr, failOnMissingParam, params);
	}

	private void assertExpression(final boolean expectedValue, final String expressionStr, final boolean failOnMissingParam, final Params params)
	{
		final boolean ignoreUnparsable = !failOnMissingParam;
		compiler.setUseOperatorPrecedence(false);

		final ILogicExpression expr = compiler.compile(expressionStr);
		final boolean actualValue = expr.evaluate(params, ignoreUnparsable);

		final String message = "Wrongly evaluated: " + expressionStr + ", params=" + params;
		Assert.assertEquals(message, expectedValue, actualValue);
	}

	private void assertTransformedExpression(final String expressionStr, final boolean failOnMissingParam, final Params params)
	{
		final boolean ignoreUnparsable = !failOnMissingParam;

		compiler.setUseOperatorPrecedence(false);
		final ILogicExpression expression = compiler.compile(expressionStr);
		// This expression shall contain parenthesis
		final String expressionFormattedStr = expression.getFormatedExpressionString();

		// Recompile the expression
		compiler.setUseOperatorPrecedence(true);
		Assert.assertTrue("UseOperatorPrecedence shall be set", compiler.isUseOperatorPrecedence());
		final ILogicExpression expressionNew = compiler.compile(expressionFormattedStr);
		final String expressionNewFormattedStr = expressionNew.getFormatedExpressionString();

		// Assume both expressions are equal
		Assert.assertEquals("Compiled and recompiled expressions shall be equal", expression, expressionNew);

		final boolean valueOld = expression.evaluate(params, ignoreUnparsable);
		final boolean valueNew = expressionNew.evaluate(params, ignoreUnparsable);
		final String message = "Wrongly evaluated:"
				+ "\n              expression=[" + expressionStr + "]"
				+ "\n, expressionFormattedStr=[" + expressionFormattedStr + "]"
				+ "\n,          expressionNew=[" + expressionNewFormattedStr + "]"
				+ "\n,                 params=" + params;
		Assert.assertEquals(message, valueNew, valueOld);
	}

	private void assertPrecedenceExpression(final boolean expectedValue, final String expressionStr, final boolean failOnMissingParam, final Params params)
	{
		final boolean ignoreUnparsable = !failOnMissingParam;
		compiler.setUseOperatorPrecedence(true);
		Assert.assertTrue("Invalid UseOperatorPrecedence", compiler.isUseOperatorPrecedence());

		final ILogicExpression expr = compiler.compile(expressionStr);
		final boolean actualValue = expr.evaluate(params, ignoreUnparsable);

		final String message = "Wrongly evaluated: " + expressionStr + ", params=" + params;
		Assert.assertEquals(message, expectedValue, actualValue);
	}

	private void assertCompileException(final String expressionStr)
	{
		compiler.setUseOperatorPrecedence(false);

		try
		{
			compiler.compile(expressionStr);
			Assert.fail("Expression should throw parse exception: " + expressionStr);
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

	private void assertException(final String expressionStr, final boolean failOnMissingParam, final Params params)
	{
		compiler.setUseOperatorPrecedence(false);

		try
		{
			final ILogicExpression expr = compiler.compile(expressionStr);
			final boolean ignoreUnparsable = !failOnMissingParam;
			final boolean value = expr.evaluate(params, ignoreUnparsable);
			Assert.fail("Expression should throw parse exception: " + expressionStr + ", params=" + params + ", value=" + value);
		}
		catch (final ExpressionException e)
		{
			// System.out.println(e.getLocalizedMessage());
		}
		catch (final AdempiereException e)
		{
			// System.out.println(e.getLocalizedMessage());
		}
	}

	@Test
	public void testNotOperators()
	{
		Check.setDefaultExClass(AdempiereException.class); // setting the exception that a failing assumption shall throw

		assertCompileException(null);
		assertCompileException("");
		assertCompileException("     ");

		assertExpression(true, "5!@a@", false, new Params()
				.addParam("a", "3"));

		assertExpression(true, "5=@a@ & (@b@!3 & @c@!4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "4")
				.addParam("c", "3"));
	}

	@Test
	public void generalTests()
	{
		Check.setDefaultExClass(AdempiereException.class); // setting the exception that a failing assumption shall throw

		assertCompileException(null);
		assertCompileException("");
		assertCompileException("     ");

		assertExpression(true, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "4"));
		assertExpression(false, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "0")
				.addParam("b", "3")
				.addParam("c", "4"));
		assertExpression(true, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "0")
				.addParam("c", "4"));
		assertExpression(false, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "0")
				.addParam("c", "0"));

		assertExpression(true, "((@a@='5' | @b@!@c@) & @d@>3)| (   @x@<'10'& (@y@!@z@) )", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3")
				.addParam("d", "4")
				.addParam("x", "3")
				.addParam("y", "3")
				.addParam("z", "3"));

		assertExpression(true, "@a@=5   &    (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));

		assertExpression(true, "@a@=5", false, new Params()
				.addParam("a", "5"));

		assertExpression(true, "@a@=5 & @b@=3 | @c@=4", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));

		assertExpression(false, "@b@=3", false, new Params()
				.addParam("b", "2"));
		assertExpression(true, "@b@=3", false, new Params()
				.addParam("b", "3"));
		assertExpression(false, "3=@b@", false, new Params()
				.addParam("b", "2"));
		assertExpression(true, "3=@b@", false, new Params()
				.addParam("b", "3"));

		assertExpression(false, "(@b@=3)", false, new Params()
				.addParam("b", "2"));
		assertExpression(true, "(@b@=3)", false, new Params()
				.addParam("b", "3"));

		assertCompileException("@b@");

		assertExpression(true, "5=@a@ & @b@=2 | @c@=4", false, new Params()
				.addParam("a", "3")
				.addParam("b", "3")
				.addParam("c", "4"));

		assertExpression(false, "@a@=5   &    @b@=3 | @c@=4 & @d@=5", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3")
				.addParam("d", "3"));

	}

	@Test
	public void checkMissingParameter_Fail()
	{
		assertException("@a@=5 & @b@=3 | @c@=4", FAIL_ON_MISSING, new Params()
				.addParam("a", "5")
				// .addParam("b", "3")
				.addParam("c", "3"));
	}

	@Test
	public void checkMissingParameter_Ignore()
	{
		assertExpression(false, "@a@=5 & @b@=3 | @c@=4", IGNORE_MISSING, new Params()
				.addParam("a", "5")
				// .addParam("b", "3")
				.addParam("c", "3"));
	}

	@Test
	public void checkExpressionWithDefaultValues()
	{
		assertExpression(true, "@a@=5 & @b/3@=3 | @c@=4", FAIL_ON_MISSING, new Params()
				.addParam("a", "5")
				// .addParam("b", "3")
				.addParam("c", "3"));

		assertExpression(false, "@a@=5 & @b/4@=3 | @c@=4", FAIL_ON_MISSING, new Params()
				.addParam("a", "5")
				// .addParam("b", "3")
				.addParam("c", "3"));

		assertExpression(true, "@a@=5 & @b/3@=3 | @c@=4", FAIL_ON_MISSING, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));

		assertExpression(false, "@a@=5 & @b/3@=3 | @c@=4", FAIL_ON_MISSING, new Params()
				.addParam("a", "5")
				.addParam("b", "4")
				.addParam("c", "3"));

		assertExpression(true, "@a@=5 & @b/4@=3 | @c@=4", FAIL_ON_MISSING, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));

		// 04659: verifying that default values also work in conjunction with "!"
		assertExpression(true, "@M_Attribute_ID/0@!0", FAIL_ON_MISSING, new Params()
				.addParam("M_Attribute_ID", "123"));

		assertExpression(false, "@M_Attribute_ID/0@!0", FAIL_ON_MISSING, new Params());
		// 04659 end
	}

	@SuppressWarnings("deprecation")
	private static final boolean evaluateLogic(final String expressionStr, final Evaluatee evalCtx)
	{
		return Evaluator.evaluateLogic(evalCtx, expressionStr);
	}

	/**
	 * Test expression evaluations by using deprecated {@link Evaluator#evaluateLogic(Evaluatee, String)}.
	 */
	@Test
	public void test_evaluateLogic_defaultValue()
	{
		final MockedEvaluatee ev = new MockedEvaluatee();
		ev.put("name1", "value1");

		// guard: the case, where we don't need the default should work, too
		assertEquals(true, evaluateLogic("@name1@=value1", ev));
		assertEquals(false, evaluateLogic("@name1@!value1", ev));

		assertEquals(true, evaluateLogic("@noSuchName1/defaultVal@=defaultVal", ev));
		assertEquals(false, evaluateLogic("@noSuchName1/defaultVal@!defaultVal", ev));

		// 'and " outside of the @ tags should be stripped
		assertEquals(true, evaluateLogic("@noSuchName1/defaultVal@='defaultVal'", ev));
		assertEquals(true, evaluateLogic("@noSuchName1/defaultVal@=\"defaultVal\"", ev));

		// because ' and " are stripped outside of @ tags, they need to be ignored, when evaluating default values, too
		assertEquals(true, evaluateLogic("@noSuchName1/'defaultVal'@='defaultVal'", ev));
		assertEquals(true, evaluateLogic("@noSuchName1/'defaultVal'@=defaultVal", ev));

		// make sure that partial quotes are not stripped out
		assertEquals(true, evaluateLogic("@noSuchName1/'defaultVal@='defaultVal", ev));
		assertEquals(true, evaluateLogic("@noSuchName1/defaultVal'@=defaultVal'", ev));
	}

	@Test
	public void checkExpressionWithPrecedence_case1_no_parenthesis()
	{
		// shall be evaluated as: 5=5 | 3=2 & 3=4 => (5=5) | (3=2 & 3=4) => TRUE
		assertPrecedenceExpression(true, "5=@a@ | @b@=2 & @c@=4", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));
	}

	@Test
	public void checkExpressionWithPrecedence_case1_with_parenthesis()
	{
		assertPrecedenceExpression(false, "(5=@a@ | @b@=2)& @c@=4", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));
	}

	@Test
	public void checkExpressionWithPrecedence_case2()
	{
		assertPrecedenceExpression(true, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "4"));
		assertPrecedenceExpression(false, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "0")
				.addParam("b", "3")
				.addParam("c", "4"));
		assertPrecedenceExpression(true, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "0")
				.addParam("c", "4"));
		assertPrecedenceExpression(false, "5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "0")
				.addParam("c", "0"));
	}

	@Test
	public void checkExpressionWithPrecedence_case3()
	{
		assertPrecedenceExpression(true, "((@a@='5' | @b@!@c@) & @d@>3)| (   @x@<'10'& (@y@!@z@) )", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3")
				.addParam("d", "4")
				.addParam("x", "3")
				.addParam("y", "3")
				.addParam("z", "3"));

	}

	@Test
	public void checkExpressionWithPrecedence_case4()
	{
		assertPrecedenceExpression(true, "@a@=5   &    (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));
	}

	@Test
	public void checkExpressionWithPrecedence_case5()
	{
		assertPrecedenceExpression(true, "@a@=5", false, new Params()
				.addParam("a", "5"));
	}

	@Test
	public void checkExpressionWithPrecedence_case6()
	{
		assertPrecedenceExpression(true, "@a@=5 & @b@=3 | @c@=4", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));
	}

	@Test
	public void checkExpressionWithPrecedence_case7()
	{
		assertPrecedenceExpression(false, "@b@=3", false, new Params()
				.addParam("b", "2"));
		assertPrecedenceExpression(true, "@b@=3", false, new Params()
				.addParam("b", "3"));
		assertPrecedenceExpression(false, "3=@b@", false, new Params()
				.addParam("b", "2"));
		assertPrecedenceExpression(true, "3=@b@", false, new Params()
				.addParam("b", "3"));

		assertPrecedenceExpression(false, "(@b@=3)", false, new Params()
				.addParam("b", "2"));
		assertPrecedenceExpression(true, "(@b@=3)", false, new Params()
				.addParam("b", "3"));

	}

	@Test
	public void checkExpressionWithPrecedence_case8()
	{
		assertPrecedenceExpression(true, "5=@a@ & @b@=2 | @c@=4", false, new Params()
				.addParam("a", "3")
				.addParam("b", "3")
				.addParam("c", "4"));

	}

	@Test
	public void checkExpressionWithPrecedence_case9()
	{
		assertPrecedenceExpression(true, "@a@=5   &    @b@=3 | @c@=4 & @d@=5", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3")
				.addParam("d", "3"));
	}

	@Test
	public void checkExpressionWithPrecedence_case10()
	{
		assertPrecedenceExpression(true, "(((@a@=5|@b@=3|@c@=5)&@d@=4|@x@=5)&@y@=3)&@z@=3", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3")
				.addParam("d", "4")
				.addParam("x", "3")
				.addParam("y", "3")
				.addParam("z", "3"));
	}

	@Test
	public void transformationTests_case01()
	{
		assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "4"));
		assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "0")
				.addParam("b", "3")
				.addParam("c", "4"));
		assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "0")
				.addParam("c", "4"));
		assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "0")
				.addParam("c", "0"));
	}

	@Test
	public void transformationTests_case02()
	{
		assertTransformedExpression("((@a@='5' | @b@!@c@) & @d@>3)| (   @x@<'10'& (@y@!@z@) )", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3")
				.addParam("d", "4")
				.addParam("x", "3")
				.addParam("y", "3")
				.addParam("z", "3"));
	}

	@Test
	public void transformationTests_case03()
	{
		assertTransformedExpression("@a@=5   &    (@b@=3 | @c@=4)", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));

		assertTransformedExpression("@a@=5", false, new Params()
				.addParam("a", "5"));

		assertTransformedExpression("@a@=5 & @b@=3 | @c@=4", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));

		assertTransformedExpression("@a@=5 | @b@=3 & @c@=4", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3"));

		assertTransformedExpression("@b@=3", false, new Params()
				.addParam("b", "2"));
		assertTransformedExpression("@b@=3", false, new Params()
				.addParam("b", "3"));
		assertTransformedExpression("3=@b@", false, new Params()
				.addParam("b", "2"));
		assertTransformedExpression("3=@b@", false, new Params()
				.addParam("b", "3"));

		assertTransformedExpression("(@b@=3)", false, new Params()
				.addParam("b", "2"));
		assertTransformedExpression("(@b@=3)", false, new Params()
				.addParam("b", "3"));

		assertTransformedExpression("5=@a@ & @b@=2 | @c@=4", false, new Params()
				.addParam("a", "3")
				.addParam("b", "3")
				.addParam("c", "4"));

		assertTransformedExpression("@a@=5   &    @b@=3 | @c@=4 & @d@=5", false, new Params()
				.addParam("a", "5")
				.addParam("b", "3")
				.addParam("c", "3")
				.addParam("d", "3"));

	}

	@Test
	public void test_stripQuotes()
	{
		Assert.assertEquals(null, LogicExpressionEvaluator.stripQuotes(null));
		Assert.assertEquals("", LogicExpressionEvaluator.stripQuotes(""));

		// Quote: '
		Assert.assertEquals("'", LogicExpressionEvaluator.stripQuotes("'"));
		Assert.assertEquals("", LogicExpressionEvaluator.stripQuotes("''"));
		Assert.assertEquals("test", LogicExpressionEvaluator.stripQuotes("test"));
		Assert.assertEquals("'test", LogicExpressionEvaluator.stripQuotes("'test"));
		Assert.assertEquals("test'", LogicExpressionEvaluator.stripQuotes("test'"));
		Assert.assertEquals("test", LogicExpressionEvaluator.stripQuotes("test"));

		// Quote: "
		Assert.assertEquals("\"", LogicExpressionEvaluator.stripQuotes("\""));
		Assert.assertEquals("", LogicExpressionEvaluator.stripQuotes("\"\""));
		Assert.assertEquals("test", LogicExpressionEvaluator.stripQuotes("test"));
		Assert.assertEquals("\"test", LogicExpressionEvaluator.stripQuotes("\"test"));
		Assert.assertEquals("test\"", LogicExpressionEvaluator.stripQuotes("test\""));
		Assert.assertEquals("test", LogicExpressionEvaluator.stripQuotes("test"));

		// Combined quotes: only first quote shall be stripped
		Assert.assertEquals("''", LogicExpressionEvaluator.stripQuotes("\"''\""));
		Assert.assertEquals("\"\"", LogicExpressionEvaluator.stripQuotes("'\"\"'"));
		Assert.assertEquals("'test'", LogicExpressionEvaluator.stripQuotes("\"'test'\""));
		Assert.assertEquals("\"test\"", LogicExpressionEvaluator.stripQuotes("'\"test\"'"));

		// Don't strip quotes from middle
		Assert.assertEquals("this is a 'test' string", LogicExpressionEvaluator.stripQuotes("this is a 'test' string"));
		Assert.assertEquals("this is a \"test\" string", LogicExpressionEvaluator.stripQuotes("this is a \"test\" string"));
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void test_evaluateLogic_OnVariableNotFound_Empty_NotSupported()
	{
		final String expressionStr = "@Variable@=SomeValue";
		final Params params = new Params();
		final Boolean expectedValue = null; // N/A
		test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.Empty, params);
	}

	@Test
	public void test_evaluateLogic_ConstantExpr_NullContext()
	{
		final Params params = null;

		for (final OnVariableNotFound onVariableNotFound : OnVariableNotFound.values())
		{
			if (onVariableNotFound == OnVariableNotFound.Preserve
					|| onVariableNotFound == OnVariableNotFound.Empty)
			{
				// skip it because it's not supported
				continue;
			}

			//
			// Expressions without variables
			test_evaluateLogic_OnVariableNotFound(
					true // expectedValue
					, "1=1" // expressionStr
					, onVariableNotFound //
					, params);
			test_evaluateLogic_OnVariableNotFound(
					false // expectedValue
					, "1=0" // expressionStr
					, onVariableNotFound //
					, params);
			//
			// Expressions with variables but with default values
			test_evaluateLogic_OnVariableNotFound(
					true // expectedValue
					, "@Variable/Y@=Y" // expressionStr
					, onVariableNotFound //
					, params);
			test_evaluateLogic_OnVariableNotFound(
					false // expectedValue
					, "@Variable/N@=Y" // expressionStr
					, onVariableNotFound //
					, params);
			//
			// Pure constant expressions
			test_evaluateLogic_OnVariableNotFound(
					true // expectedValue
					, ILogicExpression.TRUE // expression
					, onVariableNotFound //
					, params);
			test_evaluateLogic_OnVariableNotFound(
					false // expectedValue
					, ILogicExpression.FALSE // expression
					, onVariableNotFound //
					, params);
		}
	}

	@Test
	public void test_evaluateLogic_OnVariableNotFound_ReturnNoResult()
	{
		final String expressionStr = "@Variable@=SomeValue";
		final Params params = new Params();
		final Boolean expectedValue = false;
		test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.ReturnNoResult, params);
	}

	/**
	 * Test for backward compatibility: when using {@link OnVariableNotFound#ReturnNoResult} and variable not found,
	 * but variable ends with "_ID" it shall be evaluated to ZERO.
	 *
	 * NOTE: i know it's not obvious, but it's backward compatible.
	 */
	@Test
	public void test_evaluateLogic_OnVariableNotFound_ReturnNoResult_IDVariable()
	{
		final String expressionStr = "@Variable_ID@=0";
		final Params params = new Params();
		final Boolean expectedValue = true;
		test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.ReturnNoResult, params);
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void test_evaluateLogic_OnVariableNotFound_Fail()
	{
		final String expressionStr = "@Variable@=SomeValue";
		final Params params = new Params();
		final Boolean expectedValue = null; // N/A
		test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.Fail, params);
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void test_evaluateLogic_OnVariableNotFound_Preserve_NotSupported()
	{
		final String expressionStr = "@Variable@=SomeValue";
		final Params params = new Params();
		final Boolean expectedValue = null; // N/A
		test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.Fail, params);
	}

	/**
	 * Test corner case: Make sure "0" is not considered as no property value by {@link Env#isPropertyValueNull(String, String)}.
	 */
	@Test
	public void test_evaluateLogic_VariableValueZero()
	{
		final String expressionStr = "@Variable@=0";
		final Params params = new Params()
				.addParam("Variable", "0");
		final Boolean expectedValue = true;
		test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.Fail, params);
	}

	private void test_evaluateLogic_MissingVariablesWhichDoesNotMatter(final boolean expectedValue,
			final String expressionStr,
			final Params params)
	{
		for (OnVariableNotFound onVariableNotFound : Arrays.asList(OnVariableNotFound.ReturnNoResult, OnVariableNotFound.Fail))
		{
			test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, onVariableNotFound, params);
		}
	}

	@Test
	public void test_evaluateLogic_MissingVariablesWhichDoesNotMatter_AndExpression_LeftIsFalse()
	{
		final Params params = new Params()
				.addParam("A", "not-one");
		test_evaluateLogic_MissingVariablesWhichDoesNotMatter(false, "@A@=1 & @MissingB@=2", params);
	}

	@Test
	public void test_evaluateLogic_MissingVariablesWhichDoesNotMatter_AndExpression_RightIsFalse()
	{
		final Params params = new Params()
				.addParam("A", "not-one");
		test_evaluateLogic_MissingVariablesWhichDoesNotMatter(false, "@MissingB@=2 & @A@=1", params);
	}

	@Test
	public void test_evaluateLogic_MissingVariablesWhichDoesNotMatter_OrExpression_LeftIsTrue()
	{
		final Params params = new Params()
				.addParam("A", "1");
		test_evaluateLogic_MissingVariablesWhichDoesNotMatter(true, "@A@=1 | @MissingB@=2", params);
	}

	@Test
	public void test_evaluateLogic_MissingVariablesWhichDoesNotMatter_OrExpression_RightIsTrue()
	{
		final Params params = new Params()
				.addParam("A", "1");
		test_evaluateLogic_MissingVariablesWhichDoesNotMatter(true, "@MissingB@=2 | @A@=1", params);
	}

	@Test
	public void test_evaluateLogic_MissingVariablesWhichDoesNotMatter_OrExpression_LastOneIsTrue()
	{
		final Params params = new Params()
				.addParam("Z", "1");
		test_evaluateLogic_MissingVariablesWhichDoesNotMatter(true, "(@A@=1 & @B@=1) & @C@=1 | @Z@=1", params);
	}

	private final void test_evaluateLogic_OnVariableNotFound(final Boolean expectedValue,
			final String expressionStr,
			final OnVariableNotFound onVariableNotFound,
			final Params params)
	{
		// Compile
		compiler.setUseOperatorPrecedence(true);
		final ILogicExpression expression = compiler.compile(expressionStr);

		test_evaluateLogic_OnVariableNotFound(expectedValue, expression, onVariableNotFound, params);
	}

	private final void test_evaluateLogic_OnVariableNotFound(final Boolean expectedValue,
			final ILogicExpression expression,
			final OnVariableNotFound onVariableNotFound,
			final Params params)
	{
		// Evaluate
		final Boolean actualValue = expression.evaluate(params, onVariableNotFound);

		final String message = "Wrongly evaluated: " + expression
				+ "\n Params=" + params
				+ "\n OnVariableNotFound: " + onVariableNotFound
				+ "\n";
		Assert.assertEquals(message, expectedValue, actualValue);
	}

	@Test
	public void test_evaluateLogic_xor()
	{
		final String expressionStr = "@A@=1 ^ @B@=2";
		assertExpression(false, expressionStr, new Params()
				.addParam("A", "not1")
				.addParam("B", "not2"));
		assertExpression(true, expressionStr, new Params()
				.addParam("A", "1")
				.addParam("B", "not2"));
		assertExpression(true, expressionStr, new Params()
				.addParam("A", "not1")
				.addParam("B", "2"));
		assertExpression(false, expressionStr, new Params()
				.addParam("A", "1")
				.addParam("B", "2"));
	}

	@Test
	public void test_evaluateLogic_negate()
	{
		final ILogicExpression expr = compiler.compile("@A@=1");
		final ILogicExpression exprNegated = expr.negate();

		Assert.assertEquals(true, expr.evaluate(Params.singleton("A", "1"), OnVariableNotFound.Fail));
		Assert.assertEquals(false, exprNegated.evaluate(Params.singleton("A", "1"), OnVariableNotFound.Fail));

		Assert.assertEquals(false, expr.evaluate(Params.singleton("A", "not1"), OnVariableNotFound.Fail));
		Assert.assertEquals(true, exprNegated.evaluate(Params.singleton("A", "not1"), OnVariableNotFound.Fail));
	}

}
