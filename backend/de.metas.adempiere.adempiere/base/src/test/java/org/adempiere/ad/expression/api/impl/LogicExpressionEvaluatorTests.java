package org.adempiere.ad.expression.api.impl;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractBooleanAssert;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Evaluatees;
import org.compiere.util.Evaluator;
import org.compiere.util.MockedEvaluatee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author ad
 * @author tsa
 * @see <a
 * href="http://dewiki908/mediawiki/index.php/03093:_Introduce_paranthesis_support_for_our_logic_expressions_%282012080710000021%29">http://dewiki908/mediawiki/index.php/03093:
 * _Introduce_paranthesis_support_for_our_logic_expressions_%282012080710000021%29</a>
 */
public class LogicExpressionEvaluatorTests
{
	private static final boolean FAIL_ON_MISSING = true;
	private static final boolean IGNORE_MISSING = false;

	private static final class Params implements Evaluatee2
	{
		@SuppressWarnings("SameParameterValue")
		static Params singleton(final String variableName, final String value)
		{
			return new Params()
					.addParam(variableName, value);
		}

		private final Map<String, String> map = new HashMap<>();

		@Override
		public String get_ValueAsString(final String variableName)
		{
			return map.get(variableName);
		}

		Params addParam(final String variableName, final String value)
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

	@BeforeAll
	static void staticInit()
	{
		// needed in case we throw AdempiereExceptions which are checking the database for translating the messages
		AdempiereTestHelper.get().init();
	}

	private LogicExpressionCompiler compiler;

	@BeforeEach
	void init()
	{
		// AdempiereTestHelper.get().init();
		compiler = LogicExpressionCompiler.instance;
	}

	@SuppressWarnings("SameParameterValue")
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
		assertThat(actualValue).as(message).isEqualTo(expectedValue);
	}

	@Test
	void randomGeneralTests()
	{
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

	@Nested
	class operators
	{
		@Test
		void not_operator()
		{
			assertExpression(true, "5!@a@", false, new Params()
					.addParam("a", "3"));

			assertExpression(true, "5=@a@ & (@b@!3 & @c@!4)", false, new Params()
					.addParam("a", "5")
					.addParam("b", "4")
					.addParam("c", "3"));
		}
	}

	@Nested
	class checkMissingParameter
	{
		@Test
		void failOnMissing()
		{
			final Params params = new Params()
					.addParam("a", "5")
					// .addParam("b", "3")
					.addParam("c", "3");

			compiler.setUseOperatorPrecedence(false);
			final ILogicExpression expr = compiler.compile("@a@=5 & @b@=3 | @c@=4");
			assertThatThrownBy(() -> expr.evaluate(params, false))
					.isInstanceOf(ExpressionEvaluationException.class)
					.hasMessageStartingWith("Parameter 'b' not found in context");
		}

		@Test
		void ignoreMissing()
		{
			assertExpression(false, "@a@=5 & @b@=3 | @c@=4", IGNORE_MISSING, new Params()
					.addParam("a", "5")
					// .addParam("b", "3")
					.addParam("c", "3"));
		}

		@Test
		void failOnMissing_but_HaveDefaultValues()
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
	}

	@Nested
	class evaluateLogic
	{
		@SuppressWarnings("deprecation")
		private boolean evaluateLogicExpression_usingDeprecatedMethod(final String expressionStr, final Evaluatee evalCtx)
		{
			return Evaluator.evaluateLogic(evalCtx, expressionStr);
		}

		/**
		 * Test expression evaluations by using deprecated {@link Evaluator#evaluateLogic(Evaluatee, String)}.
		 */
		@Test
		void defaultValue()
		{
			final MockedEvaluatee ev = new MockedEvaluatee();
			ev.put("name1", "value1");

			// guard: the case, where we don't need the default should work, too
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@name1@=value1", ev)).isTrue();
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@name1@!value1", ev)).isFalse();

			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/defaultVal@=defaultVal", ev)).isTrue();
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/defaultVal@!defaultVal", ev)).isFalse();

			// 'and " outside the @ tags should be stripped
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/defaultVal@='defaultVal'", ev)).isTrue();
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/defaultVal@=\"defaultVal\"", ev)).isTrue();

			// because ' and " are stripped outside @ tags, they need to be ignored, when evaluating default values, too
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/'defaultVal'@='defaultVal'", ev)).isTrue();
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/'defaultVal'@=defaultVal", ev)).isTrue();

			// make sure that partial quotes are not stripped out
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/'defaultVal@='defaultVal", ev)).isTrue();
			assertThat(evaluateLogicExpression_usingDeprecatedMethod("@noSuchName1/defaultVal'@=defaultVal'", ev)).isTrue();
		}

