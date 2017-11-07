package org.adempiere.util.collections;

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


import org.adempiere.util.collections.IncludeExcludeListPredicate.Builder;
import org.junit.Assert;
import org.junit.Test;

public class IncludeExcludeListPredicateTest
{
	private IncludeExcludeListPredicate<String> includeExcludeList = null;

	private final void assertAccepted(final String testItem)
	{
		Assert.assertTrue("Item shall be accepted: " + testItem, includeExcludeList.test(testItem));
	}

	private final void assertNotAccepted(final String testItem)
	{
		Assert.assertFalse("Item shall NOT be accepted: " + testItem, includeExcludeList.test(testItem));
	}

	/**
	 * Expectation: not matter how the include/exclude list is configured, <code>null</code> items are NOT accepted
	 */
	@Test
	public void test_NullItemsAreNeverAccepted()
	{
		includeExcludeList = IncludeExcludeListPredicate.<String> builder()
				.build();
		assertNotAccepted(null);
	}

	/** Expect anything to be accepted if the list is empty */
	@Test
	public void test_EmptyList()
	{
		includeExcludeList = IncludeExcludeListPredicate.<String> builder()
				.build();

		assertAccepted("item1");
		assertAccepted("item2");
		assertAccepted("item3");
	}

	/**
	 * Case: include/exclude list has no includes defined but some items are excluded.
	 * 
	 * Expectation: excluded items are NOT accepted, anything else is accepted
	 */
	@Test
	public void test_NoIncludes_SomeExcludes()
	{
		includeExcludeList = IncludeExcludeListPredicate.<String> builder()
				.exclude("item1")
				.build();

		//
		assertNotAccepted("item1");
		assertAccepted("item2");
		assertAccepted("item3");
	}

	/**
	 * Case: include/exclude list has some includes defined but NO items are excluded.
	 * 
	 * Expectation: only included items are accepted.
	 */
	@Test
	public void test_SomeIncludes_NoExcludes()
	{
		includeExcludeList = IncludeExcludeListPredicate.<String> builder()
				.include("item1")
				.include("item2")
				.include("item3")
				.build();
		//
		assertAccepted("item1");
		assertAccepted("item2");
		assertAccepted("item3");
		assertNotAccepted("item4");
	}

	/**
	 * Case: include/exclude list has some includes defined and also some excludes.
	 * 
	 * Expectation: only included items are accepted, excluded items are always rejected, no matter if they were added to includes.
	 */
	@Test
	public void test_SomeIncludes_SomeExcludes()
	{
		includeExcludeList = IncludeExcludeListPredicate.<String> builder()
				.include("item1")
				.include("item2")
				.include("item3")
				.exclude("item1")
				.build();
		//
		assertNotAccepted("item1");
		assertAccepted("item2");
		assertAccepted("item3");
		assertNotAccepted("item4");
	}

	@Test(expected = RuntimeException.class)
	public void test_addNullInclude_Fails()
	{
		IncludeExcludeListPredicate.<String> builder()
				.include(null);
	}

	@Test
	public void test_addNullExclude_Ignored()
	{
		includeExcludeList = IncludeExcludeListPredicate.<String> builder()
				.exclude(null)
				.build();

		Assert.assertSame(IncludeExcludeListPredicate.empty(), includeExcludeList);
	}

	/**
	 * Test {@link Builder#build()} and make sure it's caching the last build result and in case nothing changed, it returns it.
	 */
	@Test
	public void test_SubsequentBuildsAreOptimized()
	{
		final Builder<String> builder = IncludeExcludeListPredicate.<String> builder();

		// If nothing added, builder shall always return the the "empty" list.
		IncludeExcludeListPredicate<String> list = builder.build();
		Assert.assertSame(IncludeExcludeListPredicate.empty(), list);

		// Add an item to builder.
		// We expect a new list to be built.
		IncludeExcludeListPredicate<String> lastList = list;
		list = builder.include("item1").build();
		Assert.assertNotSame(lastList, list);

		// Add the same item again.
		// We expect same list to be built.
		lastList = list;
		list = builder.include("item1").build();
		Assert.assertSame(lastList, list);

		// Add a new item to builder.
		// We expect a new list to be built.
		lastList = list;
		list = builder.include("item2").build();
		Assert.assertNotSame(lastList, list);
	}
}
