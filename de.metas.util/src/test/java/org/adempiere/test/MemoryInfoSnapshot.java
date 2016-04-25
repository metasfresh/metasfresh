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
import java.math.RoundingMode;
import java.util.Date;

/**
 * Memory Total/Free/Used snapshot
 *
 * @author tsa
 *
 */
public final class MemoryInfoSnapshot
{
	private static final transient BigDecimal CONSTANT_MbInBytes = BigDecimal.valueOf(1024 * 1024);
	private final BigDecimal totalBytes;
	private final BigDecimal freeBytes;
	private final BigDecimal usedBytes;
	private final BigDecimal usedPercent;
	private final Date snapshotTS;

	MemoryInfoSnapshot()
	{
		super();

		totalBytes = BigDecimal.valueOf(Runtime.getRuntime().totalMemory());
		freeBytes = BigDecimal.valueOf(Runtime.getRuntime().freeMemory());
		usedBytes = totalBytes.subtract(freeBytes);
		usedPercent = usedBytes.multiply(BigDecimal.valueOf(100))
				.divide(totalBytes, 2, RoundingMode.HALF_UP);

		snapshotTS = new Date();
	}

	@Override
	public String toString()
	{
		return "Memory usage: " + getUsedPercent() + "%"
				+ " ("
				+ "Used=" + getUsedMb() + "Mb"
				+ ", Total=" + getTotalMb() + "Mb"
				+ ", Timestamp=" + snapshotTS
				+ ")";
	}

	public BigDecimal getTotalMb()
	{
		return convertToMb(totalBytes, 2);
	}

	public BigDecimal getFreeMb()
	{
		return convertToMb(freeBytes, 2);
	}

	public BigDecimal getUsedMb()
	{
		return convertToMb(usedBytes, 2);
	}

	public BigDecimal getUsedPercent()
	{
		return usedPercent;
	}

	private static final BigDecimal convertToMb(final BigDecimal valueBytes, final int scale)
	{
		return valueBytes.divide(CONSTANT_MbInBytes, scale, RoundingMode.HALF_UP);
	}

	public BigDecimal calculateDeltaUsageFrom(final MemoryInfoSnapshot fromMemorySnapshot)
	{
		return usedPercent.subtract(fromMemorySnapshot.getUsedPercent());
	}
}
