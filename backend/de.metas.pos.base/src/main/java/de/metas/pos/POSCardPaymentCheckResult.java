package de.metas.pos;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Result of {@link POSOrdersService#checkCardPayment}.
 */
@Value
@Builder
public class POSCardPaymentCheckResult
{
	/**
	 * The order as it is after the check.
	 */
	@NonNull POSOrder order;

	/**
	 * Whether SumUp could not be asked for the current status of the card payment's transaction.
	 */
	boolean checkFailed;
}
