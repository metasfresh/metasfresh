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
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED;

@Service
@RequiredArgsConstructor
class ModularContractLogHandler
{
	public static final Logger logger = LogManager.getLogger(ModularContractLogHandler.class);

	@NonNull
	private final ModularContractLogHandlerRegistry handlerRegistry;
	@NonNull
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	@NonNull
	private final ModularContractLogDAO contractLogDAO;
	@NonNull
	private final ModularContractLogService modularLogService;

	public <T> void handleLogs(@NonNull final List<IModularContractLogHandler.HandleLogsRequest<T>> requestList)
	{
		requestList.forEach(request -> handlerRegistry
				.streamHandlers(request)
				.forEach(handler -> handleLogs(handler, request)));

	}

	private <T> void handleLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request)
	{
		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(request.getContractId());
		if (settings == null)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No ModularContractSettings found for contractId: {}! no logs will be created!", request.getContractId());

			return;
		}

		final ModuleConfig moduleConfig = settings.getModuleConfig(handler.getModularContractTypeHandlerClass())
				.orElse(null);
		if (moduleConfig == null)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No ModuleConfig found for contractId: {} and settingsId: {}! no logs will be created!", request.getContractId(), settings.getId());

			return;
		}

		final IModularContractLogHandler.LogAction action = handler.getLogAction(request);

		final Supplier<IModularContractLogHandler.CreateLogRequest<T>> buildCreateRequest = () -> IModularContractLogHandler.CreateLogRequest
				.<T>builder()
				.handleLogsRequest(request)
				.modularContractSettings(settings)
				.configId(moduleConfig.getId())
				.typeId(moduleConfig.getModularContractType().getId())
				.build();

		switch (action)
		{
			case CREATE -> createLogs(handler, buildCreateRequest.get());
			case REVERSE -> reverseLogs(handler, request);
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
			@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request)
	{
		handler.createLogEntryReverseRequest(request)
				.ifPresent(contractLogDAO::reverse)
				.ifAbsent(explanation -> Loggables.withLogger(logger, Level.DEBUG)
						.addLog("Method: {} | No logs created for contractId: {} & request: {}! reason: {}!"
								, "reverseLogs",
								request.getContractId(),
								request,
								explanation.getDefaultValue()));
	}

	private <T> void recreateLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest<T> request)
	{
		modularLogService.throwErrorIfProcessedLogsExistForRecord(request.getHandleLogsRequest().getModelRef(), MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED);

		contractLogDAO.delete(handler.getDeleteRequestFor(request.getHandleLogsRequest()));

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("Method: {} | Logs were successfully deleted for request: {}!", "recreateLogs", request);

		createLogs(handler, request);
	}

	private <T> ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest<T> createLogRequest)
	{
		final BooleanWithReason areLogsRequired = handler.doesRecordStateRequireLogCreation(
				createLogRequest.getHandleLogsRequest().getModel());

		if (areLogsRequired.isFalse())
		{
			return ExplainedOptional.emptyBecause(areLogsRequired.getReason());
		}

		return handler.createLogEntryCreateRequest(createLogRequest);
	}
}
