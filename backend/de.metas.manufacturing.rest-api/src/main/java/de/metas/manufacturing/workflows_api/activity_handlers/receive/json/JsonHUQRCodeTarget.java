package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.HUPIItemProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonHUQRCodeTarget
{
	@NonNull JsonDisplayableQRCode huQRCode;
	@Nullable HUPIItemProductId tuPIItemProductId;
}
