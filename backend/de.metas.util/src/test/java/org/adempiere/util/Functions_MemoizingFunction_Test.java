package org.adempiere.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import org.adempiere.util.Functions.MemoizingFunction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.MoreObjects;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class Functions_MemoizingFunction_Test
{
	private TestFunction testFunc;
	private MemoizingFunction<Integer, String> memoizingFunction;

	@Before
	public void beforeTest()
	{
		testFunc = new TestFunction();
		memoizingFunction = null; // the actual test shall set it
	}

	@Test
	public void test_memoizing()
	{
		memoizingFunction = Functions.memoizing(testFunc);

		assertCallsCount(0);

		assertCallsIncrementedBy(1, Arrays.asList(1, 1, 1, 1, 1, 1, 1));
		assertCallsCount(1);

		assertCallsIncrementedBy(1, Arrays.asList(1, 2, 1, 2, 1, 2, 1));
		assertCallsCount(2);
	}

	@Test
	public void test_memoizingFirstCall()
	{
		memoizingFunction = Functions.memoizingFirstCall(testFunc);

		assertCallsCount(0);

		assertCallsIncrementedBy(1, Arrays.asList(1, 1, 1, 1, 1, 1, 1));
		assertCallsCount(1);

		assertCallsIncrementedBy(0, Arrays.asList(1, 2, 1, 2, 1, 2, 1));
		assertCallsCount(1);
	}

	@Test
	public void test_memoizingWithKeyFunction()
	{
		memoizingFunction = Functions.memoizing(testFunc, input -> input <= 5);

		assertCallsCount(0);

		assertCallsIncrementedBy(1, Arrays.asList(1, 1, 1, 1, 1, 1, 1));
		assertCallsCount(1);

		assertCallsIncrementedBy(0, Arrays.asList(1, 2, 1, 2, 1, 2, 1));
		assertCallsCount(1);

		assertCallsIncrementedBy(1, Arrays.asList(6, 7, 8, 9, 10));
		assertCallsCount(2);

		assertReturnValue("1", Arrays.asList(-100000, -1, 0, 1, 2, 3, 4, 5));
		assertReturnValue("6", Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 10004));
		assertCallsCount(2);
	}

	private final void assertCallsCount(final int expectedCallsCount)
	{
		Assert.assertEquals("Invalid callsCount for " + testFunc, expectedCallsCount, testFunc.callsCount);
	}

	private final void assertCallsIncrementedBy(final int expectedCallsCountIncrement, final Collection<Integer> inputs)
	{
		final int callsCountBefore = testFunc.callsCount;

		for (final Integer input : inputs)
		{
			memoizingFunction.apply(input);
		}

		final int callsCountAfter = testFunc.callsCount;
		final int actualCallsCountIncrement = callsCountAfter - callsCountBefore;

		Assert.assertEquals("Invalid callsCount increment for " + testFunc, expectedCallsCountIncrement, actualCallsCountIncrement);
	}

	private final void assertReturnValue(final String expectedValue, final Collection<Integer> inputs)
	{
		for (final Integer input : inputs)
		{
			Assert.assertEquals("Invalid return value for " + memoizingFunction, expectedValue, memoizingFunction.apply(input));
		}
	}

	private static final class TestFunction implements Function<Integer, String>
	{
		public int callsCount = 0;

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("callsCount", callsCount)
					.toString();
		}

		@Override
		public String apply(final Integer input)
		{
			callsCount++;
			return input == null ? null : String.valueOf(input);
		}
	}
}
