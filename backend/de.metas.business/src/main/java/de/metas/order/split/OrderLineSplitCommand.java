/*
 * #%L
 * de.metas.business
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

package de.metas.order.split;

import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderLineSplitCommand
{
	public static final AdMessageKey MSG_QTY_TOO_LARGE = AdMessageKey.of("OrderLineSplit_QtyTooLarge");
	public static final AdMessageKey MSG_BELOW_DELIVERED = AdMessageKey.of("OrderLineSplit_QtyBelowDelivered");
	public static final AdMessageKey MSG_BELOW_INVOICED = AdMessageKey.of("OrderLineSplit_QtyBelowInvoiced");
	public static final AdMessageKey MSG_ORDER_NOT_COMPLETED = AdMessageKey.of("OrderLineSplit_OrderNotCompleted");

	private final IOrderLineBL orderLineBL;
	private final IOrderBL orderBL;

	/** Spring-managed constructor (production path). */
	public OrderLineSplitCommand()
	{
		this(Services.get(IOrderLineBL.class), Services.get(IOrderBL.class));
	}

	/** Test-friendly constructor. */
	OrderLineSplitCommand(
			@NonNull final IOrderLineBL orderLineBL,
			@NonNull final IOrderBL orderBL)
	{
		this.orderLineBL = orderLineBL;
		this.orderBL = orderBL;
	}

	public OrderLineSplitResult split(@NonNull final OrderLineSplitRequest request)
	{
		final I_C_OrderLine original = orderLineBL.getOrderLineById(request.getOrderLineId());
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(original.getC_Order_ID()));

		validate(order, original, request.getQtyToSplitOff());

		throw new UnsupportedOperationException("Clone path implemented in Task 7");
	}

	private void validate(
			@NonNull final I_C_Order order,
			@NonNull final I_C_OrderLine original,
			@NonNull final BigDecimal qtyToSplitOff)
	{
		if (!DocStatus.Completed.getCode().equals(order.getDocStatus()))
		{
			throw new AdempiereException(MSG_ORDER_NOT_COMPLETED);
		}
		if (qtyToSplitOff.signum() <= 0 || qtyToSplitOff.compareTo(original.getQtyOrdered()) >= 0)
		{
			throw new AdempiereException(MSG_QTY_TOO_LARGE, qtyToSplitOff, original.getQtyOrdered());
		}
		final BigDecimal newQtyOrdered = original.getQtyOrdered().subtract(qtyToSplitOff);
		if (newQtyOrdered.compareTo(original.getQtyDelivered()) < 0)
		{
			throw new AdempiereException(MSG_BELOW_DELIVERED, newQtyOrdered, original.getQtyDelivered());
		}
		if (newQtyOrdered.compareTo(original.getQtyInvoiced()) < 0)
		{
			throw new AdempiereException(MSG_BELOW_INVOICED, newQtyOrdered, original.getQtyInvoiced());
		}
	}
}
