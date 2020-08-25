package de.metas.handlingunits.inventory.interceptor;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Inventory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.tabcallout.M_InventoryLineTabCallout;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
@Component
public class M_Inventory
{
	private final InventoryService inventoryLineRecordService;

	public M_Inventory(@NonNull final InventoryService inventoryRecordHUService)
	{
		this.inventoryLineRecordService = inventoryRecordHUService;

		Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(
				I_M_InventoryLine.Table_Name,
				M_InventoryLineTabCallout.class);
	}

	@ModelChange( //
			timings = ModelValidator.TYPE_BEFORE_CHANGE, //
			ifColumnsChanged = I_M_Inventory.COLUMNNAME_C_DocType_ID)
	public void updateLineHUAggregationType(@NonNull final I_M_Inventory inventoryRecord)
	{
		// don't allow change if there are lines with diverting HU-aggregation types, because we don't want to switch the HUAggragationType of existing lines
		inventoryLineRecordService.updateHUAggregationTypeIfAllowed(inventoryRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void beforeComplete(final I_M_Inventory inventoryRecord)
	{
		if (inventoryLineRecordService.isMaterialDisposal(inventoryRecord))
		{
			return; // nothing to do
		}

		inventoryLineRecordService.syncToHUs(inventoryRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseDisposal(final I_M_Inventory inventory)
	{
		final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

		if (!inventoryLineRecordService.isMaterialDisposal(inventory))
		{
			return; // nothing to do
		}

		final String snapshotId = inventory.getSnapshot_UUID();
		if (Check.isEmpty(snapshotId, true))
		{
			throw new HUException("@NotFound@ @Snapshot_UUID@ (" + inventory + ")");
		}

		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());

		//
		// restore HUs from snapshots
		{
			final List<Integer> topLevelHUIds = inventoryDAO.retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class)
					.stream()
					.map(I_M_InventoryLine::getM_HU_ID)
					.collect(ImmutableList.toImmutableList());

			Services.get(IHUSnapshotDAO.class).restoreHUs()
					.setContext(PlainContextAware.newWithThreadInheritedTrx())
					.setSnapshotId(snapshotId)
					.setDateTrx(inventory.getMovementDate())
					.addModelIds(topLevelHUIds)
					.setReferencedModel(inventory)
					.restoreFromSnapshot();
		}

		//
		// Reverse empties movements
		{
			final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
			Services.get(IMovementDAO.class)
					.retrieveMovementsForInventoryQuery(inventoryId)
					.addEqualsFilter(I_M_Inventory.COLUMNNAME_DocStatus, X_M_Inventory.DOCSTATUS_Completed)
					.create()
					.stream()
					.forEach(emptiesMovement -> docActionBL.processEx(emptiesMovement, X_M_Inventory.DOCACTION_Reverse_Correct, X_M_Inventory.DOCSTATUS_Reversed));
		}
	}

}
