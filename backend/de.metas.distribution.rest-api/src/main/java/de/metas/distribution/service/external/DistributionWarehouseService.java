package de.metas.distribution.service.external;

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

@Service
@RequiredArgsConstructor
public class DistributionWarehouseService
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final WorkplaceService workplaceService;

	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		return warehouseBL.getWarehouseName(warehouseId);
	}

	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return warehouseBL.getLocatorNameById(locatorId);
	}

	@NonNull
	public Optional<Workplace> getWorkplaceByUserId(@NonNull final UserId userId)
	{
		return workplaceService.getWorkplaceByUserId(userId);
	}

}
