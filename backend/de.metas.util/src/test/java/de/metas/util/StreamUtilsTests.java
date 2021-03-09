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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

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

	private static final List<Integer> intRangeAsList(int startInclusive, int endInclusive)
	{
		return IntStream.rangeClosed(startInclusive, endInclusive).boxed().collect(ImmutableList.toImmutableList());
	}
}
