package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermConstants;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.api.ShipperTransportation;
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
import java.util.List;

import static de.metas.payment.paymentterm.PaymentTermConstants.PENDING_DATE;

@Builder
class OrderPayScheduleCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final OrderPayScheduleRepository orderPayScheduleRepository;
	@NonNull private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
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

		final List<PaymentTermBreak> termBreaks = context.getPaymentTerm().getSortedBreaks();
		if (termBreaks == null)
		{
			return; // Nothing to schedule
		}

		final ImmutableList.Builder<OrderPayScheduleCreateRequest.Line> linesBuilder = ImmutableList.builder();

		Money totalScheduledAmount = Money.zero(context.getGrandTotal().getCurrencyId());

		for (int i = 0; i < termBreaks.size() - 1; i++)
		{
			final PaymentTermBreak termBreak = termBreaks.get(i);

			// Calculate amount by percent
			final Money lineDueAmount = context.getGrandTotal().multiply(termBreak.getPercent(), context.getPrecision());

			final OrderPayScheduleCreateRequest.Line line = toOrderPayScheduleCreateRequestLine(
					context,
					termBreak,
					lineDueAmount
			);

			linesBuilder.add(line);
			totalScheduledAmount = totalScheduledAmount.add(lineDueAmount);
		}

		final PaymentTermBreak lastTermBreak = termBreaks.get(termBreaks.size() - 1);

		// Calculate the exact amount needed for the last line: Grand Total - accumulated total
		final Money lastLineDueAmount = context.getGrandTotal().subtract(totalScheduledAmount);

		final OrderPayScheduleCreateRequest.Line lastLine = toOrderPayScheduleCreateRequestLine(
				context,
				lastTermBreak,
				lastLineDueAmount
		);
		linesBuilder.add(lastLine);

		final OrderPayScheduleCreateRequest request = OrderPayScheduleCreateRequest.builder()
				.orderId(context.getOrderId())
				.lines(linesBuilder.build())
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

		final OrderId orderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());
		final ShipperTransportation shipperTransportation = shipperTransportationDAO.getEarliestShipperTransportationByOrderId(orderId);

		return OrderSchedulingContext.builder()
				.orderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.orderDate(TimeUtil.asInstant(orderRecord.getDateOrdered()))
				.letterOfCreditDate(TimeUtil.asInstant(orderRecord.getLC_Date()))
				.billOfLadingDate(shipperTransportation != null ? shipperTransportation.getBillOfLadingDate() : null)
				.ETADate(shipperTransportation != null ? shipperTransportation.getETADate() : null)
				.invoiceDate(invoiceBL.extractInvoiceDate(orderId))
				.grandTotal(grandTotal)
				.precision(orderBL.getAmountPrecision(orderRecord))
				.paymentTerm(paymentTerm)
				.build();
	}

	private static OrderPayScheduleCreateRequest.Line toOrderPayScheduleCreateRequestLine(
			@NonNull final OrderSchedulingContext context,
			@NonNull final PaymentTermBreak termBreak,
			@NonNull final Money dueAmount)
	{
		final ReferenceDateResult result = computeReferenceDate(context, PaymentTermBreakContext.builder()
				.offsetDays(termBreak.getOffsetDays())
				.referenceDateType(termBreak.getReferenceDateType())
				.build()
		);

		return OrderPayScheduleCreateRequest.Line.builder()
				.dueDate(result.getCalculatedDueDate())
				.dueAmount(dueAmount)
				.paymentTermBreakId(termBreak.getId())
				.referenceDateType(termBreak.getReferenceDateType())
				.percent(termBreak.getPercent())
				.orderPayScheduleStatus(result.getStatus())
				.build();
	}

	private static ReferenceDateResult computeReferenceDate(
			@NonNull final OrderSchedulingContext context,
			final @NonNull PaymentTermBreakContext termBreakContext)
	{
		final Instant referenceDate = getAvailableReferenceDate(context, termBreakContext.getReferenceDateType());
		if (referenceDate != null)
		{
			final Instant finalDueDate = referenceDate.plus(termBreakContext.getOffsetDays(), ChronoUnit.DAYS);
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
			@NonNull final OrderSchedulingContext context,
			final @NonNull ReferenceDateType referenceDateType)
	{
		switch (referenceDateType)
		{
			case OrderDate:
				return context.getOrderDate();
			case LetterOfCreditDate:
				return context.getLetterOfCreditDate();
			case BillOfLadingDate:
				return context.getBillOfLadingDate();
			case ETADate:
				return context.getETADate();
			case InvoiceDate:
				return context.getInvoiceDate();
			default:
				return null;
		}
	}

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
		@Nullable Instant billOfLadingDate;
		@Nullable Instant ETADate;
		@Nullable Instant invoiceDate;
		@NonNull Money grandTotal;
		@NonNull CurrencyPrecision precision;
		@NonNull PaymentTerm paymentTerm;
	}

	@Builder
	@Getter
	private static class PaymentTermBreakContext
	{
		@NonNull ReferenceDateType referenceDateType;
		int offsetDays;
	}

	// TODO: Movind it later on
	public void updateOrderPaySchedStatusAndReferenceDate(@NonNull final OrderId orderId)
	{
		final OrderSchedulingContext context = extractContext(orderBL.getById(orderId));
		if (context == null)
		{
			return;
		}

		orderPayScheduleRepository.getByOrderId(orderId)
				.ifPresent(orderPaySchedule -> {
					for (final OrderPayScheduleLine line : orderPaySchedule.getLines())
					{
						orderPayScheduleRepository.updateById(line.getId(),
								existingLine -> {
							if (existingLine.getOrderPayScheduleStatus().isPending())
							{
								final ReferenceDateResult result = computeReferenceDate(context, PaymentTermBreakContext.builder()
										.offsetDays(existingLine.getOffsetDays())
										.referenceDateType(existingLine.getReferenceDateType())
										.build());

								existingLine.changeStatusTo(result.getStatus());
								existingLine.setDueDate(result.getCalculatedDueDate());
							}
								});
					}
				});
	}

}
