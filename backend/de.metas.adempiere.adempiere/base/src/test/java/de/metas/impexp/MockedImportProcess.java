package de.metas.impexp;

import java.util.Properties;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.ImportDataDeleteRequest;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@Getter
@ToString
public class MockedImportProcess<ImportRecordType> implements IImportProcess<ImportRecordType>
{
	private static final Logger logger = LogManager.getLogger(MockedImportProcess.class);
	static
	{
		LogManager.setLoggerLevel(logger, Level.INFO);
	}

	private final Class<ImportRecordType> importModelClass;
	private final String importTableName;
	private final Supplier<ImportProcessResult> onRun;
	private final ToIntFunction<ImportDataDeleteRequest> onDeleteImportRecords;

	// Captured parameters
	private Properties ctx;
	private ClientId clientId;
	private IParams parameters;
	private TableRecordReferenceSet selectedRecordRefs;
	private ILoggable loggable;
	private PInstanceId selectionId;
	private boolean validateOnly;
	private boolean completeDocuments;

	// State
	private boolean runCalled;
	private boolean deleteImportRecordsCalled;

	@Builder
	private MockedImportProcess(
			@NonNull final Class<ImportRecordType> importModelClass,
			@Nullable final Supplier<ImportProcessResult> onRun,
			@Nullable final ToIntFunction<ImportDataDeleteRequest> onDeleteImportRecords)
	{
		this.importModelClass = importModelClass;
		this.importTableName = InterfaceWrapperHelper.getTableName(importModelClass);

		this.onRun = onRun;
		this.onDeleteImportRecords = onDeleteImportRecords;
	}

	@Override
	public IImportProcess<ImportRecordType> setCtx(Properties ctx)
	{
		this.ctx = ctx;
		return this;
	}

	@Override
	public IImportProcess<ImportRecordType> clientId(ClientId clientId)
	{
		this.clientId = clientId;
		return this;
	}

	@Override
	public IImportProcess<ImportRecordType> setParameters(IParams params)
	{
		this.parameters = params;
		return this;
	}

	@Override
	public IImportProcess<ImportRecordType> setLoggable(ILoggable loggable)
	{
		this.loggable = loggable;
		return this;
	}

	@Override
	public IImportProcess<ImportRecordType> selectedRecords(TableRecordReferenceSet selectedRecordRefs)
	{
		this.selectedRecordRefs = selectedRecordRefs;
		return this;
	}

	@Override
	public IImportProcess<ImportRecordType> selectedRecords(PInstanceId selectionId)
	{
		this.selectionId = selectionId;
		return this;
	}

	@Override
	public IImportProcess<ImportRecordType> validateOnly(boolean validateOnly)
	{
		this.validateOnly = validateOnly;
		return this;
	}

	@Override
	public IImportProcess<ImportRecordType> completeDocuments(boolean completeDocuments)
	{
		this.completeDocuments = completeDocuments;
		return this;
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
	public ImportProcessResult run()
	{
		if (runCalled)
		{
			throw new IllegalStateException("run() was already called: " + this);
		}
		runCalled = true;

		//
		if (onRun != null)
		{
			logger.info("Running on {}", this);
			return onRun.get();
		}
		else
		{
			throw new UnsupportedOperationException("onRun not set");
		}
	}

	@Override
	public int deleteImportRecords(ImportDataDeleteRequest request)
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
