package de.metas.handlingunits.inventory.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
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
import de.metas.inventory.InventoryLineId;
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
import org.compiere.model.X_M_Movement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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
	private final InventoryService inventoryService;
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IHUTransactionBL huTransactionBL = Services.get(IHUTransactionBL.class);
	private final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	public M_Inventory(@NonNull final InventoryService inventoryRecordHUService)
	{
		this.inventoryService = inventoryRecordHUService;

		Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(
				I_M_InventoryLine.Table_Name,
				M_InventoryLineTabCallout.class);
	}

	@ModelChange(
			timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_M_Inventory.COLUMNNAME_C_DocType_ID)
	public void beforeChange_onDocTypeChanged(@NonNull final I_M_Inventory inventoryRecord)
	{
		// don't allow change if there are lines with diverting HU-aggregation types, because we don't want to switch the HUAggragationType of existing lines
		inventoryService.updateHUAggregationTypeIfAllowed(inventoryRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void beforeComplete(final I_M_Inventory inventoryRecord)
	{
		if (!inventoryService.isMaterialDisposal(inventoryRecord))
		{
			inventoryService.syncToHUs(inventoryRecord);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REVERSECORRECT)
	public void beforeReverseCorrect(final I_M_Inventory inventoryRecord)
	{
		if (!inventoryService.isMaterialDisposal(inventoryRecord))
		{
			// NOTE we check only for regular iventories because in case of internal use/material disposal this is not reliable
			// the problem shall be fixed for good in another task.
			assertLastHUTrxWasThisInventory(inventoryRecord);
		}
	}

	private void assertLastHUTrxWasThisInventory(final I_M_Inventory inventoryRecord)
	{
		final Inventory inventory = inventoryService.toInventory(inventoryRecord);
		inventory.getLines().forEach(this::assertLastHUTrxWasThisInventoryLine);
	}

	private void assertLastHUTrxWasThisInventoryLine(@NonNull final InventoryLine line)
	{
		line.getInventoryLineHUs().forEach(hu -> assertLastHUTrxWasThisInventoryLine(line, hu));
	}

	private void assertLastHUTrxWasThisInventoryLine(@NonNull final InventoryLine line, @NonNull final InventoryLineHU lineHU)
	{
		final HuId huId = Objects.requireNonNull(lineHU.getHuId());
		final InventoryLineId lineId = Objects.requireNonNull(line.getId());
		if (!huTransactionBL.isLatestHUTrx(huId, TableRecordReference.of(I_M_InventoryLine.Table_Name, lineId)))
		{
			throw new HUException("@InventoryReverseError@")
					.setParameter("line", line)
					.setParameter("lineHU", lineHU);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void afterReverseCorrect(final I_M_Inventory inventory)
	{
		if (inventoryService.isMaterialDisposal(inventory))
		{
			restoreHUsFromSnapshots(inventory);
			reverseEmptiesMovements(inventory);
		}
	}

	private void restoreHUsFromSnapshots(final I_M_Inventory inventory)
	{
		final String snapshotId = inventory.getSnapshot_UUID();
		if (Check.isBlank(snapshotId))
		{
			throw new HUException("@NotFound@ @Snapshot_UUID@ (" + inventory + ")");
		}

		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
		final List<Integer> topLevelHUIds = inventoryDAO.retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class)
				.stream()
				.map(I_M_InventoryLine::getM_HU_ID)
				.collect(ImmutableList.toImmutableList());

		huSnapshotDAO.restoreHUs()
				.setContext(PlainContextAware.newWithThreadInheritedTrx())
				.setSnapshotId(snapshotId)
				.setDateTrx(inventory.getMovementDate())
				.addModelIds(topLevelHUIds)
				.setReferencedModel(inventory)
				.restoreFromSnapshot();
	}

	private void reverseEmptiesMovements(final I_M_Inventory inventory)
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());

		movementDAO.retrieveMovementsForInventoryQuery(inventoryId)
				.addEqualsFilter(I_M_Inventory.COLUMNNAME_DocStatus, X_M_Inventory.DOCSTATUS_Completed)
				.create()
				.stream()
				.forEach(emptiesMovement -> documentBL.processEx(emptiesMovement, X_M_Movement.DOCACTION_Reverse_Correct, X_M_Movement.DOCSTATUS_Reversed));
	}

}
