package de.metas.pos.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Outcome of a card payment release request: the order as it is now, whether the cashier must confirm that the card was
 * not charged, whether SumUp could not be asked, and why the release was refused (translated for the logged-in user), if it was.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSCardPaymentReleaseResponse
{
	@NonNull JsonPOSOrder order;
	boolean confirmationRequired;
	boolean checkFailed;
	@Nullable String refusedMessage;
}
