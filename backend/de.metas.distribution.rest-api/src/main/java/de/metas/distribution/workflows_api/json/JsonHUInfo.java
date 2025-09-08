package de.metas.distribution.workflows_api.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonHUInfo
{
	@NonNull JsonDisplayableQRCode qrCode;

	public static JsonHUInfo of(@NonNull final JsonDisplayableQRCode qrCode)
	{
		return builder().qrCode(qrCode).build();
	}
}
