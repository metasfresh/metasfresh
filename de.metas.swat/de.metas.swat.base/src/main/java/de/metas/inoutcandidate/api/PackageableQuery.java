package de.metas.inoutcandidate.api;

import java.time.LocalDate;

import org.adempiere.warehouse.WarehouseId;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PackageableQuery
{
	@NonNull
	private WarehouseId warehouseId;
	private LocalDate deliveryDate;
}
