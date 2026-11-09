package de.metas.pos.rest_api.json;

import de.metas.invoice.InvoiceId;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * {@code POST /api/v2/pos/invoices/cardSettlements}: pay an open sales invoice's full open amount by card.
 * The cashier is taken from the logged-in session, never from the request body.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSInvoiceCardSettlementStartRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull InvoiceId invoiceId;
}
