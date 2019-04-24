/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.process;

import de.metas.handlingunits.inventory.InventoryId;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.pharma.securpharm.model.DecommissionAction;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResult;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmResultRepository;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import org.compiere.Adempiere;

public class M_Inventory_SecurpharmActionRetry extends JavaProcess implements IProcessPrecondition
{
	private final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);

	private final SecurPharmResultRepository resultRepository = Adempiere.getBean(SecurPharmResultRepository.class);

	@Override protected String doIt() throws Exception
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getRecord_ID());
		final SecurPharmActionResult actionResult = resultRepository.getActionResultByInventoryId(inventoryId, DecommissionAction.DESTROY);
		if (actionResult.isError())
		{
			securPharmService.decommision(actionResult.getProductDataResult(), DecommissionAction.DESTROY, inventoryId);
		}
		else
		{
			final SecurPharmActionResult undoActionResult = resultRepository.getActionResultByInventoryId(inventoryId, DecommissionAction.UNDO_DISPENSE);
			if (undoActionResult.isError())
			{
				securPharmService.undoDecommision(undoActionResult, DecommissionAction.UNDO_DISPENSE, inventoryId);
			}
		}

		return MSG_OK;
	}

	@Override public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.reject();
		}
		I_M_Inventory inventory = context.getSelectedModel(I_M_Inventory.class);
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
		final SecurPharmActionResult actionResult = resultRepository.getActionResultByInventoryId(inventoryId, DecommissionAction.DESTROY);
		if (actionResult == null)
		{
			return ProcessPreconditionsResolution.reject();
		}
		if (!actionResult.isError())
		{
			final SecurPharmActionResult undoActionResult = resultRepository.getActionResultByInventoryId(inventoryId, DecommissionAction.UNDO_DISPENSE);
			if (!undoActionResult.isError())
			{
				return ProcessPreconditionsResolution.reject();
			}
		}
		return ProcessPreconditionsResolution.accept();
	}

}
