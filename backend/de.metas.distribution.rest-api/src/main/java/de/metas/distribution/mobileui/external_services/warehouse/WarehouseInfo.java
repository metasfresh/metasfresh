package de.metas.distribution.mobileui.external_services.warehouse;

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
