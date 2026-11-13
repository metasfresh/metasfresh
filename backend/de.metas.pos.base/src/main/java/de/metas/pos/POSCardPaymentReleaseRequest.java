package de.metas.pos;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Request of {@link POSOrdersService#releaseCardPayment}: the cashier releases a card payment whose result is unknown.
 */
@Value
@Builder
public class POSCardPaymentReleaseRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull POSOrderExternalId posOrderExternalId;
	@NonNull POSPaymentExternalId posPaymentExternalId;
	@NonNull UserId cashierId;

	/**
	 * Whether the cashier confirmed that the card was not charged (the second step).
	 */
	boolean confirmNotCharged;

	/**
	 * The status the confirmation prompt showed; required if {@link #confirmNotCharged}. The payment is released only
	 * if its status is still this one.
	 */
	@Nullable POSPaymentProcessingStatus expectedStatus;
}
