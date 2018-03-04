package de.metas.ui.web.window.datatypes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class LookupValuesListTest
{
	public void testEmpty()
	{
		Assert.assertTrue(LookupValuesList.EMPTY.isEmpty());
		Assert.assertTrue(LookupValuesList.EMPTY.getValues().isEmpty());
		Assert.assertTrue(LookupValuesList.EMPTY.getDebugProperties().isEmpty());
	}

	@Test
	public void test_buildEmpty_withoutDebugProperties_1()
	{
		final LookupValuesList list = Stream.<LookupValue> empty()
				.collect(LookupValuesList.collect());
		Assert.assertSame(LookupValuesList.EMPTY, list);
	}

	@Test
	public void test_buildEmpty_withoutDebugProperties_2()
	{
		final Map<String, String> debugProperties = null;
		final LookupValuesList list = Stream.<LookupValue> empty()
				.collect(LookupValuesList.collect(debugProperties));
		Assert.assertSame(LookupValuesList.EMPTY, list);
	}

	@Test
	public void test_buildEmpty_withoutDebugProperties_3()
	{
		final Map<String, String> debugProperties = new HashMap<>();
		final LookupValuesList list = Stream.<LookupValue> empty().collect(LookupValuesList.collect(debugProperties));
		Assert.assertSame(LookupValuesList.EMPTY, list);
	}

	@Test
	public void test_buildEmpty_withDebugProperties_1()
	{
		final Map<String, String> debugProperties = new HashMap<>();
		debugProperties.put("something", "something");

		final LookupValuesList list = Stream.<LookupValue> empty().collect(LookupValuesList.collect(debugProperties));

		Assert.assertEquals(LookupValuesList.EMPTY, list);
		Assert.assertFalse(list.isEmpty());
		Assert.assertTrue(list.getValues().isEmpty());

		Assert.assertEquals(debugProperties, list.getDebugProperties());
		Assert.assertNotSame(debugProperties, list.getDebugProperties());
	}

	@Test
	public void test_buildEmpty_withDebugProperties_usingImmutableDebugPropertiesMap()
	{
		final Map<String, String> debugProperties = ImmutableMap.of("something", "something");

		final LookupValuesList list = Stream.<LookupValue> empty().collect(LookupValuesList.collect(debugProperties));

		Assert.assertEquals(LookupValuesList.EMPTY, list);
		Assert.assertFalse(list.isEmpty());
		Assert.assertTrue(list.getValues().isEmpty());

		Assert.assertSame(debugProperties, list.getDebugProperties());
	}

	@Test
	public void test_containsId_and_getById()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(10, 20);

		//
		// Positive tests
		for (int id = 10; id <= 20; id++)
		{
			Assert.assertTrue("List shall contain id=" + id, list.containsId(id));
			final LookupValue item = list.getById(id);
			Assert.assertNotNull("Item shall not be null for id=" + id, item);
			Assert.assertEquals(id, item.getId());
			Assert.assertEquals(id, item.getIdAsInt());
			Assert.assertEquals(String.valueOf(id), item.getIdAsString());
		}

		//
		// Negative tests
		for (int id = 21; id <= 30; id++)
		{
			Assert.assertFalse("List shall NOT contain id=" + id, list.containsId(id));
			final LookupValue item = list.getById(id);
			Assert.assertNull("Item shall be null for id=" + id, item);
		}
	}

	@Test
	public void test_limit_negative_or_zero()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
		limitAndAssertSame(list, -1);
		limitAndAssertSame(list, 0);
	}

	@Test
	public void test_limit_positive_lessThanSize()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);

		for (int size = 1; size < 10; size++)
		{
			final LookupValuesList listExpected = generateIntegerLookupValuesList(1, size);
			limitAndAssertEquals(listExpected, list, size);
		}
	}

	@Test
	public void test_limit_positive_greaterThanSize()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
		limitAndAssertSame(list, 11);
		limitAndAssertSame(list, Integer.MAX_VALUE);
	}

	@Test
	public void test_offsetAndLimit_partial()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
		final LookupValuesList listExpected = generateIntegerLookupValuesList(5, 7);
		Assert.assertEquals(listExpected, list.offsetAndLimit(4, 3));
	}

	@Test
	public void test_offsetAndLimit_full()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
		Assert.assertEquals(list, list.offsetAndLimit(0, 10));
	}

	@Test
	public void test_offsetAndLimit_OutOfRange()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
		Assert.assertSame(LookupValuesList.EMPTY, list.offsetAndLimit(10, 1));
	}

	@Test
	public void test_filter()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);

		final Predicate<LookupValue> filter = item -> item.getIdAsInt() % 2 == 0;
		final LookupValuesList expected = generateIntegerLookupValuesListForIds(2, 4);
		Assert.assertEquals(expected, list.filter(filter, 0, 2));
	}

	@Test
	public void test_removeAll_StandardCase()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
		final LookupValuesList listToRemove = generateIntegerLookupValuesListForIds(2, 3, 5, 7, 11, 12);

		final LookupValuesList resultActual = list.removeAll(listToRemove);
		final LookupValuesList resultExpected = generateIntegerLookupValuesListForIds(1, 4, 6, 8, 9, 10);

		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test
	public void test_removeAll_RemoveEmptyList()
	{
		final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
		final LookupValuesList resultActual = list.removeAll(LookupValuesList.EMPTY);
		Assert.assertEquals(list, resultActual);
		Assert.assertSame(list, resultActual);
	}

	@Test
	public void test_removeAll_RemoveFromEmpty()
	{
		final LookupValuesList list = LookupValuesList.EMPTY;
		final LookupValuesList listToRemove = generateIntegerLookupValuesList(1, 10);
		final LookupValuesList resultActual = list.removeAll(listToRemove);
		Assert.assertEquals(list, resultActual);
		Assert.assertSame(list, resultActual);
	}

	/**
	 * Feature required by those LookupDataSourceFetchers which are returning lookup values with same IDs,
	 * because they are also attaching different attributes to them.
	 * See ProductLookupDescriptor.
	 * 
	 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/662
	 */
	@Test
	public void test_LookupValuesWithSameId()
	{
		IntegerLookupValue lookupValue1 = IntegerLookupValue.of(1, "item1");
		IntegerLookupValue lookupValue2 = IntegerLookupValue.of(1, "item1 again");

		final LookupValuesList list = LookupValuesList.fromCollection(ImmutableList.of(lookupValue1, lookupValue2));
		assertThat(list.getValues()).hasSize(2).containsExactly(lookupValue1, lookupValue2);
		assertThat(list.getById(1)).isEqualTo(lookupValue1);
	}

	//
	//
	// Helper methods
	//
	//

	private void limitAndAssertEquals(final LookupValuesList expected, final LookupValuesList list, final int limit)
	{
		Assert.assertEquals(expected, list.limit(limit));

		Assert.assertEquals(expected, list.offsetAndLimit(-1, limit));
		Assert.assertEquals(expected, list.offsetAndLimit(0, limit));
	}

	private void limitAndAssertSame(final LookupValuesList list, final int limit)
	{
		Assert.assertSame(list, list.limit(limit));

		Assert.assertSame(list, list.offsetAndLimit(-1, limit));
		Assert.assertSame(list, list.offsetAndLimit(0, limit));
	}

	private final LookupValuesList generateIntegerLookupValuesList(final int firstId, final int lastId)
	{
		return IntStream.rangeClosed(firstId, lastId)
				.mapToObj(id -> IntegerLookupValue.of(id, "Item " + id))
				.collect(LookupValuesList.collect());
	}

	private final LookupValuesList generateIntegerLookupValuesListForIds(final int... ids)
	{
		return IntStream.of(ids)
				.mapToObj(id -> IntegerLookupValue.of(id, "Item " + id))
				.collect(LookupValuesList.collect());
	}

}
