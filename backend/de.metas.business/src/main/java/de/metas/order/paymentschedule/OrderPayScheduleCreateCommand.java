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
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static de.metas.payment.paymentterm.PaymentTermConstants.PENDING_DATE;

@Builder
class OrderPayScheduleCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final OrderPayScheduleRepository orderPayScheduleRepository;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final I_C_Order orderRecord;

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
	}

	private void execute0()
	{
		orderPayScheduleRepository.deleteByOrderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()));

		final OrderSchedulingContext context = extractContext(orderRecord);
		if (context == null)
		{
			return; // Nothing to schedule
		}

		final OrderPayScheduleCreateRequest request = OrderPayScheduleCreateRequest.builder()
				.orderId(context.getOrderId())
				.lines(context.getPaymentTerm().getSortedBreaks()
						.map(termBreak -> toOrderPayScheduleCreateRequestLine(context, termBreak))
						.collect(ImmutableList.toImmutableList()))
				.build();

		orderPayScheduleRepository.create(request);
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
		if (grandTotal.isZero())
		{
			return null;
		}

		return OrderSchedulingContext.builder()
				.orderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.orderDate(TimeUtil.asInstant(orderRecord.getDateOrdered()))
				.letterOfCreditDate(TimeUtil.asInstant(orderRecord.getLC_Date()))
				.grandTotal(grandTotal)
				.precision(orderBL.getAmountPrecision(orderRecord))
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

	//
	//
	//
	//
	//

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
