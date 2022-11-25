package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonNewLUTarget
{
	@NonNull String luCaption;
	@Nullable String tuCaption;
	@NonNull HuPackingInstructionsItemId luPIItemId;
	@NonNull HUPIItemProductId tuPIItemProductId;
}
