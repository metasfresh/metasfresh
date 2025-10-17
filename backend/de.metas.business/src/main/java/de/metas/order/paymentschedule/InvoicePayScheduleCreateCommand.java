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

import java.util.Optional;

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

		final Optional<OrderPaySchedule> orderPaySchedule = orderPayScheduleService.getByOrderId(OrderId.ofRepoId(invoiceRecord.getC_Order_ID()));

		if (!orderPaySchedule.isPresent())
		{
			return; // nothing to schedule
		}

		final ImmutableList<InvoicePayScheduleCreateRequest.Line> invoiceLines = orderPaySchedule.get()
				.getLines().stream()
				.map(InvoicePayScheduleCreateCommand::toInvoicePayScheduleCreateRequestLine)
				.collect(ImmutableList.toImmutableList());

		final InvoicePayScheduleCreateRequest request = InvoicePayScheduleCreateRequest.builder()
				.invoiceId(invoiceId)
				.lines(invoiceLines)
				.build();

		invoicePayScheduleService.create(request);
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
