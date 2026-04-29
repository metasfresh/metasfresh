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

import de.metas.invoice.InvoiceId;
import de.metas.inout.InOutId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Immutable snapshot of all inputs required by
 * {@link OrderPayScheduleDeliveryService#recomputeDeliverySteps(OrderId)}
 * to derive the desired set of Delivery sub-rows.
 *
 * <p>Loaded once per recompute call via
 * {@link OrderPayScheduleDeliveryRepository#loadInputs(OrderId)}.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Value
@Builder
public class DeliveryStepInputs
{
	/** The order this snapshot belongs to. */
	@NonNull OrderId orderId;

	/**
	 * Order grand total — used as the 100% base for remainder calculation.
	 * {@code remainder = orderGrandTotal - sum(receipt.withTaxValue)}
	 */
	@NonNull Money orderGrandTotal;

	/**
	 * LC percentage, 0–100 (e.g. 30 for a 30/70 LC-delivery split).
	 * Derived from the LC break in the payment term.
	 */
	@NonNull BigDecimal lcPercent;

	/**
	 * Delivery percentage, 0–100 (e.g. 70 for a 30/70 LC-delivery split).
	 * Invariant: {@code lcPercent + deliveryPercent == 100}.
	 */
	@NonNull BigDecimal deliveryPercent;

	/**
	 * All completed (DocStatus=CO) receipts for this order, ordered by movement date.
	 * May be empty for orders where no goods have been received yet.
	 */
	@NonNull @Singular List<ReceiptInfo> completedReceipts;

	/**
	 * Payment ID of the iter-2 proforma prepayment payment, if the LC step has been paid.
	 * {@code null} when the LC step is still Pending or Awaiting_Pay.
	 */
	@Nullable PaymentId proformaPrepaymentPaymentId;

	// -----------------------------------------------------------------------
	// Inner type
	// -----------------------------------------------------------------------

	/**
	 * Snapshot of one completed receipt relevant to this order's delivery sub-rows.
	 */
	@Value
	@Builder
	public static class ReceiptInfo
	{
		/** M_InOut primary key. */
		@NonNull InOutId mInOutId;

		/**
		 * The receipt's {@code MovementDate} — used as the delivery-step DueDate reference.
		 * Per iter-3 design: {@code dueDate = movementDate} for sub-rows (offset = 0).
		 * Populated by {@link OrderPayScheduleDeliveryRepository#loadInputs(OrderId)}.
		 */
		@NonNull LocalDate movementDate;

		/**
		 * Value of this receipt including tax, computed per order-line tax rate via
		 * {@link ReceiptTaxCalculator}.
		 * {@code withTaxValue = sum(line.movementQty × orderLine.priceActual × (1 + taxRate/100))}
		 */
		@NonNull Money withTaxValue;

		/**
		 * Invoice already matched to this receipt, if any.
		 * {@code null} until the financial invoice for this shipment is completed.
		 * When {@code invoiceDocStatus = "RE"} (reversed), this field is treated as
		 * {@code null} by the service — the sub-row status reverts to Pending and
		 * {@code C_Invoice_ID} is cleared.
		 */
		@Nullable InvoiceId matchedInvoiceId;

		/**
		 * DocStatus of the matched invoice.
		 * {@code null} when {@code matchedInvoiceId} is {@code null}.
		 * Relevant values: {@code "DR"} (ignored — no status change),
		 * {@code "CO"} / {@code "CL"} (→ Awaiting_Pay or Paid),
		 * {@code "RE"} (→ Pending, C_Invoice_ID cleared).
		 */
		@Nullable String invoiceDocStatus;

		/**
		 * Open amount of the matched invoice after any existing allocations.
		 * {@code null} when {@code matchedInvoiceId} is {@code null}.
		 * Used to distinguish Awaiting_Pay (OpenAmt > 0) from Paid (OpenAmt = 0).
		 */
		@Nullable Money invoiceOpenAmt;

		/**
		 * Amount already allocated from the iter-2 proforma prepayment to this receipt's invoice.
		 * {@code null} when no allocation exists yet (receipt arrived but no invoice yet, or
		 * allocation was reversed).
		 */
		@Nullable Money iter3PrepayAlloc;
	}
}
