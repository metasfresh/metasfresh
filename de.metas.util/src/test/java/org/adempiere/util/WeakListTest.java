/**
 *
 */
package org.adempiere.util;

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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tsa
 *
 */
public class WeakListTest
{
	@Test
	public void test_AddSomeValuesAndWaitToExpire() throws Exception
	{
		final WeakList<String> list = new WeakList<String>();
		for (int i = 1; i <= 10; i++)
		{
			list.add("s" + i);
		}

		// Trigger garbage collection
		System.gc();

		//
		// Try again several times to see if our list gets empty
		waitExpireAll(list);
	}

	private void waitExpireAll(final WeakList<?> list) throws InterruptedException
	{
		int attempt = 0;
		final int maxAttempts = 10;
		while (!list.isEmpty())
		{
			attempt++;
			if (attempt > maxAttempts)
			{
				break;
			}

			Thread.sleep(100);
			System.gc();
		}

		Assert.assertTrue("List should be empty after " + maxAttempts + " attempts: " + list, list.isEmpty());
		System.out.println("List expired after " + attempt + " attempts");
	}

	@Test
	public void testRemove()
	{
		final DummyTestObject value1 = new DummyTestObject("value1");
		final DummyTestObject value2 = new DummyTestObject("value2");
		final DummyTestObject value3 = new DummyTestObject("value3");

		final WeakList<DummyTestObject> list = new WeakList<>();
		list.add(value1);
		list.add(value2);
		list.add(value3);
		Assert.assertEquals("Weak list shall contain exactly the same elements as our hard list",
				Arrays.asList(value1, value2, value3),
				list);

		// Remove by using the exact object reference
		list.remove(value2);
		Assert.assertEquals("Invalid weak list content",
				Arrays.asList(value1, value3),
				list);

		// Remove by using the an object which equals with an object from our list
		list.remove(new DummyTestObject("value3"));
		Assert.assertEquals("Invalid weak list content",
				Arrays.asList(value1),
				list);
	}

	@Test
	public void test_addIfAbsent() throws Exception
	{
		DummyTestObject value1 = new DummyTestObject("value");
		DummyTestObject value2 = new DummyTestObject("value");

		final WeakList<DummyTestObject> list = new WeakList<>(true);
		Assert.assertEquals(true, list.addIfAbsent(value1));
		Assert.assertEquals(false, list.addIfAbsent(value2));
		Assert.assertEquals("Invalid weak list content",
				Arrays.asList(value1),
				list);

		value1 = null;
		value2 = null;
		// Trigger garbage collection
		System.gc();

		waitExpireAll(list);
	}

	/**
	 * Dummy test object to be used for testing {@link WeakList}.
	 *
	 * NOTE: this object implements {@link #hashCode()} and {@link #equals(Object)} which are checking the underlying name.
	 *
	 * @author tsa
	 *
	 */
	private static final class DummyTestObject
	{
		private final String name;

		public DummyTestObject(final String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return "DummyTestObject[" + name + "]";
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + (name == null ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final DummyTestObject other = (DummyTestObject)obj;
			if (name == null)
			{
				if (other.name != null)
				{
					return false;
				}
			}
			else if (!name.equals(other.name))
			{
				return false;
			}
			return true;
		}
	}
}
