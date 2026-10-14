package de.metas.pos.invoice_settlement;

import de.metas.invoice.InvoiceId;
import de.metas.pos.POSTerminalId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class POSInvoiceSettleRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull UserId cashierId;
	@NonNull InvoiceId invoiceId;

	/**
	 * Amount of cash the customer handed over. {@code null} settles exactly the open amount, with no tender check
	 * (e.g. the cucumber happy-path settlement). When given, it is validated to be &gt;= the open amount BEFORE the
	 * payment is created, so a rejected tender never leaves an orphaned committed payment/cash-journal line behind.
	 */
	@Nullable BigDecimal cashTenderedAmount;
}
