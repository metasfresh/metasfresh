package de.metas.handlingunits.inventory;

import de.metas.inventory.InventoryId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Value
@Builder
public class InventoryReference
{
	@NonNull InventoryId inventoryId;
	@NonNull String documentNo;
	@NonNull ZonedDateTime movementDate;
	@NonNull WarehouseId warehouseId;
	@Nullable UserId responsibleId;

	public boolean isAssignedToResponsible() {return responsibleId != null;}

	public LocalDate getMovementDateAsLocalDate() {return movementDate.toLocalDate();}
}
