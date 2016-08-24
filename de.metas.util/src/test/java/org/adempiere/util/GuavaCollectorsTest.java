package org.adempiere.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class GuavaCollectorsTest
{
	@Test
	public void test_toImmutableList()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1, 2, 3);

		final List<Integer> resultExpected = source;
		final List<Integer> result = source.stream().collect(GuavaCollectors.toImmutableList());

		Assert.assertEquals(resultExpected, result);
	}

	@Test
	public void test_toImmutableListExcludingDuplicates()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1, 2, 3, 4);

		final List<Integer> resultExpected = Arrays.asList(1, 2, 3, 4);
		final List<Integer> result = source.stream().collect(GuavaCollectors.toImmutableListExcludingDuplicates());

		Assert.assertEquals(resultExpected, result);
	}

	@Test
	public void test_toImmutableSet()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1, 2, 3, 4);

		final Set<Integer> resultExpected = ImmutableSet.of(1, 2, 3, 4);
		final Set<Integer> result = source.stream().collect(GuavaCollectors.toImmutableSet());

		Assert.assertEquals(resultExpected, result);
	}
}
