package de.metas.pos.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPOSOrdersList
{
	@NonNull List<JsonPOSOrder> list;
}
