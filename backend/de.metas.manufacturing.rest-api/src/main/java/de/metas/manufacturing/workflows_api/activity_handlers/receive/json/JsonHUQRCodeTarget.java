package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.qrcodes.model.json.JsonRenderedHUQRCode;
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
	@NonNull JsonRenderedHUQRCode huQRCode;
	@Nullable HUPIItemProductId tuPIItemProductId;
}
