package de.metas.distribution.mobileui.external_services.warehouse;

import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.lang.NonNull;

public interface NextPickFromLocatorResolver
{
	@NonNull LocatorId resolveNext(@NonNull WarehouseId warehouseId, @NonNull LocatorId currentLocatorId);
}
