/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.dao.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

class RPadQueryFilterModifierTest
{

	@Test
	public void test_convertValue()
	{
		final RPadQueryFilterModifier modifier = new RPadQueryFilterModifier(10, "X");
		testConvertValue(null, null, modifier);
		testConvertValue("XXXXXXXXXX", "", modifier);
		testConvertValue("XXXXXXXXXX", "    ", modifier);
		testConvertValue("1XXXXXXXXX", "1", modifier);
		testConvertValue("123456XXXX", "123456", modifier);
		testConvertValue("X1234560XX", "X1234560", modifier);
		testConvertValue("0X1234560X", "0X1234560", modifier);
		testConvertValue("123456XXXX", "    123456    ", modifier);
		testConvertValue("1234567890", "1234567890", modifier);
		testConvertValue("1234567890", "12345678901234567890", modifier);
	}

	@Test
	public void test_convertValuePadIsSpace()
	{
		final RPadQueryFilterModifier modifier = new RPadQueryFilterModifier(10, " ");
		testConvertValue(null, null, modifier);
		testConvertValue("          ", "", modifier);
		testConvertValue("          ", "    ", modifier);
		testConvertValue("1         ", "1", modifier);
		testConvertValue("123456    ", "123456", modifier);
		testConvertValue("X1234560  ", "X1234560", modifier);
		testConvertValue("0X1234560 ", "0X1234560", modifier);
		testConvertValue("123456    ", "    123456    ", modifier);
		testConvertValue("1234567890", "1234567890", modifier);
		testConvertValue("1234567890", "12345678901234567890", modifier);
	}

	@Test
	public void test_convertValuePadIsSpaceDifferentSize()
	{
		final RPadQueryFilterModifier modifier = new RPadQueryFilterModifier(21, " ");
		testConvertValue("123456789012345678901", "1234567890123456789012345", modifier);
	}

	private void testConvertValue(@Nullable final String resultExpected, @Nullable final String value, final RPadQueryFilterModifier modifier)
	{
		final String columnName = "Dummy"; // N/A
		final Object model = null; // N/A
		@SuppressWarnings("ConstantConditions")
		final String resultActual = (String)modifier.convertValue(columnName, value, model);
		Assert.assertEquals("Invalid result for: " + value, resultExpected, resultActual);

	}
}
