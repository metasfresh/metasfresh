package de.metas.material.cockpit.stock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.warehouse.WarehouseId;

import com.google.common.collect.ImmutableSet;

import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-cockpit
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
public class StockDataAggregateQuery
{
	ImmutableSet<ProductCategoryId> productCategoryIds;
	Set<WarehouseId> warehouseIds;
	ImmutableSet<StockDataQueryOrderBy> orderBys;

	@Builder
	private StockDataAggregateQuery(
			@NonNull @Singular final ImmutableSet<ProductCategoryId> productCategoryIds,
			@NonNull @Singular final Set<WarehouseId> warehouseIds,
			@NonNull @Singular final ImmutableSet<StockDataQueryOrderBy> orderBys)
	{
		this.productCategoryIds = productCategoryIds;
		this.warehouseIds = Collections.unmodifiableSet(new HashSet<>(warehouseIds)); // we shall accept warehouseId=null
		this.orderBys = orderBys;
	}
}
