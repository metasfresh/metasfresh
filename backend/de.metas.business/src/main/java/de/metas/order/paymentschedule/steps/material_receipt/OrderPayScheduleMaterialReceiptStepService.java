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
		recomputeDeliverySteps(orderId, null);
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

		recomputeDeliverySteps(orderId, inoutRecord);
	}

	private void recomputeDeliverySteps(@NonNull final OrderId orderId, @Nullable final I_M_InOut completingReceipt)
	{
		final OrderSchedulingContext order = orderPayScheduleService.getContextById(orderId).orElse(null);
		if (order == null || !order.isComplexPaymentTerm()) {return;}

		final MaterialReceiptCollection receiptInfos = receiptService.getByOrderId(orderId, completingReceipt);

		orderPayScheduleService.updateById(
				orderId,
				orderPaySchedule -> updateOrderPaySchedule(orderPaySchedule, order, receiptInfos));
	}

	private void updateOrderPaySchedule(final OrderPaySchedule orderPaySchedule, final OrderSchedulingContext order, final MaterialReceiptCollection receiptInfos)
	{
		order.getBreaksBy(OrderPayScheduleMaterialReceiptStepService::isEligibleBreak)
				.forEach(termBreak -> {
					final List<OrderPayScheduleLine> newLines = computeOrderPayScheduleLines(receiptInfos, order, termBreak, orderPaySchedule);
					orderPaySchedule.replaceLinesByBreakId(newLines);
				});
	}

	private static boolean isEligibleBreak(final PaymentTermBreak termBreak)
	{
		return termBreak.getReferenceDateType().isMaterialReceiptDate();
	}

	List<OrderPayScheduleLine> computeOrderPayScheduleLines(
			@NonNull final MaterialReceiptCollection receipts,
			@NonNull final OrderSchedulingContext order,
			@NonNull final PaymentTermBreak termBreak,
			@NonNull final OrderPaySchedule orderPaySchedule)
	{
		final ArrayList<OrderPayScheduleLine> existingLines = orderPaySchedule.streamLinesByBreakId(termBreak.getId())
				.filter(OrderPayScheduleLine::isSaved)
				.collect(GuavaCollectors.toArrayList());

		final ReceiptValueCalculator receiptValueCalculator = newReceiptValueCalculator();
		receiptValueCalculator.warmUpFrom(receipts);

		Money dueAmtRemaining = order.getGrandTotalByBreakId(termBreak.getId());

		final ArrayList<OrderPayScheduleLine> result = new ArrayList<>();
		for (final MaterialReceipt receipt : receipts)
		{
			final Money receiptValue = receiptValueCalculator.computeValue(receipt).orElse(null);
			if (receiptValue == null)
			{
				continue;
			}

			final Money dueAmt = receiptValue.min(dueAmtRemaining);
			final RegularInvoice invoice = regularInvoiceService.getByReceipt(receipt).orElse(null);
			final OrderPayScheduleStatus status = computeOrderPayScheduleStatus(invoice);

			final OrderPayScheduleLine existingLine = CollectionUtils.removeFirst(existingLines, line -> InOutId.equals(line.getInoutId(), receipt.getId()));

			final OrderPayScheduleLine line = existingLine != null
					? existingLine
					: OrderPayScheduleLine.from(order, termBreak);

			line.applyAndProcess(OrderPayScheduleLineContext.builder()
					.status(status)
					.referenceDate(receipt.getMovementDate())  // delivery-step reference date = receipt MovementDate
					.dueDate(receipt.getMovementDate().plusDays(termBreak.getOffsetDays()))
					.dueAmount(dueAmt)
					.dueAmountActual(dueAmt)
					.inoutId(receipt.getId())
					.invoiceId(invoice != null ? invoice.getId() : null)
					.build());

			result.add(line);

			dueAmtRemaining = dueAmtRemaining.subtract(line.getDueAmount()).toZeroIfNegative();
		}

		// Remainder row: max(0, orderGrandTotal − Σ receipts); omit if ≤ 0 (over-delivery)
		if (dueAmtRemaining.signum() > 0)
		{
			final OrderPayScheduleLine existingLine = CollectionUtils.removeFirst(existingLines, line -> line.getInoutId() == null);

			final OrderPayScheduleLine line = existingLine != null
					? existingLine
					: OrderPayScheduleLine.from(order, termBreak);

			line.applyAndProcess(OrderPayScheduleLineContext.builder()
					.status(OrderPayScheduleStatus.Pending)
					.referenceDate(OrderPayScheduleLineContext.INFINITE_FUTURE_DATE)
					.dueDate(OrderPayScheduleLineContext.INFINITE_FUTURE_DATE)
					.dueAmount(dueAmtRemaining)
					.dueAmountActual(dueAmtRemaining)
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
