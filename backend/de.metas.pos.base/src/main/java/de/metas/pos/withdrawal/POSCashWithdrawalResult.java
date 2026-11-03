package de.metas.pos.withdrawal;

import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.pos.POSCashJournal;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

/**
 * Outcome of a POS cash withdrawal: the outbound payment that books it, plus the data printed on the withdrawal slip.
 */
@Value
@Builder
public class POSCashWithdrawalResult
{
	@NonNull PaymentId paymentId;
	@NonNull String documentNo;
	@NonNull String chargeName;
	@NonNull Money amount;
	@NonNull Instant dateTrx;
	@NonNull String cashierName;
	@NonNull String terminalName;
	@NonNull POSCashJournal journal;
}
