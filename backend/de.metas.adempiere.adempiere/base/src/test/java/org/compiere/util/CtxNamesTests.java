package org.compiere.util;

import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link CtxName} tests.
 * <p>
 * Please note that we have a lot of related tests in {@link EnvTests}
 *
 * @author tsa
 */
@SuppressWarnings("ConstantConditions")
public class CtxNamesTests
{
	private static final List<String> NO_MODIFIERS = null;

	@Nested
	class containsName
	{
		@Test
		void complexCase_SQL_with_multiple_context_vars()
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
	}

	@Nested
	class getValueAsString
	{
		@Test
		void old_modifier_using_Evaluatee_without_old_values()
		{
			final MockedEvaluatee ev = new MockedEvaluatee();
			ev.put("name1", "value1");
			ev.put("name2", "value2");
			assertThat(CtxNames.parse("name1").getValueAsString(ev)).isEqualTo("value1");
			assertThat(CtxNames.parse("name1/old/default").getValueAsString(ev)).isEqualTo("value1");
			assertThat(CtxNames.parse("name1/upper/old/default").getValueAsString(ev)).isEqualTo("value1");
		}

		@Test
		void old_modifier_using_Evaluatee2_with_old_values()
		{
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
		void missingIDVariable_WithDefault()
		{
			final MockedEvaluatee evalCtx = new MockedEvaluatee();

			// NOTE: variable name ends with "_ID"
			final CtxName name = CtxNames.parse("Test_ID/123");

			assertThat(name.getValueAsString(evalCtx)).as("It shall return default value").isEqualTo("123");
		}

		@Test
		void missingStringVariable_WithDefault()
		{
			final MockedEvaluatee evalCtx = new MockedEvaluatee();

			// NOTE: variable name DOES NOT end with "_ID"
			final CtxName name = CtxNames.parse("Test/123");

			assertThat(name.getValueAsString(evalCtx)).as("It shall return default value").isEqualTo("123");
		}

		@Test
		void missingIDVariable_NoDefault()
		{
			final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
			evalCtx.hasVariableReturn = false;

			// NOTE: variable name ends with "_ID"
			final CtxName name = CtxNames.parse("Test_ID");

			assertThat(name.getValueAsString(evalCtx)).as("It shall return null value").isEqualTo(CtxNames.VALUE_NULL);
		}

		@Test
		void missingStringVariable_NoDefault()
		{
			final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
			evalCtx.hasVariableReturn = false;

			// NOTE: variable name DOES NOT end with "_ID"
			final CtxName name = CtxNames.parse("Test");

			assertThat(name.getValueAsString(evalCtx)).as("It shall return null value").isEqualTo(CtxNames.VALUE_NULL);
		}

		@Test
		void missingVariable_WithDefault()
		{
			final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
			evalCtx.hasVariableReturn = false;

			final CtxName name = CtxNames.parse("Test/defaultValue");

			assertThat(name.getValueAsString(evalCtx)).as("It shall return default value").isEqualTo("defaultValue");
		}

		@Test
		void missingVariable_NoDefault()
		{
			final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
			evalCtx.hasVariableReturn = false;

			final CtxName name = CtxNames.parse("Test");

			assertThat(name.getValueAsString(evalCtx)).as("It shall return null value").isEqualTo(CtxNames.VALUE_NULL);
		}

		@Test
		void missingVariable()
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
		void quotedIfNotDefault()
		{
			final CtxName ctxName = CtxNames.parseWithMarkers("@SomeVar/quotedIfNotDefault/someDefaultValue@");
			final MockedEvaluatee2 context = new MockedEvaluatee2();
			context.put("SomeVar", "aaa");

			final String value = ctxName.getValueAsString(context);
			assertThat(value).isEqualTo("'aaa'");
		}

		@Test
		void quotedIfNotDefault_VarMissing()
		{
			final CtxName ctxName = CtxNames.parseWithMarkers("@SomeVar/quotedIfNotDefault/someDefaultValue@");
			final MockedEvaluatee2 context = new MockedEvaluatee2();
			// context.put("SomeVar", "aaa");

			final String value = ctxName.getValueAsString(context);
			assertThat(value).isEqualTo("someDefaultValue");
		}
	}

