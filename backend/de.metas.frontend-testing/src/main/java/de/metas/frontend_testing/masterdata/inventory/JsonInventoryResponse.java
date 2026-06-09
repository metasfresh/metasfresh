package de.metas.frontend_testing.masterdata.inventory;

import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonInventoryResponse
{
	@NonNull InventoryId id;
	@NonNull String documentNo;
}
