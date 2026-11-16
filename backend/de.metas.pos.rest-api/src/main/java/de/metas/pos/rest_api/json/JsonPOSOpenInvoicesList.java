package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.pos.invoice_settlement.POSOpenInvoice;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

/**
 * {@code GET /api/v2/pos/invoices}: list of open invoices available for cash settlement at the till.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSOpenInvoicesList
{
	@NonNull List<JsonPOSOpenInvoice> list;

	/**
	 * @param blockingCardSettlements the blocking card settlement of each invoice that has one
	 */
	public static JsonPOSOpenInvoicesList of(
			@NonNull final List<POSOpenInvoice> invoices,
			@NonNull final Map<InvoiceId, JsonPOSInvoiceCardSettlement> blockingCardSettlements)
	{
		return JsonPOSOpenInvoicesList.builder()
				.list(invoices.stream()
						.map(invoice -> JsonPOSOpenInvoice.from(invoice, blockingCardSettlements.get(invoice.getInvoiceId())))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
