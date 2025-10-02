package de.metas.inventory.mobileui.job.repository;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

@Value
@Builder
class WarehouseInfo
{
	@NonNull WarehouseId warehouseId;
	@NonNull String warehouseName;
}
