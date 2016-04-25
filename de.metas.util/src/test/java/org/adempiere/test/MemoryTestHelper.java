package org.adempiere.test;

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


import java.math.BigDecimal;

import org.junit.Assert;

/**
 * Helper class used to test memory informations while the tests run.
 * 
 * It is mainly used to deletect memory leaks.
 * 
 * @author tsa
 *
 */
public class MemoryTestHelper
{
	private MemoryInfoSnapshot currentMemorySnapshot;

	public MemoryTestHelper()
	{
		super();
		currentMemorySnapshot = new MemoryInfoSnapshot();
	}

	/**
	 * Runs the garbage collector (10 times, to make sure it actually collected everything)
	 */
	public void runGarbageCollector()
	{
		// NOTE: there is no guarantee that when running GC, it will actually garbage collect.
		// But empirically it was observed that running it at least 10 times, it's a big change to actually garbage collect everything.

		for (int i = 1; i <= 10; i++)
		{
			System.gc();
		}
	}

	public MemoryInfoSnapshot newMemorySnapshot()
	{
		return new MemoryInfoSnapshot();
	}

	public MemoryInfoSnapshot newMemorySnapshotAndSetAsCurrent()
	{
		currentMemorySnapshot = newMemorySnapshot();
		return currentMemorySnapshot;
	}

	public MemoryInfoSnapshot currentMemorySnapshot()
	{
		return currentMemorySnapshot;
	}

	public BigDecimal getDeltaUsageFromLastTime()
	{
		final MemoryInfoSnapshot memorySnapshotNow = new MemoryInfoSnapshot();
		return memorySnapshotNow.calculateDeltaUsageFrom(currentMemorySnapshot);
	}

	/**
	 * Assert memory usage% was not increased, compared with last snapshot that we have
	 *
	 * @param memorySnapshotNow
	 * @param usageIncreasePercentTolerance
	 */
	public void assertMemoryUsageNotIncreased(final String message,
			final MemoryInfoSnapshot memorySnapshotNow,
			final BigDecimal usageIncreasePercentTolerance)
	{
		final MemoryInfoSnapshot memorySnapshotReference = this.currentMemorySnapshot;
		final BigDecimal usageDelta = memorySnapshotNow.calculateDeltaUsageFrom(memorySnapshotReference);

		// If deltaUsage is negative, it means the memory usage decreased, so we are fine
		if (usageDelta.signum() <= 0)
		{
			return;
		}

		// If delta usage is smaller than our tollerance then we are fine
		if (usageDelta.compareTo(usageIncreasePercentTolerance) <= 0)
		{
			return;
		}

		final String messageToUse = "Memory usage increased more then expected: " + message
				+ "\n       Memory info (now): " + memorySnapshotNow
				+ "\n Memory info (reference): " + memorySnapshotReference
				+ "\n            Usage delta%: " + usageDelta
				+ "\n             Tollerance%: " + usageIncreasePercentTolerance;

		Assert.fail(messageToUse);
	}
}
