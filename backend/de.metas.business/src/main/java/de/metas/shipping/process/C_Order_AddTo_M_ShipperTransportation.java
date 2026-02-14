package de.metas.shipping.process;

import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.swat.base
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

public class C_Order_AddTo_M_ShipperTransportation extends JavaProcess implements IProcessPrecondition
{
	private static final int MAX_SELECTION_SIZE = 100;
	private final static AdMessageKey MSG_DOCUMENT_NOT_COMPLETE = AdMessageKey.of("DocumentNotComplete");
	private final static AdMessageKey MSG_ORDER_ASSIGNED_TO_DIFFERENT_TRANSPORTATION_ORDER = AdMessageKey.of("OrderAssignedToDifferentTransportationOrder");

	private final PurchaseOrderToShipperTransportationService orderToShipperTransportationService = SpringContextHolder.instance.getBean(PurchaseOrderToShipperTransportationService.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
	private ShipperTransportationId p_M_ShipperTransportation_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (context.isMoreThanAllowedSelected(MAX_SELECTION_SIZE))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(MAX_SELECTION_SIZE);
		}
		final IQueryFilter<I_C_Order> queryFilter = context.getQueryFilter(I_C_Order.class);
		final List<I_C_Order> selectedOrders = orderBL.getByQueryFilter(queryFilter);

		if (selectedOrders.stream().anyMatch(orderBL::isRequisition))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("is purchase requisition");
		}
		if (selectedOrders.stream().anyMatch(o -> !orderBL.isCompleted(o)))
		{
			return ProcessPreconditionsResolution.reject(MSG_DOCUMENT_NOT_COMPLETE);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<OrderId> orderIds = orderBL.getByQueryFilter(getProcessInfo().getQueryFilterOrElseFalse())
				.stream()
				.map(o -> OrderId.ofRepoId(o.getC_Order_ID()))
				.collect(Collectors.toSet());

		if (shipperTransportationBL.isAnyOrderAssignedToDifferentTransportationOrder(p_M_ShipperTransportation_ID, orderIds))
		{
			throw new AdempiereException(MSG_ORDER_ASSIGNED_TO_DIFFERENT_TRANSPORTATION_ORDER);
		}

		orderToShipperTransportationService.addPurchaseOrdersToShipperTransportation(p_M_ShipperTransportation_ID, orderIds);
		return MSG_OK;

	}

}
