package de.metas.ui.web.order.sales.hu.reservation.process;

import java.math.BigDecimal;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.UOMConversionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.handlingunits.reservation.RetrieveAvailableHUQtyRequest;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.util.Services;
import lombok.NonNull;

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

public class WEBUI_C_OrderLineSO_Make_HUReservation
		extends HUEditorProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired
	private HUReservationService huReservationService;

	@Autowired
	private SalesOrderLineRepository salesOrderLineRepository;

	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private static final String PARAMNAME_QTY_TO_RESERVE = "QtyToReserve";
	@Param(mandatory = true, parameterName = PARAMNAME_QTY_TO_RESERVE)
	private BigDecimal qtyToReserveBD;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final boolean anyActiveHuSelected = streamSelectedHUs(Select.ALL)
				.anyMatch(hu -> X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()));

		return ProcessPreconditionsResolution.acceptIf(anyActiveHuSelected);
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (PARAMNAME_QTY_TO_RESERVE.equals(parameter.getColumnName()))
		{
			final SalesOrderLine salesOrderLine = retrieveSalesOrderLine();
			final Quantity requiredQty = salesOrderLine.getOrderedQty().subtract(salesOrderLine.getDeliveredQty());

			final ImmutableList<HuId> selectedHuIds = streamSelectedHUIds(Select.ALL)
					.collect(ImmutableList.toImmutableList());

			final RetrieveAvailableHUQtyRequest request = RetrieveAvailableHUQtyRequest
					.builder()
					.huIds(selectedHuIds)
					.productId(salesOrderLine.getProductId())
					.build();
			final Quantity availableQty = huReservationService.retrieveAvailableQty(request);

			final Quantity availableQtyInSalesOrderUOM = uomConversionBL.convertQuantityTo(
					requiredQty,
					UOMConversionContext.of(salesOrderLine.getProductId()),
					availableQty.getUOM());

			return availableQty.min(availableQtyInSalesOrderUOM).getAsBigDecimal();
		}
		return null;
	}

	@Override
	protected String doIt()
	{
		final SalesOrderLine salesOrderLine = retrieveSalesOrderLine();

		final ImmutableList<HuId> selectedHuIds = streamSelectedHUIds(Select.ALL)
				.collect(ImmutableList.toImmutableList());

		final Quantity qtyToReserve = Quantity.of(qtyToReserveBD, salesOrderLine.getOrderedQty().getUOM());

		final ReserveHUsRequest reservationRequest = ReserveHUsRequest
				.builder()
				.huIds(selectedHuIds)
				.productId(salesOrderLine.getProductId())
				.qtyToReserve(qtyToReserve)
				.salesOrderLineId(salesOrderLine.getId().getOrderLineId())
				.build();
		huReservationService.makeReservation(reservationRequest);

		return MSG_OK;
	}

	private SalesOrderLine retrieveSalesOrderLine()
	{
		final OrderLineId orderLineId = getView()
				.getParameterAsIdOrNull(WEBUI_C_OrderLineSO_Launch_HUEditor.VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID);

		final SalesOrderLine salesOrderLine = salesOrderLineRepository.getById(orderLineId);
		return salesOrderLine;
	}
}
