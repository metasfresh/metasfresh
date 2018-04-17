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

public class BatchSpliteratorTest
{
	@Test
	public void test_empty()
	{
		final List<List<String>> expected = ImmutableList.of();

		final List<List<String>> actual = GuavaCollectors.batchAndStream(
				Stream.<String> empty(),
				4)
				.collect(ImmutableList.toImmutableList());

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test_simple()
	{
		final List<List<String>> expected = ImmutableList.of(
				ImmutableList.of("1", "2", "3", "4"),
				ImmutableList.of("5", "6", "7", "8"),
				ImmutableList.of("9", "10"));

		final List<List<String>> actual = GuavaCollectors.batchAndStream(
				Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
				4)
				.collect(ImmutableList.toImmutableList());

		Assert.assertEquals(expected, actual);
	}
}
