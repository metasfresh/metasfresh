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

import com.google.common.collect.ImmutableList;
import de.metas.common.util.Check;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ModularContractLogHandlerRegistry
{
	@NonNull private final List<IModularContractLogHandler> handlers;
	@NonNull private final ModularContractSettingsBL modularContractSettingsBL;

	@NonNull
	Stream<IModularContractLogHandler> streamHandlers(@NonNull final IModularContractLogHandler.HandleLogsRequest request)
	{
		return handlers.stream().filter(handler -> applies(handler, request));
	}

	private boolean applies(@NonNull final IModularContractLogHandler handler, @NonNull final IModularContractLogHandler.HandleLogsRequest request)
	{
		if (!request.getLogEntryContractType().equals(handler.getLogEntryContractType())
				|| !request.getTableRecordReference().getTableName().equals(handler.getSupportedTableName()))
		{
			return false;
		}

		final IComputingMethodHandler computingMethod = handler.getComputingMethod();
		final boolean isComputingMethodMatchingRequest = computingMethod.getComputingMethodType().equals(request.getComputingMethodType())
				&& computingMethod.applies(request.getTableRecordReference(), request.getLogEntryContractType());
		if (!isComputingMethodMatchingRequest)
		{
			return false;
		}

		final boolean isComputingMethodMatchingSettings = computingMethod.isApplicableForSettings(request.getTableRecordReference(), request.getModularContractSettings());
		if (!isComputingMethodMatchingSettings)
		{
			Loggables.addLog("Handler: {} is matching request, but not the contract settings! see request: {}!", this.getClass().getName(), request);
			return false;
		}
		return true;
	}

	@NonNull
	public IModularContractLogHandler getApplicableHandlerForOrError(@NonNull final ModularContractLogEntry logEntry)
	{
		final ImmutableList<IModularContractLogHandler> matchingHandlers = handlers.stream()
				.filter(handler -> modularContractSettingsBL.hasComputingMethodType(logEntry.getModularContractModuleId(), handler.getComputingMethod().getComputingMethodType()))
				.filter(handler -> handler.getLogEntryDocumentType() == logEntry.getDocumentType())
				.collect(ImmutableList.toImmutableList());

		Check.assume(matchingHandlers.size() == 1, "Exactly 1 log-handler should be found for log, but found " + matchingHandlers.size());
		return matchingHandlers.get(0);
	}
}
