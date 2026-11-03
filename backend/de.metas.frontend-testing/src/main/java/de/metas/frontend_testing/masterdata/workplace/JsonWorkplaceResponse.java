package de.metas.frontend_testing.masterdata.workplace;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonWorkplaceResponse
{
	@NonNull String name;
	@NonNull String qrCode;
}
