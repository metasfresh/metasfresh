/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.util;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class StreamUtilsTests
{
	@Test
	public void dice_emptyStream()
	{
		final List<List<Object>> chunks = StreamUtils.dice(Stream.empty(), 10)
				.collect(ImmutableList.toImmutableList());

		assertThat(chunks).isEmpty();
	}

	@Test
	public void dice_oneItemStream()
	{
		final List<List<Integer>> chunks = StreamUtils.dice(Stream.of(123), 10)
				.collect(ImmutableList.toImmutableList());

		assertThat(chunks).hasSize(1);
		assertThat(chunks).isEqualTo(ImmutableList.of(ImmutableList.of(123)));
	}

	@Test
	public void dice_1to57_10itemsPerChunk()
	{
		final List<List<Integer>> chunks = StreamUtils.dice(intRangeAsList(1, 57).stream(), 10)
				.collect(ImmutableList.toImmutableList());

		assertThat(chunks).hasSize(6);
		assertThat(chunks).isEqualTo(ImmutableList.of(
				intRangeAsList(1, 10),
				intRangeAsList(11, 20),
				intRangeAsList(21, 30),
				intRangeAsList(31, 40),
				intRangeAsList(41, 50),
				intRangeAsList(51, 57)));
	}

	@Test
	public void dice_1to10_10itemsPerChunk()
	{
		final List<List<Integer>> chunks = StreamUtils.dice(intRangeAsList(1, 10).stream(), 10)
				.collect(ImmutableList.toImmutableList());

		assertThat(chunks).hasSize(1);
		assertThat(chunks).isEqualTo(ImmutableList.of(intRangeAsList(1, 10)));
	}

	@Test
	public void dice_1to20_10itemsPerChunk()
	{
		final List<List<Integer>> chunks = StreamUtils.dice(intRangeAsList(1, 20).stream(), 10)
				.collect(ImmutableList.toImmutableList());

		assertThat(chunks).hasSize(2);
		assertThat(chunks).isEqualTo(ImmutableList.of(
				intRangeAsList(1, 10),
				intRangeAsList(11, 20)));
	}

	private static List<Integer> intRangeAsList(final int startInclusive, final int endInclusive)
	{
		return IntStream.rangeClosed(startInclusive, endInclusive).boxed().collect(ImmutableList.toImmutableList());
	}

	@Test
	public void dice_with_aggregation()
	{
		final List<TestObject> list = new ArrayList<>();
		list.addAll(getTestObjectList(0,1,5));
		list.addAll(getTestObjectList(0, 2, 7));
		list.addAll(getTestObjectList(1, 0, 10));

		final List<List<TestObject>> chunks = StreamUtils.dice(list.stream(), 10, TestObject::element1)
				.collect(ImmutableList.toImmutableList());

		assertThat(chunks).hasSize(3);

		final List<TestObject> expectedList1 = getTestObjectList(0,1,5);
		expectedList1.addAll(getTestObjectList(0, 2, 5));
		assertThat(chunks).isEqualTo(ImmutableList.of(
				expectedList1,
				getTestObjectList(0,2,2),
				getTestObjectList(1,0,10)));
	}

	private List<TestObject> getTestObjectList(final int element1, final int element2, final int count)
	{
		final List<TestObject> list = new ArrayList<>();
		for(int i = 1; i <= count; i++)
		{
			list.add(TestObject.builder()
							 .element1(element1)
							 .element2(element2)
							 .build()
			);
		}
		return list;
	}

	@Builder
	private record TestObject(int element1, int element2) { }
}
