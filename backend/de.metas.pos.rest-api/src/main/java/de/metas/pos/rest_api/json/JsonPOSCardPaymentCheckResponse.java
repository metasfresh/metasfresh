package de.metas.pos.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Outcome of a card payment check: the order as it is now, and whether SumUp could not be asked (the order is then shown
 * as last known), as the invoice card settlement's check reports it.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSCardPaymentCheckResponse
{
	@NonNull JsonPOSOrder order;
	boolean checkFailed;
}
