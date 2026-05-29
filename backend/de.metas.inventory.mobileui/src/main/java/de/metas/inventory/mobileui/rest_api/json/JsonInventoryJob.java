package de.metas.inventory.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonInventoryJob
{
	@NonNull InventoryId id;
	@NonNull String documentNo;
	@NonNull String movementDate;
	@NonNull String warehouseName;
	@NonNull ImmutableList<JsonInventoryJobLine> lines;
	@NonNull JsonCountStatus countStatus;
}
