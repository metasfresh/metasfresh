package de.metas.pos.rest_api.json;

import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * One open sales invoice offered for cash settlement at the till: completed or closed,
 * not yet fully paid, and not a credit memo.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSOpenInvoice
{
	@NonNull InvoiceId invoiceId;
	@NonNull String documentNo;
	@NonNull String bpartnerName;
	@NonNull LocalDate dateInvoiced;

	/** Grand total of the invoice, in the invoice's currency. */
	@NonNull BigDecimal grandTotal;

	/** Open amount, in the invoice's own currency. */
	@NonNull BigDecimal openAmt;

	public static JsonPOSOpenInvoice from(@NonNull final de.metas.pos.invoice_settlement.POSOpenInvoice invoice)
	{
		return JsonPOSOpenInvoice.builder()
				.invoiceId(invoice.getInvoiceId())
				.documentNo(invoice.getDocumentNo())
				.bpartnerName(invoice.getBpartnerName())
				.dateInvoiced(invoice.getDateInvoiced())
				.grandTotal(invoice.getGrandTotal().toBigDecimal())
				.openAmt(invoice.getOpenAmt().toBigDecimal())
				.build();
	}
}
