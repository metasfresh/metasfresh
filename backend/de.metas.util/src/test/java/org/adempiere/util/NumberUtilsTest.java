package org.adempiere.util;

/*
 * #%L
 * de.metas.util
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


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilsTest
{
	@Test
	public void test_stripTrailingDecimalZeros_NULL()
	{
		Assert.assertNull("Invalid stripTrailingDecimalZeros result for NULL",
				NumberUtils.stripTrailingDecimalZeros(null));
	}

	@Test
	public void test_stripTrailingDecimalZeros_Zeros()
	{
		test_stripTrailingDecimalZeros("0", "0");
		test_stripTrailingDecimalZeros("0", "0.0");
		test_stripTrailingDecimalZeros("0", "0.00");
		test_stripTrailingDecimalZeros("0", "0.0000000000000000000000000000000");
	}

	@Test
	public void test_stripTrailingDecimalZeros()
	{
		test_stripTrailingDecimalZeros("0", "0");
		test_stripTrailingDecimalZeros("10", "10");
		test_stripTrailingDecimalZeros("10", "10.0");
		test_stripTrailingDecimalZeros("100000000", "100000000.0");
		test_stripTrailingDecimalZeros("10.1", "10.1");
		test_stripTrailingDecimalZeros("10.1", "10.10");
		test_stripTrailingDecimalZeros("10.1", "10.1000000000");
		test_stripTrailingDecimalZeros("10.1234567", "10.1234567");
		test_stripTrailingDecimalZeros("10.1234567", "10.12345670");
		test_stripTrailingDecimalZeros("10.1234567", "10.123456700000");

		test_stripTrailingDecimalZeros("-10", "-10");
		test_stripTrailingDecimalZeros("-10", "-10.0");
		test_stripTrailingDecimalZeros("-100000000", "-100000000.0");
		test_stripTrailingDecimalZeros("-10.1", "-10.1");
		test_stripTrailingDecimalZeros("-10.1", "-10.10");
		test_stripTrailingDecimalZeros("-10.1", "-10.1000000000");
		test_stripTrailingDecimalZeros("-10.1234567", "-10.1234567");
		test_stripTrailingDecimalZeros("-10.1234567", "-10.12345670");
		test_stripTrailingDecimalZeros("-10.1234567", "-10.123456700000");

	}

	private void test_stripTrailingDecimalZeros(final String expectedNumberStr, String numberStr)
	{
		final BigDecimal expectedNumber = new BigDecimal(expectedNumberStr);
		final BigDecimal number = new BigDecimal(numberStr);

		final BigDecimal actualNumber = NumberUtils.stripTrailingDecimalZeros(number);

		Assert.assertEquals("Invalid stripTrailingDecimalZeros result for " + numberStr, expectedNumber, actualNumber);
	}

	@Test
	public void test_getErrorMarginForScale()
	{
		test_getErrorMarginForScale("0", 0);
		test_getErrorMarginForScale("0.1", 1);
		test_getErrorMarginForScale("0.01", 2);
		test_getErrorMarginForScale("0.001", 3);
		//
		test_getErrorMarginForScale("10", -1);
		test_getErrorMarginForScale("100", -2);
		test_getErrorMarginForScale("1000", -3);
	}

	private void test_getErrorMarginForScale(final String expectedValueStr, final int scale)
	{
		final BigDecimal expectedValue = new BigDecimal(expectedValueStr);

		final String message = "Invalid ErrorMargin for scale=" + scale;
		Assert.assertEquals(message,
				expectedValue,
				NumberUtils.getErrorMarginForScale(scale)
				);
	}

}
