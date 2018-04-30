package org.adempiere.impexp;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * The result of an {@link IImportProcess} execution.
 * 
 * @author tsa
 *
 */
@ToString
public class ImportProcessResult
{
	public static final ImportProcessResult newInstance(@NonNull final String targetTableName)
	{
		return new ImportProcessResult(targetTableName);
	}

	/** target table name, where the records were imported (e.g. C_BPartner) */
	@Getter
	private final String targetTableName;
	private final AtomicInteger insertCount = new AtomicInteger(0);
	private final AtomicInteger updateCount = new AtomicInteger(0);
	private final AtomicInteger errorCount = new AtomicInteger(0);

	private ImportProcessResult(@NonNull final String targetTableName)
	{
		this.targetTableName = targetTableName;
	}

	void incrementInsertCounter()
	{
		insertCount.incrementAndGet();
	}

	/**
	 * @return how many records were inserted into target table
	 */
	public int getInsertCount()
	{
		return insertCount.get();
	}

	void incrementUpdateCounter()
	{
		insertCount.incrementAndGet();
	}

	/**
	 * @return how many records were updated on target table
	 */
	public int getUpdateCount()
	{
		return updateCount.get();
	}

	void setErrorCount(final int errorCount)
	{
		this.errorCount.set(errorCount);
	}

	/**
	 * @return how many records where import errors were uncounted are
	 */
	public int getErrorCount()
	{
		return errorCount.get();
	}
}
