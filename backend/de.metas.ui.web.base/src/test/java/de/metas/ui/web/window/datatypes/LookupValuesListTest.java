package de.metas.ui.web.window.datatypes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;

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
	private void limitAndAssertEquals(final LookupValuesList expected, final LookupValuesList list, final int limit)
	{
		assertThat(list.limit(limit)).isEqualTo(expected);

		assertThat(list.offsetAndLimit(-1, limit)).isEqualTo(expected);
		assertThat(list.offsetAndLimit(0, limit)).isEqualTo(expected);
	}

	private void limitAndAssertSame(final LookupValuesList list, final int limit)
	{
		assertThat(list.limit(limit)).isSameAs(list);

		assertThat(list.offsetAndLimit(-1, limit)).isSameAs(list);
		assertThat(list.offsetAndLimit(0, limit)).isSameAs(list);
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

	@Test
	public void check_EMPTY_Constant()
	{
		assertThat(LookupValuesList.EMPTY.isEmpty()).isTrue();
		assertThat(LookupValuesList.EMPTY.getValues()).isEmpty();
		assertThat(LookupValuesList.EMPTY.getDebugProperties().toMap()).isEmpty();
	}

	@Nested
	public class hashCode_and_equals
	{
		@Test
		public void equal_values()
		{
			final LookupValuesList list1 = Stream.of(StringLookupValue.of("1", "displayName1"))
					.collect(LookupValuesList.collect());

			final LookupValuesList list2 = Stream.of(StringLookupValue.of("1", "displayName1"))
					.collect(LookupValuesList.collect());

			assertThat(list1).isEqualTo(list2);
		}

		@Test
		public void not_equal_values()
		{
			final LookupValuesList list1 = Stream.of(StringLookupValue.of("1", "displayName1"))
					.collect(LookupValuesList.collect());

			final LookupValuesList list2 = Stream.of(StringLookupValue.of("2", "displayName2"))
					.collect(LookupValuesList.collect());

			assertThat(list1).isNotEqualTo(list2);
		}

		@Test
		public void equals_ordered_flag()
		{
			final LookupValuesList list1 = Stream.of(StringLookupValue.of("1", "displayName1"))
					.collect(LookupValuesList.collect());

			final LookupValuesList list2 = Stream.of(StringLookupValue.of("1", "displayName1"))
					.collect(LookupValuesList.collect());

			assertThat(list1).isEqualTo(list2);
			assertThat(list1.notOrdered()).isEqualTo(list2.notOrdered());
		}

		@Test
		public void ignore_debugProperties()
		{
			final LookupValuesList list1 = Stream.<LookupValue> empty()
					.collect(LookupValuesList.collect(DebugProperties.EMPTY.withProperty("key1", "value1")));

			final LookupValuesList list2 = Stream.<LookupValue> empty()
					.collect(LookupValuesList.collect(DebugProperties.EMPTY.withProperty("key1", "value2")));

			assertThat(list1).isEqualTo(list2);
		}
	}

	@Nested
	public class buildEmpty
	{
		@Test
		public void withoutDebugProperties_1()
		{
			final LookupValuesList list = Stream.<LookupValue> empty()
					.collect(LookupValuesList.collect());
			assertThat(list).isSameAs(LookupValuesList.EMPTY);
		}

		@Test
		public void withoutDebugProperties_2()
		{
			final DebugProperties debugProperties = null;
			final LookupValuesList list = Stream.<LookupValue> empty()
					.collect(LookupValuesList.collect(debugProperties));
			assertThat(list).isSameAs(LookupValuesList.EMPTY);
		}

		@Test
		public void withoutDebugProperties_3()
		{
			final DebugProperties debugProperties = DebugProperties.EMPTY;
			final LookupValuesList list = Stream.<LookupValue> empty().collect(LookupValuesList.collect(debugProperties));
			assertThat(list).isSameAs(LookupValuesList.EMPTY);
		}

		@Test
		public void withDebugProperties_1()
		{
			final DebugProperties debugProperties = DebugProperties.EMPTY.withProperty("something", "something");

			final LookupValuesList list = Stream.<LookupValue> empty().collect(LookupValuesList.collect(debugProperties));

			assertThat(list).isEqualTo(LookupValuesList.EMPTY);
			assertThat(list).isNotSameAs(LookupValuesList.EMPTY);
			assertThat(list.isEmpty()).isFalse();
			assertThat(list.getValues()).isEmpty();

			assertThat(list.getDebugProperties()).isEqualTo(debugProperties);
			assertThat(list.getDebugProperties()).isSameAs(debugProperties);
		}
	}

	@Nested
	public class containsId_and_getId
	{
		/**
		 * Feature required by those LookupDataSourceFetchers which are returning lookup values with same IDs,
		 * because they are also attaching different attributes to them.
		 * See ProductLookupDescriptor.
		 * 
		 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/662
		 */
		@Test
		public void lookupValuesWithSameId()
		{
			IntegerLookupValue lookupValue1 = IntegerLookupValue.of(1, "item1");
			IntegerLookupValue lookupValue2 = IntegerLookupValue.of(1, "item1 again");

			final LookupValuesList list = LookupValuesList.fromCollection(ImmutableList.of(lookupValue1, lookupValue2));
			assertThat(list.getValues()).hasSize(2).containsExactly(lookupValue1, lookupValue2);
			assertThat(list.getById(1)).isEqualTo(lookupValue1);
		}

		@Test
		public void using_int_IDs()
		{
			final LookupValuesList list = generateIntegerLookupValuesList(10, 20);

			//
			// Positive tests
			for (int id = 10; id <= 20; id++)
			{
				assertThat(list.containsId(id)).as("List shall contain id=" + id).isTrue();

				final LookupValue item = list.getById(id);
				assertThat(item).as("Item shall not be null for id=" + id).isNotNull();

				assertThat(item.getId()).isEqualTo(id);
				assertThat(item.getIdAsInt()).isEqualTo(id);
				assertThat(item.getIdAsString()).isEqualTo(String.valueOf(id));
			}

			//
			// Negative tests
			for (int id = 21; id <= 30; id++)
			{
				assertThat(list.containsId(id)).as("List shall NOT contain id=" + id).isFalse();

				final LookupValue item = list.getById(id);
				assertThat(item).as("Item shall be null for id=" + id).isNull();
			}
		}

		@Test
		public void using_RepoIds()
		{
			final IntegerLookupValue productLookupValue1 = IntegerLookupValue.of(1, "product 1");
			final LookupValuesList list = LookupValuesList.fromNullable(productLookupValue1);

			assertThat(list.containsId(1)).isTrue();
			assertThat(list.getById(1)).isSameAs(productLookupValue1);
			assertThat(list.containsId(ProductId.ofRepoId(1))).isTrue();
			assertThat(list.getById(ProductId.ofRepoId(1))).isSameAs(productLookupValue1);

			// negative testing
			assertThat(list.containsId(2)).isFalse();
			assertThat(list.getById(2)).isNull();
			assertThat(list.containsId(ProductId.ofRepoId(2))).isFalse();
			assertThat(list.getById(ProductId.ofRepoId(2))).isNull();
		}
	}

	@Nested
	public class offset_and_limit
	{
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
			assertThat(list.offsetAndLimit(4, 3)).isEqualTo(listExpected);
		}

		@Test
		public void test_offsetAndLimit_full()
		{
			final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
			assertThat(list.offsetAndLimit(0, 10)).isEqualTo(list);
		}

		@Test
		public void test_offsetAndLimit_OutOfRange()
		{
			final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
			assertThat(list.offsetAndLimit(10, 1)).isSameAs(LookupValuesList.EMPTY);
		}
	}

	@Nested
	public class filter
	{
		@Test
		public void filter_even_IDs()
		{
			final LookupValuesList list = generateIntegerLookupValuesList(1, 10);

			final Predicate<LookupValue> filter = item -> item.getIdAsInt() % 2 == 0;
			final LookupValuesList expected = generateIntegerLookupValuesListForIds(2, 4);
			assertThat(list.filter(filter, 0, 2)).isEqualTo(expected);
		}
	}

	@Nested
	public class removeAll
	{
		@Test
		public void standardCase()
		{
			final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
			final LookupValuesList listToRemove = generateIntegerLookupValuesListForIds(2, 3, 5, 7, 11, 12);

			final LookupValuesList resultActual = list.removeAll(listToRemove);
			final LookupValuesList resultExpected = generateIntegerLookupValuesListForIds(1, 4, 6, 8, 9, 10);

			assertThat(resultActual).isEqualTo(resultExpected);
		}

		@Test
		public void removeEmptyList()
		{
			final LookupValuesList list = generateIntegerLookupValuesList(1, 10);
			final LookupValuesList resultActual = list.removeAll(LookupValuesList.EMPTY);
			assertThat(resultActual).isEqualTo(list);
			assertThat(resultActual).isSameAs(list);
		}

		@Test
		public void removeFromEmpty()
		{
			final LookupValuesList list = LookupValuesList.EMPTY;
			final LookupValuesList listToRemove = generateIntegerLookupValuesList(1, 10);
			final LookupValuesList resultActual = list.removeAll(listToRemove);
			assertThat(resultActual).isEqualTo(list);
			assertThat(resultActual).isSameAs(list);
		}
	}

	@Nested
	public class notOrdered
	{
		private LookupValuesList newOrderedList()
		{
			final LookupValuesList listOrdered = LookupValuesList.fromCollection(ImmutableList.of(
					IntegerLookupValue.of(1, "name1"),
					IntegerLookupValue.of(2, "name2")));

			assertThat(listOrdered.isOrdered()).isTrue(); // just to make sure

			return listOrdered;
		}

		@Test
		public void standardCase()
		{
			assertThat(newOrderedList().notOrdered().isOrdered()).isFalse();
		}

		@Test
		public void notOrdered_returns_the_same()
		{
			final LookupValuesList list = newOrderedList().notOrdered();
			assertThat(list.notOrdered()).isSameAs(list);
		}

		@Test
		public void empty_is_ordered()
		{
			assertThat(LookupValuesList.EMPTY.isOrdered()).isTrue();
		}

		@Test
		public void empty_notOrdered()
		{
			assertThat(LookupValuesList.EMPTY.notOrdered().isOrdered()).isFalse();
		}

	}
}
