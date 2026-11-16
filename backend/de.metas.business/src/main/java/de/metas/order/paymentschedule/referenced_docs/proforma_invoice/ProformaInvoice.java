package de.metas.order.paymentschedule.referenced_docs.proforma_invoice;

import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ProformaInvoice
{
	@NonNull InvoiceId id;
	@NonNull Money grandTotal;
	@NonNull LocalDate dateInvoiced;
	@NonNull LocalDate dueDate;
}
