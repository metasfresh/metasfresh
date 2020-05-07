package org.adempiere.util.comparator;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComparableComparatorTest
{
	@Test
	public void test_Comparator_Used_For_Sorting()
	{
		final ComparableComparator<String> cmp = new ComparableComparator<String>();

		final List<String> list = new ArrayList<String>();
		list.add("string1");
		list.add(null);

		Collections.sort(list, cmp);

		Assert.assertEquals("Invalid element at index 0 for : " + list, null, list.get(0));
		Assert.assertEquals("Invalid element at index 1 for : " + list, "string1", list.get(1));
	}
	
	public void assertCompareTo(final String s1, final String s2, final int expectedResult)
	{
		final ComparableComparator<String> cmp = new ComparableComparator<String>();
		final int actualResult = cmp.compare(s1, s2);
		final int actualResultSign = (int)Math.signum(actualResult);
		
		final int expectedResultSign = (int)Math.signum(expectedResult);

		Assert.assertEquals("Invalid comparation result for s1="+s1+", s2="+s2, expectedResultSign, actualResultSign);
	}

	@Test
	public void test_Null_comparatedTo_Null()
	{
		assertCompareTo(null, null, 0);
	}
	
	@Test
	public void test_NotNull_comparatedTo_Null()
	{
		assertCompareTo("s1", null, 1);
	}
	
	@Test
	public void test_Null_comparatedTo_NotNull()
	{
		assertCompareTo(null, "s1", -1);
	}
	
	@Test
	public void test_NotNull_comparatedTo_NotNull()
	{
		assertCompareTo("s1", "s2", -1);
	}
	
	@Test
	public void test_NotNull_comparatedTo_NotNull_2()
	{
		assertCompareTo("s2", "s1", 1);
	}
	
	@Test
	public void test_NotNull_comparatedTo_NotNull_3()
	{
		assertCompareTo("s1", "s1", 0);
	}

}
