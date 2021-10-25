package de.metas.distribution.workflows_api.json;

import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonHUInfo
{
	String barcode;
	String caption;

	public static JsonHUInfo of(@NonNull final HuId huId)
	{
		return builder()
				.barcode(HUBarcode.ofHuId(huId).getAsString())
				.caption(String.valueOf(huId.getRepoId()))
				.build();
	}
}
