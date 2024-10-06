package de.metas.pos;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSOrderChangedWebSocketEvent
{
	@NonNull POSOrderExternalId posOrderId;
}
