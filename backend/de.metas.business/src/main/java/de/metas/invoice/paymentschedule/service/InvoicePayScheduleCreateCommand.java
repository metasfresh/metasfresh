package de.metas.invoice.paymentschedule.service;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.repository.InvoicePayScheduleRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaySchedule;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Optionals;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Builder
class InvoicePayScheduleCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final InvoicePayScheduleRepository invoicePayScheduleRepository;
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
		invoicePayScheduleRepository.deleteByInvoiceId(invoiceId);
		invoiceRecord.setIsPayScheduleValid(false);

		final InvoicePayScheduleCreateRequest createRequest = newInvoicePayScheduleCreateRequest().orElse(null);
		if (createRequest != null)
		{
			invoicePayScheduleRepository.create(createRequest);
			invoiceRecord.setIsPayScheduleValid(true);
		}
	}

	private Optional<InvoicePayScheduleCreateRequest> newInvoicePayScheduleCreateRequest()
	{
		return Optionals.firstPresentOfSuppliers(
				this::newInvoicePayScheduleCreateRequest_fromOrderPaySchedule,
				this::newInvoicePayScheduleCreateRequest_fromPaymentTerm
		);
	}

	private Optional<InvoicePayScheduleCreateRequest> newInvoicePayScheduleCreateRequest_fromOrderPaySchedule()
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(invoiceRecord.getC_Order_ID());
		if (orderId == null)
		{
			return Optional.empty();
		}

		final OrderPaySchedule orderPaySchedule = orderPayScheduleService.getByOrderId(orderId).orElse(null);
		if (orderPaySchedule == null)
		{
			return Optional.empty();
		}

		return Optional.of(
				InvoicePayScheduleCreateRequest.builder()
						.invoiceId(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()))
						.lines(orderPaySchedule.getLines()
								.stream()
								.map(line -> InvoicePayScheduleCreateRequest.Line.builder()
										.dueDate(line.getDueDate())
										.dueAmount(line.getDueAmount())
										.orderAndPayScheduleId(line.getOrderAndPayScheduleId())
										.build())
								.collect(ImmutableList.toImmutableList()))
						.build()
		);
	}

	private Optional<InvoicePayScheduleCreateRequest> newInvoicePayScheduleCreateRequest_fromPaymentTerm()
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(invoiceRecord.getC_PaymentTerm_ID());
		if (paymentTermId == null)
		{
			return Optional.empty();
		}

		final PaymentTerm paymentTerm = paymentTermService.getById(paymentTermId);
		if (!paymentTerm.isValid() || paymentTerm.getPaySchedules().isEmpty())
		{
			return Optional.empty();
		}

		final Money grandTotal = invoiceBL.extractGrandTotal(invoiceRecord).toMoney();
		final CurrencyPrecision currencyPrecision = currencyDAO.getStdPrecision(grandTotal.getCurrencyId());
		final LocalDate dateInvoiced = invoiceRecord.getDateInvoiced().toLocalDateTime().toLocalDate();

		final ArrayList<InvoicePayScheduleCreateRequest.Line> lines = new ArrayList<>();
		Money dueAmtRemaining = grandTotal;
		final ImmutableList<PaySchedule> paySchedules = paymentTerm.getPaySchedules();
		for (int i = 0, paySchedulesCount = paySchedules.size(); i < paySchedulesCount; i++)
		{
			final PaySchedule paySchedule = paySchedules.get(i);
			final boolean isLast = i == paySchedulesCount - 1;

			final Money dueAmt = isLast
					? dueAmtRemaining
					: paySchedule.calculateDueAmt(grandTotal, currencyPrecision);

			lines.add(InvoicePayScheduleCreateRequest.Line.builder()
					.valid(true)
					.dueDate(paySchedule.calculateDueDate(dateInvoiced))
					.dueAmount(dueAmt)
					.discountDate(paySchedule.calculateDiscountDate(dateInvoiced))
					.discountAmount(paySchedule.calculateDiscountAmt(dueAmt, currencyPrecision))
					.paymentTermScheduleId(paySchedule.getId())
					.build());

			dueAmtRemaining = dueAmtRemaining.subtract(dueAmt);
		}

		return Optional.of(
				InvoicePayScheduleCreateRequest.builder()
						.invoiceId(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()))
						.lines(lines)
						.build()
		);
	}
}
