package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.order.OrderId;
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

		orderPayScheduleService.create(request);
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
