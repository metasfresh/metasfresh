package org.adempiere.util;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class GroupByClassifierSpliteratorTest
{
	@Test
	public void test_empty()
	{
		final List<List<String>> expected = ImmutableList.of();

		final List<List<String>> actual = GuavaCollectors.groupByAndStream(
				Stream.<String> empty(),
				value -> value)
				.collect(ImmutableList.toImmutableList());

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test_standard()
	{
		final List<List<String>> expected = ImmutableList.of(
				ImmutableList.of("aa", "ab", "ac"),
				ImmutableList.of("ba", "bb", "bc"),
				ImmutableList.of("ca", "cb", "cc"));

		final List<List<String>> actual = GuavaCollectors.groupByAndStream(
				Stream.of("aa", "ab", "ac", "ba", "bb", "bc", "ca", "cb", "cc"),
				value -> value.charAt(0))
				.collect(ImmutableList.toImmutableList());

		Assert.assertEquals(expected, actual);
	}
}
