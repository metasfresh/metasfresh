/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.picking.pickingslot.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS;

/**
 * Processes the unprocessed picking candidate of the currently selected TU.<br>
 * Processing means that
 * <ul>
 * <li>the HU is associated with its shipment schedule (changes QtyPicked and QtyToDeliver)</li>
 * <li>The HU is added to its picking slot's picking-slot-queue</li>
 * </ul>
 * <p>
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class WEBUI_Picking_M_Picking_Candidate_Process extends PickingSlotViewBasedProcess
{

	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();

		final ImmutableSet<HuId> hus = filterOutInvalidHUs(pickingSlotRow);

		if (hus.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS));
		}

		 // TODO: it would be nice when selecting the PickingSlot to automatically select (highlight) the valid HUs. This way the user sees exactly which HUs will be Processed.
		return ProcessPreconditionsResolution.accept();
	}

	private ImmutableSet<HuId> filterOutInvalidHUs(@NonNull final PickingSlotRow pickingSlotRowOrHU)
	{
		final ImmutableSet.Builder<HuId> hus = ImmutableSet.builder();
		if (pickingSlotRowOrHU.isPickingSlotRow())
		{
			for (final PickingSlotRow pickingSlotRow : pickingSlotRowOrHU.getIncludedRows())
			{
				if (isValidHu(pickingSlotRow))
				{
					hus.add(pickingSlotRow.getHuId());
				}
			}
		}
		else
		{
			if (isValidHu(pickingSlotRowOrHU))
			{
				hus.add(pickingSlotRowOrHU.getHuId());
			}
		}

		return hus.build();
	}

	private boolean isValidHu(@NonNull final PickingSlotRow pickingSlotRowOrHU)
	{
		if (!pickingSlotRowOrHU.isPickedHURow())
		{
			return false;
			// return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}
		if (!pickingSlotRowOrHU.isTopLevelHU())
		{
			return false;
			// return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}

		if (checkIsEmpty(pickingSlotRowOrHU))
		{
			return false;
			// return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_PICK_SOMETHING));
		}

		//noinspection RedundantIfStatement
		if (pickingSlotRowOrHU.isProcessed())
		{
			return false;
			// return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS));
		}
		return true;
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow rowToProcess = getSingleSelectedRow();
		final ImmutableSet<HuId> hUs = filterOutInvalidHUs(rowToProcess);

		final ShipmentScheduleId shipmentScheduleId = null;
		//noinspection ConstantConditions
		pickingCandidateService.processForHUIds(hUs, shipmentScheduleId);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		invalidatePickingSlotsView();
		invalidatePackablesView();
	}

	private boolean checkIsEmpty(final PickingSlotRow pickingSlotRowOrHU)
	{
		Check.assume(pickingSlotRowOrHU.isPickedHURow(), "Was expecting an HuId but found none!");

		if (pickingSlotRowOrHU.getHuQtyCU() != null && pickingSlotRowOrHU.getHuQtyCU().signum() > 0)
		{
			return false;
		}

		final I_M_HU hu = handlingUnitsBL.getById(pickingSlotRowOrHU.getHuId());

		return handlingUnitsBL.isEmptyStorage(hu);
	}
}
