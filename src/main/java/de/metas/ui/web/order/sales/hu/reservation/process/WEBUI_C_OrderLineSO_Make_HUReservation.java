package de.metas.ui.web.order.sales.hu.reservation.process;

import java.math.BigDecimal;

import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.reservation.HuReservationRequest;
import de.metas.handlingunits.reservation.HuReservationService;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
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
	private HuReservationService huReservationService;

	@Autowired
	private SalesOrderLineRepository salesOrderLineRepository;

	private static final String PARAMNAME_QTY_TO_RESERVE = "QtyToReserve";
	@Param(mandatory = true, parameterName = PARAMNAME_QTY_TO_RESERVE)
	private BigDecimal qtyToReserve;

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
			return salesOrderLine.getOrderedQty().getAsBigDecimal();
		}
		return null;
	}

	@Override
	protected String doIt()
	{
		final SalesOrderLine salesOrderLine = retrieveSalesOrderLine();

		final ImmutableList<HuId> selectedHuIds = streamSelectedHUIds(Select.ALL)
				.collect(ImmutableList.toImmutableList());

		final ImmutableAttributeSet attributeSet = ImmutableAttributeSet.ofAttributesetInstanceId(salesOrderLine.getAsiId());

		final HuReservationRequest reservationRequest = HuReservationRequest
				.builder()
				.huIds(selectedHuIds)
				.qtyToReserve(salesOrderLine.getOrderedQty().setQty(qtyToReserve))
				.productId(salesOrderLine.getProductId())
				.attributeSet(attributeSet)
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
