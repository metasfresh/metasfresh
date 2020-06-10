package org.adempiere.ad.dao.impl;

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

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class LpadQueryFilterModifierTest
{
	@Test
	public void test_convertValue()
	{
		final LpadQueryFilterModifier modifier = new LpadQueryFilterModifier(10, "0");
		testConvertValue(null, null, modifier);
		testConvertValue("0000000000", "", modifier);
		testConvertValue("0000000000", "         ", modifier);
		testConvertValue("0000000001", "1", modifier);
		testConvertValue("0000123456", "123456", modifier);
		testConvertValue("0000123456", "    123456    ", modifier);
		testConvertValue("1234567890", "1234567890", modifier);
		testConvertValue("1234567890", "12345678901234567890", modifier);
	}

	@Test
	public void test_convertValuePadIsSpace()
	{
		final LpadQueryFilterModifier modifier = new LpadQueryFilterModifier(10, " ");
		testConvertValue(null, null, modifier);
		testConvertValue("          ", "", modifier);
		testConvertValue("          ", "         ", modifier);
		testConvertValue("         1", "1", modifier);
		testConvertValue("    123456", "123456", modifier);
		testConvertValue("    123456", "    123456    ", modifier);
		testConvertValue("1234567890", "1234567890", modifier);
		testConvertValue("1234567890", "12345678901234567890", modifier);
	}

	private void testConvertValue(final String resultExpected, final String value, final LpadQueryFilterModifier modifier)
	{
		final String columnName = "Dummy"; // N/A
		final Object model = null; // N/A
		String resultActual = (String)modifier.convertValue(columnName, value, model);
		Assert.assertEquals("Invalid result for: " + value, resultExpected, resultActual);

	}
}
