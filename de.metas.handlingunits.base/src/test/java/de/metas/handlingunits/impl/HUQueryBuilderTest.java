package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.text.ExtendedReflectionToStringBuilder;
import org.adempiere.util.text.RecursiveIndentedMultilineToStringStyle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HUQueryBuilderTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Just makes sure that {@link HUQueryBuilder#copy()} is not failing.
	 */
	@Test
	public void test_copy_NotFails()
	{
		final HUQueryBuilder husQuery = new HUQueryBuilder();
		final HUQueryBuilder husQueryCopy = husQuery.copy();
		Assert.assertNotNull("copy shall not be null", husQueryCopy);
		Assert.assertNotSame("original and copy shall not be the same", husQueryCopy, husQuery);
		assertSameStringRepresentation(husQuery, husQueryCopy);
	}

	private final void assertSameStringRepresentation(final Object expected, final Object actual)
	{
		final String expectedStr = toString(expected);
		final String actualStr = toString(actual);

		final String message = "String representations shall be the same"
				+ "\nExpected: " + expectedStr
				+ "\nActual: " + actualStr;
		Assert.assertEquals(message, expectedStr, actualStr);
	}

	private final String toString(final Object obj)
	{
		return new ExtendedReflectionToStringBuilder(obj, RecursiveIndentedMultilineToStringStyle.instance)
				.toString();
	}
}
