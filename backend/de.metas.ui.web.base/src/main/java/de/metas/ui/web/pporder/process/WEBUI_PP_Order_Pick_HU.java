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
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.inoutcandidate.ShipmentScheduleId;
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
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Pick_ParametersFiller;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.util.HURow;
import de.metas.ui.web.pporder.util.WEBUI_PP_Order_ProcessHelper;
import de.metas.ui.web.pporder.util.WEBUI_Picking_Request;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

public class WEBUI_PP_Order_Pick_HU extends WEBUI_PP_Order_Template implements IProcessPrecondition, IProcessDefaultParametersProvider
{

	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);

	@Param(parameterName = I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID, mandatory = true)
	protected PickingSlotId pickingSlotId;

	@Param(parameterName = I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, mandatory = true)
	protected ShipmentScheduleId shipmentScheduleId;

	@Param(parameterName = "IsTakeWholeHU", mandatory = true)
	protected boolean isTakeWholeHU;

	private final static PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
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
	@RunOutOfTrx
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

		Quantity qtyLeftToPick = qtyToDeliver;
		for (final HURow huRow : huRows)
		{
			if (qtyLeftToPick.signum() <= 0 && !isTakeWholeHU)
			{
				return;
			}
			final Quantity huRowQty = huRow.getQty();
			final HuId huId = huRow.getHuId();
			final HuId huIdToProcess;

			final Quantity qtyToPick;

			if (isTakeWholeHU || qtyLeftToPick.compareTo(huRowQty) > 0)
			{
				qtyToPick = huRowQty;

				huIdToProcess = huId;

			}

			else
			{
				qtyToPick = qtyLeftToPick;

				final I_M_HU splitHU = CollectionUtils.singleElement(HUTransformService.newInstance()
																			 .husToNewCUs(HUTransformService.HUsToNewCUsRequest.builder()
																								  .sourceHU(huDAO.getById(huId))
																								  .productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
																								  .qtyCU(qtyToPick)
																								  .onlyFromUnreservedHUs(true)
																								  .build()));
				huIdToProcess = HuId.ofRepoId(splitHU.getM_HU_ID());

			}

			WEBUI_PP_Order_ProcessHelper.pickAndProcessHU(WEBUI_Picking_Request.builder()
														.huId(huIdToProcess)
														.shipmentScheduleId(shipmentScheduleId)
														.pickingSlotId(pickingSlotId)
														.isTakeWholeHU(isTakeWholeHU)
														.ppOrderId(ppOrderId)
														.build());

			qtyLeftToPick = qtyLeftToPick.subtract(qtyToPick);

		}
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
