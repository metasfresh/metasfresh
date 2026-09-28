package de.metas.pos.rest_api.json;

import de.metas.money.Money;
import de.metas.pos.invoice_settlement.POSInvoiceSettleResult;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

/**
 * Outcome of {@code POST /api/v2/pos/invoices/settle}: the invoice settled, the amount
 * that was settled, the change returned to the customer, and the till's updated cash journal.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSInvoiceSettleResponse
{
	@NonNull String documentNo;

	/** Amount settled from the invoice, in the terminal's currency. */
	@NonNull BigDecimal amount;

	/** Change returned to the customer (cashTenderedAmount - amount). */
	@NonNull BigDecimal change;

	@NonNull JsonCashJournalSummary journal;

	public static JsonPOSInvoiceSettleResponse of(
			@NonNull final POSInvoiceSettleResult result,
			@NonNull final String invoiceDocumentNo,
			@NonNull final BigDecimal cashTenderedAmount,
			@NonNull final JsonContext jsonContext)
	{
		final Money settledAmount = result.getAmount();
		final BigDecimal change = cashTenderedAmount.subtract(settledAmount.toBigDecimal());

		return JsonPOSInvoiceSettleResponse.builder()
				.documentNo(invoiceDocumentNo)
				.amount(settledAmount.toBigDecimal())
				.change(change)
				.journal(JsonCashJournalSummary.of(result.getJournal(), jsonContext))
				.build();
	}
}
