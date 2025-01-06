/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.pporder.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.picking.requests.ProcessPickingRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Pick_ParametersFiller;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.pporder.util.HURow;
import de.metas.ui.web.pporder.util.WEBUI_PP_Order_ProcessHelper;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class WEBUI_PP_Order_Pick_ReceivedHUs extends WEBUI_PP_Order_Template implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	@Param(parameterName = I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID, mandatory = true)
	protected PickingSlotId pickingSlotId;

	@Param(parameterName = I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, mandatory = true)
	protected ShipmentScheduleId shipmentScheduleId;

	@Param(parameterName = "IsTakeWholeHU", mandatory = true)
	protected boolean isTakeWholeHU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLinesView view = getView();
		if (!view.getDocStatus().isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("manufacturing order shall be completed");
		}

		final PPOrderLineRow ppOrderLineRow = getSingleSelectedRow();
		if (!ppOrderLineRow.isReceipt())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("ppOrderLineRow is not a receipt line; ppOrderLineRow=" + ppOrderLineRow);
		}
		if (!ppOrderLineRow.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("ppOrderLineRow is not yet processed; ppOrderLineRow=" + ppOrderLineRow);
		}

		if (WEBUI_PP_Order_ProcessHelper.getHURowsFromIncludedRows(getView()).isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		// Satisfy the shipment quantity over all the received HUs
		pickHURows();

		// invalidate view in order to be refreshed
		getView().invalidateAll();

		return MSG_OK;
	}

	public void pickHURows()
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);
		final Quantity qtyToDeliver = shipmentScheduleBL.getQtyToDeliver(shipmentSchedule);

		if (qtyToDeliver.signum() <= 0)
		{
			// nothing to do
			return;
		}
		final ImmutableList<HURow> huRows = WEBUI_PP_Order_ProcessHelper.getHURowsFromIncludedRows(getView());

		final PPOrderId ppOrderId = getView().getPpOrderId();

		final Set<HuId> huIds = new HashSet<>();

		Quantity qtyLeftToPick = qtyToDeliver;
		for (final HURow huRow : huRows)
		{
			if (qtyLeftToPick.signum() <= 0 && !isTakeWholeHU)
			{
				return;
			}
			final Quantity huRowQty = huRow.getQty();
			final HuId huId = huRow.getHuId();

			final Quantity qtyToPick;

			if (isTakeWholeHU || qtyLeftToPick.compareTo(huRowQty) > 0)
			{
				qtyToPick = huRowQty;
			}

			else
			{
				qtyToPick = qtyLeftToPick;
			}

			final PickRequest pickRequest = PickRequest.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.pickFrom(PickFrom.ofHuId(huId))
					.pickingSlotId(pickingSlotId)
					.build();

			pickingCandidateService.pickHU(pickRequest);

			huIds.add(huId);
			qtyLeftToPick = qtyLeftToPick.subtract(qtyToPick);

		}

		pickingCandidateService.processForHUIds(ProcessPickingRequest.builder()
				.huIds(ImmutableSet.copyOf(huIds))
				.shipmentScheduleId(shipmentScheduleId)
				.ppOrderId(ppOrderId)
														.shouldSplitHUIfOverDelivery(!isTakeWholeHU)
				.build());
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return createNewDefaultParametersFiller().getDefaultValue(parameter);
	}

	@ProcessParamLookupValuesProvider(//
			parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, //
			numericKey = true, //
			lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	private LookupValuesPage getShipmentScheduleValues(final LookupDataSourceContext context)
	{
		return createNewDefaultParametersFiller().getShipmentScheduleValues(context);
	}

	@Nullable
	private OrderLineId getSalesOrderLineId()
	{
		if (getView() != null)
		{
			return getView().getSalesOrderLineId();
		}
		return null;
	}

	@ProcessParamLookupValuesProvider(//
			parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_PickingSlot_ID, //
			dependsOn = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, //
			numericKey = true, //
			lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	private LookupValuesList getPickingSlotValues(final LookupDataSourceContext context)
	{
		final WEBUI_M_HU_Pick_ParametersFiller filler = WEBUI_M_HU_Pick_ParametersFiller
				.pickingSlotFillerBuilder()
				.shipmentScheduleId(shipmentScheduleId)
				.build();

		return filler.getPickingSlotValues(context);
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		invalidateView();
	}

	private WEBUI_M_HU_Pick_ParametersFiller createNewDefaultParametersFiller()
	{
		final HURow row = WEBUI_PP_Order_ProcessHelper.getHURowsFromIncludedRows(getView()).get(0);
		return WEBUI_M_HU_Pick_ParametersFiller.defaultFillerBuilder()
				.huId(row.getHuId())
				.salesOrderLineId(getSalesOrderLineId())
				.build();
	}

}
