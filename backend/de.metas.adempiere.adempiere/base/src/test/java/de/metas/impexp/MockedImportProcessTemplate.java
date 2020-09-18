package de.metas.impexp;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.ToIntFunction;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;

import de.metas.impexp.processing.ImportDataDeleteRequest;
import de.metas.impexp.processing.ImportGroupKey;
import de.metas.impexp.processing.ImportGroupResult;
import de.metas.impexp.processing.ImportProcessTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class MockedImportProcessTemplate<ImportRecordType> extends ImportProcessTemplate<ImportRecordType>
{
	private final Class<ImportRecordType> importModelClass;
	private final String importTableName;
	private final String targetTableName;
	private final OnImportRecords<ImportRecordType> onImportRecords;
	private final ToIntFunction<ImportDataDeleteRequest> onDeleteImportRecords;

	// State
	@Getter
	private boolean deleteImportRecordsCalled;
	@Getter
	private int importRecordsCalledCount;
	@Getter
	private boolean updateAndValidateRecordsCalled;

	//
	//
	//
	@FunctionalInterface
	public static interface OnImportRecords<ImportRecordType>
	{
		ImportGroupResult importRecords(List<ImportRecordType> importRecords, IMutable<Object> stateHolder);
	}

	@Builder
	private MockedImportProcessTemplate(
			@NonNull final Class<ImportRecordType> importModelClass,
			@NonNull final String targetTableName,
			@Nullable final OnImportRecords<ImportRecordType> onImportRecords,
			@Nullable final ToIntFunction<ImportDataDeleteRequest> onDeleteImportRecords)
	{
		this.importModelClass = importModelClass;
		this.importTableName = InterfaceWrapperHelper.getTableName(importModelClass);

		this.targetTableName = targetTableName;

		this.onImportRecords = onImportRecords;
		this.onDeleteImportRecords = onDeleteImportRecords;
	}

	@Override
	public Class<ImportRecordType> getImportModelClass()
	{
		return importModelClass;
	}

	@Override
	public String getImportTableName()
	{
		return importTableName;
	}

	@Override
	protected String getTargetTableName()
	{
		return targetTableName;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		updateAndValidateRecordsCalled = true;
	}

	@Override
	protected Iterator<ImportRecordType> retrieveRecordsToImport()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	protected ImportGroupKey extractImportGroupKey(ImportRecordType importRecord)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getImportOrderBySql()
	{
		throw new UnsupportedOperationException("shall not be called");
	}

	@Override
	protected ImportRecordType retrieveImportRecord(Properties ctx, ResultSet rs)
	{
		throw new UnsupportedOperationException("shall not be called");
	}

	@Override
	protected ImportGroupResult importRecords(
			List<ImportRecordType> importRecords,
			IMutable<Object> stateHolder)
	{
		importRecordsCalledCount++;

		if (onImportRecords != null)
		{
			return onImportRecords.importRecords(importRecords, stateHolder);
		}
		else
		{
			throw new UnsupportedOperationException("onImportRecords not set");
		}
	}

	@Override
	public int deleteImportRecords(@NonNull final ImportDataDeleteRequest request)
	{
		if (deleteImportRecordsCalled)
		{
			throw new IllegalStateException("deleteImportRecords() was already called: " + this);
		}
		deleteImportRecordsCalled = true;

		//
		if (onDeleteImportRecords != null)
		{
			return onDeleteImportRecords.applyAsInt(request);
		}
		else
		{
			return 0;
		}
	}
}
