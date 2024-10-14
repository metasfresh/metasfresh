/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.log.interceptor;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettingsRepository;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ComputingMethodType.AVERAGE_CONTRACT_SPECIFIC_PRICE_METHODS;
import static de.metas.contracts.modular.log.LogEntryDocumentType.INTERIM_INVOICE;
import static de.metas.contracts.modular.log.LogEntryDocumentType.PURCHASE_MODULAR_CONTRACT;
import static de.metas.contracts.modular.log.LogEntryDocumentType.SHIPPING_NOTIFICATION;

@Component
@Interceptor(I_ModCntr_Log.class)
@AllArgsConstructor
public class ModCntr_Log
{
	@NonNull private final ModularLogInterestRepository interestRepo;
	@NonNull private final ModularContractSettingsRepository contractSettingsRepo;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final ModularContractLogHandlerRegistry logHandlerRegistry;


	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void validateModule(@NonNull final I_ModCntr_Log log)
	{
		final ModularContractLogEntryId id = ModularContractLogEntryId.ofRepoId(log.getModCntr_Log_ID());
		final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.ofCode(log.getModCntr_Log_DocumentType());
		if (SHIPPING_NOTIFICATION.equals(logEntryDocumentType) ||
				INTERIM_INVOICE.equals(logEntryDocumentType) ||
				PURCHASE_MODULAR_CONTRACT.equals((logEntryDocumentType)))
		{
			final ModularContractModuleId modularContractModuleId = ModularContractModuleId.ofRepoId(log.getModCntr_Module_ID());
			final ComputingMethodType computingMethodType = contractSettingsRepo.getByModuleId(modularContractModuleId).getComputingMethodType();
			if (ComputingMethodType.AddValueOnInterim.equals(computingMethodType) || ComputingMethodType.SubtractValueOnInterim.equals(computingMethodType))
			{
				interestRepo.deleteByModularContractLogEntryId(id);
			}
		}
	}

	@ModelChange(timings = {ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE}, ifColumnsChanged = I_ModCntr_Log.COLUMNNAME_IsBillable)
	public void updateAverageContractSpecificPriceIfNeeded(@NonNull final I_ModCntr_Log log)
	{
		if(!LogEntryDocumentType.SHIPMENT.getCode().equals(log.getModCntr_Log_DocumentType()))
		{
			return;
		}

		//as the initial log isBillable flag changes on reversal, we don't need to update on reversal log new
		if(log.getQty().signum() <= 0)
		{
			return;
		}

		final ModularContractModuleId modularContractModuleId = ModularContractModuleId.ofRepoId(log.getModCntr_Module_ID());
		final ModuleConfig moduleConfig = contractSettingsRepo.getByModuleId(modularContractModuleId);
		if(!moduleConfig.isMatchingAnyOf(AVERAGE_CONTRACT_SPECIFIC_PRICE_METHODS))
		{
			return;
		}

		modularContractLogService.updateAverageContractSpecificPrice(moduleConfig, FlatrateTermId.ofRepoId(log.getC_Flatrate_Term_ID()), logHandlerRegistry);
	}
}
