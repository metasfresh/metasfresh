package de.metas.handlingunits.inventory.interceptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTransactionBL;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
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
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Inventory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
	private final IHUTransactionBL huTransactionBL = Services.get(IHUTransactionBL.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

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

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REVERSECORRECT)
	public void checkHUTransformationBeforeReverseCorrect(final I_M_Inventory inventoryRecord)
	{
		final Inventory inventory = inventoryLineRecordService.toInventory(inventoryRecord);
		checkHUTransformationBeforeReverseCorrect(inventory);
	}

	private void checkHUTransformationBeforeReverseCorrect(final Inventory inventory)
	{
		inventory.getLines().forEach(this::checkHUTransformationBeforeReverseCorrect);
	}

	private void checkHUTransformationBeforeReverseCorrect(final InventoryLine line)
	{
		final TableRecordReference inventoryLineRef = TableRecordReference.of(I_M_InventoryLine.Table_Name, Objects.requireNonNull(line.getId()));

		line.getInventoryLineHUs().forEach(lineHU -> checkHUTransformationBeforeReverseCorrect(lineHU, inventoryLineRef));
	}

	private void checkHUTransformationBeforeReverseCorrect(
			final InventoryLineHU lineHU,
			final TableRecordReference inventoryLineRef)
	{
		final HuId huId = lineHU.getHuId();
		assert huId != null;

		final Set<HuId> huIdsToCheck;
		if (handlingUnitsBL.isLoadingUnit(handlingUnitsBL.getById(huId)))
		{
			// Because the Qty HU transactions are never done on LU level but on TU/VHU level,
			// in case of LUs, we have to extract the involved TUs from them.
			//
			// To find out the TUs we check the HU assignments because it might be that our LU is destroyed
			// and in that case the M_HU.M_HU_Item_Parent_ID is null.
			huIdsToCheck = huAssignmentBL.getTUsByLU(inventoryLineRef, huId);
		}
		else
		{
			huIdsToCheck = ImmutableSet.of(huId);
		}

		for (final HuId huIdToCheck : huIdsToCheck)
		{
			if (!huTransactionBL.isLatestHUTrx(huIdToCheck, inventoryLineRef))
			{
				throw new HUException("@InventoryReverseError@");
			}
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseDisposal(final I_M_Inventory inventory)
	{
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
