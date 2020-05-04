package de.metas.util.reducers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import lombok.Getter;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class SingleValueReducerTest
{
	@Test
	public void singleValue_on_empty()
	{
		final Stream<Object> stream = Stream.empty();
		assertThat(stream.reduce(Reducers.singleValue())).isEmpty();
	}

	@Test
	public void singleValue_expect_OK()
	{
		final Stream<String> stream = Stream.of("1", "1", "1");
		assertThat(stream.reduce(Reducers.singleValue()).orElse(null)).isEqualTo("1");
	}

	@Test
	public void singleValue_expect_NOK()
	{
		final Stream<String> stream = Stream.of("1", "1", "2", "3");
		final Function<List<String>, MyDistinctException> exceptionFactory = MyDistinctException::new;

		expectExceptionWithValues("1", "2")
				.test(() -> stream.reduce(Reducers.singleValue(exceptionFactory)));
	}

	@SafeVarargs
	private static <T> MyDistinctExceptionExpectation expectExceptionWithValues(final T... expectedValues)
	{
		return new MyDistinctExceptionExpectation(Arrays.asList(expectedValues));
	}

	@SuppressWarnings("serial")
	private static class MyDistinctException extends RuntimeException
	{
		@Getter
		private final ImmutableList<Object> values;

		public MyDistinctException(final List<? extends Object> values)
		{
			this.values = ImmutableList.copyOf(values);
		}
	}

	private static class MyDistinctExceptionExpectation
	{
		@Getter
		private final ImmutableList<Object> expectedValues;

		public MyDistinctExceptionExpectation(final List<? extends Object> expectedValues)
		{
			this.expectedValues = ImmutableList.copyOf(expectedValues);
		}

		public void test(final Runnable runnable)
		{
			try
			{
				runnable.run();
				Assert.fail("No exceptio was thrown");
			}
			catch (final MyDistinctException ex)
			{
				assertThat(ex.getValues()).isEqualTo(expectedValues);
			}
		}

	}
}
