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
	@NonNull String barcode;
	@NonNull String caption;

	public static JsonLocatorInfo of(@NonNull final LocatorInfo locatorInfo)
	{
		return builder()
				.barcode(locatorInfo.getBarcode().getAsString())
				.caption(locatorInfo.getCaption())
				.build();
	}
}
