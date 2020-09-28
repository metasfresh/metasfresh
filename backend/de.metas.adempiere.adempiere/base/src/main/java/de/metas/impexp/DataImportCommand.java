package de.metas.impexp;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.compiere.model.IQuery;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;

import de.metas.common.util.time.SystemTime;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.impexp.parser.ImpDataParser;
import de.metas.impexp.parser.ImpDataParserFactory;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
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

final class DataImportCommand
{
	private static final Logger logger = LogManager.getLogger(DataImportCommand.class);

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final DataImportService dataImportService;
	private final DataImportRunsService dataImportRunService;
	private final InsertIntoImportTableService insertIntoImportTableService;
	private final ImpDataParserFactory parserFactory = new ImpDataParserFactory();

	private static final String SYSCONFIG_InsertBatchSize = "de.metas.impexp.insertBatchSize";

	//
	// Parameters
	private final ClientId clientId;
	private final OrgId orgId;
	private final UserId userId;
	private final boolean completeDocuments;
	private final Params additionalParameters;
	private final DataImportConfigId dataImportConfigId;
	private final ImpFormat importFormat;
	private final Resource data;
	private final boolean processImportRecordsSynchronously;
	private final boolean stopOnFirstError;

	//
	// State
	private Instant startTime = null;
	private DataImportRunId _dataImportRunId = null;
	private PInstanceId _recordsToImportSelectionId = null;
	private InsertIntoImportTableResult insertIntoImportTableResult;
	private ValidateImportRecordsResult validationResult;
	private ActualImportRecordsResult actualImportResult;
	private AsyncImportRecordsResponse asyncImportResult;

	@Builder
	private DataImportCommand(
			@NonNull final DataImportService dataImportService,
			@NonNull final IImportProcessFactory importProcessFactory,
			@NonNull final DataImportRunsService dataImportRunService,
			@NonNull final InsertIntoImportTableService insertIntoImportTableService,
			//
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final UserId userId,
			final boolean completeDocuments,
			@Nullable final IParams additionalParameters,
			//
			@NonNull final DataImportConfigId dataImportConfigId,
			@NonNull final ImpFormat importFormat,
			//
			@NonNull final Resource data,
			//
			final boolean processImportRecordsSynchronously,
			final boolean stopOnFirstError)
	{
		this.dataImportService = dataImportService;
		this.dataImportRunService = dataImportRunService;
		this.insertIntoImportTableService = insertIntoImportTableService;

		this.clientId = clientId;
		this.orgId = orgId;
		this.userId = userId;
		this.completeDocuments = completeDocuments;
		this.additionalParameters = additionalParameters != null
				? Params.copyOf(additionalParameters)
				: Params.EMPTY;
		this.dataImportConfigId = dataImportConfigId;
		this.importFormat = importFormat;
		this.data = data;
		this.processImportRecordsSynchronously = processImportRecordsSynchronously;
		this.stopOnFirstError = stopOnFirstError;
	}

	public DataImportResult execute()
	{
		startTime = SystemTime.asInstant();

		insertIntoImportTableResult = readSourceAndInsertIntoImportTable();
		if (stopOnFirstError && insertIntoImportTableResult.hasErrors())
		{
			return createResult();
		}

		if (importFormat.isManualImport())
		{
			validationResult = dataImportService.validateImportRecords(requestToValidateImportRecords());
			if (stopOnFirstError && validationResult.hasErrors())
			{
				return createResult();
			}
		}
		else
		{
			if (processImportRecordsSynchronously)
			{
				final ValidateAndActualImportRecordsResult processResult = dataImportService.validateAndImportRecordsNow(requestToActuallyImportRecords());
				validationResult = processResult.getImportRecordsValidation();
				if (stopOnFirstError && validationResult.hasErrors())
				{
					return createResult();
				}

				actualImportResult = processResult.getActualImport();
				if (stopOnFirstError && actualImportResult.hasErrors())
				{
					return createResult();
				}
			}
			else
			{
				validationResult = dataImportService.validateImportRecords(requestToValidateImportRecords());
				if (stopOnFirstError && validationResult.hasErrors())
				{
					return createResult();
				}

				asyncImportResult = dataImportService.importRecordsAsync(requestToActuallyImportRecords());
			}
		}

		return createResult();
	}

	private ValidateImportRecordsRequest requestToValidateImportRecords()
	{
		return ValidateImportRecordsRequest.builder()
				.importTableName(importFormat.getImportTableName())
				.selectionId(getOrCreateRecordsToImportSelectionId())
				.clientId(clientId)
				.additionalParameters(additionalParameters)
				.build();
	}

	private ImportRecordsRequest requestToActuallyImportRecords()
	{
		return ImportRecordsRequest.builder()
				.importTableName(importFormat.getImportTableName())
				.selectionId(getOrCreateRecordsToImportSelectionId())
				.notifyUserId(userId)
				.completeDocuments(completeDocuments)
				.additionalParameters(additionalParameters)
				.build();
	}

	private DataImportRunId getOrCreateDataImportRunId()
	{
		if (_dataImportRunId == null)
		{
			_dataImportRunId = dataImportRunService.createNewRun(DataImportRunCreateRequest.builder()
					.orgId(orgId)
					.userId(userId)
					.completeDocuments(completeDocuments)
					.importFormatId(importFormat.getId())
					.dataImportConfigId(dataImportConfigId)
					.build());
		}
		return _dataImportRunId;
	}

	private InsertIntoImportTableResult readSourceAndInsertIntoImportTable()
	{
		final ImpDataParser sourceParser = parserFactory.createParser(importFormat);

		final InsertIntoImportTableRequest request = InsertIntoImportTableRequest.builder()
				.importFormat(importFormat)
				.clientId(clientId)
				.orgId(orgId)
				.userId(userId)
				.dataImportRunId(getOrCreateDataImportRunId())
				.dataImportConfigId(dataImportConfigId)
				.insertBatchSize(getInsertBatchSize())
				.stream(sourceParser.streamDataLines(data))
				.build();

		final InsertIntoImportTableResult result = insertIntoImportTableService.insertData(request)
				.withFromResource(extractURI(data));
		logger.debug("Insert into import table result: {}", result);

		return result;
	}

	@Nullable
	private static URI extractURI(final Resource resource)
	{
		try
		{
			return resource.getURI();
		}
		catch (IOException e)
		{
			return null;
		}
	}

	private int getInsertBatchSize()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_InsertBatchSize, -1);
	}

	private PInstanceId getOrCreateRecordsToImportSelectionId()
	{
		if (_recordsToImportSelectionId == null)
		{
			final ImportTableDescriptor importTableDescriptor = importFormat.getImportTableDescriptor();
			final DataImportRunId dataImportRunId = getOrCreateDataImportRunId();

			final IQuery<Object> query = queryBL.createQueryBuilder(importTableDescriptor.getTableName())
					.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_C_DataImport_Run_ID, dataImportRunId)
					.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_I_ErrorMsg, null)
					.create();

			_recordsToImportSelectionId = query.createSelection();
			if (_recordsToImportSelectionId == null)
			{
				throw new AdempiereException("No records to import for " + query);
			}
		}

		return _recordsToImportSelectionId;
	}

	private DataImportResult createResult()
	{
		final Duration duration = Duration.between(startTime, SystemTime.asInstant());

		return DataImportResult.builder()
				.dataImportConfigId(dataImportConfigId)
				.duration(duration)
				//
				.insertIntoImportTable(insertIntoImportTableResult)
				.importRecordsValidation(validationResult)
				.actualImport(actualImportResult)
				.asyncImportResult(asyncImportResult)
				//
				.build();
	}
}
