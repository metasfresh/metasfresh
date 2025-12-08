package org.compiere.util;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link CtxName} tests.
 * <p>
 * Please note that we have a lot of related tests in {@link EnvTests}
 *
 * @author tsa
 */
public class CtxNamesTests
{
	private static final List<String> NO_MODIFIERS = null;

	@Test
	public void test_CtxName_containsName()
	{
		final String sql = "C_BPartner.IsSummary='N'" +
				" AND (" +
				" C_BPartner.C_BPartner_ID=@C_BPartner_ID@" +
				" OR 'N'='@IsSOTrx@'" +
				" OR EXISTS (SELECT * FROM C_BP_Relation r" +
				" WHERE C_BPartner.C_BPartner_ID=r.C_BPartnerRelation_ID" +
				" AND r.C_BPartner_ID=@C_BPartner_ID@ AND r.IsBillTo='Y'" +
				" AND (r.C_BPartner_Location_ID=@C_BPartner_Location_ID@ OR r.C_BPartner_Location_ID IS NULL OR r.C_BPartner_Location_ID = 0)" +
				")" +
				// Adding some dummy tests with default values
				" OR C_BPartner.C_BPartner_ID=@C_BPartner_ID2/-1@" +
				" OR C_BPartner.C_BPartner_ID=@My_BPartner_ID2/-1@" +
				" OR C_BPartner.C_BPartner_ID=@C_BPartner_ID3/upper/lower/-1@" +
				//
				")";

		assertThat(CtxNames.containsName("C_BPartner_ID", sql)).isTrue();
		assertThat(CtxNames.containsName("IsSOTrx", sql)).isTrue();
		assertThat(CtxNames.containsName("C_BPartner_Location_ID", sql)).isTrue();
		assertThat(CtxNames.containsName("C_BPartner", sql)).isFalse();
		assertThat(CtxNames.containsName("Bill_BPartner_ID", sql)).isFalse();

		assertThat(CtxNames.containsName("C_BPartner_ID2", sql)).isTrue();
		assertThat(CtxNames.containsName("My_BPartner_ID2", sql)).isTrue();
		assertThat(CtxNames.containsName("My_BPartner_ID", sql)).isFalse();
		assertThat(CtxNames.containsName("C_BPartner_ID3", sql)).isTrue();
	}

	@Test
	@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
	public void test_CtxName_parse()
	{
		{
			final CtxName expected = new CtxName("Name", NO_MODIFIERS, null);
			final CtxName actual = CtxNames.parse("Name");
			assertThat(actual).isEqualTo(expected);
		}
		{
			final CtxName expected = new CtxName("Name", NO_MODIFIERS, "defaultValue");
			final CtxName actual = CtxNames.parse("Name/defaultValue");
			assertThat(actual).isEqualTo(expected);
		}
		{
			// default value should be returned as-is (e.g. "@Name/'Tobi'@" should default to "'Tobi'", not "Tobi")
			final CtxName expected = new CtxName("Name", NO_MODIFIERS, "'defaultValue'");
			final CtxName actual = CtxNames.parse("Name/'defaultValue'");
			assertThat(actual).isEqualTo(expected);
		}
		{
			final CtxName expected = new CtxName("Name", Arrays.asList(CtxNames.MODIFIER_Old), null);
			final CtxName actual = CtxNames.parse("Name/" + CtxNames.MODIFIER_Old);
			assertThat(actual).isEqualTo(expected);
		}
		{
			// also make sure we did not change the modifier name
			final CtxName expected = new CtxName("Name", Arrays.asList("old"), null);
			final CtxName actual = CtxNames.parse("Name/old");
			assertThat(actual).isEqualTo(expected);
		}
		{
			// also make sure we did not changed the modifier name
			final CtxName expected = new CtxName("Name", Arrays.asList("old"), "def");
			final CtxName actual = CtxNames.parse("Name/old/def");
			assertThat(actual).isEqualTo(expected);
		}
		{
			// also make sure we did not changed the modifier name
			final CtxName expected = new CtxName("Name", Arrays.asList("old", "upper", "lower"), "def2");
			final CtxName actual = CtxNames.parse("Name/old/upper/lower/def2");
			assertThat(actual).isEqualTo(expected);
		}
	}

