package de.metas.distribution.service.external;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.springframework.stereotype.Service;

@Service
public class DistributionWarehouseService
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		return warehouseBL.getWarehouseName(warehouseId);
	}

	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return warehouseBL.getLocatorNameById(locatorId);
	}

}
