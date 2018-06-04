package de.metas.vertical.pharma.msv3.server.peer.metasfresh.model;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.adempiere.util.Check;
import org.adempiere.warehouse.model.WarehousePickingGroup;

import com.google.common.collect.ImmutableSet;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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

@Value
public class MSV3ServerConfig
{
	private final int qtyAvailableToPromiseMin;
	@Getter(AccessLevel.NONE)
	private final Optional<Supplier<WarehousePickingGroup>> warehousePickingGroupSupplier;
	private final Set<Integer> productCategoryIds;

	@Builder
	private MSV3ServerConfig(
			final int qtyAvailableToPromiseMin,
			final Supplier<WarehousePickingGroup> warehousePickingGroupSupplier,
			@Singular final Set<Integer> productCategoryIds)
	{
		Check.assume(qtyAvailableToPromiseMin >= 0, "qtyAvailableToPromiseMin >= 0 but it was {}", qtyAvailableToPromiseMin);

		this.qtyAvailableToPromiseMin = qtyAvailableToPromiseMin;
		this.warehousePickingGroupSupplier = Optional.ofNullable(warehousePickingGroupSupplier);
		this.productCategoryIds = productCategoryIds != null ? ImmutableSet.copyOf(productCategoryIds) : ImmutableSet.of();
	}

	public boolean hasProducts()
	{
		return !getProductCategoryIds().isEmpty()
				&& !getWarehouseIds().isEmpty();
	}

	public Set<Integer> getWarehouseIds()
	{
		return warehousePickingGroupSupplier
				.map(Supplier::get)
				.map(WarehousePickingGroup::getWarehouseIds)
				.orElse(ImmutableSet.of());
	}
}
