/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.inoutcandidate.process;

import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class M_ReceiptSchedule_AddTo_M_ShipperTransportation extends JavaProcess implements IProcessPrecondition
{
	private static final int MAX_SELECTION_SIZE = 100;

	private final PurchaseOrderToShipperTransportationService orderToShipperTransportationService = SpringContextHolder.instance.getBean(PurchaseOrderToShipperTransportationService.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
	private ShipperTransportationId p_M_ShipperTransportation_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanAllowedSelected(MAX_SELECTION_SIZE))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(MAX_SELECTION_SIZE);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<OrderAndLineId> orderAndLineIds = getOrderAndLineIds();
		final Set<OrderLineId> orderLineIds = orderAndLineIds.stream()
				.map(OrderAndLineId::getOrderLineId)
				.collect(Collectors.toSet());
		orderToShipperTransportationService.deleteShippingPackagesForOrderLines(orderLineIds);

		orderToShipperTransportationService.addOrderLinesToShipperTransportation(p_M_ShipperTransportation_ID, orderAndLineIds);

		return MSG_OK;
	}

	private Set<OrderAndLineId> getOrderAndLineIds()
	{
		final IQueryFilter<I_M_ReceiptSchedule> queryFilterOrElseFalse = getProcessInfo().getQueryFilterOrElseFalse();
		return receiptScheduleDAO.createQueryForReceiptScheduleSelection(getCtx(), queryFilterOrElseFalse)
				.create()
				.stream()
				.map(rs -> OrderAndLineId.ofRepoIds(rs.getC_Order_ID(), rs.getC_OrderLine_ID()))
				.collect(Collectors.toSet());
	}

}
