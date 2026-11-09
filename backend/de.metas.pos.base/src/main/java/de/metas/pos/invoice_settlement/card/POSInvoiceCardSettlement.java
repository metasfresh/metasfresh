package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.pos.POSTerminalId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class POSInvoiceCardSettlement
{
	@NonNull POSInvoiceCardSettlementId id;
	@NonNull POSTerminalId posTerminalId;
	@NonNull InvoiceId invoiceId;
	@NonNull BPartnerId bpartnerId;
	@NonNull UserId cashierId;
	@NonNull Money amount;
	@NonNull POSInvoiceCardSettlementStatus status;
	boolean isAbandoned;
	@Nullable String errorMsg;
	@Nullable PaymentId paymentId;
	@Nullable SumUpTransactionId sumUpTransactionId;
	int bookingAttempts;
	@Nullable String note;
	@NonNull Instant created;

	public boolean isBlocking()
	{
		return status.isBlockingCandidate() && !isAbandoned;
	}
}
