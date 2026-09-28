package de.metas.pos.invoice_settlement;

import de.metas.invoice.InvoiceId;
import de.metas.pos.POSTerminalId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSInvoiceSettleRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull UserId cashierId;
	@NonNull InvoiceId invoiceId;
}
