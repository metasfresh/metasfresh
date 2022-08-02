package de.metas.handlingunits.rest_api.move_hu;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MoveHURequest
{
	@NonNull HuId huId;
	@NonNull HUQRCode huQRCode;

	@NonNull GlobalQRCode targetQRCode;
}
