package de.metas.gplr.source.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceWarehouseInfo
{
	@NonNull WarehouseId warehouseId;
	@NonNull String warehouseCode;
	@NonNull String warehouseName;
	@Nullable String warehouseExternalId;
}
