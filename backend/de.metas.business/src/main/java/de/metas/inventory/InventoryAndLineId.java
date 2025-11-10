package de.metas.inventory;

import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_InventoryLine;

@Value(staticConstructor = "of")
public class InventoryAndLineId
{
	@NonNull InventoryId inventoryId;
	@NonNull InventoryLineId inventoryLineId;

	public static InventoryAndLineId ofRepoIds(final int inventoryId, final int inventoryLineId)
	{
		return of(InventoryId.ofRepoId(inventoryId), InventoryLineId.ofRepoId(inventoryLineId));
	}

	public static InventoryAndLineId of(@NonNull final I_M_InventoryLine record)
	{
		return InventoryAndLineId.ofRepoIds(record.getM_Inventory_ID(), record.getM_InventoryLine_ID());
	}
}
