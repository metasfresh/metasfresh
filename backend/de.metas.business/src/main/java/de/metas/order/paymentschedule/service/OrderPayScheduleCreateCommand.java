package de.metas.order.paymentschedule.service;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.DueDateAndStatus;
import de.metas.order.paymentschedule.OrderSchedulingContext;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Order;

import java.util.List;

@Builder
class OrderPayScheduleCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final I_C_Order orderRecord;

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
	}

	private void execute0()
	{
		orderPayScheduleService.deleteByOrderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()));

		final OrderSchedulingContext context = orderPayScheduleService.extractContext(orderRecord);
		if (context == null)
		{
			return; // Nothing to schedule
		}


		if (!context.getPaymentTerm().isComplex() )
		{
			return; // Nothing to schedule
		}

		final List<PaymentTermBreak> termBreaks = context.getPaymentTerm().getSortedBreaks();

		final ImmutableList.Builder<OrderPayScheduleCreateRequest.Line> linesBuilder = ImmutableList.builder();

		// Determine which base amount to use for ALL schedules
		// If payment term has LC break AND Proforma is allocated, use Proforma amount for everything
		// Otherwise use order grandTotal
		final Money baseAmount = getBaseAmountForAllSchedules(context);

		Money totalScheduledAmount = Money.zero(baseAmount.getCurrencyId());

		for (int i = 0; i < termBreaks.size() - 1; i++)
		{
			final PaymentTermBreak termBreak = termBreaks.get(i);

			// Calculate amount by percent
			final Money lineDueAmount = baseAmount.multiply(termBreak.getPercent(), context.getPrecision());

			final OrderPayScheduleCreateRequest.Line line = toOrderPayScheduleCreateRequestLine(
					context,
					termBreak,
					lineDueAmount
			);

			linesBuilder.add(line);
			totalScheduledAmount = totalScheduledAmount.add(lineDueAmount);
		}

		final PaymentTermBreak lastTermBreak = termBreaks.get(termBreaks.size() - 1);

		// Calculate the exact amount needed for the last line: Base Amount - accumulated total
		final Money lastLineDueAmount = baseAmount.subtract(totalScheduledAmount);

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

		orderPayScheduleService.create(request);
	}

	/**
	 * Returns the base amount to use for calculating ALL pay schedule line amounts.
	 * If payment term contains any LC (Letter of Credit) break AND Proforma is allocated,
	 * uses Proforma invoice amount for ALL schedules.
	 * Otherwise, uses order grandTotal for all schedules.
	 */
	private static Money getBaseAmountForAllSchedules(@NonNull final OrderSchedulingContext context)
	{
		// Check if payment term has any LC break
		final boolean hasLCBreak = context.getPaymentTerm().getSortedBreaks()
				.stream()
				.anyMatch(PaymentTermBreak::isLetterOfCredit);

		// If has LC break and Proforma is allocated, use Proforma amount for everything
		if (hasLCBreak && context.getProformaAmount() != null)
		{
			return context.getProformaAmount();
		}

		// Otherwise use order grandTotal
		return context.getGrandTotal();
	}

	private static OrderPayScheduleCreateRequest.Line toOrderPayScheduleCreateRequestLine(
			@NonNull final OrderSchedulingContext context,
			@NonNull final PaymentTermBreak termBreak,
			@NonNull final Money dueAmount)
	{
		final DueDateAndStatus result = context.computeDueDate(termBreak);

		return OrderPayScheduleCreateRequest.Line.builder()
				.dueDate(result.getDueDate())
				.dueAmount(dueAmount)
				.paymentTermBreakId(termBreak.getId())
				.referenceDateType(termBreak.getReferenceDateType())
				.percent(termBreak.getPercent())
				.orderPayScheduleStatus(result.getStatus())
				.offsetDays(termBreak.getOffsetDays())
				.build();
	}

}