	@Test
	public void test_CtxName_getValueAsString_misc()
	{
		final MockedEvaluatee ev = new MockedEvaluatee();
		ev.put("name1", "value1");
		ev.put("name2", "value2");
		assertThat(CtxNames.parse("name1").getValueAsString(ev)).isEqualTo("value1");
		assertThat(CtxNames.parse("name1/old/default").getValueAsString(ev)).isEqualTo("value1");
		assertThat(CtxNames.parse("name1/upper/old/default").getValueAsString(ev)).isEqualTo("value1");

		final MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "value1", "valueOld1");
		ev2.put("name2", "value2", "valueOld2");
		assertThat(CtxNames.parse("name1").getValueAsString(ev2)).isEqualTo("value1");
		assertThat(CtxNames.parse("name1/old/default").getValueAsString(ev2)).isEqualTo("valueOld1");
		assertThat(CtxNames.parse("name1/upper/old/default").getValueAsString(ev2)).isEqualTo("valueOld1");

		assertThat(CtxNames.parse("nameUnknown").getValueAsString(ev2)).isNull();
		assertThat(CtxNames.parse("nameUnknown/default").getValueAsString(ev2)).isEqualTo("default");
		assertThat(CtxNames.parse("nameUnknown/old/default").getValueAsString(ev2)).isEqualTo("default");
		assertThat(CtxNames.parse("nameUnknown/upper/old/default").getValueAsString(ev2)).isEqualTo("default");
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingIDVariable_WithDefault()
	{
		final MockedEvaluatee evalCtx = new MockedEvaluatee();

		// NOTE: variable name ends with "_ID"
		final CtxName name = CtxNames.parse("Test_ID/123");

		assertThat(name.getValueAsString(evalCtx)).as("It shall return default value").isEqualTo("123");
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingStringVariable_WithDefault()
	{
		final MockedEvaluatee evalCtx = new MockedEvaluatee();

		// NOTE: variable name DOES NOT end with "_ID"
		final CtxName name = CtxNames.parse("Test/123");

		assertThat(name.getValueAsString(evalCtx)).as("It shall return default value").isEqualTo("123");
	}

	@Test
	public void test_parse_withNullDefaultValue()
	{
		assert_parse__NullDefaultValueOk(null);
		assert_parse__NullDefaultValueOk("NULL");
		assert_parse__NullDefaultValueOk("Null");
		assert_parse__NullDefaultValueOk("null");
	}

	private static void assert_parse__NullDefaultValueOk(final String nullDefaultValue)
	{
		final Evaluatee evaluatee = variableName -> null;

		final CtxName parse = CtxNames.parse("Test" + CtxNames.SEPARATOR + nullDefaultValue);

		assertThat(parse.getValueAsDate(evaluatee)).isNull();
		assertThat(parse.getValueAsBigDecimal(evaluatee)).isNull();
		assertThat(parse.getValueAsBoolean(evaluatee)).isNull();
		assertThat(parse.getValueAsInteger(evaluatee)).isNull();

		final String valueAsString = parse.getValueAsString(evaluatee);
		if (nullDefaultValue == null)
		{
			assertThat(valueAsString).isEqualTo("null");
		}
		else
		{
			assertThat(valueAsString).isEqualTo(nullDefaultValue);
		}
	}

	@Test
	public void test_ofNameAndDefaultValue_withNullDefaultValue()
	{
		assert_ofNameAndDefaultValue_NullDefaultValueOk(null);
		assert_ofNameAndDefaultValue_NullDefaultValueOk("NULL");
		assert_ofNameAndDefaultValue_NullDefaultValueOk("Null");
		assert_ofNameAndDefaultValue_NullDefaultValueOk("null");
	}

	private static void assert_ofNameAndDefaultValue_NullDefaultValueOk(final String nullDefaultValue)
	{
		final Evaluatee evaluatee = variableName -> null;

		final CtxName ofNameAndDefaultValue = CtxNames.ofNameAndDefaultValue("Test", nullDefaultValue);
		if (nullDefaultValue == null)
		{
			assertThat(ofNameAndDefaultValue.getDefaultValue()).isEqualTo(CtxNames.VALUE_NULL);
			assertThat(ofNameAndDefaultValue.getValueAsString(evaluatee)).isNull();
		}
		else
		{
			assertThat(ofNameAndDefaultValue.getDefaultValue()).isEqualTo(nullDefaultValue);
			assertThat(ofNameAndDefaultValue.getValueAsString(evaluatee)).isEqualTo(nullDefaultValue);
		}

		assertThat(ofNameAndDefaultValue.getValueAsInteger(evaluatee)).isNull();
		assertThat(ofNameAndDefaultValue.getValueAsBigDecimal(evaluatee)).isNull();
		assertThat(ofNameAndDefaultValue.getValueAsBoolean(evaluatee)).isNull();
		assertThat(ofNameAndDefaultValue.getValueAsDate(evaluatee)).isNull();
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingIDVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		// NOTE: variable name ends with "_ID"
		final CtxName name = CtxNames.parse("Test_ID");

		assertThat(name.getValueAsString(evalCtx)).as("It shall return null value").isEqualTo(CtxNames.VALUE_NULL);
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingStringVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		// NOTE: variable name DOES NOT end with "_ID"
		final CtxName name = CtxNames.parse("Test");

		assertThat(name.getValueAsString(evalCtx)).as("It shall return null value").isEqualTo(CtxNames.VALUE_NULL);
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable_WithDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		final CtxName name = CtxNames.parse("Test/defaultValue");

		assertThat(name.getValueAsString(evalCtx)).as("It shall return default value").isEqualTo("defaultValue");
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		final CtxName name = CtxNames.parse("Test");

		assertThat(name.getValueAsString(evalCtx)).as("It shall return null value").isEqualTo(CtxNames.VALUE_NULL);
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable()
	{
		final MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "value1", "valueOld1");
		ev2.put("name2", "value2", "valueOld2");
		ev2.hasVariableReturn = false;

		assertThat(CtxNames.parse("nameUnknown").getValueAsString(ev2)).isEqualTo(CtxNames.VALUE_NULL);
		assertThat(CtxNames.parse("name1").getValueAsString(ev2)).isEqualTo(CtxNames.VALUE_NULL);
		assertThat(CtxNames.parse("name1/old/default").getValueAsString(ev2)).isEqualTo("default");
		assertThat(CtxNames.parse("name1/upper/old/default").getValueAsString(ev2)).isEqualTo("default");
	}

	@Test
	public void test_EmptyDefault()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@Description/@");
		assertThat(ctxName).isNotNull();
		assertThat(ctxName.getName()).as("Name").isEqualTo("Description");
		assertThat(ctxName.getDefaultValue()).as("DefaultValue").isEmpty();
	}

