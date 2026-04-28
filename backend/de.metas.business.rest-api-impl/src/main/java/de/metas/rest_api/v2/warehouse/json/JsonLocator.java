package de.metas.rest_api.v2.warehouse.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonLocator
{
	@NonNull String caption;
	@NonNull String qrCode;
}
