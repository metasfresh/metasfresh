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

import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryRepository.DesiredDeliveryRow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_OrderPaySchedule;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Authority service for the Delivery step of {@code C_OrderPaySchedule}.
 *
 * <p>This service is the <em>sole writer</em> of all Delivery rows for a given order —
 * symmetric sibling to iter-2's {@code OrderPayScheduleLCService}.
 * {@link #recomputeDeliverySteps(OrderId)} is idempotent and total: it derives the
 * complete desired set of sub-rows from current truth and converges the DB to that set.
 *
 * <p><strong>Stub state</strong>: the business logic will be filled in Tasks 21/22.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Service
@RequiredArgsConstructor
public class OrderPayScheduleDeliveryService
{
	@NonNull private final OrderPayScheduleDeliveryRepository repo;

	/**
	 * Sole-writer entry point. Idempotent and total.
	 *
	 * <p>Loads current truth from the repository, applies the dormancy guard (R13 / AC #22),
	 * then derives the desired set of Delivery sub-rows and converges the DB to that set.
	 *
	 * <p><b>Dormancy guard (R13 / AC #22)</b>: iter-3 only acts on orders with an active
	 * proforma prepayment allocation (iter-2 LC step). Without that allocation the schedule
	 * splitting makes no sense — leave any existing single Delivery row untouched.
	 */
	public void recomputeDeliverySteps(@NonNull final OrderId orderId)
	{
		final DeliveryStepInputs inputs = repo.loadInputs(orderId);
		// Dormancy guard (R13 / AC #22): iter-3 dormant for non-proforma orders.
		// Without an iter-2 prepayment there is nothing to allocate; splitting the
		// Delivery row would produce rows with no proforma context.
		if (inputs.getProformaPrepaymentPaymentId() == null)
		{
			return;
		}
		final List<OrderPayScheduleDeliveryRepository.DesiredDeliveryRow> desired = computeDesired(inputs);
		repo.writeDeliveryRows(orderId, desired);
	}

	/**
	 * Pure function: derives the desired set of Delivery sub-rows from the given inputs.
	 *
	 * <p>Rules (REQUIREMENTS.md §3.1 + §3.2 + AC #1, #3, #5, #7, #10, #19):
	 * <ul>
	 *   <li>One sub-row per completed receipt ({@code ReceiptInfo.mInOutId}).
	 *   <li>One remainder row ({@code mInOutId = null}). Omitted when
	 *       {@code Σ receipt.withTaxValue ≥ orderGrandTotal} (over-delivery).
	 *   <li>{@code BaseAmt = orderGrandTotal} for every row (receipt sub-row and remainder).
	 *   <li>{@code DueAmt} for a receipt sub-row = {@code min(receipt.withTaxValue, remaining)}
	 *       where {@code remaining = max(0, orderGrandTotal − Σ previousReceipts)}.
	 *       In the over-delivery case the last receipt's DueAmt is capped at the remaining
	 *       capacity; we still accumulate the full {@code receipt.withTaxValue} into the
	 *       running sum so the remainder row is correctly suppressed.
	 *   <li>{@code DueAmt} for the remainder row = {@code max(0, orderGrandTotal − Σ receipts)}.
	 *   <li>Status derivation per receipt: if no matched invoice (or DR/RE) → Pending;
	 *       if CO/CL with {@code OpenAmt > 0} → Awaiting_Pay; if CO/CL with
	 *       {@code OpenAmt = 0} → Paid.
	 *   <li>Reversed invoice (RE) → C_Invoice_ID cleared on the sub-row, Status = Pending.
	 * </ul>
	 */
	public List<DesiredDeliveryRow> computeDesired(@NonNull final DeliveryStepInputs inputs)
	{
		final BigDecimal orderGrandTotal = inputs.getOrderGrandTotal().toBigDecimal();

		final List<DesiredDeliveryRow> result = new ArrayList<>();
		BigDecimal sumReceiptValues = BigDecimal.ZERO;

		for (final DeliveryStepInputs.ReceiptInfo receipt : inputs.getCompletedReceipts())
		{
			final BigDecimal receiptValue = receipt.getWithTaxValue().toBigDecimal();

			// Cap DueAmt at remaining order capacity (handles over-delivery: last receipt may exceed total)
			final BigDecimal remaining = orderGrandTotal.subtract(sumReceiptValues).max(BigDecimal.ZERO);
			final BigDecimal dueAmt = receiptValue.min(remaining);

			// Accumulate full receipt value (not the capped dueAmt) so the remainder calculation is correct
			sumReceiptValues = sumReceiptValues.add(receiptValue);

			final String status = deriveStatus(receipt.getInvoiceDocStatus(), receipt.getInvoiceOpenAmt());
			final InvoiceId cInvoiceId = resolveInvoiceId(receipt.getMatchedInvoiceId(), receipt.getInvoiceDocStatus());

			result.add(DesiredDeliveryRow.builder()
					.baseAmt(orderGrandTotal)
					.dueAmt(dueAmt)
					.status(status)
					.mInOutId(receipt.getMInOutId())
					.cInvoiceId(cInvoiceId)
					.dueDate(receipt.getMovementDate())  // delivery-step reference date = receipt MovementDate
					.build());
		}

		// Remainder row: max(0, orderGrandTotal − Σ receipts); omit if ≤ 0 (over-delivery)
		final BigDecimal remainderDue = orderGrandTotal.subtract(sumReceiptValues);
		if (remainderDue.compareTo(BigDecimal.ZERO) > 0)
		{
			result.add(DesiredDeliveryRow.builder()
					.baseAmt(orderGrandTotal)
					.dueAmt(remainderDue)
					.status(X_C_OrderPaySchedule.STATUS_Pending_Ref)
					.mInOutId(null)
					.cInvoiceId(null)
					// remainder row is Pending — no real DueDate yet (sentinel from OrderPayScheduleLineContext)
					.dueDate(LocalDate.of(9999, 12, 1))
					.build());
		}

		return result;
	}

	// -----------------------------------------------------------------------
	// Private helpers
	// -----------------------------------------------------------------------

	/**
	 * Derives the sub-row status from the invoice's DocStatus and open amount.
	 *
	 * <ul>
	 *   <li>{@code null} / {@code "DR"} / {@code "RE"} → {@code Pending}
	 *   <li>{@code "CO"} / {@code "CL"} + OpenAmt > 0 → {@code Awaiting_Pay}
	 *   <li>{@code "CO"} / {@code "CL"} + OpenAmt = 0 → {@code Paid}
	 * </ul>
	 */
	private static String deriveStatus(
			@Nullable final String invoiceDocStatus,
			@Nullable final Money invoiceOpenAmt)
	{
		if (invoiceDocStatus == null
				|| IDocument.STATUS_Drafted.equals(invoiceDocStatus)
				|| IDocument.STATUS_Reversed.equals(invoiceDocStatus))
		{
			return X_C_OrderPaySchedule.STATUS_Pending_Ref;
		}

		if (IDocument.STATUS_Completed.equals(invoiceDocStatus) || IDocument.STATUS_Closed.equals(invoiceDocStatus))
		{
			final boolean fullyPaid = invoiceOpenAmt != null
					&& invoiceOpenAmt.toBigDecimal().compareTo(BigDecimal.ZERO) == 0;
			return fullyPaid
					? X_C_OrderPaySchedule.STATUS_Paid
					: X_C_OrderPaySchedule.STATUS_Awaiting_Pay;
		}

		// Unknown doc status — treat as pending (conservative)
		return X_C_OrderPaySchedule.STATUS_Pending_Ref;
	}

	/**
	 * Returns the invoice ID to store on the sub-row, or {@code null} if the invoice
	 * should not be reflected (DR or RE status — reversed or drafted invoices are not
	 * populated on the sub-row).
	 */
	@Nullable
	private static InvoiceId resolveInvoiceId(
			@Nullable final InvoiceId matchedInvoiceId,
			@Nullable final String invoiceDocStatus)
	{
		if (matchedInvoiceId == null)
		{
			return null;
		}
		// DR and RE: do not store invoice ID on the sub-row
		if (IDocument.STATUS_Drafted.equals(invoiceDocStatus) || IDocument.STATUS_Reversed.equals(invoiceDocStatus))
		{
			return null;
		}
		return matchedInvoiceId;
	}
}
