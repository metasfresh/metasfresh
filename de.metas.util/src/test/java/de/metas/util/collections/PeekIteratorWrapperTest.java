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


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import de.metas.util.collections.PeekIterator;
import de.metas.util.collections.PeekIteratorWrapper;

public class PeekIteratorWrapperTest
{

	@Test
	public void test_simple()
	{
		final Iterator<Integer> source = Arrays.asList(1, 2, 3).iterator();
		final PeekIterator<Integer> it = new PeekIteratorWrapper<Integer>(source);

		assertEquals("Iterator peek value", Integer.valueOf(1), it.peek());

		Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
		assertEquals("Invalid peek value", Integer.valueOf(1), it.peek());

		Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
		assertEquals("Invalid peek value", Integer.valueOf(1), it.peek());
		assertEquals("Invalid next value", Integer.valueOf(1), it.next());

		Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
		assertEquals("Invalid peek value", Integer.valueOf(2), it.peek());
		assertEquals("Invalid peek value", Integer.valueOf(2), it.next());

		Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
		assertEquals("Invalid peek value", Integer.valueOf(3), it.peek());
		assertEquals("Invalid peek value", Integer.valueOf(3), it.next());

		assertEquals("Invalid hasNext", false, it.hasNext());
	}

	@Test
	public void test_emptyIterator()
	{
		final Iterator<Integer> source = new ArrayList<Integer>().iterator();
		final PeekIterator<Integer> it = new PeekIteratorWrapper<Integer>(source);

		assertEquals("Invalid hasNext", false, it.hasNext());
	}
	
	/**
	 * Testing a simple iterating and searching algorithm
	 */
	@Test
	public void test_iterating()
	{
		final Iterator<Integer> source = Arrays.asList(1, 2, 3, 4, 5).iterator();
		final PeekIterator<Integer> it = new PeekIteratorWrapper<Integer>(source);
		
		boolean found = false;
		while(it.hasNext())
		{
			final Integer item = it.peek();
			if (item == 4)
			{
				found = true;
				break;
			}
			
			it.next();
		}

		assertEquals("Item not found", true, found);
		
		Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
		assertEquals("Invalid peek value", Integer.valueOf(4), it.peek());
		assertEquals("Invalid peek value", Integer.valueOf(4), it.next());
		
		Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
		assertEquals("Invalid peek value", Integer.valueOf(5), it.peek());
		assertEquals("Invalid peek value", Integer.valueOf(5), it.next());

		Assert.assertEquals("Invalid hasNext value", false, it.hasNext());
	}
	
	@Test
	public void test_oneElement_KeepPeeking()
	{
		final Iterator<Integer> source = Arrays.asList(1,2).iterator();
		final PeekIterator<Integer> it = new PeekIteratorWrapper<Integer>(source);

		// Pick the first element for a while
		for (int i = 1; i <= 10; i++)
		{
			Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
			assertEquals("Invalid peek value", Integer.valueOf(1), it.peek());
		}
		
		//  Move to next element
		Assert.assertEquals("Invalid hasNext value", true, it.hasNext());
		it.next();
		assertEquals("Invalid peek value", Integer.valueOf(2), it.peek());
	}
}
