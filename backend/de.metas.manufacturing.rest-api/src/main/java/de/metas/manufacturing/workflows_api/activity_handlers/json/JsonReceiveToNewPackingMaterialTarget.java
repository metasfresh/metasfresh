package de.metas.manufacturing.workflows_api.activity_handlers.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class JsonReceiveToNewPackingMaterialTarget
{
	@NonNull String id;
	@NonNull String caption;
}
