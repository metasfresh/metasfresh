package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.time.LocalDateTime;

import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

@Repository
public class SalesOrderLineRepository
{
	private final OrderLineRepository orderLineRepository;

	public SalesOrderLineRepository(@NonNull final OrderLineRepository orderLineRepository)
	{
		this.orderLineRepository = orderLineRepository;
	}

	public SalesOrderLine getById(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId.getRepoId(), I_C_OrderLine.class);
		return ofRecord(orderLineRecord);
	}

	public SalesOrderLine ofRecord(@NonNull final I_C_OrderLine salesOrderLineRecord)
	{
		final OrderLine orderLine = orderLineRepository.ofRecord(salesOrderLineRecord);

		final LocalDateTime preparationDate = TimeUtil.asLocalDateTime(salesOrderLineRecord.getC_Order().getPreparationDate());

		final SalesOrder salesOrder = new SalesOrder(preparationDate);
		final Quantity deliveredQty = Quantity.of(
				salesOrderLineRecord.getQtyDelivered(),
				orderLine.getOrderedQty().getUOM());

		return SalesOrderLine.builder()
				.orderLine(orderLine)
				.order(salesOrder)
				.deliveredQty(deliveredQty)
				.build();
	}
}
