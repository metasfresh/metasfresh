package de.metas.pos.invoice_settlement;

import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.pos.POSCashJournal;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Outcome of settling an open sales invoice in cash at the till: the inbound payment that settles it (already
 * allocated against the invoice), the settled amount, and the till's updated cash journal.
 */
@Value
@Builder
public class POSInvoiceSettleResult
{
	@NonNull PaymentId paymentId;
	@NonNull Money amount;
	@NonNull String documentNo;
	@NonNull POSCashJournal journal;
}
