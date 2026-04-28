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
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import de.metas.util.collections.PeekIterator;
import de.metas.util.collections.PeekIteratorWrapper;

public class PeekIteratorWrapperTest
{

	@Test
	public void test_simple()
	{
		final Iterator<Integer> source = Arrays.asList(1, 2, 3).iterator();
		final PeekIterator<Integer> it = new PeekIteratorWrapper<Integer>(source);

		assertThat(it.peek()).as("Iterator peek value").isEqualTo(Integer.valueOf(1));

		assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
		assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(1));

		assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
		assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(1));
		assertThat(it.next()).as("Invalid next value").isEqualTo(Integer.valueOf(1));

		assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
		assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(2));
		assertThat(it.next()).as("Invalid peek value").isEqualTo(Integer.valueOf(2));

		assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
		assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(3));
		assertThat(it.next()).as("Invalid peek value").isEqualTo(Integer.valueOf(3));

		assertThat(it.hasNext()).as("Invalid hasNext").isFalse();
	}

	@Test
	public void test_emptyIterator()
	{
		final Iterator<Integer> source = new ArrayList<Integer>().iterator();
		final PeekIterator<Integer> it = new PeekIteratorWrapper<Integer>(source);

		assertThat(it.hasNext()).as("Invalid hasNext").isFalse();
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

		assertThat(found).as("Item not found").isTrue();
		
		assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
		assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(4));
		assertThat(it.next()).as("Invalid peek value").isEqualTo(Integer.valueOf(4));
		
		assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
		assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(5));
		assertThat(it.next()).as("Invalid peek value").isEqualTo(Integer.valueOf(5));

		assertThat(it.hasNext()).as("Invalid hasNext value").isFalse();
	}
	
	@Test
	public void test_oneElement_KeepPeeking()
	{
		final Iterator<Integer> source = Arrays.asList(1,2).iterator();
		final PeekIterator<Integer> it = new PeekIteratorWrapper<Integer>(source);

		// Pick the first element for a while
		for (int i = 1; i <= 10; i++)
		{
			assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
			assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(1));
		}
		
		//  Move to next element
		assertThat(it.hasNext()).as("Invalid hasNext value").isTrue();
		it.next();
		assertThat(it.peek()).as("Invalid peek value").isEqualTo(Integer.valueOf(2));
	}
}