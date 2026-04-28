package de.metas.util.collections;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListUtilsTest
{
	@Test
	public void test_copyAndReverse_NULL()
	{
		final List<Object> resultActual = ListUtils.copyAndReverse(null);
		final List<Object> resultExpected = null;

		assertThat(resultActual).as("Invalid result").isEqualTo(resultExpected);
	}

	@Test
	public void test_copyAndReverse_EmptyList()
	{
		final List<Object> list = Collections.emptyList();
		final List<Object> resultExpected = new ArrayList<Object>();

		test_copyAndReverse(resultExpected, list);
	}

	@Test
	public void test_copyAndReverse_OneElement()
	{
		final List<String> list = Arrays.asList("1");
		final List<String> resultExpected = Arrays.asList("1");

		test_copyAndReverse(resultExpected, list);
	}

	@Test
	public void test_copyAndReverse_MoreThenOneElement()
	{
		final List<String> list = Arrays.asList("1", "2", "3");
		final List<String> resultExpected = Arrays.asList("3", "2", "1");

		test_copyAndReverse(resultExpected, list);
	}

	public <T> void test_copyAndReverse(final List<T> resultExpected, final List<T> list)
	{
		final List<T> listCopy = new ArrayList<>(list);
		final List<T> resultActual = ListUtils.copyAndReverse(list);

		assertThat(list).as("Original list shall not be changed").isEqualTo(listCopy);
		assertThat(resultActual).as("Invalid result for " + list).isEqualTo(resultExpected);
	}

	@Test
	public void test_copyAsList_null()
	{
		assertThat(ListUtils.copyAsList(null)).isNull();
	}

	@Test
	public void test_copyAsList_FromList()
	{
		final List<String> list = Arrays.asList("a", "b", "c");
		final List<String> listCopy = ListUtils.copyAsList(list);
		assertThat(listCopy).isEqualTo(list);
		assertThat(listCopy).isNotSameAs(list);
	}

	@Test
	public void test_copyAsList_FromPureIterable()
	{
		final List<String> list = Arrays.asList("a", "b", "c");
		final Iterable<String> iterable = new Iterable<String>()
		{
			@Override
			public Iterator<String> iterator()
			{
				return list.iterator();
			}
		};

		final List<String> listCopy = ListUtils.copyAsList(iterable);
		assertThat(listCopy).isEqualTo(list);
		assertThat(listCopy).isNotSameAs(list);
		assertThat(listCopy).isNotSameAs(iterable);
	}
}
