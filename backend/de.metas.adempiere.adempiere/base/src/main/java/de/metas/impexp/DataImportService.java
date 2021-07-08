package de.metas.impexp;

import java.util.Optional;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Stopwatch;

import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.impexp.config.DataImportConfig;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.config.DataImportConfigRepository;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatRepository;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.processing.ImportDataDeleteRequest;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
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

@Service
public class DataImportService
{
	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.of("org.adempiere.impexp.async.RecordsImported", Type.REMOTE);

	private static final Logger logger = LogManager.getLogger(DataImportService.class);
	private final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final DataImportConfigRepository dataImportConfigsRepo;
	private final ImpFormatRepository importFormatsRepo;
	private final DataImportRunsService dataImportRunService;
	private final InsertIntoImportTableService insertIntoImportTableService;
	private final ImportRecordsAsyncExecutor importRecordsAsyncExecutor;

	private static final AdMessageKey MSG_Event_RecordsImported = AdMessageKey.of("org.adempiere.impexp.async.Event_RecordsImported");

	public DataImportService(
			@NonNull final DataImportConfigRepository dataImportConfigsRepo,
			@NonNull final ImpFormatRepository importFormatsRepo,
			@NonNull final DataImportRunsService dataImportRunService,
			@NonNull final InsertIntoImportTableService insertIntoImportTableService,
			@NonNull final Optional<ImportRecordsAsyncExecutor> importRecordsAsyncExecutor)
	{
		this.dataImportConfigsRepo = dataImportConfigsRepo;
		this.importFormatsRepo = importFormatsRepo;
		this.dataImportRunService = dataImportRunService;
		this.insertIntoImportTableService = insertIntoImportTableService;

		this.importRecordsAsyncExecutor = importRecordsAsyncExecutor.orElse(null);
		if (this.importRecordsAsyncExecutor != null)
		{
			logger.info("Async executor: {}", importRecordsAsyncExecutor);
		}
		else
		{
			logger.warn("Async executor: NOT FOUND");
		}
	}

	public Optional<DataImportConfig> getDataImportConfigByInternalName(@NonNull final String internalName)
	{
		return dataImportConfigsRepo.getByInternalName(internalName);
	}

	public DataImportResult importDataFromResource(@NonNull final DataImportRequest request)
	{
		final DataImportConfigId dataImportConfigId = request.getDataImportConfigId();
		final DataImportConfig dataImportConfig = dataImportConfigsRepo.getById(dataImportConfigId);
		final ImpFormat importFormat = importFormatsRepo.getById(dataImportConfig.getImpFormatId());

		return DataImportCommand.builder()
				.dataImportService(this)
				.importProcessFactory(importProcessFactory)
				.dataImportRunService(dataImportRunService)
				.insertIntoImportTableService(insertIntoImportTableService)
				//
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.userId(request.getUserId())
				.completeDocuments(request.isCompleteDocuments())
				.additionalParameters(request.getAdditionalParameters())
				//
				.dataImportConfigId(request.getDataImportConfigId())
				.importFormat(importFormat)
				//
				.data(request.getData())
				//
				.processImportRecordsSynchronously(request.isProcessImportRecordsSynchronously())
				.stopOnFirstError(request.isStopOnFirstError())
				//
				.build()
				//
				.execute();
	}

	ValidateImportRecordsResult validateImportRecords(@NonNull final ValidateImportRecordsRequest request)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			final ImportProcessResult processResult = importProcessFactory.newImportProcessForTableName(request.getImportTableName())
					.setCtx(Env.getCtx())
					.clientId(request.getClientId())
					.setParameters(request.getAdditionalParameters())
					.selectedRecords(request.getSelectionId())
					.validateOnly(true)
					.run();

			stopwatch.stop();

			return processResult.getImportRecordsValidation()
					.withDuration(TimeUtil.toDuration(stopwatch));
		}
		finally
		{
			logger.debug("Took {} to validate import records for {}", stopwatch, request);
		}
	}

	public ValidateAndActualImportRecordsResult validateAndImportRecordsNow(@NonNull final ImportRecordsRequest request)
	{
		final ImportProcessResult result = importProcessFactory.newImportProcessForTableName(request.getImportTableName())
				.setCtx(Env.getCtx())
				.setLoggable(Loggables.get())
				.selectedRecords(request.getSelectionId())
				.completeDocuments(request.isCompleteDocuments())
				.setParameters(request.getAdditionalParameters())
				.run();

		if (request.getNotifyUserId() != null)
		{
			notifyImportDone(result.getActualImport(), request.getNotifyUserId());
		}

		return ValidateAndActualImportRecordsResult.builder()
				.importRecordsValidation(result.getImportRecordsValidation())
				.actualImport(result.getActualImport())
				.build();
	}

	AsyncImportRecordsResponse importRecordsAsync(@NonNull final ImportRecordsRequest request)
	{
		if (importRecordsAsyncExecutor == null)
		{
			throw new AdempiereException("No " + ImportRecordsAsyncExecutor.class + " defined");
		}

		return importRecordsAsyncExecutor.schedule(request);
	}

	private void notifyImportDone(
			@NonNull final ActualImportRecordsResult result,
			@NonNull final UserId recipientUserId)
	{
		try
		{
			final String targetTableName = result.getTargetTableName();
			final String windowName = adTableDAO.retrieveWindowName(Env.getCtx(), targetTableName);

			notificationBL.send(UserNotificationRequest.builder()
					.topic(USER_NOTIFICATIONS_TOPIC)
					.recipientUserId(recipientUserId)
					.contentADMessage(MSG_Event_RecordsImported)
					.contentADMessageParam(result.getCountInsertsIntoTargetTableString())
					.contentADMessageParam(result.getCountUpdatesIntoTargetTableString())
					.contentADMessageParam(windowName)
					.build());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed notifying user '{}' about {}. Ignored.", recipientUserId, result, ex);
		}
	}

	public int deleteImportRecords(@NonNull final ImportDataDeleteRequest request)
	{
		return importProcessFactory.newImportProcessForTableName(request.getImportTableName())
				.setCtx(Env.getCtx())
				.setLoggable(Loggables.get())
				.setParameters(request.getAdditionalParameters())
				.deleteImportRecords(request);
	}
}
