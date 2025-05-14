package de.metas.frontend_testing.masterdata.hu;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateHUResponse
{
	@NonNull String huId;
	@NonNull String qrCode;
}
