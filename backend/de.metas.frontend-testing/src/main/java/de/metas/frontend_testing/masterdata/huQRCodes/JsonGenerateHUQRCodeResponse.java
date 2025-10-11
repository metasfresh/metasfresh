package de.metas.frontend_testing.masterdata.huQRCodes;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonGenerateHUQRCodeResponse
{
	@NonNull String qrCode;
}
