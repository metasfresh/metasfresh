package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.process.AD_Message_Values.MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS;
import static de.metas.ui.web.picking.process.AD_Message_Values.MSG_WEBUI_PICKING_PICK_SOMETHING;
import static de.metas.ui.web.picking.process.AD_Message_Values.MSG_WEBUI_PICKING_SELECT_HU;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_M_Product;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.form.PackingItemsMap;
import de.metas.fresh.picking.form.FreshPackingItemHelper;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.service.IPackingContext;
import de.metas.fresh.picking.service.IPackingService;
import de.metas.fresh.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.PickingCandidateCommand;
import de.metas.ui.web.picking.PickingSlotRow;
import de.metas.ui.web.picking.PickingSlotView;
import de.metas.ui.web.picking.PickingSlotViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

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
 * <li>the HU is changed from status "planned" or "active" to "picked"</li>
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
	private PickingCandidateCommand pickingCandidateCommand;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_HU));
		}
		if(pickingSlotRow.getIncludedRows().isEmpty())
		{
			// we want a toplevel HU..this is kindof dirty, but should work in this context
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_HU));
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

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		final IPackingService packingService = Services.get(IPackingService.class);

		final I_M_PickingSlot pickingSlot = load(rowToProcess.getPickingSlotId(), I_M_PickingSlot.class);
		final I_M_HU hu = loadOutOfTrx(rowToProcess.getHuId(), I_M_HU.class); // HU2PackingItemsAllocator wants them to be out of trx

		final I_M_ShipmentSchedule shipmentSchedule = getView().getShipmentSchedule();
		final I_M_Product product = shipmentSchedule.getM_Product();

		BigDecimal qty = BigDecimal.ZERO;
		final List<IHUProductStorage> huProductStorages = storageFactory.getHUProductStorages(ImmutableList.of(hu), product);
		for (final IHUProductStorage storage : huProductStorages)
		{
			qty = qty.add(storage.getQty(product.getC_UOM()));
		}

		if (qty.signum() <= 0)
		{
			// should not happen due to our checkPreconditionsApplicable(), but happened in other processes, sometimes..we just were unable to reproduce
			final String msg = StringUtils.formatMessage("The current HU has no quantity of the current product; hu={}; product={}", hu, product);
			Loggables.get().addLog(msg);
			throw new AdempiereException(msg);
		}

		final IFreshPackingItem itemToPack = FreshPackingItemHelper.create(ImmutableMap.of(shipmentSchedule, qty));

		final PackingItemsMap packingItemsMap = new PackingItemsMap();
		packingItemsMap.addUnpackedItem(itemToPack);

		final IPackingContext packingContext = packingService.createPackingContext(getCtx());
		packingContext.setPackingItemsMap(packingItemsMap); // don't know what to do with it, but i saw that it can't be null
		packingContext.setPackingItemsMapKey(pickingSlot.getM_PickingSlot_ID());

		// Allocate given HUs to "itemToPack"
		new HU2PackingItemsAllocator()
				.setItemToPack(itemToPack)
				.setPackingContext(packingContext)
				.setFromHUs(ImmutableList.of(hu))
				.allocate();

		pickingCandidateCommand.setCandidatesProcessed(ImmutableList.of(rowToProcess.getHuId()));
				
		invalidateView();
		invalidateParentView();
		
		return MSG_OK;
	}

	@Override
	protected PickingSlotView getView()
	{
		return PickingSlotView.cast(super.getView());
	}

	@Override
	protected PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

}
