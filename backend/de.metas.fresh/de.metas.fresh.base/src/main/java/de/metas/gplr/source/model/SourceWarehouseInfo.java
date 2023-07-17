package de.metas.gplr.source.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

@Value
@Builder
public class SourceWarehouseInfo
{
	@NonNull WarehouseId warehouseId;
	@NonNull String warehouseCode;
	@NonNull String warehouseName;
}
