package de.metas.inventory.mobileui.job.repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

class Warehouses
{
	private final IWarehouseDAO dao = Services.get(IWarehouseDAO.class);

	private final HashMap<WarehouseId, WarehouseInfo> byId = new HashMap<>();

	public <T> void warnUp(final Collection<T> objects, Function<T, WarehouseId> idMapper)
	{
		final ImmutableSet<WarehouseId> ids = objects.stream().map(idMapper).collect(ImmutableSet.toImmutableSet());
		getByIds(ids);
	}

	public WarehouseInfo getById(@NonNull final WarehouseId id)
	{
		return CollectionUtils.singleElement(getByIds(ImmutableSet.of(id)));
	}

	private Collection<WarehouseInfo> getByIds(final Set<WarehouseId> id)
	{
		return CollectionUtils.getAllOrLoad(byId, id, this::retrieveByIds);
	}

	private Map<WarehouseId, WarehouseInfo> retrieveByIds(final Set<WarehouseId> ids)
	{
		return dao.getByIds(ids)
				.stream()
				.map(Warehouses::fromRecord)
				.collect(ImmutableMap.toImmutableMap(WarehouseInfo::getWarehouseId, Function.identity()));
	}

	private static WarehouseInfo fromRecord(final I_M_Warehouse record)
	{
		return WarehouseInfo.builder()
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.warehouseName(record.getName())
				.build();
	}

}
