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

import de.metas.contracts.modular.computing.ComputingMethodHandler;
import de.metas.util.Loggables;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
class ModularContractLogHandlerRegistry
{
	@NonNull
	private final List<IModularContractLogHandler> handlers;

	@NonNull
	Stream<IModularContractLogHandler> streamHandlers(@NonNull final IModularContractLogHandler.HandleLogsRequest request)
	{
		return handlers.stream().filter(handler -> applies(handler, request));
	}

	private boolean applies(@NonNull IModularContractLogHandler handler, @NonNull final IModularContractLogHandler.HandleLogsRequest request)
	{
		if (!request.getLogEntryContractType().equals(handler.getLogEntryContractType())
				|| !request.getTableRecordReference().getTableName().equals(handler.getSupportedTableName()))
		{
			return false;
		}

		final ComputingMethodHandler computingMethod = handler.getComputingMethod();
		final boolean isComputingMethodMatchingRequest = computingMethod.getComputingMethodType().equals(request.getComputingMethodType())
				&& computingMethod.applies(request.getTableRecordReference(), request.getLogEntryContractType());
		if (!isComputingMethodMatchingRequest)
		{
			return false;
		}

		final boolean isComputingMethodMatchingContract = computingMethod.isContractIdEligible(request.getTableRecordReference(), request.getContractId());
		if (!isComputingMethodMatchingContract)
		{
			Loggables.addLog("Handler: {} is matching request, but not the contractId! see request: {}!", this.getClass().getName(), request);
			return false;
		}

		return handler.applies(request);
	}
}
