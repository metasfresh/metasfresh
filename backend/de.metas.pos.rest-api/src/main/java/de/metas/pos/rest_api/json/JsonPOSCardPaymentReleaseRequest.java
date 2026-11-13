package de.metas.pos.rest_api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * {@code POST /api/v2/pos/orders/releasePayment}: first without confirmation; if the response says
 * {@code confirmationRequired}, again with {@code confirmNotCharged} and the status the confirmation prompt showed.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSCardPaymentReleaseRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull @JsonProperty("order_uuid") POSOrderExternalId order_uuid;
	@NonNull @JsonProperty("payment_uuid") POSPaymentExternalId payment_uuid;

	boolean confirmNotCharged;

	/** The status the confirmation prompt showed; required if {@link #confirmNotCharged}. */
	@Nullable JsonPOSPaymentStatus expectedStatus;
}
