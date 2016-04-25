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


import java.sql.Timestamp;

import org.compiere.util.Util.ArrayKey;
import org.junit.Assert;
import org.junit.Test;

public class ArrayKeyTest
{
	@Test
	public void test_toString()
	{
		final Timestamp date = TimeUtil.getDay(2013, 02, 05);
		final ArrayKey key = new ArrayKey(
				(Integer)5,
				(String)"s1",
				null,
				date
				);

		final String keyStrExpected = "5#s1#NULL#2013-02-05 00:00:00.0";
		final String keyStrActual = key.toString();
		Assert.assertEquals(keyStrExpected, keyStrActual);
	}

	@Test
	public void test_compareTo()
	{
		// tests for NULLs last
		test_compareTo(
				+1, // expected
				null, // key1
				new ArrayKey("1", null, "2") // key2
		);
		test_compareTo(
				-1, // expected
				new ArrayKey("1", null, "2"), // key1
				null // key2
		);
		test_compareTo(
				0, // expected
				null, // key1
				null // key2
		);

		//
		test_compareTo(
				0, // expected
				new ArrayKey("1", null, "2"), // key1
				new ArrayKey("1", null, "2") // key2
		);
		test_compareTo(
				-1, // expected
				new ArrayKey("1", null, "2"), // key1
				new ArrayKey("1", null, "3") // key2
		);
		test_compareTo(
				+1, // expected
				new ArrayKey("1", null, "3"), // key1
				new ArrayKey("1", null, "2") // key2
		);

	}

	private final void test_compareTo(final int expected, final ArrayKey key1, final ArrayKey key2)
	{
		final String message = "Invalid compareTo result"
				+ "\n key1=" + key1
				+ "\n key2=" + key2;
		final int actual = ArrayKey.compare(key1, key2);
		Assert.assertEquals(message, expected, actual);
	}

}
