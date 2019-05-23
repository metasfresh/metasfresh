/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;

import de.metas.handlingunits.inventory.InventoryId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.pharma.securpharm.model.DecommissionAction;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResult;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmResultRepository;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;

public class M_Inventory_SecurpharmActionRetry extends JavaProcess implements IProcessPrecondition
{
	private final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);
	private final SecurPharmResultRepository resultRepository = Adempiere.getBean(SecurPharmResultRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
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

		final InventoryId inventoryId = InventoryId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (inventoryId == null)
		{
			return ProcessPreconditionsResolution.reject();
		}

		final SecurPharmActionResult actionResult = resultRepository
				.getActionResultByInventoryId(inventoryId, DecommissionAction.DESTROY)
				.orElse(null);
		if (actionResult == null)
		{
			return ProcessPreconditionsResolution.reject();
		}
		if (!actionResult.isError())
		{
			final SecurPharmActionResult undoActionResult = resultRepository
					.getActionResultByInventoryId(inventoryId, DecommissionAction.UNDO_DISPENSE)
					.orElse(null);
			if (!undoActionResult.isError())
			{
				return ProcessPreconditionsResolution.reject();
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getRecord_ID());
		final SecurPharmActionResult actionResult = resultRepository
				.getActionResultByInventoryId(inventoryId, DecommissionAction.DESTROY)
				.orElseThrow(() -> new AdempiereException("@NotFound@"));
		if (actionResult.isError())
		{
			securPharmService.decommision(actionResult.getProductDataResult(), DecommissionAction.DESTROY, inventoryId);
		}
		else
		{
			final SecurPharmActionResult undoActionResult = resultRepository
					.getActionResultByInventoryId(inventoryId, DecommissionAction.UNDO_DISPENSE)
					.orElseThrow(() -> new AdempiereException("@NotFound@"));
			if (undoActionResult.isError())
			{
				securPharmService.undoDecommision(undoActionResult, DecommissionAction.UNDO_DISPENSE, inventoryId);
			}
		}

		return MSG_OK;
	}
}
