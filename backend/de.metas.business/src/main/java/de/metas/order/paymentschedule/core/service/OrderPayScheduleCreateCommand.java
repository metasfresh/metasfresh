package de.metas.order.paymentschedule.core.service;

import com.google.common.collect.ImmutableList;
import de.metas.order.paymentschedule.core.OrderPayScheduleLineContext;
import de.metas.order.paymentschedule.core.OrderSchedulingContext;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

@Builder
class OrderPayScheduleCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final OrderSchedulingContext context;

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
	}

	private void execute0()
	{
		if (!context.isComplexPaymentTerm())
		{
			return; // Nothing to schedule
		}

		orderPayScheduleService.create(
				OrderPayScheduleCreateRequest.builder()
						.orderId(context.getOrderId())
						.lines(context.getPaymentTerm().getSortedBreaks()
								.stream()
								.map(this::toOrderPayScheduleCreateRequestLine)
								.collect(ImmutableList.toImmutableList()))
						.build()
		);
	}

	private OrderPayScheduleCreateRequest.Line toOrderPayScheduleCreateRequestLine(@NonNull final PaymentTermBreak termBreak)
	{
		final OrderPayScheduleLineContext lineContext = context.computeLineContext(termBreak);

		return OrderPayScheduleCreateRequest.Line.builder()
				.paymentTermBreakId(termBreak.getId())
				.referenceDateType(termBreak.getReferenceDateType())
				.percent(termBreak.getPercent())
				.offsetDays(termBreak.getOffsetDays())
				//
				.status(lineContext.getStatus())
				.referenceDate(lineContext.getReferenceDate())
				.dueDate(lineContext.getDueDate())
				.baseAmount(context.getGrandTotal()) // for LC/OD rows: BaseAmt = order GrandTotal
				.dueAmount(context.getGrandTotalByBreakId(termBreak.getId()))
				//
				.build();
	}

}
