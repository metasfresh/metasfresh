package de.metas.distribution.mobileui.rest_api.json;

import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
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
