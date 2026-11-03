package de.metas.inventory.mobileui.deps.warehouse;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

@Value
@Builder
public class WarehouseInfo
{
	@NonNull WarehouseId warehouseId;
	@NonNull String warehouseName;
}
