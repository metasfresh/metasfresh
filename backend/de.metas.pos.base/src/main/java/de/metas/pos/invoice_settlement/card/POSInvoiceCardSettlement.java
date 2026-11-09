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
	private static final int ERRORMSG_MAX_LENGTH = 4000;

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

	/**
	 * @return the message cut to the length of the {@code ErrorMsg} column
	 */
	@Nullable
	static String truncateErrorMsg(@Nullable final String errorMsg)
	{
		return errorMsg != null && errorMsg.length() > ERRORMSG_MAX_LENGTH ? errorMsg.substring(0, ERRORMSG_MAX_LENGTH) : errorMsg;
	}
}
