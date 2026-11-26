package de.metas.frontend_testing.masterdata.resource;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateResourceResponse
{
	@NonNull String name;
	@NonNull String qrCode;
}
