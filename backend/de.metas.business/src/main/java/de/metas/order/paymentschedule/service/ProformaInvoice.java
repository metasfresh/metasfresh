package de.metas.order.paymentschedule.service;

import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
class ProformaInvoice
{
	@NonNull InvoiceId id;
	@NonNull Money grandTotal;
	@NonNull LocalDate dateInvoiced;
	@NonNull LocalDate dueDate;
}
