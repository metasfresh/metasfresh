package de.metas.inventory.mobileui.launchers;

import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class InventoryJobReference
{
	@NonNull InventoryId inventoryId;
	@NonNull String documentNo;
	@NonNull LocalDate movementDate;
	@NonNull String warehouseName;
	boolean isJobStarted;
}
