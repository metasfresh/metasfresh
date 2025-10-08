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

package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermConstants;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OrderPaymentScheduleCreator
{
	public static OrderPaymentScheduleCreator newInstanceForUnitTesting()
	{
		final OrderPayScheduleService orderPayScheduleService = new OrderPayScheduleService(new OrderPayScheduleRepository());
		return new OrderPaymentScheduleCreator(new PaymentTermService(), orderPayScheduleService);
	}

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final PaymentTermService paymentTermService;
	private final OrderPayScheduleService orderPayScheduleService;

	/**
	 * Entry point to create schedules for a completed Order.
	 */
	public void createOrderPaySchedules(@NonNull final I_C_Order orderRecord)
	{
		final OrderSchedulingContext context = extractContext(orderRecord);
		if (context == null)
		{
			return; // Nothing to schedule
		}

		orderPayScheduleService.create(
				OrderPayScheduleCreateRequest.builder()
						.orderId(context.getOrderId())
						.lines(context.getPaymentTerm().getSortedBreaks()
								.map(termBreak -> toOrderPayScheduleCreateRequestLine(context, termBreak))
								.collect(ImmutableList.toImmutableList()))
						.build()
		);
	}

	private @Nullable OrderSchedulingContext extractContext(final @NotNull I_C_Order orderRecord)
	{
		final PaymentTermId paymentTermId = orderBL.getPaymentTermId(orderRecord);
		final PaymentTerm paymentTerm = paymentTermService.getById(paymentTermId);
		if (!paymentTerm.isComplex())
		{
			return null;
		}
		if (paymentTermService.hasPaySchedule(paymentTermId))
		{
			throw new AdempiereException(PaymentTermConstants.MSG_ComplexTermConflict)
					.appendParametersToMessage()
					.setParameter("PaymentTerm", paymentTerm.getName());

		}

		final Money grandTotal = orderBL.getGrandTotal(orderRecord);
		final CurrencyPrecision precision = orderBL.getAmountPrecision(orderRecord);
		if (grandTotal.isZero())
		{
			return null;
		}

		return OrderSchedulingContext.builder()
				.orderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.orderDate(TimeUtil.asInstant(orderRecord.getDateOrdered()))
				.letterOfCreditDate(TimeUtil.asInstant(orderRecord.getLC_Date()))
				.grandTotal(grandTotal)
				.precision(precision)
				.paymentTerm(paymentTerm)
				.build();
	}

	private static OrderPayScheduleCreateRequest.Line toOrderPayScheduleCreateRequestLine(
			@NonNull final OrderSchedulingContext context,
			@NonNull final PaymentTermBreak termBreak)
	{
		final ReferenceDateResult result = computeReferenceDate(context.getOrderDate(), context.getLetterOfCreditDate(), termBreak);

		return OrderPayScheduleCreateRequest.Line.builder()
				.dueDate(result.getCalculatedDueDate())
				.dueAmount(context.getGrandTotal().multiply(termBreak.getPercent(), context.getPrecision()))
				.paymentTermBreakId(termBreak.getId())
				.referenceDateType(termBreak.getReferenceDateType())
				.percent(termBreak.getPercent())
				.orderPayScheduleStatus(result.getStatus())
				.build();
	}

	/**
	 * Determines the initial reference date and calculates the due date.
	 */
	private static ReferenceDateResult computeReferenceDate(
			final @Nullable Instant orderDate,
			final @Nullable Instant letterOfCreditDate,
			final @NonNull PaymentTermBreak termBreak)
	{
		final Instant referenceDate = getAvailableReferenceDate(orderDate, letterOfCreditDate, termBreak.getReferenceDateType());
		if (referenceDate != null)
		{
			// Date is available (OrderDate or existing letterOfCreditDate): calculate the due date
			final Instant finalDueDate = referenceDate.plus(termBreak.getOffsetDays(), ChronoUnit.DAYS);
			final OrderPayScheduleStatus status = OrderPayScheduleStatus.Awaiting_Pay;
			return new ReferenceDateResult(finalDueDate, status);
		}
		else
		{
			// Date is missing (BLDate, ETADate, etc.): set sentinel date and pending status
			final Instant finalDueDate = PaymentTermConstants.PENDING_DATE;
			final OrderPayScheduleStatus status = OrderPayScheduleStatus.Pending;
			return new ReferenceDateResult(finalDueDate, status);
		}
	}

	private static @Nullable Instant getAvailableReferenceDate(
			final @Nullable Instant orderDate,
			final @Nullable Instant letterOfCreditDate,
			final @NonNull ReferenceDateType referenceDateType)
	{
		switch (referenceDateType)
		{
			case OrderDate:
				return orderDate;
			case LetterOfCreditDate:
				// LC Date is captured directly on the Order
				return letterOfCreditDate;
			case BillOfLadingDate:
			case ETADate:
			case InvoiceDate:
			default:
				// These dates are not available yet. We return null to signal 'Pending reference' status.
				return null;
		}
	}

	@Value
	private static class ReferenceDateResult
	{
		@NonNull Instant calculatedDueDate;
		@NonNull OrderPayScheduleStatus status;
	}

	@Builder
	@Getter
	private static class OrderSchedulingContext
	{
		@NonNull OrderId orderId;
		@Nullable Instant orderDate;
		@Nullable Instant letterOfCreditDate;
		@NonNull Money grandTotal;
		@NonNull CurrencyPrecision precision;
		@NonNull PaymentTerm paymentTerm;
	}
}
