package de.metas.pos.rest_api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * {@code POST /api/v2/pos/orders/checkPayment}: the till checks a card payment whose result is unknown.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSCardPaymentCheckRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull @JsonProperty("order_uuid") POSOrderExternalId order_uuid;
	@NonNull @JsonProperty("payment_uuid") POSPaymentExternalId payment_uuid;
}
