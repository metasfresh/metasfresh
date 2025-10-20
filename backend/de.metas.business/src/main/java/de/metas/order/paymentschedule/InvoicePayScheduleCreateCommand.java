package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Invoice;

@Builder
class InvoicePayScheduleCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final InvoicePayScheduleService invoicePayScheduleService;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final I_C_Invoice invoiceRecord;

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
	}

	private void execute0()
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
		invoicePayScheduleService.deleteByInvoiceId(invoiceId);
		toInvoicePayScheduleCreateRequest(OrderId.ofRepoId(invoiceRecord.getC_Order_ID()), invoiceId);
	}

	private void toInvoicePayScheduleCreateRequest(@NonNull final OrderId orderId, @NonNull final InvoiceId invoiceId)
	{
		orderPayScheduleService.getByOrderId(orderId)
				.map(orderPaySchedule -> InvoicePayScheduleCreateRequest.builder()
						.invoiceId(invoiceId)
						.lines(orderPaySchedule.getLines()
								.stream()
								.map(InvoicePayScheduleCreateCommand::toInvoicePayScheduleCreateRequestLine)
								.collect(ImmutableList.toImmutableList()))
						.build())
				.ifPresent(invoicePayScheduleService::create);
	}

	private static InvoicePayScheduleCreateRequest.Line toInvoicePayScheduleCreateRequestLine(@NonNull final OrderPayScheduleLine line)
	{
		return InvoicePayScheduleCreateRequest.Line.builder()
				.dueDate(line.getDueDate())
				.dueAmount(line.getDueAmount())
				.orderPayScheduleId(line.getId())
				.orderId(line.getOrderId())
				.build();
	}

}
