package de.metas.distribution.workflows_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

@Value
@Builder
public class WarehouseInfo
{
	@NonNull WarehouseId warehouseId;
	@NonNull String caption;
}
