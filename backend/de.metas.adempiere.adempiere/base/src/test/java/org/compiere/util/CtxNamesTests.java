package org.compiere.util;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link CtxName} tests.
 *
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

		assertTrue(CtxNames.containsName("C_BPartner_ID", sql));
		assertTrue(CtxNames.containsName("IsSOTrx", sql));
		assertTrue(CtxNames.containsName("C_BPartner_Location_ID", sql));
		assertFalse(CtxNames.containsName("C_BPartner", sql));
		assertFalse(CtxNames.containsName("Bill_BPartner_ID", sql));

		assertTrue(CtxNames.containsName("C_BPartner_ID2", sql));
		assertTrue(CtxNames.containsName("My_BPartner_ID2", sql));
		assertFalse(CtxNames.containsName("My_BPartner_ID", sql));
		assertTrue(CtxNames.containsName("C_BPartner_ID3", sql));
	}

	@Test
	public void test_CtxName_parse()
	{
		{
			final CtxName expected = new CtxName("Name", NO_MODIFIERS, null);
			final CtxName actual = CtxNames.parse("Name");
			assertEquals(expected, actual);
		}
		{
			final CtxName expected = new CtxName("Name", NO_MODIFIERS, "defaultValue");
			final CtxName actual = CtxNames.parse("Name/defaultValue");
			assertEquals(expected, actual);
		}
		{
			// default value should be returned as-is (e.g. "@Name/'Tobi'@" should default to "'Tobi'", not "Tobi")
			final CtxName expected = new CtxName("Name", NO_MODIFIERS, "'defaultValue'");
			final CtxName actual = CtxNames.parse("Name/'defaultValue'");
			assertEquals(expected, actual);
		}
		{
			final CtxName expected = new CtxName("Name", Arrays.asList(CtxNames.MODIFIER_Old), null);
			final CtxName actual = CtxNames.parse("Name/" + CtxNames.MODIFIER_Old);
			assertEquals(expected, actual);
		}
		{
			// also make sure we did not changed the modifier name
			final CtxName expected = new CtxName("Name", Arrays.asList("old"), null);
			final CtxName actual = CtxNames.parse("Name/old");
			assertEquals(expected, actual);
		}
		{
			// also make sure we did not changed the modifier name
			final CtxName expected = new CtxName("Name", Arrays.asList("old"), "def");
			final CtxName actual = CtxNames.parse("Name/old/def");
			assertEquals(expected, actual);
		}
		{
			// also make sure we did not changed the modifier name
			final CtxName expected = new CtxName("Name", Arrays.asList("old", "upper", "lower"), "def2");
			final CtxName actual = CtxNames.parse("Name/old/upper/lower/def2");
			assertEquals(expected, actual);
		}
	}

	@Test
	public void test_CtxName_getValueAsString_misc()
	{
		final MockedEvaluatee ev = new MockedEvaluatee();
		ev.put("name1", "value1");
		ev.put("name2", "value2");
		assertEquals("value1", CtxNames.parse("name1").getValueAsString(ev));
		assertEquals("value1", CtxNames.parse("name1/old/default").getValueAsString(ev));
		assertEquals("value1", CtxNames.parse("name1/upper/old/default").getValueAsString(ev));

		final MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "value1", "valueOld1");
		ev2.put("name2", "value2", "valueOld2");
		assertEquals("value1", CtxNames.parse("name1").getValueAsString(ev2));
		assertEquals("valueOld1", CtxNames.parse("name1/old/default").getValueAsString(ev2));
		assertEquals("valueOld1", CtxNames.parse("name1/upper/old/default").getValueAsString(ev2));

		assertNull(CtxNames.parse("nameUnknown").getValueAsString(ev2));
		assertEquals("default", CtxNames.parse("nameUnknown/default").getValueAsString(ev2));
		assertEquals("default", CtxNames.parse("nameUnknown/old/default").getValueAsString(ev2));
		assertEquals("default", CtxNames.parse("nameUnknown/upper/old/default").getValueAsString(ev2));
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingIDVariable_WithDefault()
	{
		final MockedEvaluatee evalCtx = new MockedEvaluatee();

		// NOTE: variable name ends with "_ID"
		final CtxName name = CtxNames.parse("Test_ID/123");

		assertEquals("It shall return default value", "123", name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingStringVariable_WithDefault()
	{
		final MockedEvaluatee evalCtx = new MockedEvaluatee();

		// NOTE: variable name DOES NOT end with "_ID"
		final CtxName name = CtxNames.parse("Test/123");

		assertEquals("It shall return default value", "123", name.getValueAsString(evalCtx));
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

		assertEquals("It shall return null value", CtxNames.VALUE_NULL, name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingStringVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		// NOTE: variable name DOES NOT end with "_ID"
		final CtxName name = CtxNames.parse("Test");

		assertEquals("It shall return null value", CtxNames.VALUE_NULL, name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable_WithDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		final CtxName name = CtxNames.parse("Test/defaultValue");

		assertEquals("It shall return default value", "defaultValue", name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		final CtxName name = CtxNames.parse("Test");

		assertEquals("It shall return null value", CtxNames.VALUE_NULL, name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable()
	{
		final MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "value1", "valueOld1");
		ev2.put("name2", "value2", "valueOld2");
		ev2.hasVariableReturn = false;

		assertEquals(CtxNames.VALUE_NULL, CtxNames.parse("nameUnknown").getValueAsString(ev2));
		assertEquals(CtxNames.VALUE_NULL, CtxNames.parse("name1").getValueAsString(ev2));
		assertEquals("default", CtxNames.parse("name1/old/default").getValueAsString(ev2));
		assertEquals("default", CtxNames.parse("name1/upper/old/default").getValueAsString(ev2));
	}

	@Test
	public void test_EmptyDefault()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@Description/@");
		assertEquals("Name", "Description", ctxName.getName());
		assertEquals("DefaultValue", "", ctxName.getDefaultValue());
	}

	@Test
	public void test_getValueAsDate_Default_NULL()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@Date/NULL@");
		final MockedEvaluatee2 context = new MockedEvaluatee2();

		final Date value = ctxName.getValueAsDate(context);
		assertThat(value).isNull();
	}

	@Test
	public void test_getValueAsString_QuotedIfNotDefault()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@SomeVar/quotedIfNotDefault/someDefaultValue@");
		final MockedEvaluatee2 context = new MockedEvaluatee2();
		context.put("SomeVar", "aaa");

		final String value = ctxName.getValueAsString(context);
		assertThat(value).isEqualTo("'aaa'");
	}

	@Test
	public void test_getValueAsString_QuotedIfNotDefault_VarMissing()
	{
		final CtxName ctxName = CtxNames.parseWithMarkers("@SomeVar/quotedIfNotDefault/someDefaultValue@");
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

	private static void assertInvalidName(final String name)
	{
		try
		{
			CtxName ctxName = CtxNames.parse(name);
			Assert.fail("Parsing '" + name + "' should fail but it didn't and returned: " + ctxName);
		}
		catch (AdempiereException ex)
		{
			// OK
		}
	}

}
