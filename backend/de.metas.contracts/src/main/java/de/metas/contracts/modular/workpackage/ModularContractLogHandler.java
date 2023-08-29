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
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.status.ModularLogCreateStatus;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.contracts.modular.log.status.ProcessingStatus;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
class ModularContractLogHandler
{
	public static final Logger logger = LogManager.getLogger(ModularContractLogHandler.class);

	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@NonNull
	private final ModularContractLogHandlerRegistry handlerRegistry;
	@NonNull
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	@NonNull
	private final ModularContractLogDAO contractLogDAO;
	@NonNull
	private final ModularLogCreateStatusService createStatusService;

	public <T> void handleLogs(@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request)
	{
		try
		{
			handlerRegistry.streamHandlers(request).forEach(handler -> handleLogs(handler, request));

			createStatusService.save(ModularLogCreateStatus.builder()
											 .workPackageId(request.getWorkPackageId())
											 .recordReference(TableRecordReference.of(request.getModel()))
											 .status(ProcessingStatus.SUCCESSFULLY_PROCESSED)
											 .build());
		}
		catch (final Throwable t)
		{
			final AdIssueId adIssueId = errorManager.createIssue(t);

			createStatusService.save(ModularLogCreateStatus.builder()
											 .workPackageId(request.getWorkPackageId())
											 .recordReference(TableRecordReference.of(request.getModel()))
											 .status(ProcessingStatus.ERRORED)
											 .issueId(adIssueId)
											 .build());
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
					.addLog("No ModularContractTypeId found for contractId: {} and settingsId: {}! no logs will be created!",
							contractId, settings.getId());

			return;
		}

		final Supplier<IModularContractLogHandler.CreateLogRequest<T>> buildCreateLogRequest = () -> IModularContractLogHandler.CreateLogRequest
				.<T>builder()
				.handleLogsRequest(request)
				.modularContractSettings(settings)
				.typeId(contractTypeId)
				.contractId(contractId)
				.build();

		final IModularContractLogHandler.LogAction action = handler.getLogAction(request);

		switch (action)
		{
			case CREATE -> createLogs(handler, buildCreateLogRequest.get());
			case REVERSE -> reverseLogs(handler, request, contractId);
			default -> throw new AdempiereException("Unknown action!")
					.appendParametersToMessage()
					.setParameter("Action", action);
		}
	}

	private <T> void createLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest<T> request)
	{
		final ExplainedOptional<LogEntryCreateRequest> createLogEntryReq = handler.createLogEntryCreateRequest(request);

		if (!createLogEntryReq.isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("Method: {} | No logs created for request: {}! reason: {}!",
							"createLogs",
							request,
							createLogEntryReq.getExplanation().toString());
			return;
		}

		contractLogDAO.create(createLogEntryReq.get());
	}

	private <T> void reverseLogs(
			@NonNull final IModularContractLogHandler<T> handler,
			@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request,
			@NonNull final FlatrateTermId contractId)
	{
		final ExplainedOptional<LogEntryReverseRequest> reverseLogEntryReq = handler.createLogEntryReverseRequest(request, contractId);

		if (!reverseLogEntryReq.isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("Method: {} | No logs created for contractId: {} & request: {}! reason: {}!"
							, "reverseLogs",
							contractId,
							request,
							reverseLogEntryReq.getExplanation().toString());

			return;
		}

		contractLogDAO.reverse(reverseLogEntryReq.get());
	}
}
