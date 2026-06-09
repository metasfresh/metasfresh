package de.metas.inventory.mobileui.deps.warehouse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@Builder(access = AccessLevel.PACKAGE)
public class WarehousesLoadingCache
{
	@NonNull private final IWarehouseDAO dao;

	private final HashMap<WarehouseId, WarehouseInfo> warehousesByWarehouseId = new HashMap<>();
	private final HashMap<WarehouseId, WarehouseLocatorsInfo> locatorsByWarehouseId = new HashMap<>();

	public <T> void warnUp(@NonNull final Collection<T> objects, Function<T, WarehouseId> idMapper)
	{
		if (objects.isEmpty()) {return;}
		
		final ImmutableSet<WarehouseId> ids = objects.stream().map(idMapper).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
		getByIds(ids);
	}

	public WarehouseInfo getById(@NonNull final WarehouseId id)
	{
		return CollectionUtils.singleElement(getByIds(ImmutableSet.of(id)));
	}

	private Collection<WarehouseInfo> getByIds(final Set<WarehouseId> id)
	{
		return CollectionUtils.getAllOrLoad(warehousesByWarehouseId, id, this::retrieveByIds);
	}

	private ImmutableMap<WarehouseId, WarehouseInfo> retrieveByIds(final Set<WarehouseId> ids)
	{
		if (ids.isEmpty()) {return ImmutableMap.of();}

		return dao.getByIds(ids)
				.stream()
				.map(WarehousesLoadingCache::fromRecord)
				.collect(ImmutableMap.toImmutableMap(WarehouseInfo::getWarehouseId, Function.identity()));
	}

	private static WarehouseInfo fromRecord(final I_M_Warehouse record)
	{
		return WarehouseInfo.builder()
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.warehouseName(record.getName())
				.build();
	}

	public String getLocatorName(final LocatorId locatorId)
	{
		return getWarehouseLocators(locatorId.getWarehouseId()).getLocatorName(locatorId);
	}

	private WarehouseLocatorsInfo getWarehouseLocators(@NonNull final WarehouseId warehouseId)
	{
		return locatorsByWarehouseId.computeIfAbsent(warehouseId, this::retrieveWarehouseLocators);
	}

	private WarehouseLocatorsInfo retrieveWarehouseLocators(@NonNull final WarehouseId warehouseId)
	{
		return WarehouseLocatorsInfo.builder()
				.warehouseId(warehouseId)
				.locators(dao.getLocators(warehouseId)
						.stream()
						.map(WarehousesLoadingCache::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();

	}

	private static LocatorInfo fromRecord(final I_M_Locator record)
	{
		return LocatorInfo.builder()
				.locatorId(LocatorId.ofRepoId(record.getM_Warehouse_ID(), record.getM_Locator_ID()))
				.locatorName(record.getValue())
				.build();
	}

}
