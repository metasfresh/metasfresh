package org.adempiere.util.lang;

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


import org.junit.Assert;
import org.junit.Test;

public class LazyInitializerTest
{
	private static class MockedLazyInitializer<T> extends LazyInitializer<T>
	{
		private final T valueToInitialize;
		private int initializeCallsCount = 0;

		public MockedLazyInitializer(final T valueToInitialize)
		{
			super();
			this.valueToInitialize = valueToInitialize;
		}

		@Override
		protected T initialize()
		{
			initializeCallsCount++;
			return valueToInitialize;
		}

		public int getInitializeCallsCount()
		{
			return initializeCallsCount;
		}
	}

	/**
	 * Make sure that calling {@link LazyInitializer#toString()} won't initialize the reference
	 */
	@Test
	public void test_toString_shall_not_initialize()
	{
		final MockedLazyInitializer<String> ref = new MockedLazyInitializer<String>("initValue");

		ref.toString();

		Assert.assertEquals("ref.toString() shall not initalize the lazy reference", false, ref.isInitialized());
		Assert.assertEquals("ref.toString() shall not call getValue()", 0, ref.getInitializeCallsCount());
	}

	/**
	 * Make sure that calling {@link LazyInitializer#hashCode()} won't initialize the reference.
	 * 
	 * NOTE: maybe this is a subject to change, but atm we enforce this
	 */
	@Test
	public void test_hashCode_shall_not_initialize()
	{
		final MockedLazyInitializer<String> ref = new MockedLazyInitializer<String>("initValue");

		ref.hashCode();

		Assert.assertEquals("ref hashCode() not initalize the lazy reference", false, ref.isInitialized());
		Assert.assertEquals("ref.hashCode() shall not call getValue()", 0, ref.getInitializeCallsCount());
	}

	/**
	 * Make sure that calling {@link LazyInitializer#hashCode()} won't initialize the reference.
	 * 
	 * NOTE: maybe this is a subject to change, but atm we enforce this
	 */
	@Test
	public void test_equals_shall_not_initialize()
	{
		final MockedLazyInitializer<String> ref1 = new MockedLazyInitializer<String>("initValue1");
		final MockedLazyInitializer<String> ref2 = new MockedLazyInitializer<String>("initValue1");

		final boolean equals = ref1.equals(ref2);
		Assert.assertEquals("ref1 shall not be equal with ref2 (comparation shall be made by reference only)", false, equals);

		Assert.assertEquals("ref.equals() shall not initalize the lazy reference", false, ref1.isInitialized());
		Assert.assertEquals("ref.equals() shall not call getValue()", 0, ref1.getInitializeCallsCount());
		Assert.assertEquals("ref.equals() shall not initalize the lazy reference", false, ref2.isInitialized());
		Assert.assertEquals("ref.equals() shall not call getValue()", 0, ref2.getInitializeCallsCount());
	}

	@Test
	public void test_StandardCase()
	{
		final String valueExpected = "initValue";
		final MockedLazyInitializer<String> ref = new MockedLazyInitializer<String>(valueExpected);

		//
		// Check initial state
		Assert.assertEquals("Reference shall not be initalized: " + ref, false, ref.isInitialized());
		Assert.assertEquals("Invalid initializer calls count", 0, ref.getInitializeCallsCount());

		//
		// Get the value (first time)
		{
			final String valueActual = ref.getValue();
			Assert.assertEquals("Invalid reference value (compared by equals)", valueExpected, valueActual);
			Assert.assertSame("Invalid reference value (compared by ==)", valueExpected, valueActual);
			Assert.assertEquals("Invalid initializer calls count", 1, ref.getInitializeCallsCount());
		}

		//
		// Get the value (second time...)
		// The initialize() method shall not be called never-ever after first time
		for (int i = 1; i <= 10; i++)
		{
			final String valueActual = ref.getValue();
			Assert.assertEquals("Invalid reference value (compared by equals)", valueExpected, valueActual);
			Assert.assertSame("Invalid reference value (compared by ==)", valueExpected, valueActual);
			Assert.assertEquals("Invalid initializer calls count", 1, ref.getInitializeCallsCount());
		}
	}
}
