package de.metas.util;

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.util
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

public class GuavaCollectorsTest
{
	@Test
	void test_toImmutableList()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1, 2, 3);
		assertThat(source.stream().collect(GuavaCollectors.toImmutableList()))
				.isEqualTo(source);
	}

	@Test
	void test_toImmutableListExcludingDuplicates()
	{
		assertThat(Stream.of(1, 2, 3, 1, 2, 3, 4).collect(GuavaCollectors.toImmutableListExcludingDuplicates()))
				.containsExactly(1, 2, 3, 4);
	}

	@Test
	void test_toImmutableListExcludingDuplicates_With_KeyFunction_DuplicatesConsumer()
	{
		final List<IPair<Integer, String>> source = Arrays.asList(
				ImmutablePair.of(1, "one"),
				ImmutablePair.of(2, "two"),
				ImmutablePair.of(3, "three"),
				ImmutablePair.of(1, "one-again"),
				ImmutablePair.of(2, "two-again"),
				ImmutablePair.of(3, "three-again"),
				ImmutablePair.of(4, "four")
		);

		final List<IPair<Integer, String>> duplicatesActual = new ArrayList<>();
		assertThat(source.stream().collect(GuavaCollectors.toImmutableListExcludingDuplicates(IPair::getLeft, (key, item) -> duplicatesActual.add(item))))
				.containsExactly(
						ImmutablePair.of(1, "one"),
						ImmutablePair.of(2, "two"),
						ImmutablePair.of(3, "three"),
						ImmutablePair.of(4, "four")
				);
		assertThat(duplicatesActual)
				.containsExactly(
						ImmutablePair.of(1, "one-again"),
						ImmutablePair.of(2, "two-again"),
						ImmutablePair.of(3, "three-again")
				);
	}

	@Test
	void test_toImmutableSet()
	{
		assertThat(Stream.of(1, 2, 3, 1, 2, 3, 4).collect(GuavaCollectors.toImmutableSet()))
				.containsExactly(1, 2, 3, 4);
	}

	@Test
	void test_toImmutableSetHandlingDuplicates()
	{
		//noinspection ResultOfMethodCallIgnored
		assertThatThrownBy(() ->
				Stream.of("1", "2", "3", "1").collect(GuavaCollectors.toImmutableSetHandlingDuplicates(DuplicateElementException.throwExceptionConsumer()))
		).isInstanceOfSatisfying(DuplicateElementException.class, (ex) -> assertThat((String)ex.getElement()).isEqualTo("1"));
	}

	@Test
	void test_toImmutableSetHandlingDuplicates_NoDuplicates()
	{
		assertThat(Stream.of(1, 2, 3, 4).collect(GuavaCollectors.toImmutableSetHandlingDuplicates(DuplicateElementException.throwExceptionConsumer())))
				.containsExactly(1, 2, 3, 4);
	}

	@Test
	void test_toImmutableMapByKeyKeepFirstDuplicate()
	{
		assertThat(Stream.of("1_one", "2_two1", "2_two2", "3_three").collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(value -> value.substring(0, 1))))
				.isEqualTo(ImmutableMap.of("1", "1_one", "2", "2_two1", "3", "3_three"));
	}

	/**
	 * i.e. make sure the last duplicate is kept
	 */
	@Test
	void test_toImmutableMapByKey()
	{
		assertThat(Stream.of("1_one", "2_two1", "2_two2", "3_three").collect(GuavaCollectors.toImmutableMapByKey(value -> value.substring(0, 1))))
				.isEqualTo(ImmutableMap.of("1", "1_one", "2", "2_two2", "3", "3_three"));
	}

	private static final class DuplicateElementException extends RuntimeException
	{
		public static <T> Consumer<T> throwExceptionConsumer()
		{
			return (element1) -> {
				throw new DuplicateElementException(element1);
			};
		}

		private final Object element;

		public DuplicateElementException(final Object element)
		{
			super("Duplicate element test exception: " + element);
			this.element = element;
		}

		public <T> T getElement()
		{
			@SuppressWarnings("unchecked") final T elementCasted = (T)element;
			return elementCasted;
		}
	}

	@Nested
	class uniqueElementOrThrow
	{
		@Test
		void empty()
		{
			//noinspection ResultOfMethodCallIgnored
			assertThatThrownBy(
					() -> Stream.empty().collect(GuavaCollectors.uniqueElementOrThrow(set -> new RuntimeException("error: " + set)))
			).hasMessageStartingWith("error: []");
		}

		@Test
		void singleElement()
		{
			assertThat(
					Stream.of("1").collect(GuavaCollectors.uniqueElementOrThrow(set -> new RuntimeException("error: " + set)))
			).isEqualTo("1");
		}

		@Test
		void singleElementButMoreTimes()
		{
			assertThat(
					Stream.of("1", "1", "1").collect(GuavaCollectors.uniqueElementOrThrow(set -> new RuntimeException("error: " + set)))
			).isEqualTo("1");
		}

		@Test
		void multipleUniqueElements()
		{
			//noinspection ResultOfMethodCallIgnored
			assertThatThrownBy(
					() -> Stream.of("1", "2", "3", "2", "1").collect(GuavaCollectors.uniqueElementOrThrow(set -> new RuntimeException("error: " + set)))
			).hasMessageStartingWith("error: [1, 2, 3]");
		}

	}
}
