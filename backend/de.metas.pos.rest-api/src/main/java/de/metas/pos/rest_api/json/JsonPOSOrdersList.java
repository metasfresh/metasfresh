package de.metas.pos.rest_api.json;

import de.metas.pos.POSOrderExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonPOSOrdersList
{
	@NonNull List<JsonPOSOrder> list;
	@NonNull Set<POSOrderExternalId> missingIds;
}
