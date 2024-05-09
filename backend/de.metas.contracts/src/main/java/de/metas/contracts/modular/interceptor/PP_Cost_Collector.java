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

package de.metas.contracts.modular.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.log.LogEntryContractType;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
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
		contractService.scheduleLogCreation(DocStatusChangedEvent.builder()
													.tableRecordReference(TableRecordReference.of(orderCostCollector))
													.logEntryContractTypes(ImmutableSet.of(LogEntryContractType.MODULAR_CONTRACT))
													.modelAction(orderCostCollector.isProcessed() ? COMPLETED : REACTIVATED)
													.userInChargeId(Env.getLoggedUserId())
													.build()
		);
	}
}
