package org.adempiere.warehouse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

final class WarehouseMap
{
	private final ImmutableList<Warehouse> allActive;
	private final ImmutableMap<WarehouseId, Warehouse> byId;
	private final ImmutableMap<Integer, Locator> locatorsByRepoId;

	WarehouseMap(final List<Warehouse> list)
	{
		this.allActive = list.stream().filter(Warehouse::isActive).collect(ImmutableList.toImmutableList());
		this.byId = Maps.uniqueIndex(list, Warehouse::getWarehouseId);
		this.locatorsByRepoId = list.stream()
				.flatMap(warehouse -> warehouse.getLocators().stream())
				.collect(ImmutableMap.toImmutableMap(
						locator -> locator.getLocatorId().getRepoId(),
						locator -> locator
				));
	}

	@NonNull
	public Warehouse getById(@NonNull final WarehouseId id)
	{
		final Warehouse warehouse = getByIdOrNull(id);
		if (warehouse == null)
		{
			throw new AdempiereException("Warehouse not found by ID: " + id);
		}
		return warehouse;
	}

	@Nullable
	private Warehouse getByIdOrNull(@NonNull final WarehouseId id)
	{
		return byId.get(id);
	}

	@NonNull
	public ImmutableSet<WarehouseId> getAllActiveIds()
	{
		return allActive.stream()
				.map(Warehouse::getWarehouseId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		final Warehouse warehouse = getByIdOrNull(warehouseId);
		return warehouse != null ? warehouse.getName() : "<" + warehouseId.getRepoId() + ">";
	}

	@NonNull
	public Locator getLocatorById(@NonNull final LocatorId locatorId)
	{
		return getById(locatorId.getWarehouseId()).getLocatorById(locatorId);
	}

	public Locator getLocatorByRepoId(final int locatorRepoId)
	{
		final Locator locator = locatorsByRepoId.get(locatorRepoId);
		if (locator == null)
		{
			throw new AdempiereException("Locator not found by ID: " + locatorRepoId);
		}
		return locator;
	}

	public Optional<WarehouseId> getInTransitWarehouseIdIfExists(final @NonNull OrgId orgId)
	{
		return allActive.stream()
				.filter(warehouse -> warehouse.isInTransit() && OrgId.equals(warehouse.getOrgId(), orgId))
				// deterministic tie-break (lowest WarehouseId) for the rare org with more than one in-transit warehouse
				.sorted(Comparator.comparing(Warehouse::getWarehouseId))
				.map(Warehouse::getWarehouseId)
				.findFirst();
	}
}
