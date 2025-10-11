package de.metas.handlingunits.rest_api;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.scannable_code.ScannedCode;
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
	@Nullable ScannedCode huQRCode;
	@Nullable QtyTU numberOfTUs;

	@NonNull ScannedCode targetQRCode;
}
