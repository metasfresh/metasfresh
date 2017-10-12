package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_PICK_SOMETHING;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.fresh.picking.form.FreshPackingItemHelper;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.service.IPackingContext;
import de.metas.fresh.picking.service.IPackingService;
import de.metas.fresh.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.service.PackingItemsMap;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Processes the unprocessed {@link I_M_Picking_Candidate} of the currently selected TU.<br>
 * Processing means that
 * <ul>
 * <li>the HU is associated with its shipment schedule (changes QtyPicked and QtyToDeliver)</li>
 * <li>The HU is added to its picking slot's picking-slot-queue</li>
 * </ul>
 * 
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_M_Picking_Candidate_Process
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{

	@Autowired
	private PickingCandidateService pickingCandidateService;

	@Autowired
	private PickingCandidateRepository pickingCandidateRepository;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}
		if (pickingSlotRow.getIncludedRows().isEmpty())
		{
			// we want a toplevel HU..this is kindof dirty, but should work in this context
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}

		if (pickingSlotRow.getHuQtyCU().signum() <= 0)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_PICK_SOMETHING));
		}

		if (pickingSlotRow.isProcessed())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow rowToProcess = getSingleSelectedRow();

		final I_M_HU hu = loadOutOfTrx(rowToProcess.getHuId(), I_M_HU.class); // HU2PackingItemsAllocator wants them to be out of trx

		allocateHuToShipmentSchedule(
				rowToProcess.getPickingSlotId(),
				hu);

		pickingCandidateService.setCandidatesProcessed(ImmutableList.of(rowToProcess.getHuId()));

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	private void allocateHuToShipmentSchedule(
			final int pickingSlotId,
			@NonNull final I_M_HU hu)
	{
		final IFreshPackingItem itemToPack = createItemToPack(hu);

		final PackingItemsMap packingItemsMap = new PackingItemsMap();
		packingItemsMap.addUnpackedItem(itemToPack);

		final IPackingService packingService = Services.get(IPackingService.class);
		final IPackingContext packingContext = packingService.createPackingContext(getCtx());
		packingContext.setPackingItemsMap(packingItemsMap); // don't know what to do with it, but i saw that it can't be null
		packingContext.setPackingItemsMapKey(pickingSlotId);

		// Allocate given HUs to "itemToPack"
		new HU2PackingItemsAllocator()
				.setItemToPack(itemToPack)
				.setPackingContext(packingContext)
				.setFromHUs(ImmutableList.of(hu))
				.allocate();
	}

	private IFreshPackingItem createItemToPack(@NonNull final I_M_HU hu)
	{
		final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys = new HashMap<>();

		final List<I_M_Picking_Candidate> pickingCandidates = pickingCandidateRepository.retrievePickingCandidates(hu.getM_HU_ID());
		for (final I_M_Picking_Candidate pc : pickingCandidates)
		{
			final I_M_ShipmentSchedule shipmentSchedule = pc.getM_ShipmentSchedule();
			final BigDecimal qty = pc.getQtyPicked();
			scheds2Qtys.put(shipmentSchedule, qty);
		}

		final IFreshPackingItem itemToPack = FreshPackingItemHelper.create(scheds2Qtys);
		return itemToPack;
	}

	@Override
	protected PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

}
