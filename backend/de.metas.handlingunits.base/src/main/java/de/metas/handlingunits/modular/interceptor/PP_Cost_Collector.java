/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.modular.interceptor;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryContractType;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.REACTIVATED;

@Interceptor(I_PP_Cost_Collector.class)
@Component
public class PP_Cost_Collector
{
	private final ModularContractService contractService;

	public PP_Cost_Collector(@NonNull final ModularContractService contractService)
	{
		this.contractService = contractService;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_PP_Cost_Collector.COLUMNNAME_Processed)
	public void afterProcessed(@NonNull final I_PP_Cost_Collector orderCostCollector)
	{
		contractService.invokeWithModel(orderCostCollector, orderCostCollector.isProcessed() ? COMPLETED : REACTIVATED, LogEntryContractType.MODULAR_CONTRACT);
	}
}
