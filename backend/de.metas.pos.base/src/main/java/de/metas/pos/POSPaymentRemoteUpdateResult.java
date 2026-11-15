package de.metas.pos;

import lombok.NonNull;
import lombok.Value;

/**
 * Outcome of {@link POSPayment#changingStatusFromRemote}.
 */
@Value(staticConstructor = "of")
public class POSPaymentRemoteUpdateResult
{
	@NonNull POSPayment payment;

	/** true if SumUp approved a payment that is deleted or released: it stays as it is and is not booked, so someone has to review it */
	boolean lateApprovalNotBooked;
}
