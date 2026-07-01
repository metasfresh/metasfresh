package de.metas.ui.web.picking.pickingslot.process;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_PICK_SOMETHING;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_Picking_TU_Label extends PickingSlotViewBasedProcess
{
	private final HULabelService huLabelService = SpringContextHolder.instance.getBean(HULabelService.class);
	private final PickingJobHUService pickingJobHUService = SpringContextHolder.instance.getBean(PickingJobHUService.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}
		if (!pickingSlotRow.isTopLevelHU())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}

		if (pickingSlotRow.getHuQtyCU().signum() <= 0)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_PICK_SOMETHING));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PickingSlotRow rowToProcess = getSingleSelectedRow();
		printPickingLabel(rowToProcess.getHuId());

		return MSG_OK;

	}

	protected void printPickingLabel(@NonNull final HuId huId)
	{
		stampConsigneeIfNotSet(huId);

		huLabelService.print(HULabelPrintRequest.builder()
				.sourceDocType(HULabelSourceDocType.Picking)
				.hu(HUToReportWrapper.of(handlingUnitsDAO.getById(huId)))
				.onlyIfAutoPrint(false)
				.failOnMissingLabelConfig(true)
				.build());
	}

	/**
	 * Desktop-picking parity with the mobile close-LU fix (me03 #30763, AC8): a picking HU materialised without a
	 * {@code C_BPartner_ID} would miss the per-BPartner {@code M_HU_Label_Config} and — because this process prints
	 * with {@code failOnMissingLabelConfig=true} — throw instead of printing the consignee's label. Resolve the
	 * consignee from the current shipment schedule and stamp it (only-if-unset) so the unchanged label-matching path
	 * selects the correct per-BPartner config. Reuses the same guarded stamp method as the mobile fix.
	 */
	private void stampConsigneeIfNotSet(@NonNull final HuId huId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();
		final BPartnerLocationId bpLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule);
		if (bpLocationId == null)
		{
			return;
		}

		pickingJobHUService.setBPartnerAndLocationIfNotSet(huId, bpLocationId);
	}
}
