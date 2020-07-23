package de.metas.impexp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.api.IParams;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;

import com.google.common.base.Stopwatch;

import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.impexp.parser.ImpDataParser;
import de.metas.impexp.parser.ImpDataParserFactory;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
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
	private final IImportProcessFactory importProcessFactory;
	private final DataImportRunsService dataImportRunService;
	private final ImpDataParserFactory parserFactory = new ImpDataParserFactory();

	private static final String SYSCONFIG_InsertBatchSize = "de.metas.impexp.insertBatchSize";

	private final ClientId clientId;
	private final OrgId orgId;
	private final UserId userId;
	private final boolean completeDocuments;
	private final IParams additionalParameters;
	private final DataImportConfigId dataImportConfigId;
	private final ImpFormat importFormat;
	private final Resource data;

	//
	// State
	private DataImportRunId dataImportRunId = null;

	@Builder
	private DataImportCommand(
			@NonNull final IImportProcessFactory importProcessFactory,
			@NonNull final DataImportRunsService dataImportRunService,
			//
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final UserId userId,
			final boolean completeDocuments,
			final IParams additionalParameters,
			//
			@NonNull final DataImportConfigId dataImportConfigId,
			@NonNull final ImpFormat importFormat,
			//
			@NonNull final Resource data)
	{
		this.importProcessFactory = importProcessFactory;
		this.dataImportRunService = dataImportRunService;

		this.clientId = clientId;
		this.orgId = orgId;
		this.userId = userId;
		this.completeDocuments = completeDocuments;
		this.additionalParameters = CoalesceUtil.coalesce(additionalParameters, IParams.NULL);
		this.dataImportConfigId = dataImportConfigId;
		this.importFormat = importFormat;
		this.data = data;
	}

	public DataImportResult execute()
	{
		dataImportRunId = dataImportRunService.createNewRun(DataImportRunCreateRequest.builder()
				.orgId(orgId)
				.userId(userId)
				.completeDocuments(completeDocuments)
				.importFormatId(importFormat.getId())
				.dataImportConfigId(dataImportConfigId)
				.build());

		final ImportTableAppendResult insertResult = readSourceAndInsertIntoImportTable();
		logger.debug("Insert into import table result: {}", insertResult);

		final PInstanceId importRecordsSelectionId = createSelectionIdFromDataImportConfigId();
		final ImportProcessResult validateResult = validateImportRecords(importRecordsSelectionId);

		if (!importFormat.isManualImport())
		{
			scheduleToImportAsync(importRecordsSelectionId);
		}

		return DataImportResult.builder()
				.dataImportConfigId(dataImportConfigId)
				.importFormatName(importFormat.getName())
				.countSourceFileValidLines(insertResult.getCountValidRows())
				.countSourceFileErrorLines(insertResult.getCountRowsWithError())
				.importTableName(validateResult.getImportTableName())
				.countImportRecordsWithErrors(validateResult.getCountImportRecordsWithErrors().orElse(-1))
				.targetTableName(validateResult.getTargetTableName())
				.duration(validateResult.getDuration())
				.build();
	}

	private ImportTableAppendResult readSourceAndInsertIntoImportTable()
	{
		final ImpDataParser sourceParser = parserFactory.createParser(importFormat);

		final ImportTableAppender importTableAppender = ImportTableAppender.builder()
				.importFormat(importFormat)
				.clientId(clientId)
				.orgId(orgId)
				.userId(userId)
				.dataImportRunId(dataImportRunId)
				.dataImportConfigId(dataImportConfigId)
				.insertBatchSize(getInsertBatchSize())
				.build();

		return importTableAppender.appendStream(sourceParser.streamDataLines(data));
	}

	private int getInsertBatchSize()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_InsertBatchSize, -1);
	}

	private ImportProcessResult validateImportRecords(@NonNull final PInstanceId selectionId)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			return importProcessFactory.newImportProcessForTableName(importFormat.getImportTableName())
					.setCtx(Env.getCtx())
					.clientId(clientId)
					.validateOnly(true)
					.completeDocuments(completeDocuments)
					.setParameters(additionalParameters)
					.selectedRecords(selectionId)
					.run();
		}
		finally
		{
			stopwatch.stop();
			logger.debug("Validated import records of selectionId={} in {}", selectionId, stopwatch);
		}
	}

	private PInstanceId createSelectionIdFromDataImportConfigId()
	{
		Check.assumeNotNull(dataImportRunId, "dataImportRunId is not null");

		final ImportTableDescriptor importTableDescriptor = importFormat.getImportTableDescriptor();
		return Services.get(IQueryBL.class)
				.createQueryBuilder(importTableDescriptor.getTableName())
				.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_C_DataImport_Run_ID, dataImportRunId)
				.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_I_ErrorMsg, null)
				.create()
				.createSelection();
	}

	private void scheduleToImportAsync(final PInstanceId selectionId)
	{
		importProcessFactory
				.newAsyncImportProcessBuilder()
				.setCtx(Env.getCtx())
				.setImportTableName(importFormat.getImportTableName())
				.setImportFromSelectionId(selectionId)
				.setCompleteDocuments(completeDocuments)
				.buildAndEnqueue();
	}

}
