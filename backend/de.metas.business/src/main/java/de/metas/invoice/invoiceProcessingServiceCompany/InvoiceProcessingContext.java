package de.metas.invoice.invoiceProcessingServiceCompany;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

/**
 * Who the service company is and as of which date its fee is evaluated, when allocating an invoice with a service fee.
 */
@Value(staticConstructor = "of")
public class InvoiceProcessingContext
{
	@NonNull BPartnerId serviceCompanyId;
	@NonNull ZonedDateTime paymentDate;
}
