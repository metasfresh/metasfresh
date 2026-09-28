package de.metas.pos.rest_api.json;

import de.metas.invoice.InvoiceId;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

/**
 * {@code POST /api/v2/pos/invoices/settle}: settle an open sales invoice in cash at the till.
 * The cashier is taken from the logged-in session, never from the request body
 * (mirrors every other POS REST endpoint).
 */
@Value
@Builder
@Jacksonized
public class JsonPOSInvoiceSettleRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull InvoiceId invoiceId;
	@NonNull String documentNo;
	@NonNull BigDecimal cashTenderedAmount;
}
