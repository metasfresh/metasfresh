package de.metas.workplace;

import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class WorkplaceCreateRequest
{
	@NonNull String name;
	@NonNull WarehouseId warehouseId;
	@Nullable PickingSlotId pickingSlotId;
}
