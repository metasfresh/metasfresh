package de.metas.handlingunits.model.validator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.minventory.api.IInventoryDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inventory.IHUInventoryBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Validator(I_M_Inventory.class)
public class M_Inventory
{

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseDisposal(final I_M_Inventory inventory)
	{

		final IHUInventoryBL huInventoryBL = Services.get(IHUInventoryBL.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		if (!(huInventoryBL.isMaterialDisposal(inventory)))
		{
			return; // nothing to do
		}

		final de.metas.handlingunits.model.I_M_Inventory huInventory = InterfaceWrapperHelper.create(inventory, de.metas.handlingunits.model.I_M_Inventory.class);

		final String snapshotId = huInventory.getSnapshot_UUID();
		if (Check.isEmpty(snapshotId, true))
		{
			throw new HUException("@NotFound@ @Snapshot_UUID@ (" + huInventory + ")");
		}

		final List<I_M_InventoryLine> inventoryLines = Services.get(IInventoryDAO.class).retrieveLinesForInventory(inventory);

		for (final I_M_InventoryLine inventoryLine : inventoryLines)
		{
			final List<I_M_HU> topLevelHUsForInventoryLine = huAssignmentDAO.retrieveTopLevelHUsForModel(inventoryLine);

			final IContextAware context = InterfaceWrapperHelper.getContextAware(huInventory);

			// restore HUs from snapshots
			Services.get(IHUSnapshotDAO.class).restoreHUs()
					.setContext(context)
					.setSnapshotId(snapshotId)
					.setDateTrx(huInventory.getMovementDate())
					.addModels(topLevelHUsForInventoryLine)
					.setReferencedModel(huInventory)
					.restoreFromSnapshot();

			// TODO: create movement for the HUs. I don't know if needed and how this should be done efficiently

		}

	}

}
