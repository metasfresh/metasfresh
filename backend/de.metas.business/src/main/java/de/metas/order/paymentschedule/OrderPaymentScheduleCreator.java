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
import de.metas.util.lang.SeqNo;
import de.metas.util.lang.SeqNoProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

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
	public void createComplexSchedules(@NonNull final I_C_Order orderRecord)
	{
		final PaymentTermId paymenttermId = orderBL.getPaymentTermId(orderRecord);
		final PaymentTerm paymentTerm = paymentTermService.getById(paymenttermId);

		if (!paymentTerm.isComplex())
		{
			return;
		}

		if (paymentTermService.hasPaySchedule(paymenttermId))
		{
			throw new AdempiereException(PaymentTermConstants.MSG_ComplexTermConflict)
					.appendParametersToMessage()
					.setParameter("PaymentTerm", paymentTerm.getName());

		}

		final Money grandTotal = orderBL.getGrandTotal(orderRecord);
		final CurrencyPrecision precision = orderBL.getAmountPrecision(orderRecord);

		if (grandTotal.isZero())
		{
			return; // Nothing to schedule
		}

		final OrderSchedulingContext context = OrderSchedulingContext.builder()
				.orderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.orderDate(TimeUtil.asInstant(orderRecord.getDateOrdered()))
				.letterOfCreditDate(TimeUtil.asInstant(orderRecord.getLC_Date()))
				.grandTotal(grandTotal)
				.precision(precision)
				.build();

		final SeqNo lastSeqNo = orderPayScheduleService.getNextSeqNo(context.getOrderId());
		final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(lastSeqNo.toInt());


		final ImmutableList<OrderPaySchedule> schedules = paymentTerm
				.getSortedBreaks()
				.map(termBreak -> createOrderPaySchedule(context, termBreak, seqNoProvider))
				.collect(ImmutableList.toImmutableList());

		orderPayScheduleService.create(schedules);
	}

	/**
	 * Processes a single PaymentTermBreak, calculates the due amount, determines the due date/status,
	 * and creates the corresponding OrderPaySchedule object.
	 */
	private OrderPaySchedule createOrderPaySchedule(@NonNull final OrderSchedulingContext context, @NonNull final PaymentTermBreak termBreak, @NonNull final SeqNoProvider seqNoProvider
	)
	{
		final ReferenceDateResult result = computeReferenceDate(context.getOrderDate(), context.getLetterOfCreditDate(), termBreak);

		return OrderPaySchedule.builder()
				.id(null) // New record, no ID yet
				.orderId(context.getOrderId())
				.dueDate(result.getCalculatedDueDate())
				.dueAmount(context.getGrandTotal().multiply(termBreak.getPercent(), context.getPrecision()))
				.paymentTermId(termBreak.getId().getPaymentTermId())
				.paymentTermBreakId(termBreak.getId())
				.referenceDateType(termBreak.getReferenceDateType())
				.percent(termBreak.getPercent())
				.seqNo(seqNoProvider.getAndIncrement())
				.orderPayScheduleStatus(result.getStatus())
				.build();
	}

	/**
	 * Determines the initial reference date and calculates the due date.
	 */
	private ReferenceDateResult computeReferenceDate(final @Nullable Instant orderDate,
													 final @Nullable Instant letterOfCreditDate,
													 final @NonNull PaymentTermBreak termBreak)
	{
		final Instant referenceDate = getAvailableReferenceDate(orderDate, letterOfCreditDate, termBreak.getReferenceDateType());
		final Instant finalDueDate;
		final OrderPayScheduleStatus status;

		if (referenceDate != null)
		{
			// Date is available (OrderDate or existing letterOfCreditDate): calculate the due date
			finalDueDate = TimeUtil.addDays(referenceDate, termBreak.getOffsetDays());
			status = OrderPayScheduleStatus.Awaiting_Pay;
		}
		else
		{
			// Date is missing (BLDate, ETADate, etc.): set sentinel date and pending status
			finalDueDate = PaymentTermConstants.PENDING_DATE;
			status = OrderPayScheduleStatus.Pending;
		}

		return new ReferenceDateResult(finalDueDate, status);
	}

	private @Nullable Instant getAvailableReferenceDate(
			final @Nullable Instant orderDate,
			final @Nullable Instant letterOfCreditDate,
			final @NonNull ReferenceDateType referenceDateType)
	{
		switch (referenceDateType)
		{
			case OrderDate:
				return orderDate;
			case LCDate:
				// LC Date is captured directly on the Order
				return letterOfCreditDate;
			case BLDate:
			case ETADate:
			case InvoiceDate:
			default:
				// These dates are not available yet. We return null to signal 'Pending reference' status.
				return null;
		}
	}

	@Value
	static class ReferenceDateResult
	{
		@NonNull Instant calculatedDueDate;
		@NonNull OrderPayScheduleStatus status;
	}

	@Builder
	@Getter
	static class OrderSchedulingContext
	{
		@NonNull OrderId orderId;
		@Nullable Instant orderDate;
		@Nullable Instant letterOfCreditDate;
		@NonNull Money grandTotal;
		@NonNull CurrencyPrecision precision;
	}
}
