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

package de.metas.order.paymentschedule.steps.material_receipt;

import de.metas.currency.CurrencyPrecision;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceipt;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceiptCollection;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Builder
class ReceiptValueCalculator
{
	// services
	@NonNull private final MoneyService moneyService;
	@NonNull private final IOrderBL orderBL;
	@NonNull private final IOrderLineBL orderLineBL;

	// state
	private final HashMap<OrderAndLineId, I_C_OrderLine> orderLinesCache = new HashMap<>();

	public void warmUpFrom(@NonNull final MaterialReceiptCollection receipts)
	{
		CollectionUtils.getAllOrLoad(orderLinesCache, receipts.getOrderLineIds(), orderBL::getLinesByIds);
	}

	@NonNull
	public Optional<Money> computeValue(@NonNull final MaterialReceipt receipt)
	{
		return receipt.getLines()
				.stream()
				.map(this::computeValue)
				.filter(Objects::nonNull)
				.reduce(Money::add);
	}

	@Nullable
	private Money computeValue(final MaterialReceipt.Line receiptLine)
	{
		final OrderAndLineId orderLineId = receiptLine.getOrderLineId();
		if (orderLineId == null)
		{
			return null;
		}

		final Quantity qtyReceived = receiptLine.getMovementQty();

		final I_C_OrderLine orderLine = getOrderLine(orderLineId);
		final Money lineGrossAmt = orderLineBL.getLineGrossAmt(orderLine);
		final Quantity qtyOrdered = orderLineBL.getQtyOrdered(orderLine);

		if (qtyOrdered.equals(qtyReceived))
		{
			return lineGrossAmt;
		}

		final CurrencyPrecision precision = moneyService.getStdPrecision(lineGrossAmt.getCurrencyId());
		return lineGrossAmt.divide(qtyOrdered.toBigDecimal(), CurrencyPrecision.TEN)
				.multiply(qtyReceived.toBigDecimal())
				.roundIfNeeded(precision);
	}

	private I_C_OrderLine getOrderLine(final OrderAndLineId orderLineId)
	{
		return CollectionUtils.getOrLoad(orderLinesCache, orderLineId, orderBL::getLinesByIds);
	}
}
