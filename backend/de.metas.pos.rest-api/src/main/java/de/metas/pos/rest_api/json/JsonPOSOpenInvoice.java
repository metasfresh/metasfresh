package de.metas.pos.rest_api.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.invoice.InvoiceId;
import de.metas.pos.invoice_settlement.POSOpenInvoice;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
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

	/** The unfinished card settlement of this invoice, which blocks any further payment of it; absent if there is none. */
	@JsonInclude(JsonInclude.Include.NON_NULL) @Nullable JsonPOSInvoiceCardSettlement blockingCardSettlement;

	public static JsonPOSOpenInvoice from(
			@NonNull final POSOpenInvoice invoice,
			@Nullable final JsonPOSInvoiceCardSettlement blockingCardSettlement)
	{
		return JsonPOSOpenInvoice.builder()
				.invoiceId(invoice.getInvoiceId())
				.documentNo(invoice.getDocumentNo())
				.bpartnerName(invoice.getBpartnerName())
				.dateInvoiced(invoice.getDateInvoiced())
				.grandTotal(invoice.getGrandTotal().toBigDecimal())
				.openAmt(invoice.getOpenAmt().toBigDecimal())
				.blockingCardSettlement(blockingCardSettlement)
				.build();
	}
}
