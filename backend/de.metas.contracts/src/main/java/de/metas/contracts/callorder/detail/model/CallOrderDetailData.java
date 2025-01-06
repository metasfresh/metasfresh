/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.detail.model;

import de.metas.contracts.callorder.summary.model.CallOrderSummaryId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

@Value
public class CallOrderDetailData
{
	@NonNull
	CallOrderSummaryId summaryId;

	@Nullable
	OrderDetail orderDetail;

	@Nullable
	ShipmentDetail shipmentDetail;

	@Nullable
	InvoiceDetail invoiceDetail;

	@Builder
	public CallOrderDetailData(
			@NonNull final CallOrderSummaryId summaryId,
			@Nullable final OrderId orderId,
			@Nullable final OrderLineId orderLineId,
			@Nullable final Quantity qtyEntered,
			@Nullable final InOutId shipmentId,
			@Nullable final InOutLineId shipmentLineId,
			@Nullable final Quantity qtyDelivered,
			@Nullable final InvoiceId invoiceId,
			@Nullable final InvoiceAndLineId invoiceAndLineId,
			@Nullable final Quantity qtyInvoiced)
	{
		final long nonNullSources = Stream.of(orderLineId, shipmentLineId, invoiceAndLineId)
				.filter(Objects::nonNull)
				.count();

		if (nonNullSources == 0)
		{
			throw new AdempiereException("One of orderLineId, shipmentLineId, invoiceLineId must be provided!");
		}

		if (nonNullSources > 1)
		{
			throw new AdempiereException("Only one of orderLineId, shipmentLineId, invoiceLineId must be provided!");
		}

		if (orderLineId != null)
		{
			Check.assumeNotNull(qtyEntered, "qtyEntered cannot be missing on detail referencing order line!");
			Check.assumeNotNull(orderId, "orderId cannot be missing on detail referencing order line!");

			this.orderDetail = OrderDetail.builder()
					.orderId(orderId)
					.orderLineId(orderLineId)
					.qtyEntered(qtyEntered)
					.build();

			this.shipmentDetail = null;
			this.invoiceDetail = null;
		}

		else if (shipmentLineId != null)
		{
			Check.assumeNotNull(qtyDelivered, "qtyDelivered cannot be missing on detail referencing shipment line!");
			Check.assumeNotNull(shipmentId, "shipmentId cannot be missing on detail referencing shipment line!");

			this.shipmentDetail = ShipmentDetail.builder()
					.shipmentId(shipmentId)
					.shipmentLineId(shipmentLineId)
					.qtyDelivered(qtyDelivered)
					.build();

			this.orderDetail = null;
			this.invoiceDetail = null;
		}

		else
		{
			Check.assumeNotNull(qtyInvoiced, "qtyInvoiced cannot be missing on detail referencing invoice line!");
			Check.assumeNotNull(invoiceId, "invoiceId cannot be missing on detail referencing invoice line!");

			this.invoiceDetail = InvoiceDetail.builder()
					.invoiceId(invoiceId)
					.invoiceAndLineId(invoiceAndLineId)
					.qtyInvoiced(qtyInvoiced)
					.build();

			this.orderDetail = null;
			this.shipmentDetail = null;
		}

		this.summaryId = summaryId;
	}

	@Value
	@Builder
	public static class InvoiceDetail
	{
		@NonNull
		InvoiceId invoiceId;
		@NonNull
		InvoiceAndLineId invoiceAndLineId;
		@NonNull
		Quantity qtyInvoiced;
	}

	@Value
	@Builder
	public static class ShipmentDetail
	{
		@NonNull
		InOutId shipmentId;
		@NonNull
		InOutLineId shipmentLineId;
		@NonNull
		Quantity qtyDelivered;

	}

	@Value
	@Builder
	public static class OrderDetail
	{
		@NonNull
		OrderId orderId;
		@NonNull
		OrderLineId orderLineId;
		@NonNull
		Quantity qtyEntered;
	}
}
