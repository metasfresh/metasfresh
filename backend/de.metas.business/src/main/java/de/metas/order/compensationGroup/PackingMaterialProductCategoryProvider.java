package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.product.ProductCategoryId;
import lombok.NonNull;

import java.util.Set;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Resolves, per packing instruction ({@code M_HU_PI_Item_Product}), the product categories (including all ancestors)
 * of the products of its packing materials. Implemented in the handling-unit module.
 */
public interface PackingMaterialProductCategoryProvider
{
	/** Used when no implementation is available: no packing instruction has any category. */
	PackingMaterialProductCategoryProvider NONE = new PackingMaterialProductCategoryProvider()
	{
		@Override
		public @NonNull ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> getPackingMaterialProductCategoryIdsAndAncestors(@NonNull final Set<HUPIItemProductId> ids)
		{
			return ImmutableMap.of();
		}
	};

	/**
	 * @return for each given regular packing instruction the union of the categories (and their ancestors) of its active packing materials' products;
	 * ids without any such category (virtual, unknown, no packing material product) have no entry.
	 */
	@NonNull ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> getPackingMaterialProductCategoryIdsAndAncestors(@NonNull Set<HUPIItemProductId> ids);
}
