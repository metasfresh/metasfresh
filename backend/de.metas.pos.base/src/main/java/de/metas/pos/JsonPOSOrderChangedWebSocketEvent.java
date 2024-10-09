package de.metas.pos;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPOSOrderChangedWebSocketEvent
{
	@NonNull POSOrderExternalId posOrderId;
}
