package de.metas.invoice.paymentschedule.repository;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.InvoicePayScheduleLine;
import de.metas.invoice.paymentschedule.InvoicePayScheduleLineId;
import de.metas.invoice.paymentschedule.service.InvoicePayScheduleCreateRequest;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderAndPayScheduleId;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.payment.paymentterm.PayScheduleId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.util.TimeUtil;

@UtilityClass
class InvoicePayScheduleConverter
{
	@NonNull
	public static InvoicePayScheduleLine fromRecord(@NonNull final I_C_InvoicePaySchedule record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return InvoicePayScheduleLine.builder()
				.id(InvoicePayScheduleLineId.ofRepoId(record.getC_InvoicePaySchedule_ID()))
				.invoiceId(InvoiceId.ofRepoId(record.getC_Invoice_ID()))
				.isValid(record.isValid())
				//
				.dueDate(record.getDueDate().toInstant())
				.dueAmount(Money.of(record.getDueAmt(), currencyId))
				//
				.discountDate(TimeUtil.asInstant(record.getDiscountDate()))
				.discountAmount(Money.of(record.getDiscountAmt(), currencyId))
				//
				.orderAndPayScheduleId(OrderAndPayScheduleId.ofRepoIdsOrNull(record.getC_Order_ID(), record.getC_OrderPaySchedule_ID()))
				.invoicePayScheduleId(InvoicePayScheduleLineId.ofRepoIdOrNull(record.getC_InvoicePaySchedule_ID()))
				//
				.build();
	}

	public static void updateRecord(@NonNull final I_C_InvoicePaySchedule record, final @NonNull InvoicePayScheduleLine from)
	{
		record.setC_Invoice_ID(from.getInvoiceId().getRepoId());
		record.setIsValid(from.isValid());

		record.setDueDate(TimeUtil.asTimestamp(from.getDueDate()));
		record.setDueAmt(from.getDueAmount().toBigDecimal());
		record.setC_Currency_ID(from.getDueAmount().getCurrencyId().getRepoId());

		record.setDiscountDate(TimeUtil.asTimestamp(from.getDiscountDate()));
		record.setDiscountAmt(from.getDiscountAmount() != null ? from.getDiscountAmount().toBigDecimal() : null);

		record.setC_Order_ID(OrderId.toRepoId(from.getOrderAndPayScheduleId() != null ? from.getOrderAndPayScheduleId().getOrderId() : null));
		record.setC_OrderPaySchedule_ID(OrderPayScheduleId.toRepoId(from.getOrderAndPayScheduleId() != null ? from.getOrderAndPayScheduleId().getOrderPayScheduleId() : null));
		record.setC_InvoicePaySchedule_ID(InvoicePayScheduleLineId.toRepoId(from.getInvoicePayScheduleId()));
	}

	public static void updateRecord(@NonNull final I_C_InvoicePaySchedule record, @NonNull final InvoicePayScheduleCreateRequest.Line from)
	{
		record.setIsValid(from.isValid());

		record.setDueDate(TimeUtil.asTimestamp(from.getDueDate()));
		record.setC_Currency_ID(from.getCurrencyId().getRepoId());
		record.setDueAmt(from.getDueAmount().toBigDecimal());

		record.setDiscountDate(TimeUtil.asTimestamp(from.getDiscountDate()));
		record.setDiscountAmt(from.getDiscountAmount() != null ? from.getDiscountAmount().toBigDecimal() : null);

		record.setC_Order_ID(OrderId.toRepoId(from.getOrderAndPayScheduleId() != null ? from.getOrderAndPayScheduleId().getOrderId() : null));
		record.setC_OrderPaySchedule_ID(OrderPayScheduleId.toRepoId(from.getOrderAndPayScheduleId() != null ? from.getOrderAndPayScheduleId().getOrderPayScheduleId() : null));
		record.setC_PaySchedule_ID(PayScheduleId.toRepoId(from.getPaymentTermScheduleId()));
	}

}
