/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.workpackage;

import ch.qos.logback.classic.Level;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED;

@Service
@RequiredArgsConstructor
class ModularContractLogHandler
{
	public static final Logger logger = LogManager.getLogger(ModularContractLogHandler.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull
	private final ModularContractLogHandlerRegistry handlerRegistry;
	@NonNull
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	@NonNull
	private final ModularContractLogDAO contractLogDAO;
	@NonNull
	private final ModularLogCreateStatusService createStatusService;
	@NonNull
	private final ModularContractLogService modularLogService;

	public <T> void handleLogs(@NonNull final List<IModularContractLogHandler.HandleLogsRequest<T>> requestList)
	{
		try
		{
			requestList.forEach(request -> handlerRegistry
					.streamHandlers(request)
					.forEach(handler -> {
						handleLogs(handler, request);
						createStatusService.setStatusSuccessfullyProcessed(request.getWorkPackageId(), request.getModelRef());
					}));
		}
		catch (final Throwable error)
		{
			// the current one will be rolled back
			trxManager.runInNewTrx(() -> requestList.forEach(request -> createStatusService
					.setStatusErrored(request.getWorkPackageId(), request.getModelRef(), error)));

			throw error;
		}
	}

	private <T> void handleLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request)
	{
		final Set<FlatrateTermId> contractIds = handler.getContractIds(request.getModel());
		if (contractIds.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No Contract provided by handler: {} for request: {}! no logs will be created!",
							handler.getClass().getName(),
							request);

			return;
		}

		contractIds.forEach(contractId -> handleLogs(handler, contractId, request));
	}

	private <T> void handleLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final FlatrateTermId contractId,
			@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request)
	{
		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(contractId);
		if (settings == null)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No ModularContractSettings found for contractId: {}! no logs will be created!", contractId);

			return;
		}

		final ModularContractTypeId contractTypeId = settings.getModularContractTypeId(handler.getModularContractTypeHandlerClass())
				.orElse(null);
		if (contractTypeId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No ModularContractTypeId found for contractId: {} and settingsId: {}! no logs will be created!", contractId, settings.getId());

			return;
		}

		final IModularContractLogHandler.LogAction action = handler.getLogAction(request);

		final Supplier<IModularContractLogHandler.CreateLogRequest<T>> buildCreateRequest = () -> IModularContractLogHandler.CreateLogRequest
				.<T>builder()
				.handleLogsRequest(request)
				.modularContractSettings(settings)
				.typeId(contractTypeId)
				.contractId(contractId)
				.build();

		switch (action)
		{
			case CREATE -> createLogs(handler, buildCreateRequest.get());
			case REVERSE -> reverseLogs(handler, request, contractId);
			case RECOMPUTE -> recreateLogs(handler, buildCreateRequest.get());
			default -> throw new AdempiereException("Unknown action: " + action);
		}
	}

	private <T> void createLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest<T> request)
	{
		createLogEntryCreateRequest(handler, request)
				.ifPresent(contractLogDAO::create)
				.ifAbsent(explanation -> Loggables.withLogger(logger, Level.DEBUG)
						.addLog("Method: {} | No logs created for request: {}! reason: {}!",
								"createLogs",
								request,
								explanation.getDefaultValue()));
	}

	private <T> void reverseLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request,
			@NonNull final FlatrateTermId contractId)
	{
		handler.createLogEntryReverseRequest(request, contractId)
				.ifPresent(contractLogDAO::reverse)
				.ifAbsent(explanation -> Loggables.withLogger(logger, Level.DEBUG)
						.addLog("Method: {} | No logs created for contractId: {} & request: {}! reason: {}!"
								, "reverseLogs",
								contractId,
								request,
								explanation.getDefaultValue()));
	}

	private <T> void recreateLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest<T> request)
	{
		modularLogService.throwErrorIfProcessedLogsExistForRecord(request.getHandleLogsRequest().getModelRef(), MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED);

		contractLogDAO.delete(handler.getDeleteRequestFor(request.getHandleLogsRequest(), request.getContractId()));

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("Method: {} | Logs were successfully deleted for request: {}!", "recreateLogs", request);

		createLogs(handler, request);
	}

	private <T> ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest<T> createLogRequest)
	{
		final BooleanWithReason areLogsRequired = handler.doesRecordRequireLogCreation(createLogRequest);

		if (areLogsRequired.isFalse())
		{
			return ExplainedOptional.emptyBecause(areLogsRequired.getReason());
		}

		return handler.createLogEntryCreateRequest(createLogRequest);
	}
}
