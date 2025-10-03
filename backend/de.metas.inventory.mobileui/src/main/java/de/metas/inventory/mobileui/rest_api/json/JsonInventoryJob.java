package de.metas.inventory.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.mobileui.job.InventoryJobId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonInventoryJob
{
	@NonNull InventoryJobId id;
	@NonNull ImmutableList<JsonInventoryJobLine> lines;
}
