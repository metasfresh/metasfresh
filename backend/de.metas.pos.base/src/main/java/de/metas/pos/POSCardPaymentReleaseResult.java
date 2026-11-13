package de.metas.pos;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Result of {@link POSOrdersService#releaseCardPayment}.
 */
@Value
@Builder
public class POSCardPaymentReleaseResult
{
	/**
	 * The order as it is after the request.
	 */
	@NonNull POSOrder order;

	/**
	 * Whether the payment is still unknown after the re-check, so the cashier must confirm that the card was not charged.
	 */
	boolean confirmationRequired;

	/**
	 * Whether SumUp could not be asked for the current status during the re-check.
	 */
	boolean checkFailed;

	/**
	 * Why the release was refused (nothing was changed), to be shown to the cashier; null if not refused.
	 */
	@Nullable ITranslatableString refusedMessage;

	/**
	 * The AD_Message of {@link #refusedMessage}, for callers that react to the reason; null if not refused.
	 */
	@Nullable AdMessageKey refusedMessageKey;

	public boolean isRefused() {return refusedMessageKey != null;}
}
