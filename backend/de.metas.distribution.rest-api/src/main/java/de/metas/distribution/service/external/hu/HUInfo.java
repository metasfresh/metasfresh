package de.metas.distribution.service.external.hu;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HUInfo
{
	@NonNull HuId id;
	@NonNull HUQRCode qrCode;
}
