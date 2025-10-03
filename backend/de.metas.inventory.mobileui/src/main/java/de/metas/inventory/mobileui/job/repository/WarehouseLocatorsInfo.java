package de.metas.inventory.mobileui.job.repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import java.util.List;

@Value
public class WarehouseLocatorsInfo
{
	@NonNull WarehouseId warehouseId;
	@NonNull ImmutableMap<LocatorId, LocatorInfo> locatorsById;

	@Builder
	private WarehouseLocatorsInfo(
			@NonNull WarehouseId warehouseId,
			@NonNull List<LocatorInfo> locators)
	{
		this.warehouseId = warehouseId;
		this.locatorsById = Maps.uniqueIndex(locators, LocatorInfo::getLocatorId);
	}

	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		final LocatorInfo locatorInfo = locatorsById.get(locatorId);
		return locatorInfo != null
				? locatorInfo.getLocatorName()
				: "<" + locatorId.getRepoId() + ">";
	}
}