	@Nested
	class getValueAsDate
	{
		@Test
		void defaultValue_NULL_string()
		{
			final CtxName ctxName = CtxNames.parseWithMarkers("@Date/NULL@");
			final MockedEvaluatee2 context = new MockedEvaluatee2();

			final Date value = ctxName.getValueAsDate(context);
			assertThat(value).isNull();
		}

	}

	@Nested
	class parse
	{
		@Test
		void standardCases()
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
				final CtxName expected = new CtxName("Name", Collections.singletonList(CtxNames.MODIFIER_Old), null);
				final CtxName actual = CtxNames.parse("Name/" + CtxNames.MODIFIER_Old);
				assertThat(actual).isEqualTo(expected);
			}
			{
				// also make sure we did not change the modifier name
				final CtxName expected = new CtxName("Name", Collections.singletonList("old"), null);
				final CtxName actual = CtxNames.parse("Name/old");
				assertThat(actual).isEqualTo(expected);
			}
			{
				// also make sure we did not change the modifier name
				final CtxName expected = new CtxName("Name", Collections.singletonList("old"), "def");
				final CtxName actual = CtxNames.parse("Name/old/def");
				assertThat(actual).isEqualTo(expected);
			}
			{
				// also make sure we did not change the modifier name
				final CtxName expected = new CtxName("Name", Arrays.asList("old", "upper", "lower"), "def2");
				final CtxName actual = CtxNames.parse("Name/old/upper/lower/def2");
				assertThat(actual).isEqualTo(expected);
			}
		}

		@Test
		void nullDefaultValueOk()
		{
			nullDefaultValueOk(null);
			nullDefaultValueOk("NULL");
			nullDefaultValueOk("Null");
			nullDefaultValueOk("null");
		}

		private void nullDefaultValueOk(final String nullDefaultValue)
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

		@ParameterizedTest
		@ValueSource(strings = {
				"Test",
				"Test0_ID",
				"#Test0_ID",
				"$Test0_ID",
				"$Test0-ID", // not sure if this shall be tolerated, but for now we tolerate it
		})
		void validName(String name)
		{
			CtxNames.parse(name);
		}

		@ParameterizedTest
		@ValueSource(strings = "AD_Element_ID\\0")
		void invalidName(final String name)
		{
			Assertions.assertThatThrownBy(() -> CtxNames.parse(name))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Invalid name ");
		}
	}

	@Nested
	class parseWithMarkers
	{
		@Test
		void emptyDefaultValue()
		{
			final CtxName ctxName = CtxNames.parseWithMarkers("@Description/@");
			assertThat(ctxName.getName()).as("Name").isEqualTo("Description");
			assertThat(ctxName.getDefaultValue()).as("DefaultValue").isEmpty();
		}
	}

	@Nested
	class ofNameAndDefaultValue
	{
		@Test
		void nullDefaultValueOk()
		{
			nullDefaultValueOk(null);
			nullDefaultValueOk("NULL");
			nullDefaultValueOk("Null");
			nullDefaultValueOk("null");
		}

		private void nullDefaultValueOk(@Nullable final String nullDefaultValue)
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
	}

	@Nested
	class equalsByName
	{
		@Test
		void sameName_differentDefaultValues()
		{
			final CtxName ctxName1 = CtxNames.parse("Var/DefaultValue1");
			final CtxName ctxName2 = CtxNames.parse("Var/DefaultValue2");
			assertThat(ctxName1.equalsByName(ctxName2)).isTrue();
		}

		@Test
		void sameName_differentModifiers_differentDefaultValues()
		{
			final CtxName ctxName1 = CtxNames.parse("Var/quotedIfNotDefault/DefaultValue1");
			final CtxName ctxName2 = CtxNames.parse("Var/old/DefaultValue2");
			assertThat(ctxName1.equalsByName(ctxName2)).isTrue();
		}

		@Test
		void differentNames()
		{
			final CtxName ctxName1 = CtxNames.parse("Var1");
			final CtxName ctxName2 = CtxNames.parse("Var2");
			assertThat(ctxName1.equalsByName(ctxName2)).isFalse();
		}

	}
}
