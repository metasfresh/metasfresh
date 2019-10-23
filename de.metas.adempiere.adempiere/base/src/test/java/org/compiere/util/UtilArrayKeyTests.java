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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.compiere.util.Util.ArrayKey;
import org.junit.Test;

/**
 * Some noob tests that I need to gather some self-confidence using array as keys in hashset and hashmaps.
 * 
 * @author ts
 * 
 */
public class UtilArrayKeyTests
{

	/**
	 * Different int arrays can't be used as keys, because they are not equal
	 */
	@Test
	public void intArrarysAreNotEqual()
	{
		final int[] intArray1 = new int[] { 1, 2 };
		final int[] intArray2 = new int[] { 1, 2 };

		assertTrue(intArray1[0] == intArray2[0]);
		assertTrue(intArray1[1] == intArray2[1]);

		assertFalse(intArray1.equals(intArray2));
		assertFalse(intArray1.hashCode() == intArray2.hashCode());
	}

	/**
	 * Different Integer arrays can't be used as keys, because they are not equal
	 */
	@Test
	public void integerArrarysArenotEqual()
	{
		final Integer[] intArray1 = new Integer[] { new Integer(1), new Integer(2) };
		final Integer[] intArray2 = new Integer[] { new Integer(1), new Integer(2) };

		assertTrue(intArray1[0].equals(intArray2[0]));
		assertTrue(intArray1[1].equals(intArray2[1]));

		assertFalse(intArray1.equals(intArray2));
		assertFalse(intArray1.hashCode() == intArray2.hashCode());
	}

	@Test
	public void arraysKeyWithIntegerAreEqual()
	{
		final ArrayKey key1 = Util.mkKey(new Integer(1), new Integer(2));
		final ArrayKey key2 = Util.mkKey(new Integer(1), new Integer(2));

		assertTrue(key1.equals(key2));
		assertTrue(key2.equals(key1));

		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void arraysKeyWithIntAreEqual()
	{
		final ArrayKey key1 = Util.mkKey(1, 2);
		final ArrayKey key2 = Util.mkKey(1, 2);

		assertTrue(key1.equals(key2));
		assertTrue(key2.equals(key1));

		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void arraysKeyWithIntAreEqual2()
	{
		ArrayKey key1 = Util.mkKey(new Object[] { 1, 2 });
		ArrayKey key2 = Util.mkKey(new Object[] { 1, 2 });
		// Please note that if we use following code, IT WILL NOT WORK:
		//ArrayKey key1 = MiscUtils.mkKey(new int[] { 1, 2 });
		//ArrayKey key2 = MiscUtils.mkKey(new int[] { 1, 2 });

		assertTrue(key1.hashCode() == key2.hashCode());
		assertTrue(key1.equals(key2));
	}
}
