package de.metas.distribution.workflows_api.json;

import de.metas.distribution.workflows_api.LocatorInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonLocatorInfo
{
	@NonNull String caption;
	@NonNull String qrCode;

	public static JsonLocatorInfo of(@NonNull final LocatorInfo locatorInfo)
	{
		return builder()
				.caption(locatorInfo.getCaption())
				.qrCode(locatorInfo.getQrCode().toGlobalQRCodeJsonString())
				.build();
	}
}
