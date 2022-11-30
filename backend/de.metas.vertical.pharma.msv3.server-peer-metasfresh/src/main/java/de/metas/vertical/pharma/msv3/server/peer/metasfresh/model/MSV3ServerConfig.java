package de.metas.vertical.pharma.msv3.server.peer.metasfresh.model;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductCategoryId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.groups.picking.WarehousePickingGroup;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;

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
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MSV3ServerConfig
{
	OptionalInt fixedQtyAvailableToPromise;
	@Getter(AccessLevel.NONE)
	Optional<Supplier<WarehousePickingGroup>> warehousePickingGroupSupplier;
	Set<ProductCategoryId> productCategoryIds;

	@Builder
	private MSV3ServerConfig(
			final int fixedQtyAvailableToPromise,
			final Supplier<WarehousePickingGroup> warehousePickingGroupSupplier,
			@Singular final Set<ProductCategoryId> productCategoryIds)
	{
		this.fixedQtyAvailableToPromise = fixedQtyAvailableToPromise > 0 ? OptionalInt.of(fixedQtyAvailableToPromise) : OptionalInt.empty();
		this.warehousePickingGroupSupplier = Optional.ofNullable(warehousePickingGroupSupplier);
		this.productCategoryIds = productCategoryIds != null ? ImmutableSet.copyOf(productCategoryIds) : ImmutableSet.of();
	}

	public boolean hasProducts()
	{
		return !getProductCategoryIds().isEmpty()
				&& !getWarehouseIds().isEmpty();
	}

	public Set<WarehouseId> getWarehouseIds()
	{
		return warehousePickingGroupSupplier
				.map(Supplier::get)
				.map(WarehousePickingGroup::getWarehouseIds)
				.orElse(ImmutableSet.of());
	}
}
