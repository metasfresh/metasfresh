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
import java.util.Optional;

import static de.metas.payment.paymentterm.PaymentTermConstants.PENDING_DATE;

/**
 * Loads existing payment schedules and creates new ones based on the Order's payment terms.
 * Delegates actual database persistence to {@link OrderPayScheduleRepository}.
 */
@Service
@RequiredArgsConstructor
public class OrderPayScheduleLoaderAndSaver
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final OrderPayScheduleRepository repository;


	public void saveSchedulesForOrder(@NonNull final I_C_Order orderRecord)
	{
		final OrderSchedulingContext context = extractContext(orderRecord);
		final OrderId orderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

		if (context == null)
		{
			repository.deleteByOrderId(orderId);
			return; // Nothing to schedule
		}

		final OrderPayScheduleCreateRequest request = OrderPayScheduleCreateRequest.builder()
				.orderId(context.getOrderId())
				.lines(context.getPaymentTerm().getSortedBreaks()
						.map(termBreak -> toOrderPayScheduleCreateRequestLine(context, termBreak))
						.collect(ImmutableList.toImmutableList()))
				.build();

		save(request);
	}

	/**
	 * Deletes existing schedules for the OrderId in the request and creates new records from the lines.
	 */
	public void save(@NonNull final OrderPayScheduleCreateRequest request)
	{
		repository.deleteByOrderId(request.getOrderId());
		repository.create(request);
	}

	@NonNull
	public Optional<OrderPaySchedule> loadByOrderId(@NonNull final OrderId orderId)
	{
		return repository.getByOrderId(orderId);
	}

	public void deleteByOrderId(@NonNull final OrderId orderId)
	{
		repository.deleteByOrderId(orderId);
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

	private static ReferenceDateResult computeReferenceDate(
			final @Nullable Instant orderDate,
			final @Nullable Instant letterOfCreditDate,
			final @NonNull PaymentTermBreak termBreak)
	{
		final Instant referenceDate = getAvailableReferenceDate(orderDate, letterOfCreditDate, termBreak.getReferenceDateType());
		if (referenceDate != null)
		{
			final Instant finalDueDate = referenceDate.plus(termBreak.getOffsetDays(), ChronoUnit.DAYS);
			final OrderPayScheduleStatus status = OrderPayScheduleStatus.Awaiting_Pay;
			return new ReferenceDateResult(finalDueDate, status);
		}
		else
		{
			final OrderPayScheduleStatus status = OrderPayScheduleStatus.Pending;
			return new ReferenceDateResult(PENDING_DATE, status);
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
				return letterOfCreditDate;
			case BillOfLadingDate:
			case ETADate:
			case InvoiceDate:
			default:
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