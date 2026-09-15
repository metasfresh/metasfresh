package de.metas.rest_api.v2.warehouse.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonResolveLocatorRequest
{
	@NonNull String scannedBarcode;
}
