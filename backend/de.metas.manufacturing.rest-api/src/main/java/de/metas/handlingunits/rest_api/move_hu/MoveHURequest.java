package de.metas.handlingunits.rest_api.move_hu;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class MoveHURequest
{
	@NonNull HuId huId;
	@NonNull HUQRCode huQRCode;
	@Nullable QtyTU numberOfTUs;

	@NonNull ScannedCode targetQRCode;
}
