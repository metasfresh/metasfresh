/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipping.process;

import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.util.List;
import java.util.Set;

import static de.metas.shipping.process.C_Order_AddTo_M_ShipperTransportation.MSG_DOCUMENT_NOT_COMPLETE;

public class C_OrderLine_AddTo_M_ShipperTransportation extends AddOrderLinesToShipperTransportation
{
	private static final AdMessageKey MSG_ORDER_LINE_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER = AdMessageKey.of("OrderLineAssignedToProcessedTransportationOrder");
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final ProcessPreconditionsResolution processPreconditionsResolution = super.checkPreconditionsApplicable(context);
		if (processPreconditionsResolution.isRejected())
		{
			return processPreconditionsResolution;
		}
		final IQueryFilter<I_C_OrderLine> contextQueryFilter = context.getQueryFilter(I_C_OrderLine.class);
		final List<I_C_Order> selectedOrders = orderDAO.getByLineQueryFilter(contextQueryFilter);

		if (selectedOrders.stream().anyMatch(orderBL::isRequisition))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("is purchase requisition");
		}
		if (selectedOrders.stream().anyMatch(o -> !orderBL.isCompleted(o)))
		{
			return ProcessPreconditionsResolution.reject(MSG_DOCUMENT_NOT_COMPLETE);
		}

		final IQueryFilter<I_C_OrderLine> selectedNonPackagingLines = queryBL.createCompositeQueryFilter(I_C_OrderLine.class)
				.addFilter(contextQueryFilter)
				.addNotEqualsFilter(I_C_OrderLine.COLUMNNAME_IsPackagingMaterial, true);
		if (orderDAO.getLineIdsByQueryFilter(selectedNonPackagingLines).isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only packing material lines selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected Set<OrderLineId> getOrderLineIds()
	{
		final IQueryFilter<I_C_OrderLine> processAndNotPackingMaterial = queryBL.createCompositeQueryFilter(I_C_OrderLine.class)
				.addFilter(getProcessInfo().getQueryFilterOrElseFalse())
				.addNotEqualsFilter(I_C_OrderLine.COLUMNNAME_IsPackagingMaterial, true);
		return orderDAO.getLineIdsByQueryFilter(processAndNotPackingMaterial);
	}

	@Override
	protected AdMessageKey getFailureMessage()
	{
		return MSG_ORDER_LINE_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER;
	}

}
