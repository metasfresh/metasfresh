package de.metas.handlingunits.model.validator;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.minventory.api.IInventoryBL;
import org.adempiere.minventory.api.IInventoryDAO;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Inventory;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inventory.IHUInventoryBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
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
@Interceptor(I_M_Inventory.class)
public class M_Inventory
{
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void beforeComplete(final I_M_Inventory inventory)
	{
		final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
		final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
		
		for (final I_M_InventoryLine line : inventoryDAO.retrieveLinesForInventoryId(inventory.getM_Inventory_ID(), I_M_InventoryLine.class))
		{
			final BigDecimal qtyDiff = inventoryBL.getMovementQty(line);
			if (qtyDiff.signum() == 0)
			{
				continue;
			}
			else if (qtyDiff.signum() > 0)
			{
				// TODO: add to HU or create one
			}
			else // qtyDiff < 0
			{
				if (line.getM_HU_ID() <= 0)
				{
					throw new FillMandatoryException(I_M_InventoryLine.COLUMNNAME_M_HU_ID)
							.setParameter(I_M_InventoryLine.COLUMNNAME_Line, line.getLine())
							.appendParametersToMessage();
				}

				// TODO subtract from HU
			}
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseDisposal(final I_M_Inventory inventory)
	{
		final IHUInventoryBL huInventoryBL = Services.get(IHUInventoryBL.class);

		if (!(huInventoryBL.isMaterialDisposal(inventory)))
		{
			return; // nothing to do
		}

		final String snapshotId = inventory.getSnapshot_UUID();
		if (Check.isEmpty(snapshotId, true))
		{
			throw new HUException("@NotFound@ @Snapshot_UUID@ (" + inventory + ")");
		}

		//
		// restore HUs from snapshots
		{
			final List<I_M_HU> topLevelHUs = huInventoryBL.getAssignedTopLevelHUsByInventoryId(inventory.getM_Inventory_ID());
			Services.get(IHUSnapshotDAO.class).restoreHUs()
					.setContext(PlainContextAware.newWithThreadInheritedTrx())
					.setSnapshotId(snapshotId)
					.setDateTrx(inventory.getMovementDate())
					.addModels(topLevelHUs)
					.setReferencedModel(inventory)
					.restoreFromSnapshot();
		}

		//
		// Reverse empties movements
		{
			final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
			Services.get(IMovementDAO.class)
					.retrieveMovementsForInventoryQuery(inventory.getM_Inventory_ID())
					.addEqualsFilter(I_M_Inventory.COLUMNNAME_DocStatus, X_M_Inventory.DOCSTATUS_Completed)
					.create()
					.stream()
					.forEach(emptiesMovement -> docActionBL.processEx(emptiesMovement, X_M_Inventory.DOCACTION_Reverse_Correct, X_M_Inventory.DOCSTATUS_Reversed));
		}
	}

}
