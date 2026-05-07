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

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleLineContext;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.core.OrderSchedulingContext;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceipt;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceiptCollection;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.OrderPayScheduleMaterialReceiptService;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.OrderPayScheduleRegularInvoiceService;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.RegularInvoice;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleMaterialReceiptStepService
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	@NonNull private final MoneyService moneyService;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final OrderPayScheduleMaterialReceiptService receiptService;
	@NonNull private final OrderPayScheduleRegularInvoiceService regularInvoiceService;

	public void recomputeDeliverySteps(@NonNull final OrderId orderId)
	{
		recomputeDeliverySteps(orderId, null, null, null, null);
	}

	/**
	 * Overload used by {@code C_Invoice}'s AFTER_COMPLETE interceptor to thread the in-memory
	 * {@code RegularInvoice} all the way down to the per-receipt lookup, bypassing the stale
	 * {@code DocStatus="IP"} that the DB still shows at that point in the completion lifecycle.
	 * See {@link OrderPayScheduleRegularInvoiceService#getByReceipt(MaterialReceipt, RegularInvoice)}
	 * for the full explanation.
	 *
	 * @param orderId          the order whose delivery steps must be recomputed
	 * @param completingInvoice the in-memory invoice that is currently completing (not yet {@code CO} in DB)
	 */
	public void recomputeDeliveryStepsAfterInvoiceCompleted(@NonNull final OrderId orderId, @NonNull final RegularInvoice completingInvoice)
	{
		recomputeDeliverySteps(orderId, null, null, completingInvoice, null);
	}

	/**
	 * Overload used by {@code M_InOut}'s AFTER_REVERSECORRECT / AFTER_REVERSEACCRUAL interceptor
	 * to thread the in-memory reversed receipt down to the receipt-retrieval path so it is
	 * excluded from the receipts list despite still appearing as {@code DocStatus=CO /
	 * Reversal_ID=0} in the DB at the time the interceptor fires.
	 *
	 * <p>Why this overload exists: {@code MInOut.reverseCorrectIt()} sets {@code Reversal_ID}
	 * on the in-memory original receipt at line 2208 but does NOT save it before firing
	 * AFTER_REVERSECORRECT at line 2262.  Any listener that re-reads the receipt from DB sees
	 * {@code Reversal_ID=0} + {@code DocStatus=CO} – i.e. it looks "still active" – even though
	 * we are inside the after-reverse callback.  We thread the in-memory reversed receipt down to
	 * the receipt-retrieval path so the lookup excludes this one receipt while reading all others
	 * normally.
	 *
	 * <p>Sibling pattern to {@link #recomputeDeliveryStepsAfterInvoiceCompleted} from J.1 (which
	 * threads the in-memory completing invoice through to bypass the analogous
	 * {@code DocStatus=IP} gap during AFTER_COMPLETE).
	 *
	 * @param orderId         the order whose delivery steps must be recomputed
	 * @param reversedReceipt the in-memory receipt that is currently being reversed
	 *                        (its {@code Reversal_ID} is set in memory but not yet saved to DB)
	 */
	public void recomputeDeliveryStepsAfterReceiptReversed(@NonNull final OrderId orderId, @NonNull final I_M_InOut reversedReceipt)
	{
		recomputeDeliverySteps(orderId, null, reversedReceipt, null, null);
	}

	/**
	 * Overload used by {@code C_Invoice}'s AFTER_REVERSECORRECT / AFTER_REVERSEACCRUAL interceptor
	 * to thread the in-memory reversed invoice down to the invoice-retrieval path so it is
	 * excluded from per-receipt invoice lookups despite still appearing as {@code DocStatus=CO}
	 * in the DB at the time the interceptor fires.
	 *
	 * <p>Why this overload exists: {@code MInvoice.reverseCorrectIt()} sets {@code DocStatus=RE}
	 * on the in-memory invoice at line 1456 but the document is NOT saved to the DB until
	 * {@code AbstractDocumentBL.processEx()} returns (after all interceptors have fired).
	 * Any listener that re-reads the invoice from DB sees {@code DocStatus=CO} — i.e. still active.
	 * We thread the in-memory reversed invoice's ID as an exclude parameter so the lookup skips
	 * this invoice while reading all others normally.
	 *
	 * <p>Sibling pattern to {@link #recomputeDeliveryStepsAfterReceiptReversed} (K.3) which
	 * threads the reversed receipt to exclude it despite {@code Reversal_ID} not yet saved to DB.
	 *
	 * @param orderId         the order whose delivery steps must be recomputed
	 * @param reversedInvoice the in-memory invoice that is currently being reversed
	 *                        (its {@code DocStatus=RE} is set in memory but not yet saved to DB)
	 */
	public void recomputeDeliveryStepsAfterInvoiceReversed(@NonNull final OrderId orderId, @NonNull final I_C_Invoice reversedInvoice)
	{
		recomputeDeliverySteps(orderId, null, null, null, InvoiceId.ofRepoId(reversedInvoice.getC_Invoice_ID()));
	}

	public void recomputeDeliverySteps(@NonNull final I_M_InOut inoutRecord)
	{
		// Only purchase receipts drive the Delivery step
		if (!OrderPayScheduleMaterialReceiptService.isMaterialReceipt(inoutRecord))
		{
			return;
		}

		// Must be linked to a purchase order
		final OrderId orderId = OrderPayScheduleMaterialReceiptService.extractOrderIdOrNull(inoutRecord);
		if (orderId == null)
		{
			return;
		}

		recomputeDeliverySteps(orderId, inoutRecord, null, null, null);
	}

	private void recomputeDeliverySteps(
			@NonNull final OrderId orderId,
			@Nullable final I_M_InOut completingReceipt,
			@Nullable final I_M_InOut excludeReceipt,
			@Nullable final RegularInvoice completingInvoice,
			@Nullable final InvoiceId excludeInvoiceId)
	{
		final OrderSchedulingContext order = orderPayScheduleService.getContextById(orderId).orElse(null);
		if (order == null || !order.isComplexPaymentTerm()) {return;}

		final MaterialReceiptCollection receiptInfos = receiptService.getByOrderId(orderId, completingReceipt, excludeReceipt);

		orderPayScheduleService.updateById(
				orderId,
				orderPaySchedule -> updateOrderPaySchedule(orderPaySchedule, order, receiptInfos, completingInvoice, excludeInvoiceId));
	}

	private void updateOrderPaySchedule(
			final OrderPaySchedule orderPaySchedule,
			final OrderSchedulingContext order,
			final MaterialReceiptCollection receiptInfos,
			@Nullable final RegularInvoice completingInvoice,
			@Nullable final InvoiceId excludeInvoiceId)
	{
		order.getBreaksBy(OrderPayScheduleMaterialReceiptStepService::isEligibleBreak)
				.forEach(termBreak -> {
					final List<OrderPayScheduleLine> newLines = computeOrderPayScheduleLines(receiptInfos, order, termBreak, orderPaySchedule, completingInvoice, excludeInvoiceId);
					orderPaySchedule.replaceLinesByBreakId(newLines);
				});
	}

	private static boolean isEligibleBreak(final PaymentTermBreak termBreak)
	{
		return termBreak.getReferenceDateType().isMaterialReceiptDate();
	}

	// Package-visible for unit tests in the same package; production callers go through recomputeDeliverySteps.
	List<OrderPayScheduleLine> computeOrderPayScheduleLines(
			@NonNull final MaterialReceiptCollection receipts,
			@NonNull final OrderSchedulingContext order,
			@NonNull final PaymentTermBreak termBreak,
			@NonNull final OrderPaySchedule orderPaySchedule)
	{
		return computeOrderPayScheduleLines(receipts, order, termBreak, orderPaySchedule, null, null);
	}

	List<OrderPayScheduleLine> computeOrderPayScheduleLines(
			@NonNull final MaterialReceiptCollection receipts,
			@NonNull final OrderSchedulingContext order,
			@NonNull final PaymentTermBreak termBreak,
			@NonNull final OrderPaySchedule orderPaySchedule,
			@Nullable final RegularInvoice completingInvoice)
	{
		return computeOrderPayScheduleLines(receipts, order, termBreak, orderPaySchedule, completingInvoice, null);
	}

	List<OrderPayScheduleLine> computeOrderPayScheduleLines(
			@NonNull final MaterialReceiptCollection receipts,
			@NonNull final OrderSchedulingContext order,
			@NonNull final PaymentTermBreak termBreak,
			@NonNull final OrderPaySchedule orderPaySchedule,
			@Nullable final RegularInvoice completingInvoice,
			@Nullable final InvoiceId excludeInvoiceId)
	{
		final ArrayList<OrderPayScheduleLine> existingLines = orderPaySchedule.streamLinesByBreakId(termBreak.getId())
				.filter(OrderPayScheduleLine::isSaved)
				.collect(GuavaCollectors.toArrayList());

		final ReceiptValueCalculator receiptValueCalculator = newReceiptValueCalculator();
		receiptValueCalculator.warmUpFrom(receipts);

		Money totalReceiptValue = order.getGrandTotal().toZero(); // Σ receipt.with_tax across all receipts

		final ArrayList<OrderPayScheduleLine> result = new ArrayList<>();
		for (final MaterialReceipt receipt : receipts)
		{
			final Money receiptValue = receiptValueCalculator.computeValue(receipt).orElse(null);
			if (receiptValue == null)
			{
				continue;
			}

			// I-1: BaseAmt = receipt GrandTotal (with-tax); DueAmt = BaseAmt × break%
			final Money dueAmt = receiptValue.multiply(termBreak.getPercent(), order.getPrecision());
			final RegularInvoice invoice = regularInvoiceService.getByReceipt(receipt, completingInvoice, excludeInvoiceId).orElse(null);
			final OrderPayScheduleStatus status = computeOrderPayScheduleStatus(invoice);

			final OrderPayScheduleLine existingLine = CollectionUtils.removeFirst(existingLines, line -> InOutId.equals(line.getInoutId(), receipt.getId()));

			final OrderPayScheduleLine line = existingLine != null
					? existingLine
					: OrderPayScheduleLine.from(order, termBreak);

			line.applyAndProcess(OrderPayScheduleLineContext.builder()
					.status(status)
					.referenceDate(receipt.getMovementDate())  // delivery-step reference date = receipt MovementDate
					.dueDate(receipt.getMovementDate().plusDays(termBreak.getOffsetDays()))
					.baseAmount(receiptValue)        // receipt-row: BaseAmt = receipt.GrandTotal (with-tax)
					.dueAmount(dueAmt)               // DueAmt = BaseAmt × break%
					.dueAmountActual(dueAmt)
					.inoutId(receipt.getId())
					.invoiceId(invoice != null ? invoice.getId() : null)
					.build());

			result.add(line);

			totalReceiptValue = totalReceiptValue.add(receiptValue);
		}

		// Remainder row: BaseAmt = max(0, order.GrandTotal − Σ receipt.with_tax); omit if BaseAmt ≤ 0 (over-delivery)
		// I-4: DueAmt = BaseAmt × break%
		final Money remainderBaseAmt = order.getGrandTotal().subtract(totalReceiptValue).toZeroIfNegative();
		if (remainderBaseAmt.signum() > 0)
		{
			final Money remainderDueAmt = remainderBaseAmt.multiply(termBreak.getPercent(), order.getPrecision());

			final OrderPayScheduleLine existingLine = CollectionUtils.removeFirst(existingLines, line -> line.getInoutId() == null);

			final OrderPayScheduleLine line = existingLine != null
					? existingLine
					: OrderPayScheduleLine.from(order, termBreak);

			line.applyAndProcess(OrderPayScheduleLineContext.builder()
					.status(OrderPayScheduleStatus.Pending)
					.referenceDate(OrderPayScheduleLineContext.INFINITE_FUTURE_DATE)
					.dueDate(OrderPayScheduleLineContext.INFINITE_FUTURE_DATE)
					.baseAmount(remainderBaseAmt)    // remainder-row: BaseAmt = max(0, order.GrandTotal − Σ receipt.with_tax)
					.dueAmount(remainderDueAmt)       // DueAmt = BaseAmt × break%
					.dueAmountActual(remainderDueAmt)
					.inoutId(null)
					.invoiceId(null)
					.build());

			result.add(line);
		}

		return result;
	}

	private static OrderPayScheduleStatus computeOrderPayScheduleStatus(@Nullable final RegularInvoice invoice)
	{
		if (invoice != null)
		{
			return invoice.isPaid() ? OrderPayScheduleStatus.Paid : OrderPayScheduleStatus.Awaiting_Pay;
		}
		else
		{
			return OrderPayScheduleStatus.Pending;
		}
	}

	private ReceiptValueCalculator newReceiptValueCalculator()
	{
		return ReceiptValueCalculator.builder()
				.moneyService(moneyService)
				.orderBL(orderBL)
				.orderLineBL(orderLineBL)
				.build();
	}

}
