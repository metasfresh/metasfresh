package de.metas.handlingunits.picking.job.service.external.warehouse;

import com.google.common.collect.ImmutableSet;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingJobWarehouseService
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final WorkplaceService workplaceService;

	public String getLocatorNameById(final LocatorId locatorId)
	{
		return warehouseBL.getLocatorById(locatorId).getValue();
	}

	public ImmutableSet<LocatorId> getLocatorIdsOfTheSamePickingGroup(@NonNull final WarehouseId warehouseId)
	{
		return warehouseBL.getLocatorIdsOfTheSamePickingGroup(warehouseId);
	}

	public Optional<Workplace> getWorkplaceByUserId(@NonNull final UserId userId)
	{
		return workplaceService.getWorkplaceByUserId(userId);
	}

	public Optional<WarehouseId> getWarehouseIdByUserId(@NonNull final UserId userId)
	{
		return workplaceService.getWarehouseIdByUserId(userId);
	}

	public Set<LocatorId> getPickFromLocatorIds(final Workplace workplace)
	{
		return workplaceService.getPickFromLocatorIds(workplace);
	}

}
