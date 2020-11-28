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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.metas.util.collections.IteratorChain;

public class IteratorChainTest
{
	@Test
	public void testCommonScenario()
	{
		final List<List<String>> testData = new ArrayList<List<String>>();
		testData.add(createTestList("list1", 5));
		testData.add(createTestList("list2", 0));
		testData.add(createTestList("list2", 7));
		testData.add(createTestList("list3", 0));
		testData.add(createTestList("list4", 0));
		testData.add(createTestList("list5", 1));

		test(testData);
	}

	@Test
	public void emptyTest()
	{
		final List<List<String>> testData = new ArrayList<List<String>>();
		test(testData);
	}

	@SuppressWarnings("resource")
	@Test(expected = IllegalArgumentException.class)
	public void addingNullIterator()
	{
		IteratorChain<String> it = new IteratorChain<String>();
		it.addIterator(null); // shall throw IllegalArgumentException

	}

	@SuppressWarnings("resource")
	@Test(expected = IllegalStateException.class)
	public void lockedChain()
	{
		IteratorChain<String> it = new IteratorChain<String>();
		it.addIterator(new ArrayList<String>().iterator());
		it.hasNext(); // this one locks the iterator for adding

		it.addIterator(new ArrayList<String>().iterator()); // throws IllegalStateException
	}

	private <E> void test(List<List<E>> testData)
	{
		final List<E> dataExpected = join(testData);
		final IteratorChain<E> it = createIteratorChain(testData);
		final List<E> dataActual = join(it);

		Assert.assertEquals(dataExpected, dataActual);
	}

	private List<String> createTestList(String prefix, int size)
	{
		final List<String> list = new ArrayList<String>();
		for (int i = 1; i <= size; i++)
		{
			list.add(prefix + "-" + i + "/" + size);
		}
		return list;
	}

	private <E> IteratorChain<E> createIteratorChain(List<List<E>> lists)
	{
		final IteratorChain<E> it = new IteratorChain<E>();
		for (List<E> l : lists)
		{
			it.addIterator(l.iterator());
		}
		return it;
	}

	private <E> List<E> join(List<List<E>> lists)
	{
		final List<E> result = new ArrayList<E>();
		for (List<E> l : lists)
		{
			result.addAll(l);
		}
		return result;
	}

	private <E> List<E> join(Iterator<E> it)
	{
		final List<E> result = new ArrayList<E>();
		while (it.hasNext())
		{
			E e = it.next();
			result.add(e);
		}

		return result;
	}

}
