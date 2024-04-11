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

package de.metas.contracts.modular;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.computing.ComputingMethodHandler;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.workpackage.ProcessModularLogsEnqueuer;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModularContractService
{
	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final ModularContractComputingMethodHandlerRegistry modularContractHandlers;
	@NonNull private final ModularContractSettingsDAO modularContractSettingsDAO;
	@NonNull private final ProcessModularLogsEnqueuer processLogsEnqueuer;
	@NonNull private final ComputingMethodService computingMethodService;

	public void scheduleLogCreation(@NonNull final DocStatusChangedEvent event)
	{
		boolean isRequestValidated = false;

		for (final LogEntryContractType logEntryContractType : event.getLogEntryContractTypes())
		{
			for (final ComputingMethodHandler handler : modularContractHandlers.getApplicableHandlersFor(event.getTableRecordReference(), logEntryContractType))
			{
				final ComputingMethodType computingMethodType = handler.getComputingMethodType();

				for (final FlatrateTermId flatrateTermId : handler.streamContractIds(event.getTableRecordReference()).toList())
				{
					if (!isApplicableContract(computingMethodType, flatrateTermId))
					{
						continue;
					}

					if (!isRequestValidated)
					{
						computingMethodService.validateAction(event.getTableRecordReference(), event.getModelAction());
						isRequestValidated = true;
					}

					processLogsEnqueuer.enqueueAfterCommit(ProcessModularLogsEnqueuer.EnqueueRequest.builder()
							.recordReference(event.getTableRecordReference())
							.action(event.getModelAction())
							.userInChargeId(event.getUserInChargeId())
							.logEntryContractType(logEntryContractType)
							.computingMethodType(computingMethodType)
							.flatrateTermId(flatrateTermId)
							.build());
				}
			}
		}
	}

	private boolean isApplicableContract(@NonNull final ComputingMethodType computingMethodType, @NonNull final FlatrateTermId flatrateTermId)
	{
		if (!isModularOrInterimContract(flatrateTermId))
		{
			return false;
		}

		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(flatrateTermId);
		return settings != null && settings.isMatching(computingMethodType);
	}

	private boolean isModularOrInterimContract(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term flatrateTermRecord = flatrateDAO.getById(flatrateTermId);
		final TypeConditions typeConditions = TypeConditions.ofCode(flatrateTermRecord.getType_Conditions());
		return typeConditions.isModularOrInterim();
	}
}
