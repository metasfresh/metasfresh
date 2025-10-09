package de.metas.handlingunits.inventory.draftlinescreator;

import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DraftInventoryLinesCreateResponse
{
	@NonNull InventoryId inventoryId;
	long countInventoryLines;
}
