package de.metas.ui.web.picking.pickingslot.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_PICKED_HU_FOUND;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_TO_EXISTING_CUS_NOT_ALLOWED;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_TO_THE_SAME_HU_FOR_MULTIPLE_ORDERS_NOT_ALLOWED;

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
 * 
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_PickQtyToExistingHU
		extends WEBUI_Picking_With_M_Source_HU_Base
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final String PARAM_QTY_CU = "QtyCU";
	@Param(parameterName = PARAM_QTY_CU, mandatory = true)
	private BigDecimal qtyCU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Optional<ProcessPreconditionsResolution> preconditionsResolution = checkValidSelection();

		if (preconditionsResolution.isPresent())
		{
			return preconditionsResolution.get();
		}

		if (isForceDelivery())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Use WEBUI_Picking_ForcePickToExistingHU in case of force delivery!");
		}

		if (noSourceHUAvailable())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected Quantity getQtyToPack()
	{
		final I_C_UOM uom = getCurrentShipmentScheuduleUOM();
		return Quantity.of(qtyCU, uom);
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();

		validatePickingToHU();

		pickHUsAndPackTo(getSourceHUIds(), getQtyToPack(), pickingSlotRow.getHuId());

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(PARAM_QTY_CU, parameter.getColumnName()))
		{
			return retrieveQtyToPick().toBigDecimal();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}

	}

	protected void validatePickingToHU()
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();

		final I_M_HU hu = handlingUnitsBL.getById(pickingSlotRow.getHuId());

		if (handlingUnitsBL.isVirtual(hu))
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_TO_EXISTING_CUS_NOT_ALLOWED));
		}

		final Set<ShipmentScheduleId> shipmentScheduleIds = getPickingCandidateService().getScheduleIdsByHuId(pickingSlotRow.getHuId());

		if (shipmentScheduleIds.isEmpty())
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_PICKED_HU_FOUND));
		}

		final ImmutableSet<OrderId> orderIds = getShipmentSchedulePA().getByIds(shipmentScheduleIds)
				.values()
				.stream()
				.map(I_M_ShipmentSchedule::getC_Order_ID)
				.map(OrderId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (orderIds.isEmpty())
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_PICKED_HU_FOUND));
		}

		if (orderIds.size() > 1)
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_TO_THE_SAME_HU_FOR_MULTIPLE_ORDERS_NOT_ALLOWED));
		}

		final OrderId orderIdOfThePickedHU = orderIds.asList().asList().get(0);
		final OrderId orderIdOfTheCurrentSchedule = OrderId.ofRepoIdOrNull(getCurrentShipmentSchedule().getC_Order_ID());

		if (!orderIdOfThePickedHU.equals(orderIdOfTheCurrentSchedule))
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_TO_THE_SAME_HU_FOR_MULTIPLE_ORDERS_NOT_ALLOWED));
		}
	}

	protected Optional<ProcessPreconditionsResolution> checkValidSelection()
	{
		if (getParentViewRowIdsSelection() == null)
		{
			return Optional.of(ProcessPreconditionsResolution.rejectBecauseNoSelection());
		}

		if (getSelectedRowIds().isMoreThanOneDocumentId()
				|| getParentViewRowIdsSelection().getRowIds().isMoreThanOneDocumentId() )
		{
			return Optional.of(ProcessPreconditionsResolution.rejectBecauseNotSingleSelection());
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return Optional.of(ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU)));
		}

		if (pickingSlotRow.isProcessed())
		{
			return Optional.of(ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS)));
		}

		return Optional.empty();
	}
}
