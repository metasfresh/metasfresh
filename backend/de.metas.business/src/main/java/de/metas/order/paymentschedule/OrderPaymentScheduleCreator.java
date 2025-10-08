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

import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
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
public class OrderPaymentScheduleCreator
{
	public static OrderPaymentScheduleCreator newInstanceForUnitTesting()
	{
		final OrderPayScheduleService orderPayScheduleService = new OrderPayScheduleService(new OrderPayScheduleRepository());
		return new OrderPaymentScheduleCreator(new PaymentTermService(), orderPayScheduleService);
	}

	public static final Instant PENDING_DATE =
			LocalDateTime
					.of(9999, Month.JANUARY, 1, 0, 0, 0) // Create the local date and time
					.toInstant(ZoneOffset.UTC);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final PaymentTermService paymentTermService;
	private final OrderPayScheduleService orderPayScheduleService;

	public OrderPaymentScheduleCreator(@NonNull final PaymentTermService paymentTermService, @NonNull final OrderPayScheduleService orderPayScheduleService)
	{
		this.paymentTermService = paymentTermService;
		this.orderPayScheduleService = orderPayScheduleService;
	}

	/**
	 * Entry point to create schedules for a completed Order.
	 */
	public void createComplexSchedules(@NonNull final I_C_Order orderRecord)
	{
		final PaymentTermId paymenttermId = orderBL.getPaymentTermId(orderRecord);
		final PaymentTerm paymentTerm = paymentTermService.getById(paymenttermId);

		if (!paymentTerm.hasBreaks())
		{
			return;
		}

		if (orderHasLegacyPaySchedule(orderRecord))
		{
			throw new AdempiereException("Payment Term " + paymentTerm.getValue() +
					" is flagged as complex and cannot coexist with legacy C_PaySchedule records.");
		}

		final Amount grandTotal = orderBL.getGrandTotal(orderRecord);

		if (grandTotal.isZero())
		{
			return; // Nothing to schedule
		}

		final CurrencyPrecision precision = orderBL.getAmountPrecision(orderRecord);

		for (final PaymentTermBreak termBreak : paymentTerm.getSortedBreaks())
		{

			createSingleOrderPaySchedule(OrderSchedulingContext.builder()
					.order(orderRecord)
					.grandTotal(grandTotal)
					.precision(precision)
					.termBreak(termBreak)
					.build());
		}

	}

	/**
	 * Processes a single PaymentTermBreak, calculates the due amount, determines the due date/status,
	 * and creates the corresponding OrderPaySchedule record.
	 */
	private void createSingleOrderPaySchedule(@NonNull final OrderSchedulingContext context)
	{
		final Amount grandTotal = context.getGrandTotal();
		final CurrencyPrecision precision = context.getPrecision();
		final PaymentTermBreak termBreak = context.getTermBreak();
		final I_C_Order orderRecord = context.getOrder();
		final OrderId orderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

		final Percent percentage = termBreak.getPercent();
		final Amount dueAmount = grandTotal.multiply(percentage, precision);
		final ReferenceDateResult result = computeReferenceDate(orderRecord, termBreak);
		final SeqNo seqNo = orderPayScheduleService.getNextSeqNo(orderId);

		final OrderPayScheduleRequest orderPayScheduleRequest = OrderPayScheduleRequest.builder()
				.orderId(orderId)
				.dueDate(result.getCalculatedDueDate())
				.dueAmount(dueAmount)
				.paymentTermBreakId(termBreak.getId())
				.referenceDateType(termBreak.getReferenceDateType())
				.percent(percentage)
				.seqNo(seqNo)
				.orderPayScheduleStatus(result.getStatus())
				.build();

		orderPayScheduleService.createSchedule(orderPayScheduleRequest);
	}

	/**
	 * Determines the initial reference date and calculates the due date.
	 */
	private ReferenceDateResult computeReferenceDate(final @NonNull I_C_Order order, final @NonNull PaymentTermBreak termBreak)
	{
		final Instant referenceDate = getAvailableReferenceDate(order, termBreak.getReferenceDateType());
		final Instant finalDueDate;
		final OrderPayScheduleStatus status;

		if (referenceDate != null)
		{
			// Date is available (OrderDate or existing LCDate): calculate the due date
			finalDueDate = TimeUtil.addDays(referenceDate, termBreak.getOffsetDays());
			status = OrderPayScheduleStatus.Awaiting_Pay;
		}
		else
		{
			// Date is missing (BLDate, ETADate, etc.): set sentinel date and pending status
			finalDueDate = PENDING_DATE;
			status = OrderPayScheduleStatus.Pending;
		}

		return new ReferenceDateResult(finalDueDate, status);
	}

	private @Nullable Instant getAvailableReferenceDate(
			final @NonNull I_C_Order order,
			final @NonNull ReferenceDateType referenceDateType)
	{
		switch (referenceDateType)
		{
			case OrderDate:
				return TimeUtil.asInstant(order.getDateOrdered());
			case LCDate:
				// LC Date is captured directly on the Order
				return TimeUtil.asInstant(order.getLC_Date());
			case BLDate:
			case ETADate:
			case InvoiceDate:
			default:
				// These dates are not available yet. We return null to signal 'Pending reference' status.
				return null;
		}
	}

	private boolean orderHasLegacyPaySchedule(@NonNull final I_C_Order order)
	{
		//TODO to be implemented
		return false;
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
		@NonNull I_C_Order order;
		@NonNull Amount grandTotal;
		@NonNull CurrencyPrecision precision;
		@NonNull PaymentTermBreak termBreak;
	}
}
