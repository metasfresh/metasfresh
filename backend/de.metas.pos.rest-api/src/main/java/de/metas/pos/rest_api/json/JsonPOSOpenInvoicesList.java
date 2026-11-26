package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.invoice_settlement.POSOpenInvoice;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * {@code GET /api/v2/pos/invoices}: list of open invoices available for cash settlement at the till.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSOpenInvoicesList
{
	@NonNull List<JsonPOSOpenInvoice> list;

	public static JsonPOSOpenInvoicesList of(@NonNull final List<POSOpenInvoice> invoices)
	{
		return JsonPOSOpenInvoicesList.builder()
				.list(invoices.stream()
						.map(JsonPOSOpenInvoice::from)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
