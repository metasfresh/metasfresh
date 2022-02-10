package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.qrcodes.model.json.JsonRenderedHUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonAggregateToExistingLU
{
	@NonNull JsonRenderedHUQRCode huQRCode;
	@NonNull HUPIItemProductId tuPIItemProductId;
}
