package de.metas.impexp.processing;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;

import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public abstract class SimpleImportProcessTemplate<ImportRecordType> extends ImportProcessTemplate<ImportRecordType>
{
	@Override
	protected final ImportGroupKey extractImportGroupKey(final ImportRecordType importRecord)
	{
		final int recordId = InterfaceWrapperHelper.getId(importRecord);
		return ImportGroupKey.of("importRecordId", recordId);
	}

	@Override
	protected final ImportGroupResult importRecords(
			final List<ImportRecordType> importRecords,
			final IMutable<Object> stateHolder) throws Exception
	{
		final ImportRecordType importRecord = CollectionUtils.singleElement(importRecords);
		ImportRecordResult result = importRecord(stateHolder, importRecord, isInsertOnly());
		if (result == ImportRecordResult.Inserted)
		{
			return ImportGroupResult.ONE_INSERTED;
		}
		else if (result == ImportRecordResult.Updated)
		{
			return ImportGroupResult.ONE_UPDATED;
		}
		else
		{
			return ImportGroupResult.ZERO;
		}
	}

	public enum ImportRecordResult
	{
		Inserted, Updated, Nothing,
	}

	protected abstract ImportRecordResult importRecord(
			@NonNull final IMutable<Object> stateHolder,
			@NonNull final ImportRecordType importRecord,
			final boolean isInsertOnly)
			throws Exception;
}