	@Test
	public void test_getValueAsDate_Default_NULL()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@Date/NULL@");
		assertThat(ctxName).isNotNull();

		final MockedEvaluatee2 context = new MockedEvaluatee2();

		final Date value = ctxName.getValueAsDate(context);
		assertThat(value).isNull();
	}

	@Test
	public void test_getValueAsString_QuotedIfNotDefault()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@SomeVar/quotedIfNotDefault/someDefaultValue@");
		assertThat(ctxName).isNotNull();

		final MockedEvaluatee2 context = new MockedEvaluatee2();
		context.put("SomeVar", "aaa");

		final String value = ctxName.getValueAsString(context);
		assertThat(value).isEqualTo("'aaa'");
	}

	@Test
	public void test_getValueAsString_QuotedIfNotDefault_VarMissing()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@SomeVar/quotedIfNotDefault/someDefaultValue@");
		assertThat(ctxName).isNotNull();

		final MockedEvaluatee2 context = new MockedEvaluatee2();
		// context.put("SomeVar", "aaa");

		final String value = ctxName.getValueAsString(context);
		assertThat(value).isEqualTo("someDefaultValue");
	}

	@Test
	public void test_validName()
	{
		CtxNames.parse("Test");
		CtxNames.parse("Test0_ID");
		CtxNames.parse("#Test0_ID");
		CtxNames.parse("$Test0_ID");
		CtxNames.parse("$Test0-ID"); // not sure if this shall be tollerated, but for now we tollerate it

		assertInvalidName("AD_Element_ID\\0");
	}

	@SuppressWarnings("SameParameterValue")
	private static void assertInvalidName(final String name)
	{
		assertThatThrownBy(() -> CtxNames.parse(name)).isInstanceOf(AdempiereException.class);
	}

	@Nested
	class jsonEscape_modifier
	{
		private CtxName ctxName;

		@BeforeEach
		void beforeEach()
		{
			this.ctxName = CtxNames.parseWithMarkers("@variable/asJsonString@");
			assertThat(ctxName).isNotNull();
		}

		@Test
		void noSpecialChars()
		{
			final Evaluatee2 ctx = Evaluatees.ofSingleton("variable", "simple string");
			assertThat(ctxName.getValueAsString(ctx)).isEqualTo("\"simple string\"");
		}

		@Test
		void doubleQuotes()
		{
			final Evaluatee2 ctx = Evaluatees.ofSingleton("variable", "with \"quoted\" string");
			assertThat(ctxName.getValueAsString(ctx)).isEqualTo("\"with \\\"quoted\\\" string\"");
		}

		@Test
		void singleQuotes()
		{
			final Evaluatee2 ctx = Evaluatees.ofSingleton("variable", "with 'quoted' string");
			assertThat(ctxName.getValueAsString(ctx)).isEqualTo("\"with 'quoted' string\"");
		}

		@Test
		void backslashes()
		{
			final Evaluatee2 ctx = Evaluatees.ofSingleton("variable", "with \\ string");
			assertThat(ctxName.getValueAsString(ctx)).isEqualTo("\"with \\\\ string\"");
		}
	}
}
