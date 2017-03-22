package org.compiere.util;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * {@link CtxName} tests.
 * 
 * Please note that we have a lot of related tests in {@link EnvTests}
 * 
 * @author tsa
 */
public class CtxNameTests
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

		assertTrue(CtxName.containsName("C_BPartner_ID", sql));
		assertTrue(CtxName.containsName("IsSOTrx", sql));
		assertTrue(CtxName.containsName("C_BPartner_Location_ID", sql));
		assertFalse(CtxName.containsName("C_BPartner", sql));
		assertFalse(CtxName.containsName("Bill_BPartner_ID", sql));

		assertTrue(CtxName.containsName("C_BPartner_ID2", sql));
		assertTrue(CtxName.containsName("My_BPartner_ID2", sql));
		assertFalse(CtxName.containsName("My_BPartner_ID", sql));
		assertTrue(CtxName.containsName("C_BPartner_ID3", sql));
	}

	@Test
	public void test_CtxName_parse()
	{
		{
			CtxName expected = new CtxName("Name", NO_MODIFIERS, null);
			CtxName actual = CtxName.parse("Name");
			assertEquals(expected, actual);
		}
		{
			CtxName expected = new CtxName("Name", NO_MODIFIERS, "defaultValue");
			CtxName actual = CtxName.parse("Name/defaultValue");
			assertEquals(expected, actual);
		}
		{
			// default value should be returned as-is (e.g. "@Name/'Tobi'@" should default to "'Tobi'", not "Tobi")
			CtxName expected = new CtxName("Name", NO_MODIFIERS, "'defaultValue'");
			CtxName actual = CtxName.parse("Name/'defaultValue'");
			assertEquals(expected, actual);
		}
		{
			CtxName expected = new CtxName("Name", Arrays.asList(CtxName.MODIFIER_Old), null);
			CtxName actual = CtxName.parse("Name/" + CtxName.MODIFIER_Old);
			assertEquals(expected, actual);
		}
		{
			// also make sure we did not changed the modifier name
			CtxName expected = new CtxName("Name", Arrays.asList("old"), null);
			CtxName actual = CtxName.parse("Name/old");
			assertEquals(expected, actual);
		}
		{
			// also make sure we did not changed the modifier name
			CtxName expected = new CtxName("Name", Arrays.asList("old"), "def");
			CtxName actual = CtxName.parse("Name/old/def");
			assertEquals(expected, actual);
		}
		{
			// also make sure we did not changed the modifier name
			CtxName expected = new CtxName("Name", Arrays.asList("old", "upper", "lower"), "def2");
			CtxName actual = CtxName.parse("Name/old/upper/lower/def2");
			assertEquals(expected, actual);
		}
	}

	@Test
	public void test_CtxName_getValueAsString_misc()
	{
		MockedEvaluatee ev = new MockedEvaluatee();
		ev.put("name1", "value1");
		ev.put("name2", "value2");
		assertEquals("value1", CtxName.parse("name1").getValueAsString(ev));
		assertEquals("value1", CtxName.parse("name1/old/default").getValueAsString(ev));
		assertEquals("value1", CtxName.parse("name1/upper/old/default").getValueAsString(ev));

		MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "value1", "valueOld1");
		ev2.put("name2", "value2", "valueOld2");
		assertEquals("value1", CtxName.parse("name1").getValueAsString(ev2));
		assertEquals("valueOld1", CtxName.parse("name1/old/default").getValueAsString(ev2));
		assertEquals("valueOld1", CtxName.parse("name1/upper/old/default").getValueAsString(ev2));

		assertNull(CtxName.parse("nameUnknown").getValueAsString(ev2));
		assertEquals("default", CtxName.parse("nameUnknown/default").getValueAsString(ev2));
		assertEquals("default", CtxName.parse("nameUnknown/old/default").getValueAsString(ev2));
		assertEquals("default", CtxName.parse("nameUnknown/upper/old/default").getValueAsString(ev2));
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingIDVariable_WithDefault()
	{
		final MockedEvaluatee evalCtx = new MockedEvaluatee();

		// NOTE: variable name ends with "_ID"
		final CtxName name = CtxName.parse("Test_ID/123");

		assertEquals("It shall return default value", "123", name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingStringVariable_WithDefault()
	{
		final MockedEvaluatee evalCtx = new MockedEvaluatee();

		// NOTE: variable name DOES NOT end with "_ID"
		final CtxName name = CtxName.parse("Test/123");

		assertEquals("It shall return default value", "123", name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingIDVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		// NOTE: variable name ends with "_ID"
		final CtxName name = CtxName.parse("Test_ID");

		assertEquals("It shall return null value", CtxName.VALUE_NULL, name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee_MissingStringVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		// NOTE: variable name DOES NOT end with "_ID"
		final CtxName name = CtxName.parse("Test");

		assertEquals("It shall return null value", CtxName.VALUE_NULL, name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable_WithDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		final CtxName name = CtxName.parse("Test/defaultValue");

		assertEquals("It shall return default value", "defaultValue", name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable_NoDefault()
	{
		final MockedEvaluatee2 evalCtx = new MockedEvaluatee2();
		evalCtx.hasVariableReturn = false;

		final CtxName name = CtxName.parse("Test");

		assertEquals("It shall return null value", CtxName.VALUE_NULL, name.getValueAsString(evalCtx));
	}

	@Test
	public void test_getValueAsString_Evaluatee2_MissingVariable()
	{
		final MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "value1", "valueOld1");
		ev2.put("name2", "value2", "valueOld2");
		ev2.hasVariableReturn = false;

		assertEquals(CtxName.VALUE_NULL, CtxName.parse("nameUnknown").getValueAsString(ev2));
		assertEquals(CtxName.VALUE_NULL, CtxName.parse("name1").getValueAsString(ev2));
		assertEquals("default", CtxName.parse("name1/old/default").getValueAsString(ev2));
		assertEquals("default", CtxName.parse("name1/upper/old/default").getValueAsString(ev2));
	}

	@Test
	public void test_EmptyDefault()
	{
		final CtxName ctxName = CtxName.parseWithMarkers("@Description/@");
		assertEquals("Name", "Description", ctxName.getName());
		assertEquals("DefaultValue", "", ctxName.getDefaultValue());
	}
}
