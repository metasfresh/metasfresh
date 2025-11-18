package de.metas.handlingunits.picking.job.service.external.warehouse;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.springframework.stereotype.Service;

@Service
public class PickingJobWarehouseService
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	public String getLocatorNameById(final LocatorId locatorId)
	{
		return warehouseBL.getLocatorById(locatorId).getValue();
	}

	public ImmutableSet<LocatorId> getLocatorIdsOfTheSamePickingGroup(@NonNull final WarehouseId warehouseId)
	{
		return warehouseBL.getLocatorIdsOfTheSamePickingGroup(warehouseId);
	}

}