		@Test
		void OnVariableNotFound_Empty_NotSupported()
		{
			final String expressionStr = "@Variable@=SomeValue";
			final Params params = new Params();
			final Boolean expectedValue = null; // N/A

			assertThatThrownBy(() -> test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.Empty, params))
					.isInstanceOf(ExpressionEvaluationException.class);
		}

		@Test
		void ConstantExpr_NullContext()
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
						, ConstantLogicExpression.TRUE // expression
						, onVariableNotFound //
						, params);
				test_evaluateLogic_OnVariableNotFound(
						false // expectedValue
						, ConstantLogicExpression.FALSE // expression
						, onVariableNotFound //
						, params);
			}
		}

		@Test
		void OnVariableNotFound_ReturnNoResult()
		{
			final String expressionStr = "@Variable@=SomeValue";
			final Params params = new Params();
			final Boolean expectedValue = false;
			test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.ReturnNoResult, params);
		}

		/**
		 * Test for backward compatibility: when using {@link OnVariableNotFound#ReturnNoResult} and variable not found,
		 * but variable ends with "_ID" it shall be evaluated to ZERO.
		 * <p>
		 * NOTE: I know it's not obvious, but it's backward compatible.
		 */
		@Test
		void OnVariableNotFound_ReturnNoResult_IDVariable()
		{
			final String expressionStr = "@Variable_ID@=0";
			final Params params = new Params();
			final Boolean expectedValue = true;
			test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.ReturnNoResult, params);
		}

		@Test
		void OnVariableNotFound_Fail()
		{
			final String expressionStr = "@Variable@=SomeValue";
			final Params params = new Params();
			final Boolean expectedValue = null; // N/A

			assertThatThrownBy(() -> test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.Fail, params))
					.isInstanceOf(ExpressionEvaluationException.class);
		}

		@Test
		void OnVariableNotFound_Preserve_NotSupported()
		{
			final String expressionStr = "@Variable@=SomeValue";
			final Params params = new Params();
			final Boolean expectedValue = null; // N/A

			assertThatThrownBy(() -> test_evaluateLogic_OnVariableNotFound(expectedValue, expressionStr, OnVariableNotFound.Fail, params))
					.isInstanceOf(ExpressionEvaluationException.class);
		}

		/**
		 * Test corner case: Make sure "0" is not considered as no property value by {@link Env#isPropertyValueNull(String, String)}.
		 */
		@Test
		void VariableValueZero()
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
		void MissingVariablesWhichDoesNotMatter_AndExpression_LeftIsFalse()
		{
			final Params params = new Params()
					.addParam("A", "not-one");
			test_evaluateLogic_MissingVariablesWhichDoesNotMatter(false, "@A@=1 & @MissingB@=2", params);
		}

		@Test
		void MissingVariablesWhichDoesNotMatter_AndExpression_RightIsFalse()
		{
			final Params params = new Params()
					.addParam("A", "not-one");
			test_evaluateLogic_MissingVariablesWhichDoesNotMatter(false, "@MissingB@=2 & @A@=1", params);
		}

		@Test
		void MissingVariablesWhichDoesNotMatter_OrExpression_LeftIsTrue()
		{
			final Params params = new Params()
					.addParam("A", "1");
			test_evaluateLogic_MissingVariablesWhichDoesNotMatter(true, "@A@=1 | @MissingB@=2", params);
		}

		@Test
		void MissingVariablesWhichDoesNotMatter_OrExpression_RightIsTrue()
		{
			final Params params = new Params()
					.addParam("A", "1");
			test_evaluateLogic_MissingVariablesWhichDoesNotMatter(true, "@MissingB@=2 | @A@=1", params);
		}

		@Test
		void MissingVariablesWhichDoesNotMatter_OrExpression_LastOneIsTrue()
		{
			final Params params = new Params()
					.addParam("Z", "1");
			test_evaluateLogic_MissingVariablesWhichDoesNotMatter(true, "(@A@=1 & @B@=1) & @C@=1 | @Z@=1", params);
		}

		private void test_evaluateLogic_OnVariableNotFound(final Boolean expectedValue,
														   final String expressionStr,
														   final OnVariableNotFound onVariableNotFound,
														   final Params params)
		{
			// Compile
			compiler.setUseOperatorPrecedence(true);
			final ILogicExpression expression = compiler.compile(expressionStr);

			test_evaluateLogic_OnVariableNotFound(expectedValue, expression, onVariableNotFound, params);
		}

		private void test_evaluateLogic_OnVariableNotFound(final Boolean expectedValue,
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
			assertThat(actualValue).as(message).isEqualTo(expectedValue);
		}

		@Test
		void xor()
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
		void negate()
		{
			final ILogicExpression expr = compiler.compile("@A@=1");
			final ILogicExpression exprNegated = expr.negate();

			assertThat(expr.evaluate(Params.singleton("A", "1"), OnVariableNotFound.Fail)).isTrue();
			assertThat(exprNegated.evaluate(Params.singleton("A", "1"), OnVariableNotFound.Fail)).isFalse();

			assertThat(expr.evaluate(Params.singleton("A", "not1"), OnVariableNotFound.Fail)).isFalse();
			assertThat(exprNegated.evaluate(Params.singleton("A", "not1"), OnVariableNotFound.Fail)).isTrue();
		}
	}

	@Nested
	class checkExpressionWithPrecedence
	{
		private void assertPrecedenceExpression(final boolean expectedValue, final String expressionStr, final Params params)
		{
			compiler.setUseOperatorPrecedence(true);
			assertThat(compiler.isUseOperatorPrecedence()).as("Invalid UseOperatorPrecedence").isTrue();

			final ILogicExpression expr = compiler.compile(expressionStr);
			final boolean actualValue = expr.evaluate(params, true);

			assertThat(actualValue).as("Wrongly evaluated: " + expressionStr + ", params=" + params).isEqualTo(expectedValue);
		}

		@Test
		void case1_no_parenthesis()
		{
			// shall be evaluated as: 5=5 | 3=2 & 3=4 => (5=5) | (3=2 & 3=4) => TRUE
			assertPrecedenceExpression(true, "5=@a@ | @b@=2 & @c@=4", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3"));
		}

		@Test
		void case1_with_parenthesis()
		{
			assertPrecedenceExpression(false, "(5=@a@ | @b@=2)& @c@=4", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3"));
		}

		@Test
		void case2()
		{
			assertPrecedenceExpression(true, "5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "4"));
			assertPrecedenceExpression(false, "5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "0")
					.addParam("b", "3")
					.addParam("c", "4"));
			assertPrecedenceExpression(true, "5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "0")
					.addParam("c", "4"));
			assertPrecedenceExpression(false, "5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "0")
					.addParam("c", "0"));
		}

		@Test
		void case3()
		{
			assertPrecedenceExpression(true, "((@a@='5' | @b@!@c@) & @d@>3)| (   @x@<'10'& (@y@!@z@) )", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3")
					.addParam("d", "4")
					.addParam("x", "3")
					.addParam("y", "3")
					.addParam("z", "3"));

		}

		@Test
		void case4()
		{
			assertPrecedenceExpression(true, "@a@=5   &    (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3"));
		}

		@Test
		void case5()
		{
			assertPrecedenceExpression(true, "@a@=5", new Params()
					.addParam("a", "5"));
		}

		@Test
		void case6()
		{
			assertPrecedenceExpression(true, "@a@=5 & @b@=3 | @c@=4", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3"));
		}

		@Test
		void case7()
		{
			assertPrecedenceExpression(false, "@b@=3", new Params()
					.addParam("b", "2"));
			assertPrecedenceExpression(true, "@b@=3", new Params()
					.addParam("b", "3"));
			assertPrecedenceExpression(false, "3=@b@", new Params()
					.addParam("b", "2"));
			assertPrecedenceExpression(true, "3=@b@", new Params()
					.addParam("b", "3"));

			assertPrecedenceExpression(false, "(@b@=3)", new Params()
					.addParam("b", "2"));
			assertPrecedenceExpression(true, "(@b@=3)", new Params()
					.addParam("b", "3"));

		}

		@Test
		void case8()
		{
			assertPrecedenceExpression(true, "5=@a@ & @b@=2 | @c@=4", new Params()
					.addParam("a", "3")
					.addParam("b", "3")
					.addParam("c", "4"));

		}

		@Test
		void case9()
		{
			assertPrecedenceExpression(true, "@a@=5   &    @b@=3 | @c@=4 & @d@=5", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3")
					.addParam("d", "3"));
		}

		@Test
		void case10()
		{
			assertPrecedenceExpression(true, "(((@a@=5|@b@=3|@c@=5)&@d@=4|@x@=5)&@y@=3)&@z@=3", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3")
					.addParam("d", "4")
					.addParam("x", "3")
					.addParam("y", "3")
					.addParam("z", "3"));
		}
	}

	@Nested
	class transformationTests
	{
		private void assertTransformedExpression(final String expressionStr, final Params params)
		{

			compiler.setUseOperatorPrecedence(false);
			final ILogicExpression expression = compiler.compile(expressionStr);
			// This expression shall contain parenthesis
			final String expressionFormattedStr = expression.getFormatedExpressionString();

			// Recompile the expression
			compiler.setUseOperatorPrecedence(true);
			assertThat(compiler.isUseOperatorPrecedence()).as("UseOperatorPrecedence shall be set").isTrue();
			final ILogicExpression expressionNew = compiler.compile(expressionFormattedStr);
			final String expressionNewFormattedStr = expressionNew.getFormatedExpressionString();

			// Assume both expressions are equal
			assertThat(expressionNew).as("Compiled and recompiled expressions shall be equal").isEqualTo(expression);

			final boolean valueOld = expression.evaluate(params, true);
			final boolean valueNew = expressionNew.evaluate(params, true);
			assertThat(valueOld)
					.as("Wrongly evaluated:"
							+ "\n              expression=[" + expressionStr + "]"
							+ "\n, expressionFormattedStr=[" + expressionFormattedStr + "]"
							+ "\n,          expressionNew=[" + expressionNewFormattedStr + "]"
							+ "\n,                 params=" + params)
					.isEqualTo(valueNew);
		}

		@Test
		void transformationTests_case01()
		{
			assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "4"));
			assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "0")
					.addParam("b", "3")
					.addParam("c", "4"));
			assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "0")
					.addParam("c", "4"));
			assertTransformedExpression("5=@a@ & (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "0")
					.addParam("c", "0"));
		}

		@Test
		void transformationTests_case02()
		{
			assertTransformedExpression("((@a@='5' | @b@!@c@) & @d@>3)| (   @x@<'10'& (@y@!@z@) )", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3")
					.addParam("d", "4")
					.addParam("x", "3")
					.addParam("y", "3")
					.addParam("z", "3"));
		}

		@Test
		void transformationTests_case03()
		{
			assertTransformedExpression("@a@=5   &    (@b@=3 | @c@=4)", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3"));

			assertTransformedExpression("@a@=5", new Params()
					.addParam("a", "5"));

			assertTransformedExpression("@a@=5 & @b@=3 | @c@=4", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3"));

			assertTransformedExpression("@a@=5 | @b@=3 & @c@=4", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3"));

			assertTransformedExpression("@b@=3", new Params()
					.addParam("b", "2"));
			assertTransformedExpression("@b@=3", new Params()
					.addParam("b", "3"));
			assertTransformedExpression("3=@b@", new Params()
					.addParam("b", "2"));
			assertTransformedExpression("3=@b@", new Params()
					.addParam("b", "3"));

			assertTransformedExpression("(@b@=3)", new Params()
					.addParam("b", "2"));
			assertTransformedExpression("(@b@=3)", new Params()
					.addParam("b", "3"));

			assertTransformedExpression("5=@a@ & @b@=2 | @c@=4", new Params()
					.addParam("a", "3")
					.addParam("b", "3")
					.addParam("c", "4"));

			assertTransformedExpression("@a@=5   &    @b@=3 | @c@=4 & @d@=5", new Params()
					.addParam("a", "5")
					.addParam("b", "3")
					.addParam("c", "3")
					.addParam("d", "3"));
		}
	}

	@Nested
	class stripQuotes
	{
		@Test
		void null_and_empty()
		{
			assertThat(LogicExpressionEvaluator.stripQuotes(null)).isNull();
			assertThat(LogicExpressionEvaluator.stripQuotes("")).isEmpty();
		}

		@Test
		void singleQuote()
		{
			assertThat(LogicExpressionEvaluator.stripQuotes("'")).isEqualTo("'");
			assertThat(LogicExpressionEvaluator.stripQuotes("''")).isEmpty();
			assertThat(LogicExpressionEvaluator.stripQuotes("test")).isEqualTo("test");
			assertThat(LogicExpressionEvaluator.stripQuotes("'test")).isEqualTo("'test");
			assertThat(LogicExpressionEvaluator.stripQuotes("test'")).isEqualTo("test'");
			assertThat(LogicExpressionEvaluator.stripQuotes("test")).isEqualTo("test");
		}

		@Test
		void doubleQuote()
		{
			assertThat(LogicExpressionEvaluator.stripQuotes("\"")).isEqualTo("\"");
			assertThat(LogicExpressionEvaluator.stripQuotes("\"\"")).isEmpty();
			assertThat(LogicExpressionEvaluator.stripQuotes("test")).isEqualTo("test");
			assertThat(LogicExpressionEvaluator.stripQuotes("\"test")).isEqualTo("\"test");
			assertThat(LogicExpressionEvaluator.stripQuotes("test\"")).isEqualTo("test\"");
			assertThat(LogicExpressionEvaluator.stripQuotes("test")).isEqualTo("test");
		}

		/**
		 * Combined quotes: only first quote shall be stripped
		 */
		@Test
		void combined_single_and_double_quotes()
		{
			assertThat(LogicExpressionEvaluator.stripQuotes("\"''\"")).isEqualTo("''");
			assertThat(LogicExpressionEvaluator.stripQuotes("'\"\"'")).isEqualTo("\"\"");
			assertThat(LogicExpressionEvaluator.stripQuotes("\"'test'\"")).isEqualTo("'test'");
			assertThat(LogicExpressionEvaluator.stripQuotes("'\"test\"'")).isEqualTo("\"test\"");
		}

		@Test
		void do_not_strip_quotes_from_middle()
		{
			assertThat(LogicExpressionEvaluator.stripQuotes("this is a 'test' string")).isEqualTo("this is a 'test' string");
			assertThat(LogicExpressionEvaluator.stripQuotes("this is a \"test\" string")).isEqualTo("this is a \"test\" string");
		}
	}

	@Nested
	class isPossibleNumber
	{
		private AbstractBooleanAssert<?> assertPossibleNumber(final String valueStr)
		{
			return assertThat(LogicExpressionEvaluator.isPossibleNumber(valueStr))
					.as("isPossibleNumber(%s)", valueStr);
		}

		@Test
		void null_and_empty()
		{
			assertPossibleNumber(null).isFalse();
			assertPossibleNumber("").isFalse();
		}

		@Test
		void string_starting_with_quotes()
		{
			assertPossibleNumber("'").isFalse();
		}

		@Test
		void integers()
		{
			assertPossibleNumber("0").isTrue();
			assertPossibleNumber("1").isTrue();
			assertPossibleNumber("12345").isTrue();
			assertPossibleNumber("+12345").isTrue();
			assertPossibleNumber("-12345").isTrue();
		}

		@Test
		void decimals()
		{
			assertPossibleNumber("1.1").isTrue();
			assertPossibleNumber("12345.00100").isTrue();
			assertPossibleNumber("+12345.00100").isTrue();
			assertPossibleNumber("-12345.00100").isTrue();
		}
	}

	@Nested
	class evaluateLogicTuple
	{
		@Test
		void pure_strings()
		{
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("value", "=", "value")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("'value'", "=", "value")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("\"value\"", "=", "value")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("\"value\"", "=", "'value'")).isTrue();
		}

		@Test
		void numbers()
		{
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("0", "!", "0.00")).isFalse();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("0", "=", "0.00")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("10", "=", "10")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("10.0", "=", "10.0")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("10.0001", "=", "10.0001")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("10.0001000000000000000000", "=", "10.0001")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("+10.0001000000000000000000", "=", "+10.0001")).isTrue();
			assertThat(LogicExpressionEvaluator.evaluateLogicTuple("-10.0001000000000000000000", "=", "-10.0001")).isTrue();
		}
	}

	@Nested
	class evaluateToResult
	{
		@Test
		void check_UsedParameters()
		{
			final ILogicExpression expr = compiler.compile("@Var1/A@=X | @Var1/B@=X");
			final LogicExpressionResult result = expr.evaluateToResult(Evaluatees.empty(), OnVariableNotFound.Fail);
			assertThat(result.getUsedParameters())
					.hasSize(2)
					.containsEntry(CtxNames.parse("Var1/A"), "A")
					.containsEntry(CtxNames.parse("Var1/B"), "B");
		}
	}
}
