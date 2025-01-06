package de.metas.handlingunits.rest_api;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonMoveHURequest
{
	@NonNull HuId huId;
	@NonNull String huQRCode;
	@Nullable QtyTU numberOfTUs;

	@NonNull String targetQRCode;
}
