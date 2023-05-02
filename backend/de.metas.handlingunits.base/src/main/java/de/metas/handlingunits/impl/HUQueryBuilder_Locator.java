package de.metas.handlingunits.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Locator;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link HUQueryBuilder} Warehouse/Locator specific filtering
 */
@EqualsAndHashCode
@ToString
final class HUQueryBuilder_Locator
{
	private final Set<WarehouseId> onlyInWarehouseIds = new HashSet<>();
	private boolean notInAnyWarehouse = false;

	private final Set<Integer> onlyInLocatorIds = new HashSet<>();

	private boolean excludeAfterPickingLocator = false;
	/**
	 * Flag to set determine if the query shall only retrieve the HUs from AfterPicking locators or not
	 * 
	 * @implNote Task 08544
	 */
	private boolean includeAfterPickingLocator = false;

	public HUQueryBuilder_Locator copy()
	{
		final HUQueryBuilder_Locator copy = new HUQueryBuilder_Locator();
		copy.onlyInWarehouseIds.addAll(this.onlyInWarehouseIds);
		copy.notInAnyWarehouse = this.notInAnyWarehouse;
		copy.onlyInLocatorIds.addAll(this.onlyInLocatorIds);
		copy.excludeAfterPickingLocator = this.excludeAfterPickingLocator;
		copy.includeAfterPickingLocator = this.includeAfterPickingLocator;
		return copy;
	}

	public ICompositeQueryFilter<I_M_HU> createQueryFilter()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);

		//
		// Filter by Warehouses
		if (!onlyInWarehouseIds.isEmpty()
				|| excludeAfterPickingLocator
				|| includeAfterPickingLocator)
		{
			final IQueryBuilder<I_M_Locator> locatorsQueryBuilder = queryBL.createQueryBuilder(I_M_Locator.class);

			if (!onlyInWarehouseIds.isEmpty())
			{
				locatorsQueryBuilder.addInArrayOrAllFilter(I_M_Locator.COLUMN_M_Warehouse_ID, onlyInWarehouseIds);
			}
			// Make sure _includeAfterPickingLocator and _excludeAfterPickingLocator are not both selected
			Check.assume(!(includeAfterPickingLocator && excludeAfterPickingLocator), "Cannot both include and exclude AfterPickingLocator");

			if (excludeAfterPickingLocator)
			{
				locatorsQueryBuilder.addEqualsFilter(de.metas.handlingunits.model.I_M_Locator.COLUMNNAME_IsAfterPickingLocator, false);
			}
			if (includeAfterPickingLocator)
			{
				locatorsQueryBuilder.addEqualsFilter(de.metas.handlingunits.model.I_M_Locator.COLUMNNAME_IsAfterPickingLocator, true);
			}

			final IQuery<I_M_Locator> locatorsQuery = locatorsQueryBuilder.create();
			filters.addInSubQueryFilter(I_M_HU.COLUMNNAME_M_Locator_ID, I_M_Locator.COLUMNNAME_M_Locator_ID, locatorsQuery);
		}

		if (notInAnyWarehouse)
		{
			filters.addEqualsFilter(I_M_HU.COLUMNNAME_M_Locator_ID, null);
		}

		//
		// Filter by Locators
		if (!onlyInLocatorIds.isEmpty())
		{
			filters.addInArrayFilter(I_M_HU.COLUMNNAME_M_Locator_ID, onlyInLocatorIds);
		}

		return filters;
	}

	public void addOnlyInWarehouseIds(final Collection<WarehouseId> warehouseIds)
	{
		if (warehouseIds != null && !warehouseIds.isEmpty())
		{
			onlyInWarehouseIds.addAll(warehouseIds);
		}

		updateNotInAnyWarehouseFlag();
	}

	public Set<WarehouseId> getOnlyInWarehouseIds()
	{
		return ImmutableSet.copyOf(onlyInWarehouseIds);
	}

	private void updateNotInAnyWarehouseFlag()
	{
		notInAnyWarehouse = onlyInWarehouseIds.isEmpty();
	}

	public void addOnlyInLocatorRepoId(final int locatorId)
	{
		Check.assumeGreaterThanZero(locatorId, "locatorId");
		onlyInLocatorIds.add(locatorId);
	}

	public void addOnlyInLocatorId(@NonNull final LocatorId locatorId)
	{
		onlyInLocatorIds.add(locatorId.getRepoId());
	}

	public void addOnlyInLocatorRepoIds(final Collection<Integer> locatorIds)
	{
		if (locatorIds != null && !locatorIds.isEmpty())
		{
			locatorIds.forEach(this::addOnlyInLocatorRepoId);
		}
	}

	public void addOnlyInLocatorIds(final Collection<LocatorId> locatorIds)
	{
		if (locatorIds != null && !locatorIds.isEmpty())
		{
			locatorIds.forEach(this::addOnlyInLocatorId);
		}
	}

	public void setExcludeAfterPickingLocator(final boolean excludeAfterPickingLocator)
	{
		this.excludeAfterPickingLocator = excludeAfterPickingLocator;
	}

	public void setIncludeAfterPickingLocator(final boolean includeAfterPickingLocator)
	{
		this.includeAfterPickingLocator = includeAfterPickingLocator;
	}
}
