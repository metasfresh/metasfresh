package de.metas.pos.invoice_settlement;

import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * One open sales invoice offered for cash settlement at the till: completed or closed, not yet fully paid, and not
 * a credit memo.
 */
@Value
@Builder
public class POSOpenInvoice
{
	@NonNull InvoiceId invoiceId;
	@NonNull String documentNo;
	@NonNull String bpartnerName;
	@NonNull LocalDate dateInvoiced;
	@NonNull Money grandTotal;

	/** Open amount, in the invoice's own currency. */
	@NonNull Money openAmt;

	/** The unfinished card settlement of this invoice (it blocks any further payment), if any. */
	@Nullable POSInvoiceCardSettlement blockingCardSettlement;
}
