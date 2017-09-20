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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test {@link Evaluator} class.
 * 
 * NOTE: {@link Evaluator} class is kind of deprecated but we keep here some tests until we decide to delete it
 * 
 * @author tsa
 * 
 */
public class EvaluatorTests
{
	/**
	 * @see org.adempiere.ad.expression.api.impl.ExpressionFactoryTests#test_compileStringExpression_getParameters()
	 */
	@Test
	public void test_parseDepends()
	{
		{
			String sql = "C_BPartner_ID=@C_BPartner_Override_ID/-1@ AND C_BPartner_Location_ID=@C_BPartner_Location_Override_ID/-1@";
			List<String> dependsActual = new ArrayList<String>();
			List<String> dependsExpected = Arrays.asList("C_BPartner_Override_ID", "C_BPartner_Location_Override_ID");
			Evaluator.parseDepends(dependsActual, sql);
			assertThat(dependsActual).containsAll(dependsExpected);
		}
		{
			// test if is also works with strings and with nested '-signs
			String sql = "Type='@StringVar/'NONE'@' AND C_BPartner_Location_ID=@IntVar_ID/-1@ AND Type2='@NoDefaultStringVar@'";
			List<String> dependsActual = new ArrayList<String>();
			List<String> dependsExpected = Arrays.asList("StringVar", "IntVar_ID", "NoDefaultStringVar");
			Evaluator.parseDepends(dependsActual, sql);
			assertThat(dependsActual).containsAll(dependsExpected);
		}
	}

	@Test
	public void test_evaluateLogic_valuesWithSpaces()
	{
		final MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "this is a value");
		ev2.put("name2", " this is a value with spaces ");

		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name1@ = 'this is a value'"));
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name1@ = \"this is a value\""));

		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name2@ = ' this is a value with spaces '"));
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name2@ = \" this is a value with spaces \""));

		assertEquals(true, Evaluator.evaluateLogic(ev2, "@NoSuchName/'this is a default value'@ = 'this is a default value'"));
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@NoSuchName/\"this is a default value\"@ = 'this is a default value'"));
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@noSuchName/' this is a default value with spaces '@ = \" this is a default value with spaces \""));
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@noSuchName/\" this is a default value with spaces \"@ = \" this is a default value with spaces \""));

		// If default value is not quoted, it will be trimmed. If user really wants to have spaces at the begining/ending of the string, he/she shall quote it
		assertEquals(false, Evaluator.evaluateLogic(ev2, "@noSuchName/ this is a default value with spaces @ = 'this is a default value with spaces'"));

		// Variable name contains spaces
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@ name1 @ = 'this is a value'"));

		// Expression string contains leading/tailing spaces
		assertEquals(true, Evaluator.evaluateLogic(ev2, "     @ name1 @ = 'this is a value'"));
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@ name1 @      = 'this is a value'"));
		assertEquals(true, Evaluator.evaluateLogic(ev2, " \r\n\t   @ name1 @ \t\n= 'this is a value'"));
	}

	@Test
	public void test_evaluateLogic_oldValue()
	{
		MockedEvaluatee2 ev2 = new MockedEvaluatee2();
		ev2.put("name1", "value1", "valueOld1");

		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name1@=value1"));
		assertEquals(false, Evaluator.evaluateLogic(ev2, "@name1@!value1"));

		// ' and "-signs outside of @ tags are ignored
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name1@='value1'"));
		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name1@=\"value1\""));

		assertEquals(true, Evaluator.evaluateLogic(ev2, "@name1/old@=valueOld1"));
		assertEquals(false, Evaluator.evaluateLogic(ev2, "@name1/old@!valueOld1"));

		// ' and "-signs outside of @ tags are ignored
		assertEquals("'-signs outside of @ tags are ignored", true, Evaluator.evaluateLogic(ev2, "@name1/old@='valueOld1'"));
		assertEquals("\"-signs outside of @ tags are ignored", true, Evaluator.evaluateLogic(ev2, "@name1/old@=\"valueOld1\""));
	}

	/**
	 * Please keep in sync with {@link org.adempiere.ad.expression.api.impl.ExpressionEvaluatorBLTest#test_evaluateLogic_defaultValue()}
	 */
	@Test
	public void test_evaluateLogic_defaultValue()
	{
		MockedEvaluatee ev = new MockedEvaluatee();
		ev.put("name1", "value1");

		// guard: the case, where we don't need the default should work, too
		assertEquals(true, Evaluator.evaluateLogic(ev, "@name1@=value1"));
		assertEquals(false, Evaluator.evaluateLogic(ev, "@name1@!value1"));

		assertEquals(true, Evaluator.evaluateLogic(ev, "@noSuchName1/defaultVal@=defaultVal"));
		assertEquals(false, Evaluator.evaluateLogic(ev, "@noSuchName1/defaultVal@!defaultVal"));

		// 'and " outside of the @ tags should be stripped
		assertEquals(true, Evaluator.evaluateLogic(ev, "@noSuchName1/defaultVal@='defaultVal'"));
		assertEquals(true, Evaluator.evaluateLogic(ev, "@noSuchName1/defaultVal@=\"defaultVal\""));

		// because ' and " are stripped outside of @ tags, they need to be ignored, when evaluating default values, too
		assertEquals(true, Evaluator.evaluateLogic(ev, "@noSuchName1/'defaultVal'@='defaultVal'"));
		assertEquals(true, Evaluator.evaluateLogic(ev, "@noSuchName1/'defaultVal'@=defaultVal"));

		// make sure that partial quotes are not stripped out
		assertEquals(true, Evaluator.evaluateLogic(ev, "@noSuchName1/'defaultVal@='defaultVal"));
		assertEquals(true, Evaluator.evaluateLogic(ev, "@noSuchName1/defaultVal'@=defaultVal'"));
	}

}
