package de.metas.pos.websocket.json;

import de.metas.pos.POSOrderExternalId;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPOSOrderChangedWebSocketEvent
{
	@NonNull JsonPOSOrder posOrder;
}
