package de.metas.distribution.mobileui.external_services.warehouse;

import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

public interface NextPickFromLocatorResolver
{
	@NonNull LocatorId resolveNext(@NonNull WarehouseId warehouseId, @NonNull LocatorId currentLocatorId);
}
