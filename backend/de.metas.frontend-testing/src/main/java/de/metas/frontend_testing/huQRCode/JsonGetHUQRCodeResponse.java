package de.metas.frontend_testing.huQRCode;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonGetHUQRCodeResponse
{
	@NonNull String qrCode;
}
