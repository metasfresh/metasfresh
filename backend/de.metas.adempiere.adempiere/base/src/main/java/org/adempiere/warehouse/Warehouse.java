/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.adempiere.warehouse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ResourceId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Value
public class Warehouse
{
	@NonNull WarehouseId warehouseId;
	boolean active;
	@NonNull OrgId orgId;
	@NonNull String name;
	@Nullable ResourceId plantId;
	boolean isInTransit;
	boolean isReceiveAsSourceHU;
	boolean isAutoDistributionOrder;
	@Nullable DistributionNetworkId distributionNetworkId;
	@NonNull WarehouseSourceHUConfigList warehouseSourceHUConfigs;
	@NonNull @Getter(AccessLevel.NONE) ImmutableMap<LocatorId, Locator> locatorsById;
	@NonNull ImmutableList<Locator> activeLocators;

	@Builder
	private Warehouse(
			@NonNull final WarehouseId warehouseId,
			@NonNull final Boolean active,
			@NonNull final OrgId orgId,
			@NonNull final String name,
			@Nullable final ResourceId plantId,
			final boolean isInTransit,
			final boolean isReceiveAsSourceHU,
			final boolean isAutoDistributionOrder,
			@Nullable final DistributionNetworkId distributionNetworkId,
			@NonNull final Collection<Locator> locators,
			@NonNull final WarehouseSourceHUConfigList warehouseSourceHUConfigs)
	{
		this.warehouseId = warehouseId;
		this.active = active;
		this.orgId = orgId;
		this.name = name;
		this.plantId = plantId;
		this.isInTransit = isInTransit;
		this.isReceiveAsSourceHU = isReceiveAsSourceHU;
		this.isAutoDistributionOrder = isAutoDistributionOrder;
		this.distributionNetworkId = distributionNetworkId;
		this.warehouseSourceHUConfigs = warehouseSourceHUConfigs;
		this.locatorsById = Maps.uniqueIndex(locators, Locator::getLocatorId);
		this.activeLocators = locators.stream().filter(Locator::isActive).collect(ImmutableList.toImmutableList());
	}

	public boolean isConfiguredToReceiveAsSourceHU(@NonNull final ProductCategoryId productCategoryId)
	{
		if (!isReceiveAsSourceHU) {return false;}
		return warehouseSourceHUConfigs.applies(productCategoryId);
	}

	public Collection<Locator> getLocators()
	{
		return locatorsById.values();
	}

	@NonNull
	public Locator getLocatorById(final @NonNull LocatorId locatorId)
	{
		final Locator locator = locatorsById.get(locatorId);
		if (locator == null)
		{
			throw new AdempiereException("Locator with id " + locatorId + " not found in warehouse " + getName());
		}
		return locator;
	}

	public List<LocatorId> getGroundFloorLocatorIdsOrderedByPriority()
	{
		return activeLocators.stream()
				.filter(Locator::isGroundFloor)
				.sorted(Comparator.comparing(Locator::getPriorityNo).thenComparing(Locator::getLocatorId))
				.map(Locator::getLocatorId)
				.collect(ImmutableList.toImmutableList());
	}
}
