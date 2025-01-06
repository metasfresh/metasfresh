/*
 * #%L
 * de.metas.servicerepair.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.servicerepair.sales_order.handleordervoid;

import de.metas.handlingunits.reservation.handleordervoid.HUReservationAfterOrderUnProcessService;
import de.metas.handlingunits.reservation.handleordervoid.HandlerResult;
import de.metas.handlingunits.reservation.handleordervoid.IUReservationAfterOrderUnProcessHandler;
import de.metas.order.OrderId;
import de.metas.servicerepair.sales_order.RepairSalesOrderInfo;
import de.metas.servicerepair.sales_order.RepairSalesOrderService;
import de.metas.servicerepair.sales_order.RepairSalesProposalInfo;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Supposed to be injected into {@link HUReservationAfterOrderUnProcessService} and be executed when an order is voided or reversed.
 */
@Component
public class RepairOrderUnProcessHandler implements IUReservationAfterOrderUnProcessHandler
{
	private final RepairSalesOrderService salesOrderService;

	public RepairOrderUnProcessHandler(@NonNull final RepairSalesOrderService salesOrderService)
	{
		this.salesOrderService = salesOrderService;
	}

	@Override
	public HandlerResult onOrderVoid(@NonNull final OrderId orderId, @NonNull final DocTimingType timing)
	{
		if (!timing.isVoid() && !timing.isReverse() && !timing.isReactivate())
		{
			return HandlerResult.NOT_HANDELED;
		}
		
		final de.metas.handlingunits.model.I_C_Order order = InterfaceWrapperHelper.load(orderId, de.metas.handlingunits.model.I_C_Order.class);
		final Optional<RepairSalesProposalInfo> proposal = salesOrderService.extractSalesProposalInfo(order);
		if (proposal.isPresent())
		{
			return HandlerResult.NOT_HANDELED;
		}

		final Optional<RepairSalesOrderInfo> repairSalesOrderInfo = salesOrderService.extractSalesOrderInfo(order);
		if (repairSalesOrderInfo.isEmpty())
		{
			return HandlerResult.NOT_HANDELED;
		}

		salesOrderService.transferVHUsFromSalesOrderToProject(repairSalesOrderInfo.get());
		return HandlerResult.HANDELED_DONE; // this order is special. Other handlers shall heep their hands off it.
	}
}
