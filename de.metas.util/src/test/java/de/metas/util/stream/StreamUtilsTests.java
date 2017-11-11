package de.metas.util.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class StreamUtilsTests
{
	@Test
	public void test_bufferAndMap()
	{
		final List<List<Integer>> chunks = new ArrayList<>();

		final Stream<String> downStream = StreamUtils.<Integer, String> bufferAndMap()
				.upstream(IntStream.rangeClosed(1, 57).boxed())
				.mapper(chunk -> {
					chunks.add(chunk);
					return mapToString(chunk);
				})
				.bufferSize(10)
				.build();

		final List<String> listActual = downStream.collect(ImmutableList.toImmutableList());
		// System.out.println(listActual);
		// chunks.forEach(chunk -> System.out.println(chunk));

		assertThat(listActual).hasSize(57);
		assertThat(listActual).isEqualTo(mapToString(intRangeAsList(1, 57)).collect(ImmutableList.toImmutableList()));
		assertThat(chunks).isEqualTo(ImmutableList.of(
				intRangeAsList(1, 10),
				intRangeAsList(11, 20),
				intRangeAsList(21, 30),
				intRangeAsList(31, 40),
				intRangeAsList(41, 50),
				intRangeAsList(51, 57)));
	}

	private final Stream<String> mapToString(final List<Integer> chunk)
	{
		return chunk.stream().map(item -> "string-" + item);
	}

	private static final List<Integer> intRangeAsList(int startInclusive, int endInclusive)
	{
		return IntStream.rangeClosed(startInclusive, endInclusive).boxed().collect(ImmutableList.toImmutableList());
	}
}
