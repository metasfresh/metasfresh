package org.adempiere.util.lang;

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

public class MutableBigDecimalTest
{
	@Test
	public void test_min()
	{
		// when equals, we expect the first one too
		test_min(1, "1", "1");
		test_min(1, "1.0", "1.0000000");
		
		test_min(1, "1.0", "1.00000000000000001");
		test_min(1, "1", "2");
		test_min(1, "1", "3");
		
		test_min(2, "1", "0");
		test_min(2, "1", "-1");
		test_min(2, "1", "-2");
	}
	
	private void test_min(final int expectedMinIndex, final String valueStr1, final String valueStr2)
	{
		final MutableBigDecimal value1 = new MutableBigDecimal(new BigDecimal(valueStr1));
		final MutableBigDecimal value2 = new MutableBigDecimal(new BigDecimal(valueStr2));

		final MutableBigDecimal valueMinExpected;
		if (expectedMinIndex == 1)
		{
			valueMinExpected = value1;
		}
		else if (expectedMinIndex == 2)
		{
			valueMinExpected = value2;
		}
		else
		{
			throw new IllegalArgumentException("Invalid expectedMinIndex");
		}
		
		final MutableBigDecimal valueMin = value1.min(value2);
		Assert.assertSame(valueMinExpected, valueMin);
	}
	
	@Test
	public void test_max()
	{
		// when equals, we expect the first one too
		test_max(1, "1", "1");
		test_max(1, "1.0", "1.0000000");
		
		test_max(2, "1.0", "1.00000000000000001");
		test_max(2, "1", "2");
		test_max(2, "1", "3");
		
		test_max(1, "1", "0");
		test_max(1, "1", "-1");
		test_max(1, "1", "-2");
	}
	
	private void test_max(final int expectedMinIndex, final String valueStr1, final String valueStr2)
	{
		final MutableBigDecimal value1 = new MutableBigDecimal(new BigDecimal(valueStr1));
		final MutableBigDecimal value2 = new MutableBigDecimal(new BigDecimal(valueStr2));

		final MutableBigDecimal valueMinExpected;
		if (expectedMinIndex == 1)
		{
			valueMinExpected = value1;
		}
		else if (expectedMinIndex == 2)
		{
			valueMinExpected = value2;
		}
		else
		{
			throw new IllegalArgumentException("Invalid expectedMinIndex");
		}
		
		final MutableBigDecimal valueMin = value1.max(value2);
		Assert.assertSame(valueMinExpected, valueMin);
	}

}
