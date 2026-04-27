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

package de.metas.order.payschedule.delivery;

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderPaySchedule;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Data-access layer for the Delivery sub-rows of {@code C_OrderPaySchedule}.
 *
 * <p>All mutating methods are called exclusively by
 * {@link OrderPayScheduleDeliveryService} (sole-writer pattern — see iter-2 §E).
 *
 * <p><strong>Implementation note:</strong> all methods currently throw
 * {@link UnsupportedOperationException}; they will be filled in Tasks 21 and 22b.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Repository
public class OrderPayScheduleDeliveryRepository
{
	/**
	 * Returns all Delivery-type {@code C_OrderPaySchedule} rows for the given order,
	 * including both sub-rows (with {@code M_InOut_ID} set) and the remainder row
	 * (with {@code M_InOut_ID} null).
	 *
	 * @throws UnsupportedOperationException TODO Task 22b
	 */
	public List<I_C_OrderPaySchedule> getDeliveryRowsByOrderId(@NonNull final OrderId orderId)
	{
		throw new UnsupportedOperationException("TODO Task 22b");
	}

	/**
	 * Loads the full {@link DeliveryStepInputs} snapshot for the given order.
	 * Aggregates order grand total, LC%/Delivery%, completed receipts, per-line tax, and
	 * any existing proforma-prepayment allocation.
	 *
	 * @throws UnsupportedOperationException TODO Task 21
	 */
	public DeliveryStepInputs loadInputs(@NonNull final OrderId orderId)
	{
		throw new UnsupportedOperationException("TODO Task 21");
	}

	/**
	 * Converges the existing Delivery sub-rows for the given order to the {@code desired} set
	 * (insert / update / delete as needed — "upsert" semantics).
	 * Matching is by {@code M_InOut_ID} (null for the remainder row).
	 *
	 * @throws UnsupportedOperationException TODO Task 22b
	 */
	public void writeDeliveryRows(@NonNull final OrderId orderId, @NonNull final List<DesiredDeliveryRow> desired)
	{
		throw new UnsupportedOperationException("TODO Task 22b");
	}

	/**
	 * Loads a {@code C_Invoice} record by its ID. Used by
	 * {@link OrderPayScheduleDeliveryService} to determine invoice DocStatus and
	 * {@code IsPartialInvoice} flag.
	 *
	 * @throws UnsupportedOperationException TODO Task 22a
	 */
	public I_C_Invoice getInvoice(@NonNull final InvoiceId id)
	{
		throw new UnsupportedOperationException("TODO Task 22a");
	}

	// -----------------------------------------------------------------------
	// Inner type — desired row descriptor
	// -----------------------------------------------------------------------

	/**
	 * Describes the desired state of a single Delivery sub-row (or remainder row).
	 * Created by {@link OrderPayScheduleDeliveryService#computeDesired(DeliveryStepInputs)}
	 * and consumed by {@link #writeDeliveryRows(OrderId, List)}.
	 */
	@Value
	@Builder
	public static class DesiredDeliveryRow
	{
		/**
		 * The eligible base amount for this sub-row.
		 * {@code baseAmt = receipt.withTaxValue} for sub-rows; {@code orderGrandTotal - sum(receipts)} for the remainder row.
		 */
		@NonNull BigDecimal baseAmt;

		/**
		 * The actual due amount for this sub-row.
		 * {@code dueAmt = round(baseAmt × deliveryPercent / 100, 2)}.
		 */
		@NonNull BigDecimal dueAmt;

		/**
		 * Status string — one of {@code "Pending"}, {@code "Awaiting_Pay"}, {@code "Paid"}.
		 * Mapped to {@code X_C_OrderPaySchedule.STATUS_*} constants by
		 * {@link #writeDeliveryRows}.
		 */
		@NonNull String status;

		/**
		 * Receipt FK — set on sub-rows, {@code null} on the remainder row.
		 */
		@Nullable InOutId mInOutId;

		/**
		 * Invoice FK — set once a financial invoice is matched and completed for this receipt,
		 * {@code null} until then.
		 */
		@Nullable InvoiceId cInvoiceId;
	}
}
