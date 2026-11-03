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
	int id;
	@NonNull String caption;
	@NonNull String qrCode;

	public static JsonLocatorInfo of(@NonNull final LocatorInfo locatorInfo)
	{
		return builder()
				.id(locatorInfo.getLocatorId().getRepoId())
				.caption(locatorInfo.getCaption())
				.qrCode(locatorInfo.getQrCode().toGlobalQRCodeJsonString())
				.build();
	}
}
