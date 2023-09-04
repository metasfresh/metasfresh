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

package de.metas.contracts.modular.interceptor;

import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.REVERSED;

@Component
@Interceptor(I_M_Inventory.class)
public class M_Inventory
{
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

	private final ModularContractService contractService;

	public M_Inventory(@NonNull final ModularContractService contractService)
	{
		this.contractService = contractService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_M_Inventory inventoryRecord)
	{
		invokeHandlerForEachLine(inventoryRecord, COMPLETED);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void afterReverse(@NonNull final I_M_Inventory inventoryRecord)
	{
		invokeHandlerForEachLine(inventoryRecord, REVERSED);
	}

	private void invokeHandlerForEachLine(
			@NonNull final I_M_Inventory inventoryRecord,
			@NonNull final ModelAction modelAction)
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
		inventoryDAO.retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class)
				.forEach(line -> contractService.invokeWithModel(line, modelAction, LogEntryContractType.MODULAR_CONTRACT));
	}
}
